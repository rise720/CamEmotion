package com.cac.CamEmotion.springmvc.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cac.CamEmotion.annotation.PermissionCode;
import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.http.CacheStack;
import com.cac.CamEmotion.http.HttpInfoUtil;

/**
 * 
 * 类说明: api接口访问权限拦截器
 * <P>
 * 		拦截被访问方法或类，判断用户拥有访问权限,需要在目标方法或类注解PermissionCode
 * 		<br>
 * 
 * 	    配置要求:<br>
 *     在spring-mvc.xml文件中配置对应的拦截规则:<mvc:interceptors>，如下：
 *     <!-- 匹配的是url路径， 如果不配置或/**,将拦截所有的Controller -->
 *   	<mvc:interceptor>
 *   		<mvc:mapping path="/"/>
 *   		<mvc:mapping path="/api/**"/>
 *   		<!-- 后端是否登录验证 -->
 *   		<bean class="com.cac.CamEmotion.springmvc.interceptor.AuthInterceptor"></bean>
 *   	</mvc:interceptor>
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月28日
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LogManager.getLogger(AuthInterceptor.class);

	/**
	  * 
	  * 方法描述：访问权限检查
	  * <P>  
	  *		拦截被访问方法或类的PermissionCode注解，调用SessionListener.hasAuth进行判断<BR>
	  * 		
	  * </P>
	  *
	  * @return 允许继续执行, 或是异常画面
	  */	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;		
			Method method = handlerMethod.getMethod();					// 目标方法
			Class<?> classz = handlerMethod.getBeanType();				// 目标类
			
			try{
				if(null != classz && null != method){
					PermissionCode permissionCode = null;
					boolean isClassAnnotation =  classz.isAnnotationPresent(PermissionCode.class);
					boolean isMethodAnnotation =  method.isAnnotationPresent(PermissionCode.class);
					
					/* 方法级注解优先  */
					if(isMethodAnnotation){
						permissionCode = method.getAnnotation(PermissionCode.class);
					}else if(isClassAnnotation){
						permissionCode = classz.getAnnotation(PermissionCode.class);
					}
					
					/** 没有定义权限代码不验证 **/
					if(null != permissionCode){
						/** 获取类或方法的注解权限代码 **/
						String value = permissionCode.value();
						/** 遍历列表,判断用户是否有访问权限 **/
						boolean isOK = CacheStack.getInstance().hasAuth(request.getSession().getId(), value);
						
						if(!isOK){
							// 回单查看页面，允许有临时凭证的用户查看
//							Object scrip = request.getSession().getAttribute(Constants.SCRIP);
//							if(null != scrip){
//								String requestUri = request.getRequestURI();
//								String contextPath = request.getContextPath();
//								String url = requestUri.substring(contextPath.length());
//								
//								if("/api/accountDetails/electronicReceiptInfo".equals(url)
//										|| "/api/electronicReceipt/electronicReceiptInfo".equals(url)){
//									logger.info("回单验证，允许有临时凭证用户通过...");
//									return true;
//								}
//							}
							
							logger.warn("+++++++++++++++++++++++++++++++++++++++++++++");
							logger.warn("无访问权限用户非法访问！机器IP:" + HttpInfoUtil.getIpAddr(request));
							logger.warn("访问url:" + request.getRequestURI());
							logger.warn("+++++++++++++++++++++++++++++++++++++++++++++");
							
							request.setAttribute("accessUrl", request.getRequestURI());
							request.getRequestDispatcher("/api/nopermission").forward(request, response);
							return false;
						}
					}
				}
			}catch(Exception e){
				logger.error("检查访问权限发生错误!", e);
				request.setAttribute("accessUrl", request.getRequestURI());
				request.getRequestDispatcher("/api/nopermission").forward(request, response);
				throw e;
			}
		}else{
			logger.warn("handler is not HandlerMethod!");
		}
		
		return true;
	}
}
