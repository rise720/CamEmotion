package com.cac.CamEmotion.util;

import java.text.DecimalFormat;

/**
 * 
 * 
 * 类说明: 字符串补位操作
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年6月28日
 */
public class CharacterUtil {
	static final String STR_FORMAT = "0";
	
	/**
	 * 
	 * 
	 * 方法说明: 数字补位
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param num 原数据
	 * @param len 长度
	 * @return
	 */
	public static String prefix(long num, long len){  
		DecimalFormat df = new DecimalFormat(formatchar(STR_FORMAT, len));  
		return df.format(num);  
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 数据补位格式
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月28日
	 * @param fix 补位字符
	 * @param len 长度
	 * @return
	 */
	public static String formatchar(String fix, long len){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < len; i++){
			sb.append(fix);
		}
		return sb.toString();
	}
}
