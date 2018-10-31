package com.cac.CamEmotion.http;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hazelcast.web.SessionListener;

/**
 * 
 * 类说明: 全局缓存对象
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月19日
 */
public class CacheStack {
	private static Logger logger = LogManager.getLogger(CacheStack.class);
	
	/* 企业端登录用户信息 <sessionID, UnitCode + UserId>*/
//    private static Hashtable<String, String> enterpriseUser = new Hashtable<String, String>(100);
    
    /* 银行端登录用户信息<sessionID, UnitCode + UserId> */
//    private static Hashtable<String, String> bankUser = new Hashtable<String, String>(100);
    
    /* 用户api权限列表<sessionID, Map<String, String>> */
    private static Hashtable<String, String[]> SESSION_AUTHLIST = new Hashtable<String, String[]>(100);
    
    /* 用户html路径访问列表<sessionID, Map<String, String>> */
    private static Hashtable<String, String[]> SESSION_HTMLAUTHLIST = new Hashtable<String, String[]>(100);
    
    // 唯一实例对象
    private static CacheStack instance = null;
    
    // 私有锁
    private static Object lock = new Object();
    
    /**
     * 
     * 构造函数: 私有构造函数，不允许显示的创建本类
     * <P>
     *     
     * </P>
     *
     * @author zhangsh
     * @data 2017年5月19日
     */
    private CacheStack(){
    	
    }
    
    /**
     * 
     * 方法说明: 全局唯一实例获取方法
     * <P>
     *     
     * </P>
     *
     * @author zhangsh
     * @data 2017年5月19日
     * @return
     */
    public static CacheStack getInstance(){
    	if(null == instance){
    		synchronized (lock) {
    			if(null == instance){
    				instance = new CacheStack();
    			}
			}
    	}
    	
    	return instance;
    }
    
    /**
	 * 
	 * 方法说明: 企业端用户登录状态判断
	 * <P>
	 *     检查用户登录状态，以及登录用户所使用的会话，总4种状态：<br>
	 *     1.不同session，不同用户 -> 1 <br>
	 *     2.不同session, 同用户　-> 2 <br>
	 *     3.同session, 同用户        -> 0 <br> 
	 *     4.同session, 不同用户    -> -1 <br>
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月8日
	 * @param sessionId
	 * @param userID
	 * @return
	 */
//	public int isUserLogged(String sessionId, String userID){
//		int rstl = 1;
//		
//		if(enterpriseUser.containsKey(sessionId)){
//			rstl = -1;
//			
//			String tmpUserID = enterpriseUser.get(sessionId);
//			if(StringUtils.equals(userID, tmpUserID)){
//				rstl = 0;
//			}
//		}else{
//			rstl = 1;
//			
//			/* 全登录用户是检索，判断userID是否相同　 */
//			Enumeration<String> keys = enterpriseUser.keys();
//			while(keys.hasMoreElements()){
//				String tmpUserID = enterpriseUser.get(keys.nextElement());
//				if(StringUtils.equals(userID, tmpUserID)){
//					rstl = 2;
//					break;
//				}
//			}
//		}
//		
//		logger.debug("企业端用户登录状态检查，SessionId:" + sessionId + ", userID:" + userID + ", reslt:" + rstl);
//		
//		return rstl;
//	}
	
	/**
	 * 
	 * 方法说明: 银行端用户登录状态判断
	 * <P>
	 *     检查用户登录状态，以及登录用户所使用的会话，总4种状态：<br>
	 *     1.不同session，不同用户 -> 1 <br>
	 *     2.不同session, 同用户　-> 2 <br>
	 *     3.同session, 同用户        -> 0 <br> 
	 *     4.同session, 不同用户    -> -1 <br>
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月8日
	 * @param sessionId
	 * @param userID
	 * @return
	 */
//	public int isBankUserLogged(String sessionId, String userID){
//		int rstl = 1;
//	
//		if(bankUser.containsKey(sessionId)){
//			rstl = -1;
//			
//			String tmpUserID = bankUser.get(sessionId);
//			if(StringUtils.equals(userID, tmpUserID)){
//				rstl = 0;
//			}
//		}else{
//			rstl = 1;
//			
//			/* 全登录用户是检索，判断userID是否相同　 */
//			Enumeration<String> keys = bankUser.keys();
//			while(keys.hasMoreElements()){
//				String tmpUserID = bankUser.get(keys.nextElement());
//				if(StringUtils.equals(userID, tmpUserID)){
//					rstl = 2;
//					break;
//				}
//			}
//		}
//		
//		logger.debug("银行端用户登录状态检查，SessionId:" + sessionId + ", userID:" + userID + ", reslt:" + rstl);
//		
//		return rstl;
//	}
	
    
    /**
	 * 
	 * 方法说明: 缓存用户api权限列表
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 * @param authList
	 */
	public void cacheAuthList(String sessionID, String[] authList){
    	SESSION_AUTHLIST.put(sessionID, authList);
    }
	
	/**
	 * 
	 * 方法说明: 清理缓存用户api权限列表
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 */
	public void removeAuthList(String sessionID){
		if(SESSION_AUTHLIST.containsKey(sessionID)){
			SESSION_AUTHLIST.remove(sessionID);
		}
	}
    
	/**
	 * 
	 * 方法说明: 是否有用户api权限
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 * @param value
	 * @return
	 */
    public boolean hasAuth(String sessionID, String value){
    	boolean rstl = false;
    	
    	if(SESSION_AUTHLIST.containsKey(sessionID)){
    		String[] authlist = SESSION_AUTHLIST.get(sessionID);
    		
    		if(null != authlist){
    			for(String code : authlist){
    				if(StringUtils.endsWithIgnoreCase(code, value)){
    					rstl = true;
    					break;
    				}
    			}
    		}
    	}
    	
    	return rstl;
    }

    /**
	 * 
	 * 方法说明: 缓存用户html url列表
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 * @param authList
	 */
	public void cacheHTMLList(String sessionID, String[] urlList){
		SESSION_HTMLAUTHLIST.put(sessionID, urlList);
    }
    
	/**
	 * 
	 * 方法说明: 清理缓存用户html url列表
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 */
	public void removeHTMLList(String sessionID){
		if(SESSION_HTMLAUTHLIST.containsKey(sessionID)){
			SESSION_HTMLAUTHLIST.remove(sessionID);
		}
	}
	
	/**
	 * 
	 * 方法说明: 判断用户是否该html url的访问权限
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @param sessionID
	 * @param url
	 * @return
	 */
    public boolean hasHTMLAuth(String sessionID, String url){
    	boolean rstl = false;
    	
    	if(SESSION_HTMLAUTHLIST.containsKey(sessionID)){
    		String[] authlist = SESSION_HTMLAUTHLIST.get(sessionID);
    		
    		if(null != authlist){
    			for(String code : authlist){
    				if(StringUtils.endsWithIgnoreCase(code, url)){
    					rstl = true;
    					break;
    				}
    			}
    		}
    	}
    	
    	return rstl;
    }

//	public Hashtable<String, String> getEnterpriseUser() {
//		return enterpriseUser;
//	}
//
//	public Hashtable<String, String> getBankUser() {
//		return bankUser;
//	}
}
