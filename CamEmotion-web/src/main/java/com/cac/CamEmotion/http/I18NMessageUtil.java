package com.cac.CamEmotion.http;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

/**
 * 
 * 类说明: 读i18n配置信息
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月19日
 */
public class I18NMessageUtil {
	private static Logger logger = LogManager.getLogger(I18NMessageUtil.class);
	
	// 找不到消息时,默认的消息
	private static String defaultmessage = "The code Not defined!";
	
	private ApplicationContext applicationContext;
	
	public I18NMessageUtil(){
		
	}
	
	public I18NMessageUtil(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 
	 * 方法说明: 读i18n配置信息
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年4月19日
	 * @param request
	 * @param code
	 * @return
	 */
	public static String getMessage(HttpServletRequest request, String code){
		return getMessage(request, code, null);
	}
	
	/**
	 * 
	 * 方法说明: 读i18n配置信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param request HttpServletRequest
	 * @param code 消息的key
	 * @return
	 */
	public static String getMessage(HttpServletRequest request, String code, Object[] args){
		if(null == request){
			logger.warn("获取i18n消息，传入的reqeust 为 null !");
			return null;
		}
		
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(code, args, defaultmessage);
	}
	
	/**
	 * 
	 * 方法说明: 批量获取message
	 * <P>
	 *     如果资源key中包含多级，将去掉第1级返回
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年7月17日
	 * @param request
	 * @param codes 资源key
	 * @return
	 */
	public static Map<String, String> getMessage(HttpServletRequest request, String[] codes){
		Map<String, String> message = new TreeMap<String, String>();
		if(null != codes){
			for(String key : codes){
				if(null != key)
					message.put(key.indexOf(".") > 0 ? key.substring(key.indexOf(".") + 1) : key, getMessage(request, key, null));
			}
		}
		return message;
	}
	
	/**
	 * 
	 * 方法说明: 获取message的另一种方式
	 * <P>
	 *     该方法最大问题: 得在Controller层里获取applicationContext，在service层里获取的applicationContext读不取资源
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年7月17日
	 * @param key 消息key
	 * @param args 参数
	 * @param locale 语言
	 * @return
	 */
	public String getMessage(String key, Object[] args, Locale locale){
		return this.applicationContext.getMessage(key, args, locale);
	}
}
