package com.cac.CamEmotion.model;

public class FaceContrastConfig {
    private Integer hostno;

    private String hostip;

    private String hostname;

    private Double facesimitaritythresh;

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

    public Double getFacesimitaritythresh() {
        return facesimitaritythresh;
    }

    public void setFacesimitaritythresh(Double facesimitaritythresh) {
        this.facesimitaritythresh = facesimitaritythresh;
    }
}