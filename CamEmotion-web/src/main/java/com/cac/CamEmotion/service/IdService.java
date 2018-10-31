package com.cac.CamEmotion.service;

/**
 * 
 * 类说明: 序号生成服务
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年6月28日
 */
public interface IdService {
	/**
	 * 
	 * 
	 * 方法说明: 获取一个序号
	 * <P>
	 *     序号在一个期间段内不会重复采集，达到最大序号后，停止提供序号。
	 * 程序中对序号有一个缓冲区间，这个区间可以通过sequence_tbl 配置表进行调整，仅重启服务生效，重启服务过程，
	 * 可能出现序号不连续的现象，属于正常现象
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param keyId 相关序号的key
	 * @return
	 */
	public String getSerialNumber(String keyId);
	
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
	@Deprecated
	public void proxyReadWork(String keyId);

}
