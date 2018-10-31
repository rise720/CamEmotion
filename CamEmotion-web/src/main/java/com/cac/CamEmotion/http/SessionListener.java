package com.cac.CamEmotion.http;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * 类功能说明: session 监听服务
 * <P>
 * 	
 * </P>
 *
 * @author zhangsh
 * @data 2016年4月13日
 */
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	private static Logger logger = LogManager.getLogger(SessionListener.class);
	
    // session创建时调用这个方法
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    }
    
    /**
     * Session失效或者过期的时候调用的这个方法
     * 过期时清空缓存的登录用户，通过session中判断SESSION_USER或SESSION_BANK_USER来清空对应的对象
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 如果session超时, 则从map中移除这个用户
        try {
//        	Object userid = se.getSession().getAttribute(Constants.SESSION_ENTERPRISE_USER);
//        	
//        	CacheStack cacheStack = CacheStack.getInstance();
//        	Hashtable<String, String> enterpriseUser = cacheStack.getEnterpriseUser();
//        	Hashtable<String, String> bankUser = cacheStack.getBankUser();
//        	
//        	// 企业端用户
//        	if(null != userid){
//        		if(logger.isDebugEnabled()){
//        			logger.debug("----------------------------------------------");
//        			logger.debug("Session 过期...");
//        			logger.debug("Session ID : "+ se.getSession().getId());
//        			logger.debug("当前登录用户数:" + enterpriseUser.size());
//        			logger.debug("----------------------------------------------");
//        		}
//        		enterpriseUser.remove(se.getSession().getId());
//        	}else{// 银行端用户
//        		userid = se.getSession().getAttribute(Constants.SESSION_BANK_USER);
//        		
//        		if(null != userid){
//        			if(logger.isDebugEnabled()){
//            			logger.debug("----------------------------------------------");
//            			logger.debug("Session 过期...");
//            			logger.debug("Session ID : "+ se.getSession().getId());
//            			logger.debug("当前登录用户数:" + bankUser.size());
//            			logger.debug("----------------------------------------------");
//            		}
//        			bankUser.remove(se.getSession().getId());
//        		}
//        	}
        	
        	/*
        	 * 清空缓存权限列表
        	 */
        	CacheStack.getInstance().removeAuthList(se.getSession().getId());
        	CacheStack.getInstance().removeHTMLList(se.getSession().getId());
        	
        } catch (Exception e) {
            logger.warn("清理超时session 发生异常...", e);
        }

    } 
    /**
     * 向session中添加用户
     */
	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
//		CacheStack cacheStack = CacheStack.getInstance();
//    	Hashtable<String, String> enterpriseUser = cacheStack.getEnterpriseUser();
//    	Hashtable<String, String> bankUser = cacheStack.getBankUser();
//    	
//		// 企业端用户
//		if(Constants.SESSION_ENTERPRISE_USER.equals(arg0.getName())){
//			String userID = (String) arg0.getSession().getAttribute(Constants.SESSION_ENTERPRISE_USER);
//			
//			if(null != userID){
//				enterpriseUser.put(arg0.getSession().getId(), userID);
//				if(logger.isDebugEnabled()){
//					logger.debug("向session添加一个用户, userID :" + userID);
//					logger.debug("当前登录用户数:" + enterpriseUser.size());
//				}
//			}
//		}else if(Constants.SESSION_BANK_USER.equals(arg0.getName())){// 银行端用户
//			String userID = (String) arg0.getSession().getAttribute(Constants.SESSION_BANK_USER);
//			
//			if(null != userID){
//				bankUser.put(arg0.getSession().getId(), userID);
//				if(logger.isDebugEnabled()){
//					logger.debug("向session添加一个用户, userID :" + userID);
//					logger.debug("当前登录用户数:" + bankUser.size());
//				}
//			}
//		}
	}
	/**
	 * 清空session
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		try{
//			CacheStack cacheStack = CacheStack.getInstance();
//        	Hashtable<String, String> enterpriseUser = cacheStack.getEnterpriseUser();
//        	Hashtable<String, String> bankUser = cacheStack.getBankUser();
//        	
//			// 企业端用户
//			if(Constants.SESSION_ENTERPRISE_USER.equals(arg0.getName())){
//				enterpriseUser.remove(arg0.getSession().getId());
//				
//				if(logger.isDebugEnabled()){
//					logger.debug("清空用户session...");
//				}
//			}else if(Constants.SESSION_BANK_USER.equals(arg0.getName())){// 银行端用户
//				bankUser.remove(arg0.getSession().getId());
//				
//				if(logger.isDebugEnabled()){
//					logger.debug("清空用户session...");
//				}
//			}
			
			/*
        	 * 清空缓存权限列表
        	 */
        	CacheStack.getInstance().removeAuthList(arg0.getSession().getId());
        	CacheStack.getInstance().removeHTMLList(arg0.getSession().getId());
        	
		}catch(Exception e){
			logger.warn("从session 移出属性时发生异常", e);
		}
	}
	
	/**
     * 向session中添加用户
     */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
//		CacheStack cacheStack = CacheStack.getInstance();
//    	Hashtable<String, String> enterpriseUser = cacheStack.getEnterpriseUser();
//    	Hashtable<String, String> bankUser = cacheStack.getBankUser();
//    	
//		if(Constants.SESSION_ENTERPRISE_USER.equals(arg0.getName())){
//			String userID = (String) arg0.getSession().getAttribute(Constants.SESSION_ENTERPRISE_USER);
//			if(null != userID){
//				enterpriseUser.put(arg0.getSession().getId(), userID);
//				if(logger.isInfoEnabled()){
//					logger.info("向session添加一个用户, userID :" + userID);
//				}
//			}
//		}else if(Constants.SESSION_BANK_USER.equals(arg0.getName())){
//			String userID = (String) arg0.getSession().getAttribute(Constants.SESSION_BANK_USER);
//			if(null != userID){
//				bankUser.put(arg0.getSession().getId(), userID);
//				if(logger.isInfoEnabled()){
//					logger.info("向session添加一个用户, userID :" + userID);
//				}
//			}
//		}
	}
}
