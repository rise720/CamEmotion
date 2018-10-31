package com.cac.CamEmotion.model;

import java.util.Date;

public class SequenceTbl {
    private String seqKey;

    private Long initValue;

    private Long length;

    private Integer step;

    private Integer cacheSize;

    private String prefix;

    private Date timestamp;

    public String getSeqKey() {
        return seqKey;
    }

    public void setSeqKey(String seqKey) {
        this.seqKey = seqKey == null ? null : seqKey.trim();
    }

    public Long getInitValue() {
        return initValue;
    }

    public void setInitValue(Long initValue) {
        this.initValue = initValue;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(Integer cacheSize) {
        this.cacheSize = cacheSize;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String toString(){
    	return "seqKey:" + seqKey + ", initValue:" + initValue 
    			+ ", length:" + length + ", step:" + step 
    			+ ", cacheSize:" + cacheSize + ", prefix:" + prefix;
    }
}