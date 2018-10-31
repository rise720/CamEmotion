package com.cac.CamEmotion.service.impl;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cac.CamEmotion.dao.SequenceTblMapper;
import com.cac.CamEmotion.exception.SysException;
import com.cac.CamEmotion.model.SequenceTbl;
import com.cac.CamEmotion.service.IdService;
import com.cac.CamEmotion.util.CharacterUtil;

/**
 * 
 * 
 * 类说明: 序号生成服务实现类
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年12月20日
 */
@Service
public class IdServiceImpl implements IdService {
	private static Logger logger = LogManager.getLogger(IdServiceImpl.class);
	// 序号缓存池，包含模板
	private static Map<String, IdWork> idMap = new TreeMap<String, IdWork>();
	//锁记录表操作服务
	@Resource
	private SequenceTblMapper sequenceTblMapper;
	
	//Spring 的ApplicationContext
	@Resource
	private ApplicationContext context;  
	
	// 同步锁
	Lock lock = new ReentrantLock();
	
	// 表示代理类
	private IdService proxySelf;
	
	/**
	 * 
	 * 
	 * 方法说明: 
	 * <P>
	 *     由spring 容器在初始对应是自动注入本类的spring aop代理类，用户本例调用系统的方法，会开启aop事务处理
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月10日
	 */
	@PostConstruct 
    private void setSelf() {  
        //从上下文获取代理对象（如果通过proxtSelf=this是不对的，this是目标对象）  
        this.proxySelf = context.getBean(IdService.class);   
    }  
	
	
	
	/**
	 * 
	 * 
	 * 方法说明: 取序号
	 * <P>
	 *     使用ReentrantLock技术，更新数据库表与内在对象值时进行同步锁控制，不同key的取值过程不产生同步控制，相同key进行方法级同步控制
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param keyId
	 * @param count
	 * @return
	 */
	@Override
	public String getSerialNumber(String keyId) {
		return getSerialNumber(keyId, 0);
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 取序号
	 * <P>
	 *     使用ReentrantLock技术，更新数据库表与内在对象值时进行同步锁控制，不同key的取值过程不产生同步控制，相同key进行方法级同步控制
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param keyId 队列名
	 * @param count 当前是重试的次数
	 * @return
	 */
	private String getSerialNumber(String keyId, int count) {
		// 未缓存配置信息
		if(!idMap.containsKey(keyId)){
			proxySelf.proxyReadWork(keyId);
		}
		
		IdWork w = idMap.get(keyId);
		
		// 多线程处理过程中，可能队列被清空
		if(null == w){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("队列[" + keyId + "]被移除, 重试...");
			}
			
			count++;
			
			return getSerialNumber(keyId, count);
		}
		
		String id =  w.nextSerial();
		
		// 缓存号段取完,从数据库再读一段
		if(null == id){
			// 防止超出最大范围
			if(!w.isQueueEnd()){
				idMap.remove(keyId);
				
				if(logger.isDebugEnabled()){
					logger.debug("队列[" + keyId + "]超出最大缓存范围,移除旧的缓存...");
				}
				
				count++;
				
				return getSerialNumber(keyId, count);
			}else{
				logger.error("[" + w.getSequenceTbl().getSeqKey() + "]队列取值到了最大值! "
						+ " 当前值:" + w.getCurId() + ", 最大值:" + w.getMax());
				throw new SysException("90005");
			}
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("从队列[" + keyId + "]上取得序号[" + id + "]");
		}
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 本方法仅限内部调用
	 * <P>
	 *     用于读取数据库配置，因为需要用到spring 的事务控制，必须将方法暴露出现
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月10日
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)	// 不管是否存在事务,都创建一个新的事务,原来的挂起,新的执行完毕,继续执行老的事务
	public void proxyReadWork(String keyId){
		lock.lock();	// 块级同步锁
		try{
			if(logger.isDebugEnabled()){
				logger.debug("取得执行权限, 准备读[self_propagation_tbl]表...");
			}
			
			if(!idMap.containsKey(keyId)){
				int count = 0;
				while(true){
					IdWork work = readWork(keyId);
					
					if(null != work){
						idMap.put(keyId, work);
						break;
					}
					
					// 重试3次退出
					if(count >= 3){
						throw new SysException("90005");
					}
					
					count++;
					
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
					}
				}
			}
		}finally{
			lock.unlock();	// 释放块级同步锁
		}
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 读配置表内容
	 * <P>
	 *     悲观锁对访问记录行进行锁定，对最大值进行更新，返回对应的IdWork对象
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param keyId 队列名
	 * @return
	 */
	private IdWork readWork(String keyId){
		logger.info("读取队列:" + keyId + "的配置信息");
		IdWork idwork = null;
		SequenceTbl sequenceTbl = sequenceTblMapper.selectByPrimaryKey(keyId);	// 后台采用悲观锁，每个查询锁定记录行
		
		if(null != sequenceTbl){
			// 起始值
			long curId = sequenceTbl.getInitValue();
			
			// 缓存最大值 = 本次起始值 + 步长
			sequenceTbl.setInitValue(sequenceTbl.getInitValue() + sequenceTbl.getCacheSize());
			
			// 更新数据库
			sequenceTblMapper.updateByPrimaryKey(sequenceTbl);
			
			idwork = new IdWork(sequenceTbl, curId);
			
			logger.info(sequenceTbl.toString());
		}
		
		return idwork;
	}
	
	/**
	 * 
	 * 
	 * 类说明: 序号队列信息
	 * <P>
	 *     每个IdWork表示一个序号队列，存储这个队列的当前值、缓存最大值、步长、前缀、序号长度等规则，
	 * 当队列允许获取的序号达到本次缓存最大值，将与数据库同步缓存数据，当然同步控制由上层服务控制
	 * 	   2个重要的方法: nextSerial() 和 isQueueEnd() 有同步锁控制
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2016年6月28日
	 */
	private class IdWork{
		SequenceTbl sequenceTbl;	// 配置规则
		
		Long curId;	// 当前值
		
		Long max;	// 队列最大值
		
		IdWork(SequenceTbl sequenceTbl, Long curId){
			this.sequenceTbl = sequenceTbl;
			this.curId = curId;	// 赋初始值
			
			// 队列最大值 = 10的n次方 - 1，n为最大长度
			this.max = (long)Math.pow(10, sequenceTbl.getLength()) - 1;
		}
		
		/**
		 * 
		 * 方法说明: 取下一个值
		 * <P>
		 * 	   1. 同一个队列上获取序号有同步锁控制
		 *     2. 序号值的格式为：前缀 + 序号（会补齐长度）
		 * </P>
		 * 
		 * @author zhangsh
		 * @date 2016年6月28日
		 * @return 下一个序号，没有可取值是返回null
		 */
		public synchronized String nextSerial(){
			curId += sequenceTbl.getStep();	// 当前值自增
			
			// 判断是否超出范围，返回null
			if(curId > sequenceTbl.getInitValue()){
				logger.debug("从[" + sequenceTbl.getSeqKey() + "]队列中获取序号超出缓存最大值, "
						+ " 当前值:" + curId + ", 缓存最大值:" + sequenceTbl.getInitValue());
				return null;
			}else if(curId > max){
				logger.error("[" + sequenceTbl.getSeqKey() + "]队列取值到了最大值! "
						+ " 当前值:" + curId + ", 最大值:" + max);
				throw new SysException("90005");
			}
			
			return (sequenceTbl.getPrefix() == null ? "" : sequenceTbl.getPrefix())
					+ CharacterUtil.prefix(curId, sequenceTbl.getLength());
		}
		
		/**
		 * 
		 * 
		 * 方法说明: 是否超出队列的最大值
		 * <P>
		 *     下一个值与队列允许的最大长度值进行比较，防止超出范围
		 * </P>
		 * 
		 * @author zhangsh
		 * @date 2016年6月29日
		 * @return 达到末尾时返回true，其他返回false
		 */
		public synchronized boolean isQueueEnd(){
			return ((curId + sequenceTbl.getStep()) > max) ? true : false;
		}

		public SequenceTbl getSequenceTbl() {
			return sequenceTbl;
		}

		public Long getCurId() {
			return curId;
		}

		public Long getMax() {
			return max;
		}
	}
}