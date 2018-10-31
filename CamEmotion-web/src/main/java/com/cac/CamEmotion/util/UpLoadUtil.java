package com.cac.CamEmotion.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.exception.BIException;
import com.cac.CamEmotion.exception.SysException;

/**
 * 
 * 
 * 类说明: 文件上传工具类
 * <P>
 *     包括3方面的功能，分别是：
 *     1、数据校验（包括：文件类型、文件大小、文件名长度）
 *     2、接收上线文件流数据并存入临时目录
 *     3、文件迁入
 * </P>
 *
 * @author zhangsh
 * @data 2016年10月27日
 */
public class UpLoadUtil {
	private static Logger logger = LogManager.getLogger(UpLoadUtil.class);
	
	// 上传文件存放顶级路径
	private static String baseUploadFilePath = LoadProperties.getText("system.homepath");

	/* 允许上传图片类型 */
	private static String[] allowedTypes = {".png", ".gif", ".jpg", ".jpeg"};
	
	/* 允许上传的附件类型 */
	private static String[] allowedAttTypes = {".html", ".htm"};
	
	/**
	 * 
	 * 
	 * 方法说明: 文件上传
	 * <P>
	 *     创建临时目录后将文件上传至指定目录
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName
	 * @param in 
	 * @param type 类别（0:老师头像）
	 * @return
	 */
	public static String fileUpload(String fileName, InputStream in, String type){
		String systemp = System.getProperty("user.dir").replace("bin", "");
		String tmpFilePath = DateUtil.DateToString(new Date(), "yyyyMMdd") + "/" + type;
		// 检查路径是否存在，不存在则创建
		File tmpFullPath = new File(systemp + baseUploadFilePath + tmpFilePath );
		if(!tmpFullPath.exists()){
			tmpFullPath.mkdirs();
		}
		// 目标文件路径
		File toFile = new File(tmpFullPath.getAbsolutePath() + "/" + fileName);
		
		// 文件数据拷贝
		OutputStream out = null;
		try {
			out = new FileOutputStream(toFile);
			
			byte[] b = new byte[4096];
			int len = -1;
			
			while((len = in.read(b)) > 0){
				out.write(b, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件失败", e);
			throw new SysException("60100");
		}finally{
			try{
				if(null != out){
					out.close();
				}
				// 外部创建IO流，此处不关闭
//				if(null != in){
//					in.close();
//				}
			}catch (IOException e) {
			}
		}
		
		logger.info(fileName + "上传成功!保存路径:" + toFile.getAbsolutePath());
		
		return tmpFilePath + "/" + fileName;
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 上传文件校验
	 * <P>
	 *     类型检查、大小检查、文件名长度检查，检查不通过删除文件
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName 上传文件名
	 * @param relativePath	上传文件的相对路径
	 * @param type 上传类别（0:学校, 1:教师图片）
	 * @param len 上传文件大小（单位字节）
	 * @return 0 成功；负数表示失败(-1,不是图片;-2,文件超长;-3,文件名超长;-4不是html页面;-9,其他错误)
	 * @throws UnsupportedEncodingException
	 */
	public static int check(String fileName, String relativePath, String type, long len) throws UnsupportedEncodingException{
		String systemp = System.getProperty("user.dir").replace("bin", "");
		File file = new File(systemp + baseUploadFilePath + relativePath);
		
		// 如果文件不存在
		if(!file.exists()){
			return -8;
		}
		
		switch(type){
		case "1":// 教师图片
			if(!isImage(fileName, file)){
				file.deleteOnExit();
				return -1;
			}else if(!checkLength(type, len)){
				file.deleteOnExit();
				return -2;
			}else if(!checkfileNameLength(fileName)){
				file.deleteOnExit();
				return -3;
			}
			
			break;
		case "2":// 产品附件
			if(!isHtmlPage(fileName)){
				file.deleteOnExit();
				return -4;
			}else if(!checkLength(type, len)){
				file.deleteOnExit();
				return -2;
			}else if(!checkfileNameLength(fileName)){
				file.deleteOnExit();
				return -3;
			}
			
			break;
		case "3":// 其他
			if(!checkfileNameLength(fileName)){
				file.deleteOnExit();
				return -3;
			}
			
			break;
		default:
			return -9;
		}
		
		return 0;
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 返回指定上传文件的web访问路径
	 * <P>
	 *     此路径不包含http协议、域名和端口
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param path 文件路径
	 * @return web访问路径
	 */
	public static String getWebUrl(String path){
		return LoadProperties.getText("download.path") + path;
	}
	
	/**
	 * 
	 * 方法功能说明: 将其他产品的文件复制一份至当前产品目录
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年4月14日
	 * @param fromProdUrl 原文件地址
	 * @param toFilePath  新文件地址
	 * @return
	 */
	public static void fileCopy(String fromProdUrl, String toFilePath){
        
		File fromFile = new File(baseUploadFilePath +  fromProdUrl);
		//如果被复制文件不存在，则报错
		if(false == fromFile.exists() || false == fromFile.isFile()){
			throw new BIException("60096");
		}
		
		//创建复制文件
		File toFile = new File(baseUploadFilePath +  toFilePath);
		
		//文件复制
		try{
			//复制文件的路径创建
			File toParentFile = toFile.getParentFile();
			if(!toParentFile.exists()){
				toParentFile.mkdirs();
			}
			FileInputStream inFileInputStream = new FileInputStream(fromFile);
			FileOutputStream outFileInputStream = new FileOutputStream(toFile);
			FileChannel inFileChannel = inFileInputStream.getChannel();
			FileChannel outFileChannel = outFileInputStream.getChannel();
			inFileChannel.transferTo(0, inFileChannel.size(), outFileChannel);
            //outFileChannel.transferFrom(outFileChannel, 0, inFileChannel.size());
			//析构
			inFileInputStream.close();
			outFileInputStream.close();
			inFileChannel.close();
			outFileChannel.close();			
		}catch(IOException e){
			throw new BIException("60095");
		}
		
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 检查文件是否为图片
	 * <P>
	 *     1、检查文件是否为指定的图片格式，检查参考:allowedTypes
	 *     2、检查图片内容是否为图片格式
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName 文件名
	 * @param file 文件
	 * @return 是图片返回true，其他返回false
	 */
	private static boolean isImage(String fileName, File file){
		boolean isImage = false;
		
		/* 先判断文件类型 */
		fileName = fileName.toLowerCase();
		for(String fileType : allowedTypes){
			if(fileName.endsWith(fileType)){
				isImage = true;
				
				if(logger.isDebugEnabled()){
					logger.debug(fileName + " 文件类型检查通过...");
				}
				break;
			}
		}
		
		// 判断上传文件是否为图片
		if(isImage){
			 try {
				Image image = ImageIO.read(file);
				if(null == image){
					isImage = false;
					if(logger.isInfoEnabled()){
						logger.info("上传文件非图片格式...");
					}
				}
			} catch (IOException e) {
				logger.info("判断文件是否为图片失败...");
				isImage = false;
			}
		}
		
		return isImage;
	}
	
	
	/**
	 * 
	 * 
	 * 方法说明: 是否为html页面
	 * <P>
	 *     检查文件名是否为html格式
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName 文件名
	 * @return  是html页面返回true，其他返回false
	 */
	private static boolean isHtmlPage(String fileName){
		boolean isHtml = false;
		
		/* 先判断文件类型 */
		fileName = fileName.toLowerCase();
		
		
		for(String fileType : allowedAttTypes){
			if(fileName.endsWith(fileType)){
				isHtml = true;
				
				if(logger.isDebugEnabled()){
					logger.debug(fileName + " 文件类型检查通过...");
				}
				break;
			}
		}
		return isHtml;
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 文件内容长度检查
	 * <P>
	 *     根据上传文件的type，进行格式检查
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param type 上传文件类别
	 * @param len 文件大小（单位字节）
	 * @return  未超过允许的范围返回true，其他返回false
	 */
	private static boolean checkLength(String type, long len){
		if("0".equals(type)){
			if((len / 1024) > 60){	// 60 KB
				return false;
			}		
		}else if("1".equals(type)){	
			if((len / 1024) > 100){
				return false;
			}
		}else if("2".equals(type)){	// 100 KB
			if((len / 1024) > 100){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 检查文件名长度
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName 文件名
	 * @return 未超过最大值返回true，其他返回false
	 * @throws UnsupportedEncodingException
	 */
	private static boolean checkfileNameLength(String fileName) throws UnsupportedEncodingException{
		return Constants.MAXLENGTH > fileName.getBytes(Constants.ENCODING_UTF8).length ? true : false;
	}
}
