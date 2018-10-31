package com.cac.CamEmotion.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.jsonModel.UpLoad;
import com.cac.CamEmotion.springmvc.model.Response;
import com.cac.CamEmotion.util.UpLoadUtil;

/**
 * 
 * 
 * 类说明: 文件上传服务
 * <P>
 * 上传文件存放在临时目录下,这个目录由“system.properties”中的system.homepath指定 上传成功 返回json数据:
 * retCode: 返回码( = 0) relativePath: 上传文件存放的临时目录 webUrl: 上传文件存放的临时Web目录
 * 上传失败返回json数据: retCode: 返回码 (< 0)
 * </P>
 *
 * @author zhangsh
 * @data 2016年10月27日
 */
@RestController
@RequestMapping(value = "/rest/upLoadApi")
public class UpLoadAPI {
	private static Logger logger = LogManager.getLogger(UpLoadAPI.class);

	/**
	 * 
	 * 
	 * 方法功能说明:
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param request
	 * @param files
	 * @param uploadtype 1  3其他
	 * @return
	 *
	 */
	@RequestMapping(value = "/upLoad", produces = "application/json; charset=UTF-8")
	public Response<UpLoad> upLoadVote(HttpServletRequest request, MultipartFile files, String uploadtype) {
		UpLoad rstl = new UpLoad();
		logger.info("上传文件:" + files.getOriginalFilename() + " 文件类型:" + files.getContentType() + " 类别:" + uploadtype);
		String nFileName = "";
		String fileType = "0";
		try {
			if (!uploadtype.equals("3")) {
				if (files.getContentType().indexOf("image") == -1){
					return new Response<UpLoad>().failure("", "文件类型错误");
				}
			}else{
				if (files.getContentType().indexOf("image") != -1){
					fileType = "0";
				}else if(files.getContentType().indexOf("audio") != -1){
					fileType = "1";
				}else if(files.getContentType().indexOf("video") != -1){
					fileType = "2";
				}else{
					return new Response<UpLoad>().failure("", "文件类型错误");
				}
			}
			String originalFilename = files.getOriginalFilename(); // 原文件名
			nFileName += uploadtype + "_" + DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
			// 取扩展文件名
			nFileName += originalFilename.lastIndexOf(".") > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
			if (logger.isInfoEnabled()) {
				logger.info("新文件名:" + nFileName);
			}
			// 临时路径
			String relativePath = UpLoadUtil.fileUpload(nFileName, files.getInputStream(), uploadtype);
			// 数据校验
			int retCode = UpLoadUtil.check(nFileName, relativePath, uploadtype, files.getSize());
			if (retCode == -2) {
				return new Response<UpLoad>().failure(retCode + "", "上传文件大小不能超过100KB");
			}else if(retCode == -3){
				return new Response<UpLoad>().failure(retCode + "", "上传文件名不能超过50个字符");
			}else if(retCode == -1){
				return new Response<UpLoad>().failure(retCode + "", "上传文件类型不支持");
			}
			
			rstl.setUploadtype(uploadtype); // 类别
			rstl.setRetCode(String.valueOf(retCode)); // 返回码
			rstl.setRelativePath(relativePath); // 文件存储相对地址
			rstl.setWebUrl(UpLoadUtil.getWebUrl(relativePath)); // web
			rstl.setFileName(originalFilename); // 文件名
			rstl.setFileType(fileType); // 文件类型
		} catch (IOException e) {
			logger.error(e);
			return new Response<UpLoad>().failure("20001", e.getMessage());
		}catch (Exception e) {
			logger.error(e);
			return new Response<UpLoad>().failure("20001", e.getMessage());
		}
		return new Response<UpLoad>().success(rstl);
	}
}
