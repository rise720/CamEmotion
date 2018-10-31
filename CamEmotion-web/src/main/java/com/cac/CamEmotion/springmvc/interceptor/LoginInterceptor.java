package com.cac.CamEmotion.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.http.HttpInfoUtil;

/**
 * 
 * 
 * 类功能说明: 用户登录验证拦截器
 * <P>
 * 		1.已登录用户允许通过
 * 		2.未登录用户将request请求转发为至‘/api/notlogged’
 * 		3.用户登录或系统通过服务属于例外，可以通过
 * 		<br>
 * 		配置要求:<br>
 *     在spring-mvc.xml文件中配置对应的拦截规则:<mvc:interceptors>，如下：
 *     <!-- 匹配的是url路径， 如果不配置或/**,将拦截所有的Controller -->
 *   	<mvc:interceptor>
 *   		<mvc:mapping path="/"/>
 *   		<mvc:mapping path="/api/**"/>
 *   		<!-- 后端是否登录验证 -->
 *   		<bean class="com.cac.eBankingES.springmvc.interceptor.LoginInterceptor"></bean>
 *   	</mvc:interceptor>
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月24日
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	 private static Logger logger = LogManager.getLogger(LoginInterceptor.class);
	 
	 /**
	  * 
	  * 方法描述：用户是否登录验证
	  * <P>  
	  *		判断当前Session是否包含'uu_session_id'属性，存在则允许通过<BR>
	  *		1. 正常登录等方法，允许通过<BR>
	  *		2. 以上条件不满足，拦截请求，返回统一错误<BR>
	  * 		
	  * </P>
	  *
	  * @return 允许继续执行, 或是异常画面
	  */	
	 @Override
	 public boolean preHandle(HttpServletRequest request,    
	            HttpServletResponse response, Object handler) throws Exception{
		 String requestUri = null;
		 String contextPath = null ;
		 String url = null ;
		 
		/**
		 * 是否登录判断
		 */
		if(request.getSession().getAttribute(Constants.UU_SESSION_ID) == null){
			requestUri = request.getRequestURI();
			contextPath = request.getContextPath();
			url = requestUri.substring(contextPath.length());
			
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
		
		return true;
	 }
}
