package com.cac.CamEmotion.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.http.CacheStack;
import com.cac.CamEmotion.http.HttpInfoUtil;

/**
 * 
 * 类说明: html页面访问权限拦截器
 * <P>
 * 		拦截被访问html的url，判断用户拥有访问该url权限
 * 		<br>
 * 
 * 	    配置要求:<br>
 *     在spring-mvc.xml文件中配置对应的拦截规则:<mvc:interceptors>，如下：
 *     <!-- 匹配的是url路径， 如果不配置或/**,将拦截所有的Controller -->
 *   	<mvc:interceptor>
 *   		<mvc:mapping path="/"/>
 *   		<mvc:mapping path="/api/**"/>
 *   		<!-- 后端是否登录验证 -->
 *   		<bean class="com.cac.CamEmotion.springmvc.interceptor.HandlerInterceptorAdapter"></bean>
 *   	</mvc:interceptor>
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月28日
 */
public class HTMLAuthInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LogManager.getLogger(AuthInterceptor.class);

	/**
	  * 
	  * 方法描述：url访问权限检查
	  * <P>  
	  *		拦截被访问html的url，判断用户拥有访问该url权限
	  * 		
	  * </P>
	  *
	  * @return 允许继续执行, 或是异常画面
	  */	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		
		/**
		 * 是否登录判断
		 */
		if(request.getSession().getAttribute(Constants.UU_SESSION_ID) == null){
			logger.debug("requestUri:" + requestUri);
			logger.debug("contextPath:" + contextPath);
			logger.debug("url:" + url);
			logger.warn("---------------------------------------------");
			logger.warn("未登录用户非法访问！机器IP:" + HttpInfoUtil.getIpAddr(request));
			logger.warn("访问url:" + requestUri);
			logger.warn("---------------------------------------------");
			
			request.getRequestDispatcher("/api/notlogged").forward(request, response);
			return false;
		}
		
		if(null != url && StringUtils.containsIgnoreCase(url, ".html")){
			/** 遍历列表,判断用户是否有访问权限 **/
			boolean isOK = CacheStack.getInstance().hasHTMLAuth(request.getSession().getId(), url);
			
			if(!isOK){
				// 回单查看页面，允许有临时凭证的用户查看
				Object scrip = request.getSession().getAttribute(Constants.SCRIP);
				if(null != scrip){
//					if("/html/accountDetails/accountDetailsView.html".equals(url) 
//							|| "/html/noticeDeposit/electronicReceipt.html".equals(url) ){
//						logger.info("回单验证，允许有临时凭证用户通过...");
//						return true;
//					}
				}
				
				logger.warn("*********************************************");
				logger.warn("无访问权限用户非法访问！机器IP:" + HttpInfoUtil.getIpAddr(request));
				logger.warn("访问url:" + requestUri);
				logger.warn("*********************************************");
				
				request.setAttribute("accessUrl", request.getRequestURI());
				request.getRequestDispatcher("/api/nopermission").forward(request, response);
				return false;
			}
		}
		
		return true;
	}
}
