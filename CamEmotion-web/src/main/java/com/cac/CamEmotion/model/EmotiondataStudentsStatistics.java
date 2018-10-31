package com.cac.CamEmotion.model;

import java.util.Date;

public class EmotiondataStudentsStatistics {
    private Integer id;

    private Integer curriculumid;

    private Integer studentid;

    private String charttype;

    private Integer lasttime;

    private String currenttime;

    private String value;
    
    private String joy;

    private String sadness;

    private String disgust;

    private String contempt;

    private String anger;

    private String surprise;

    private String fear;
    
    private String averageValue;

    private Integer number;

    private String percent;

    private Integer level;
    
    private Integer actionLevel;
    
    private Integer attentionLevel;
    
    private Integer playduration;

    private Date timestamp;
    
    private String statisticType;
    
    private String tableName;
    
    private String studentids;
    
    
    public String getStudentids() {
		return studentids;
	}

	public void setStudentids(String studentids) {
		this.studentids = studentids;
	}

	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getStatisticType() {
		return statisticType;
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

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getCharttype() {
        return charttype;
    }

    public void setCharttype(String charttype) {
        this.charttype = charttype == null ? null : charttype.trim();
    }

    public Integer getLasttime() {
        return lasttime;
    }

    public void setLasttime(Integer lasttime) {
        this.lasttime = lasttime;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime == null ? null : currenttime.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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

    public Integer getPlayduration() {
        return playduration;
    }

    public void setPlayduration(Integer playduration) {
        this.playduration = playduration;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

	public Integer getActionLevel() {
		return actionLevel;
	}

	public void setActionLevel(Integer actionLevel) {
		this.actionLevel = actionLevel;
	}

	public Integer getAttentionLevel() {
		return attentionLevel;
	}

	public void setAttentionLevel(Integer attentionLevel) {
		this.attentionLevel = attentionLevel;
	}

	public String getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(String averageValue) {
		this.averageValue = averageValue;
	}

	public String getJoy() {
		return joy;
	}

	public void setJoy(String joy) {
		this.joy = joy;
	}

	public String getSadness() {
		return sadness;
	}

	public void setSadness(String sadness) {
		this.sadness = sadness;
	}

	public String getDisgust() {
		return disgust;
	}

	public void setDisgust(String disgust) {
		this.disgust = disgust;
	}

	public String getContempt() {
		return contempt;
	}

	public void setContempt(String contempt) {
		this.contempt = contempt;
	}

	public String getAnger() {
		return anger;
	}

	public void setAnger(String anger) {
		this.anger = anger;
	}

	public String getSurprise() {
		return surprise;
	}

	public void setSurprise(String surprise) {
		this.surprise = surprise;
	}

	public String getFear() {
		return fear;
	}

	public void setFear(String fear) {
		this.fear = fear;
	}
}