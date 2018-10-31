package com.cac.CamEmotion.http;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.springmvc.model.Response;

/**
 * 
 * 类说明: 获取访问客户端的IP地址信息
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月26日
 */
public class HttpInfoUtil {
	private static Logger logger = LogManager.getLogger(HttpInfoUtil.class);
    
    /**
     * 
     * @Description 获取访问客户端的IP地址信息
     * <P> 
     *      优化判断是否通过代理服务器进行访问，如果不代理服务器才获取真实地址，
     *      如果是多级代码，IP 地址是一串地址
     * </P>
     * @param request http 请求对应
     * @return 访问的IP地址
     */
    public static String getIpAddr(HttpServletRequest request){
        String ip = null ;
        
        ip = request.getHeader("x-forwarded-for");
        
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("PRoxy-Client-IP"); 
        }
        
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        
        logger.debug("当前请求用户IP地址串:" + ip);
        
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ip != null && ip.length() > 15){ //"***.***.***.***".length() = 15  
            if(ip.indexOf(",")>0){  
                ip = ip.substring(0, ip.indexOf(","));  
            }  
        }  
        
        return ip;
    }
    
    /**
	 * 
	 * 方法说明: 错误请求响应
	 * <P>
	 *     请求类型为'application/json',返回json数据的错误提示
	 *     类型为其他，返回错误页面
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月26日
	 * @param request 
	 * @param response
	 * @param retCode 
	 * @param message
	 * @return
	 * @throws IOException
	 */
	public static Response send(HttpServletRequest request, HttpServletResponse response, String retCode, String message){
		String accept = request.getHeader("Accept");
		
		/* 如果接收 json 格式，由返回json格式*/
		if(StringUtils.contains(accept, "json")){
			return new Response().failure(retCode, message);
		}
		
		try {
			String msg = "retCode=" + retCode +"&message=" 
					+ (null == message ? "" : URLEncoder.encode(message, Constants.ENCODING_UTF8))
					+ "&" + System.currentTimeMillis();
			
			response.sendRedirect(request.getContextPath() + "/html/error/err.html?" + msg);
		} catch (IOException e) {
			logger.error(e);
		}
		
		return null;
	}
}
