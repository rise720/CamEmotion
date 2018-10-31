package com.cac.CamEmotion.model;

public class FaceIdentificationConfig {
    private Integer hostno;

    private String hostip;

    private String hostname;

    private Integer analysisNo;

    private String analysisIp;

	private Integer faceidentitime;

    private Integer maxclusters;

    private Integer kmeanorfaceid;

    private Integer insertdbcount;
    
    
	public String getAnalysisIp() {
		return analysisIp;
	}

	public void setAnalysisIp(String analysisIp) {
		this.analysisIp = analysisIp;
	}
    
    public Integer getHostno() {
        return hostno;
    }

    public void setHostno(Integer hostno) {
        this.hostno = hostno;
    }

    public String getHostip() {
        return hostip;
    }

    public void setHostip(String hostip) {
        this.hostip = hostip == null ? null : hostip.trim();
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname == null ? null : hostname.trim();
    }

    public Integer getAnalysisNo() {
		return analysisNo;
	}

	public void setAnalysisNo(Integer analysisNo) {
		this.analysisNo = analysisNo;
	}

	public Integer getFaceidentitime() {
        return faceidentitime;
    }

    public void setFaceidentitime(Integer faceidentitime) {
        this.faceidentitime = faceidentitime;
    }

    public Integer getMaxclusters() {
        return maxclusters;
    }

    public void setMaxclusters(Integer maxclusters) {
        this.maxclusters = maxclusters;
    }

    public Integer getKmeanorfaceid() {
        return kmeanorfaceid;
    }

    public void setKmeanorfaceid(Integer kmeanorfaceid) {
        this.kmeanorfaceid = kmeanorfaceid;
    }

    public Integer getInsertdbcount() {
        return insertdbcount;
    }

    public void setInsertdbcount(Integer insertdbcount) {
        this.insertdbcount = insertdbcount;
    }
}