package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.Date;

public class EmotionaldataTbl implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private Integer curriculumid;

    private Date createdate;

    private String cameraip;

    private Long frameid;

    private Integer faceid;

    private int facecategory;

    private String faceimageaddr;

    private Integer keyface;

    private Double pitch;

    private Double yaw;

    private Double roll;

    private Double joy;

    private Double sadness;

    private Double disgust;

    private Double contempt;

    private Double anger;

    private Double surprise;

    private Double fear;

    private Double valence;

    private Double valenceview;

    private Double engagement;

    private Double concentration;

    private Double confused;

    private Double fatiguedegree;

    private Double lookaround;

    private Double lookdown;

    private Double sleepeducation;

    private Double speak;

    private Double strophocephaly;

    private Double strophocephalyspeak;

    private Double yawn;

    private Double attention;

    private Double browfurrow;

    private Double browraise;

    private Double cheekraise;

    private Double chinraise;

    private Double dimpler;

    private Double eyeclosure;

    private Double eyewiden;

    private Double innerbrowraise;

    private Double jawdrop;

    private Double lidtighten;

    private Double lipcornerdepressor;

    private Double lippress;

    private Double lippucker;

    private Double lipstretch;

    private Double lipsuck;

    private Double mouthopen;

    private Double nosewrinkle;

    private Double smile;

    private Double smirk;

    private Double upperlipraise;

    private String json;
    
    private Long incidencekey;
    
    private int frameFaceCount;
    
    private int faceCount;
    
    //20171213
    private String emotionTableName;
    
    public String getEmotionTableName() {
		return emotionTableName;
	}

	public void setEmotionTableName(String emotionTableName) {
		this.emotionTableName = emotionTableName;
	}

	public int getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}

	public int getFrameFaceCount() {
		return frameFaceCount;
	}

	public void setFrameFaceCount(int frameFaceCount) {
		this.frameFaceCount = frameFaceCount;
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

    public String getCameraip() {
        return cameraip;
    }

    public void setCameraip(String cameraip) {
        this.cameraip = cameraip == null ? null : cameraip.trim();
    }

    public Integer getFaceid() {
        return faceid;
    }

    public void setFaceid(Integer faceid) {
        this.faceid = faceid;
    }

    public Double getJoy() {
        return joy;
    }

    public void setJoy(Double joy) {
        this.joy = joy;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    public Double getContempt() {
        return contempt;
    }

    public void setContempt(Double contempt) {
        this.contempt = contempt;
    }

    public Double getAnger() {
        return anger;
    }

    public void setAnger(Double anger) {
        this.anger = anger;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }

    public Double getEngagement() {
        return engagement;
    }

    public void setEngagement(Double engagement) {
        this.engagement = engagement;
    }

    public Double getConcentration() {
        return concentration;
    }

    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public Double getConfused() {
        return confused;
    }

    public void setConfused(Double confused) {
        this.confused = confused;
    }

    public Double getFatiguedegree() {
        return fatiguedegree;
    }

    public void setFatiguedegree(Double fatiguedegree) {
        this.fatiguedegree = fatiguedegree;
    }

    public Double getLookaround() {
        return lookaround;
    }

    public void setLookaround(Double lookaround) {
        this.lookaround = lookaround;
    }

    public Double getLookdown() {
        return lookdown;
    }

    public void setLookdown(Double lookdown) {
        this.lookdown = lookdown;
    }

    public Double getSleepeducation() {
        return sleepeducation;
    }

    public void setSleepeducation(Double sleepeducation) {
        this.sleepeducation = sleepeducation;
    }

    public Double getSpeak() {
        return speak;
    }

    public void setSpeak(Double speak) {
        this.speak = speak;
    }

    public Double getStrophocephaly() {
        return strophocephaly;
    }

    public void setStrophocephaly(Double strophocephaly) {
        this.strophocephaly = strophocephaly;
    }

    public Double getStrophocephalyspeak() {
        return strophocephalyspeak;
    }

    public void setStrophocephalyspeak(Double strophocephalyspeak) {
        this.strophocephalyspeak = strophocephalyspeak;
    }

    public Double getYawn() {
        return yawn;
    }

    public void setYawn(Double yawn) {
        this.yawn = yawn;
    }

    public Double getAttention() {
        return attention;
    }

    public void setAttention(Double attention) {
        this.attention = attention;
    }

    public Double getBrowfurrow() {
        return browfurrow;
    }

    public void setBrowfurrow(Double browfurrow) {
        this.browfurrow = browfurrow;
    }

    public Double getBrowraise() {
        return browraise;
    }

    public void setBrowraise(Double browraise) {
        this.browraise = browraise;
    }

    public Double getCheekraise() {
        return cheekraise;
    }

    public void setCheekraise(Double cheekraise) {
        this.cheekraise = cheekraise;
    }

    public Double getChinraise() {
        return chinraise;
    }

    public void setChinraise(Double chinraise) {
        this.chinraise = chinraise;
    }

    public Double getDimpler() {
        return dimpler;
    }

    public void setDimpler(Double dimpler) {
        this.dimpler = dimpler;
    }

    public Double getEyeclosure() {
        return eyeclosure;
    }

    public void setEyeclosure(Double eyeclosure) {
        this.eyeclosure = eyeclosure;
    }

    public Double getEyewiden() {
        return eyewiden;
    }

    public void setEyewiden(Double eyewiden) {
        this.eyewiden = eyewiden;
    }

    public Double getInnerbrowraise() {
        return innerbrowraise;
    }

    public void setInnerbrowraise(Double innerbrowraise) {
        this.innerbrowraise = innerbrowraise;
    }

    public Double getJawdrop() {
        return jawdrop;
    }

    public void setJawdrop(Double jawdrop) {
        this.jawdrop = jawdrop;
    }

    public Double getLidtighten() {
        return lidtighten;
    }

    public void setLidtighten(Double lidtighten) {
        this.lidtighten = lidtighten;
    }

    public Double getLipcornerdepressor() {
        return lipcornerdepressor;
    }

    public void setLipcornerdepressor(Double lipcornerdepressor) {
        this.lipcornerdepressor = lipcornerdepressor;
    }

    public Double getLippress() {
        return lippress;
    }

    public void setLippress(Double lippress) {
        this.lippress = lippress;
    }

    public Double getLippucker() {
        return lippucker;
    }

    public void setLippucker(Double lippucker) {
        this.lippucker = lippucker;
    }

    public Double getLipstretch() {
        return lipstretch;
    }

    public void setLipstretch(Double lipstretch) {
        this.lipstretch = lipstretch;
    }

    public Double getLipsuck() {
        return lipsuck;
    }

    public void setLipsuck(Double lipsuck) {
        this.lipsuck = lipsuck;
    }

    public Double getMouthopen() {
        return mouthopen;
    }

    public void setMouthopen(Double mouthopen) {
        this.mouthopen = mouthopen;
    }

    public Double getNosewrinkle() {
        return nosewrinkle;
    }

    public void setNosewrinkle(Double nosewrinkle) {
        this.nosewrinkle = nosewrinkle;
    }

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }

    public Double getSmirk() {
        return smirk;
    }

    public void setSmirk(Double smirk) {
        this.smirk = smirk;
    }

    public Double getUpperlipraise() {
        return upperlipraise;
    }

    public void setUpperlipraise(Double upperlipraise) {
        this.upperlipraise = upperlipraise;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Long getFrameid() {
		return frameid;
	}

	public void setFrameid(Long frameid) {
		this.frameid = frameid;
	}

	public int getFacecategory() {
		return facecategory;
	}

	public void setFacecategory(int facecategory) {
		this.facecategory = facecategory;
	}

	public String getFaceimageaddr() {
		return faceimageaddr;
	}

	public void setFaceimageaddr(String faceimageaddr) {
		this.faceimageaddr = faceimageaddr;
	}

	public Integer getKeyface() {
		return keyface;
	}

	public void setKeyface(Integer keyface) {
		this.keyface = keyface;
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

	public Double getValenceview() {
		return valenceview;
	}

	public void setValenceview(Double valenceview) {
		this.valenceview = valenceview;
	}

	public Long getIncidencekey() {
		return incidencekey;
	}

	public void setIncidencekey(Long incidencekey) {
		this.incidencekey = incidencekey;
	}
	
}