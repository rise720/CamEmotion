package com.cac.CamEmotion.jsonModel;

import java.util.List;

public class FrameInfo {
	private Long frameId;
	
	private Integer courseId;
	
	private String cameraIp;
	
	private Integer wPixel;
	
	private Integer hPixel;

	private List<FaceInfo> faceList;

	public Integer getwPixel() {
		return wPixel;
	}

	public void setwPixel(Integer wPixel) {
		this.wPixel = wPixel;
	}

	public Integer gethPixel() {
		return hPixel;
	}

	public void sethPixel(Integer hPixel) {
		this.hPixel = hPixel;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Long getFrameId() {
		return frameId;
	}

	public void setFrameId(Long frameId) {
		this.frameId = frameId;
	}

	public List<FaceInfo> getFaceList() {
		return faceList;
	}

	public String getCameraIp() {
		return cameraIp;
	}

	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}

	public void setFaceList(List<FaceInfo> faceList) {
		this.faceList = faceList;
	}

}
