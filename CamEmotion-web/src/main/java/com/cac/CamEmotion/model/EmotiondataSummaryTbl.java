package com.cac.CamEmotion.model;

public class EmotiondataSummaryTbl {
    private Integer id;

    private String coursename;

    private String classnature;

    private String charttype;

    private String percent;
    
    private int levels;
    

    public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

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

    public String getCharttype() {
        return charttype;
    }

    public void setCharttype(String charttype) {
        this.charttype = charttype == null ? null : charttype.trim();
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent == null ? null : percent.trim();
    }
}