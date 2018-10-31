package com.cac.CamEmotion.model;

import java.sql.Date;

public class CurriculumEvaluateTbl {
    private Integer id;

    private Integer curriculumid;

    private String evaluatorinfo;

    private String evaluatecontent;

    private String fileaddr;

    private Integer filetype;
    
    private Date timestamp;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurriculumid() {
        return curriculumid;
    }

    public void setCurriculumid(Integer curriculumid) {
        this.curriculumid = curriculumid;
    }

    public String getEvaluatorinfo() {
        return evaluatorinfo;
    }

    public void setEvaluatorinfo(String evaluatorinfo) {
        this.evaluatorinfo = evaluatorinfo == null ? null : evaluatorinfo.trim();
    }

    public String getEvaluatecontent() {
        return evaluatecontent;
    }

    public void setEvaluatecontent(String evaluatecontent) {
        this.evaluatecontent = evaluatecontent == null ? null : evaluatecontent.trim();
    }

    public String getFileaddr() {
        return fileaddr;
    }

    public void setFileaddr(String fileaddr) {
        this.fileaddr = fileaddr == null ? null : fileaddr.trim();
    }

    public Integer getFiletype() {
        return filetype;
    }

    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }
}