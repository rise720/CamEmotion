package com.cac.CamEmotion.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * 类功能说明:图片压缩
 * <P>
 * 
 * </P>
 *
 * @author zhangfei
 * @data 2017年1月18日
 */
public class ImageCompressUtil {

	private static Logger logger = LogManager.getLogger(ImageCompressUtil.class);

	/* 允许上传图片类型 */
	private static String[] allowedTypes = { ".png", ".gif", ".jpg", ".jpeg" };

	public static InputStream reduceImg(InputStream files,long fileSize, String fileName) {
		try {
			if (!isImage(fileName)) {
				return files;
			}
			BufferedImage src = ImageIO.read(files);
			int width = src.getWidth();
			int height = src.getHeight();
			// 图片像素大于400万
			double ratio = width * height / 4000000;
			if (ratio > 1) {
				ratio = Math.sqrt(ratio);
				width /= ratio;
				height /= ratio;
			}
			// 图片像素大于100万
			ratio = width * height / 1000000;
			if (ratio > 1) {
				ratio = (int) (Math.sqrt(ratio) + 1);
				width /= ratio;
				height /= ratio;
			}
//			 图片像素大于20万
			ratio = width * height / 250000;
			if (ratio > 1) {
				ratio = (int) (Math.sqrt(ratio) + 1);
				width /= ratio;
				height /= ratio;
			}
			
//			 最后
			ratio = 1;
			if(fileSize > 150*1024 && fileSize <= 1024*1024){
				ratio = 0.7;
				
			}else if(fileSize > 1024*1024){
				ratio = 0.5;
				
			}
			
			width *= ratio;
			height *= ratio;
			

			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String[] token = fileName.split("\\.");
			String pf = token[1];
			ImageIO.write(tag, pf, os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			tag.flush();
			return is;
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error(ex);
			return files;
		}
	}

	public static void InputImg(BufferedImage canvas, String path, String fileName) {
		path = path + fileName;
		File file = new File(path + fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			ImageIO.write(canvas, path.substring(path.lastIndexOf(".") + 1), new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * 方法说明: 检查文件是否为图片
	 * <P>
	 * 1、检查文件是否为指定的图片格式，检查参考:allowedTypes 2、检查图片内容是否为图片格式
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年10月27日
	 * @param fileName
	 *            文件名
	 * @param file
	 *            文件
	 * @return 是图片返回true，其他返回false
	 */
	private static boolean isImage(String fileName) {
		boolean isImage = false;

		/* 先判断文件类型 */
		fileName = fileName.toLowerCase();
		for (String fileType : allowedTypes) {
			if (fileName.endsWith(fileType)) {
				isImage = true;

				if (logger.isDebugEnabled()) {
					logger.debug(fileName + " 文件类型检查通过...");
				}
				break;
			}
		}
		return isImage;
	}

}
