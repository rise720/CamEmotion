package com.cac.CamEmotion.util;

import java.security.MessageDigest;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.exception.SysException;

/**
 * 
 * 类功能说明: 加密类
 * <P>
 *     
 * </P>
 *
 * @author jinwh
 * @data 2016年4月6日
 */
public class MD5 {
	/**
	 * 
	 * 方法功能说明: 使用MD5加密的静态方法
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2016年4月6日
	 * @return
	 */
	public static String encodeByMd5(String strMd5before){
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(strMd5before.getBytes(Constants.ENCODING_UTF8));
			StringBuffer hexValue = new StringBuffer(); 
			for(int i = 0; i < md5Bytes.length; i++){
				 int val = ((int) md5Bytes[i]) & 0xff;  
				 if (val < 16)  
		                hexValue.append("0");
				 hexValue.append(Integer.toHexString(val));   
			}
			return hexValue.toString();
		}catch(Exception e){
			throw new SysException(e, "90002", "MD5加密失败");
		}	
	}
	
	/**
	 * 
	 * 方法功能说明: SHA-1 加密
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年5月10日
	 * @param before
	 * @return
	 */
	public static String encodeBySHA1(String before){
		try{
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			byte[] sha1Bytes = sha1.digest(before.getBytes(Constants.ENCODING_UTF8));
			StringBuffer hexValue = new StringBuffer(); 
			for(int i = 0; i < sha1Bytes.length; i++){
				 int val = ((int) sha1Bytes[i]) & 0xff;  
				 if (val < 16)  
		                hexValue.append("0");
				 hexValue.append(Integer.toHexString(val));   
			}
			return hexValue.toString();
		}catch(Exception e){
			throw new SysException(e, "90002", "SHA1加密失败");
		}	
	}
}
