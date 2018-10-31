package com.cac.CamEmotion.model;

import com.cac.CamEmotion.util.MemcacheUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaceIdentificationConfigExtend extends FaceIdentificationConfig{

    /*
     * 定义该主机对应的memcached访问工具
     */
    @JsonIgnore
    private MemcacheUtil memcacheUtil;
    public MemcacheUtil getMemcacheUtil() {
		return memcacheUtil;
	}

	public void setMemcacheUtil(MemcacheUtil memcacheUtil) {
		this.memcacheUtil = memcacheUtil;
	}
}
