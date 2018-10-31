package com.cac.CamEmotion.util;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * 
 * 类说明: memcache操作工具类
 * <P>
 * 
 * </P>
 *
 * @author zhangsh
 * @date 2017年10月11日
 */
public class MemcacheUtil {
	private static Logger logger = LogManager.getLogger(MemcacheUtil.class);

	// 连接
	private MemcachedClient mClient = null;

	// // 本类实例
	private MemcacheUtil _instance = null;

	public String ip;
	public int port;

	public MemcacheUtil() {
	}

	Gson gson = new Gson(); // json解析器

	private static Object lock = new Object();

	// 获取本类实例方法
	public MemcacheUtil getInstance(String ip, int port) throws Exception {
		if (null == _instance) {

			synchronized (lock) {
				if (null == _instance) {
					_instance = new MemcacheUtil(ip, port);
				}
			}
		}

		return _instance;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public MemcachedClient getmClient() {
		return mClient;
	}

	public void setmClient(MemcachedClient mClient) {
		close();
		this.mClient = mClient;
	}

	public MemcacheUtil(String ip, int port) throws IOException {
		try {
			this.ip = ip;
			this.port = port;
			setmClient(new MemcachedClient(new InetSocketAddress(ip, port)));
		} catch (Exception e) {
			logger.error("Memcached构造失败！ip:" + ip + "  port:" + port);
			setmClient(null);
		}
	}

	public boolean setKey(String keyName, int exp, Object keyValue) {
		try {
			OperationFuture<Boolean> future = mClient.set(keyName, exp, String.valueOf(keyValue).trim());
			if (future.getStatus() == null || !future.getStatus().isSuccess()) {
				setmClient(new MemcachedClient(new InetSocketAddress(ip, port)));
				future = mClient.set(keyName, exp, String.valueOf(keyValue).trim());
				if (future.getStatus() == null || !future.getStatus().isSuccess()) {
					logger.error("memcached修改记录失败！");
					return false;
				}
			}
		} catch (IOException e) {
			logger.error("memcached修改记录失败！");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public boolean delete(String keyName) {
		OperationFuture<Boolean> future = mClient.delete(keyName);
		if(future.getStatus() == null || !future.getStatus().isSuccess()){
			logger.error("memcached删除记录失败！");
			return false;
		}
		return true;
	}
	
	public Object getKey(String keyName) {
		Object object = mClient.get(keyName);
		try{
			if(object == null)
				setmClient(new MemcachedClient(new InetSocketAddress(ip, port)));
				object = mClient.get(keyName);
		}catch(IOException e){
			return object;
		}
		return mClient.get(keyName);
	}

	/**
	 * 
	 * 方法说明: 关闭连接
	 * <P>
	 * 
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年10月11日
	 */
	public void close() {
		if(mClient != null)
			mClient.shutdown();
	}
}
