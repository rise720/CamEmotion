package com.cac.CamEmotion.model;

public class StatisticalStandardTbl {
    private Integer id;

    private String coursename;

    private String classnature;

    private String itemtype;

    private Double levelone;

    private Double leveltwo;

    private Double levelthree;

    private Double levelfour;

    private String projects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename == null ? null : coursename.trim();
    }

    public String getClassnature() {
        return classnature;
    }

    public void setClassnature(String classnature) {
        this.classnature = classnature == null ? null : classnature.trim();
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype == null ? null : itemtype.trim();
    }

    public Double getLevelone() {
        return levelone;
    }

    public void setLevelone(Double levelone) {
        this.levelone = levelone;
    }

    public Double getLeveltwo() {
        return leveltwo;
    }

    public void setLeveltwo(Double leveltwo) {
        this.leveltwo = leveltwo;
    }

    public Double getLevelthree() {
        return levelthree;
    }

    public void setLevelthree(Double levelthree) {
        this.levelthree = levelthree;
    }

    public Double getLevelfour() {
        return levelfour;
    }

    public void setLevelfour(Double levelfour) {
        this.levelfour = levelfour;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects == null ? null : projects.trim();
    }
}