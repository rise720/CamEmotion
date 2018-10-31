package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.Date;

public class EmotiondataStatistics implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer curriculumid;

    private String charttype;

    private Integer minutes;

    private String datetime;

    private Double value;

    private Integer number;

    private String percent;

    private Integer level;

    private String name;

    private Integer facecount;

    private Double averagefacecount;

    private Double averagevalueall;

    private Double facecountpercent;

    private Date createtime;
    
    private Integer playduration;
    
    private String statisticType;
    
    
	public String getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}

	public Integer getPlayduration() {
		return playduration;
	}

	public void setPlayduration(Integer playduration) {
		this.playduration = playduration;
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

    public String getCharttype() {
        return charttype;
    }

    public void setCharttype(String charttype) {
        this.charttype = charttype == null ? null : charttype.trim();
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent == null ? null : percent.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFacecount() {
        return facecount;
    }

    public void setFacecount(Integer facecount) {
        this.facecount = facecount;
    }

    public Double getAveragefacecount() {
        return averagefacecount;
    }

    public void setAveragefacecount(Double averagefacecount) {
        this.averagefacecount = averagefacecount;
    }

    public Double getAveragevalueall() {
        return averagevalueall;
    }

    public void setAveragevalueall(Double averagevalueall) {
        this.averagevalueall = averagevalueall;
    }

    public Double getFacecountpercent() {
        return facecountpercent;
    }

    public void setFacecountpercent(Double facecountpercent) {
        this.facecountpercent = facecountpercent;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    } 
}