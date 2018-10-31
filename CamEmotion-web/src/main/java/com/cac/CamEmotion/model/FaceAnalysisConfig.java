package com.cac.CamEmotion.model;

public class FaceAnalysisConfig{
	private Integer hostno;

	private String hostname;

	private String hostip;

	private String cameraip;
	
	private Integer courseId;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	private String camaddrm;

	private String camaddra;

	private Integer drawdisplay;

	private Integer facerectangle;

	private Integer recordingaudio;

	private Integer camerafiltertime;

	private Double audio;

	private Integer fillpolysize;

	private String fillpoly;

	private Integer processframerate;

	private Integer buffersize;

	private Integer maxnumfaces;

	private Integer faceconfig;

	private Integer saveframeimage;

	private String shareaddr;

	private Integer classroomId;
	
	/**
	 * 是否填写默认特征提取服务器
	 * 0不填写默认 1填写默认
	 */
	private Integer identificationFlag;

	public Integer getIdentificationFlag() {
		return identificationFlag;
	}

	public void setIdentificationFlag(Integer identificationFlag) {
		this.identificationFlag = identificationFlag;
	}

	public Integer getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
	}

	public Integer getHostno() {
		return hostno;
	}

	public void setHostno(Integer hostno) {
		this.hostno = hostno;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname == null ? null : hostname.trim();
	}

	public String getHostip() {
		return hostip;
	}

	public void setHostip(String hostip) {
		this.hostip = hostip == null ? null : hostip.trim();
	}

	public String getCameraip() {
		return cameraip;
	}

	public void setCameraip(String cameraip) {
		this.cameraip = cameraip == null ? null : cameraip.trim();
	}

	public String getCamaddrm() {
		return camaddrm;
	}

	public void setCamaddrm(String camaddrm) {
		this.camaddrm = camaddrm == null ? null : camaddrm.trim();
	}

	public String getCamaddra() {
		return camaddra;
	}

	public void setCamaddra(String camaddra) {
		this.camaddra = camaddra == null ? null : camaddra.trim();
	}

	public Integer getDrawdisplay() {
		return drawdisplay;
	}

	public void setDrawdisplay(Integer drawdisplay) {
		this.drawdisplay = drawdisplay;
	}

	public Integer getFacerectangle() {
		return facerectangle;
	}

	public void setFacerectangle(Integer facerectangle) {
		this.facerectangle = facerectangle;
	}

	public Integer getRecordingaudio() {
		return recordingaudio;
	}

	public void setRecordingaudio(Integer recordingaudio) {
		this.recordingaudio = recordingaudio;
	}

	public Integer getCamerafiltertime() {
		return camerafiltertime;
	}

	public void setCamerafiltertime(Integer camerafiltertime) {
		this.camerafiltertime = camerafiltertime;
	}

	public Double getAudio() {
		return audio;
	}

	public void setAudio(Double audio) {
		this.audio = audio;
	}

	public Integer getFillpolysize() {
		return fillpolysize;
	}

	public void setFillpolysize(Integer fillpolysize) {
		this.fillpolysize = fillpolysize;
	}

	public String getFillpoly() {
		return fillpoly;
	}

	public void setFillpoly(String fillpoly) {
		this.fillpoly = fillpoly == null ? null : fillpoly.trim();
	}

	public Integer getProcessframerate() {
		return processframerate;
	}

	public void setProcessframerate(Integer processframerate) {
		this.processframerate = processframerate;
	}

	public Integer getBuffersize() {
		return buffersize;
	}

	public void setBuffersize(Integer buffersize) {
		this.buffersize = buffersize;
	}

	public Integer getMaxnumfaces() {
		return maxnumfaces;
	}

	public void setMaxnumfaces(Integer maxnumfaces) {
		this.maxnumfaces = maxnumfaces;
	}

	public Integer getFaceconfig() {
		return faceconfig;
	}

	public void setFaceconfig(Integer faceconfig) {
		this.faceconfig = faceconfig;
	}

	public Integer getSaveframeimage() {
		return saveframeimage;
	}

	public void setSaveframeimage(Integer saveframeimage) {
		this.saveframeimage = saveframeimage;
	}

	public String getShareaddr() {
		return shareaddr;
	}

	public void setShareaddr(String shareaddr) {
		this.shareaddr = shareaddr == null ? null : shareaddr.trim();
	}
}