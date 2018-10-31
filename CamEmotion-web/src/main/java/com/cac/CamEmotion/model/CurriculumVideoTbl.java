package com.cac.CamEmotion.model;

import java.util.Date;

public class CurriculumVideoTbl {
    private Integer id;

    private Integer curriculumid;

    private String videourl;

    private String videoimgurl;

    private Date createdate;

    private String cameraip;

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

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl == null ? null : videourl.trim();
    }

    public String getVideoimgurl() {
        return videoimgurl;
    }

    public void setVideoimgurl(String videoimgurl) {
        this.videoimgurl = videoimgurl == null ? null : videoimgurl.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCameraip() {
        return cameraip;
    }

    public void setCameraip(String cameraip) {
        this.cameraip = cameraip == null ? null : cameraip.trim();
    }
}