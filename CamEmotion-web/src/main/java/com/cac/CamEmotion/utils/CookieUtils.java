package com.cac.CamEmotion.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
 

/**
 * 
 * 类功能说明: Cookie 工具类
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年4月13日
 */
public class CookieUtils {
	
	public static final String USER_COOKIE = "user.cookie";  
	
	/**
	 * 
	 * 方法功能说明: 删除Cookie
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年4月13日
	 * @param request
	 * @return
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		
		if(null != cookies){
			for(Cookie c : cookies){
				if(c.getName().equals(USER_COOKIE)){
					c.setMaxAge(0);
					c.setPath(request.getContextPath());
					c.setValue(null);
					response.addCookie(c);
				}
			}
		}
	}
	
	/**
	 * 
	 * 方法功能说明:将cookie中的json转换为java对象
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	zhangfei
	 * @date	2016年10月21日
	 * @param c java对象
	 * @param request 
	 * @param name cookie保存的名称
	 * @return java对象
	 */
	public static <T> T ToObject(Class<T> c, HttpServletRequest request, String name) {
		T t = null;
		try {
			t = c.newInstance();
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(name)) {
						String value = Escape.unescape(cookie.getValue());
						Gson gson = new Gson();
						t = gson.fromJson(value, c);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 
	 * 方法功能说明:删除指定Cookie内容
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	zhangfei
	 * @date	2016年10月24日
	 * @param request 
	 * @param response
	 * @param name 键值
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response,String [] name){
		Cookie[] cookies = request.getCookies();
		
		if (null != cookies) {
			for (Cookie c : cookies) {
				if (name != null && name.length > 0) {
					for (String n : name) {
						if (c.getName().equals(n)) {
							c.setMaxAge(0);
							c.setPath(request.getContextPath());
							c.setValue(null);
							response.addCookie(c);
						}
					}
				}
			}
		}
	}
}
