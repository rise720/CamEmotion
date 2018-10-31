package com.cac.CamEmotion.jsonModel;

import com.cac.CamEmotion.model.CurriculumTbl;

public class ClassnoModel{
	private CurriculumTbl curriculum;
	private int status;
	private String log;
	
	public CurriculumTbl getCurriculum() {
		return curriculum;
	}
	public void setCurriculum(CurriculumTbl curriculum) {
		this.curriculum = curriculum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}