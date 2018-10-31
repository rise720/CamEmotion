package com.cac.CamEmotion.model;

import java.util.Date;

public class FacefeatureTbl {
    private int id;

    private Integer courseid;

    private String cameraip;

    private Integer facecategory;
    
    private Integer faceCategoryCombine;

    private Integer originfacecategory;

    private Long frameid;

    private Integer faceid;

    private Integer facecategoryx;

    private Integer facecategoryy;

    private String faceimageaddr;

    private Double pitch;

    private Double yaw;

    private Double roll;

    private Integer isdel;
    
    private Long incidencekey;

    private byte[] feature;
    
    /**
     * @author chenyang
     * @date 
     */
    private String facecategorys;
    
    
    
    
    
    public Integer getFaceCategoryCombine() {
		return faceCategoryCombine;
	}

	public void setFaceCategoryCombine(Integer faceCategoryCombine) {
		this.faceCategoryCombine = faceCategoryCombine;
	}

	public String getFacecategorys() {
		return facecategorys;
	}

	public void setFacecategorys(String facecategorys) {
		this.facecategorys = facecategorys;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public String getCameraip() {
        return cameraip;
    }

    public void setCameraip(String cameraip) {
        this.cameraip = cameraip == null ? null : cameraip.trim();
    }

    public Integer getFacecategory() {
        return facecategory;
    }

    public void setFacecategory(Integer facecategory) {
        this.facecategory = facecategory;
    }

    public Integer getOriginfacecategory() {
        return originfacecategory;
    }

    public void setOriginfacecategory(Integer originfacecategory) {
        this.originfacecategory = originfacecategory;
    }

    public Long getFrameid() {
        return frameid;
    }

    public void setFrameid(Long frameid) {
        this.frameid = frameid;
    }

    public Integer getFaceid() {
        return faceid;
    }

    public void setFaceid(Integer faceid) {
        this.faceid = faceid;
    }

    public Integer getFacecategoryx() {
        return facecategoryx;
    }

    public void setFacecategoryx(Integer facecategoryx) {
        this.facecategoryx = facecategoryx;
    }

    public Integer getFacecategoryy() {
        return facecategoryy;
    }

    public void setFacecategoryy(Integer facecategoryy) {
        this.facecategoryy = facecategoryy;
    }

    public String getFaceimageaddr() {
        return faceimageaddr;
    }

    public void setFaceimageaddr(String faceimageaddr) {
        this.faceimageaddr = faceimageaddr == null ? null : faceimageaddr.trim();
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public byte[] getFeature() {
        return feature;
    }

    public void setFeature(byte[] feature) {
        this.feature = feature;
    }

	public Long getIncidencekey() {
		return incidencekey;
	}

	public void setIncidencekey(Long incidencekey) {
		this.incidencekey = incidencekey;
	}
}