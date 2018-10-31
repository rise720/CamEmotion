package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;

/**
 * 
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年2月7日
 */
public class UpLoad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String retCode;				//返回码
	private String retMsg;				//返回码对应的信息
	private String uploadtype;			//上传类别 附件作用(0：老师头像)
	private String relativePath;		//文件存储相对地址
	private String webUrl;				//web显示地址
	private String fileName;			//文件名
	private String fileType;			//附件类型(0：图片，1：音频，2：视频)
	private String base64Stream;		//文件的base64流
	
	
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getBase64Stream() {
		return base64Stream;
	}

	public void setBase64Stream(String base64Stream) {
		this.base64Stream = base64Stream;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getUploadtype() {
		return uploadtype;
	}

	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
