/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.cac.CamEmotion.model.EmojiBubble;

/**
 * 
 * 类功能说明:
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2017年12月5日
 */
public class ReportData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	private String imagePng;
	
	private File imageFile;

	private String dataVal1;
	
	private String dataVal2;
	
	private String dataVal3;
	
	private String dataVal4;

	public String getDataVal1() {
		return dataVal1;
	}

	public void setDataVal1(String dataVal1) {
		this.dataVal1 = dataVal1;
	}

	public String getDataVal2() {
		return dataVal2;
	}

	public void setDataVal2(String dataVal2) {
		this.dataVal2 = dataVal2;
	}

	public String getDataVal3() {
		return dataVal3;
	}

	public void setDataVal3(String dataVal3) {
		this.dataVal3 = dataVal3;
	}

	public String getDataVal4() {
		return dataVal4;
	}

	public void setDataVal4(String dataVal4) {
		this.dataVal4 = dataVal4;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePng() {
		return imagePng;
	}

	public void setImagePng(String imagePng) {
		this.imagePng = imagePng;
	}
}
