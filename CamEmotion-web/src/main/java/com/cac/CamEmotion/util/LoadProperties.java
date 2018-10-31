package com.cac.CamEmotion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cac.CamEmotion.exception.SysException;

/**
 * 
 * 类功能说明: 获取系统资源配置
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年4月6日
 */
public class LoadProperties {
	private static Properties prop = new Properties();

	static{
		InputStream in = null;
		
		try {
			in = LoadProperties.class.getResourceAsStream("/system.properties");
			prop.load(in);
		} catch (IOException e) {
			new SysException(e, "90001", "初始化加载system.properties失败");
		}finally{
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * 方法功能说明: 根据code获取配置表信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2016年4月5日
	 * @param text
	 * @return
	 */
	public static String getText(String text){
		return prop.getProperty(text);
	}
	
}
