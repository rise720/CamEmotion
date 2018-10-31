package com.cac.CamEmotion.model;

import java.util.List;

public class CameraReadyModel {

	private int curriculumId;
	private SystemConfig systemConfig;
	private List<FaceAnalysisConfigExtend> hostList;
	
	public int getCurriculumId() {
		return curriculumId;
	}
	public void setCurriculumId(int curriculumId) {
		this.curriculumId = curriculumId;
	}
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	public List<FaceAnalysisConfigExtend> getHostList() {
		return hostList;
	}
	public void setHostList(List<FaceAnalysisConfigExtend> hostList) {
		this.hostList = hostList;
	}
	 
}
