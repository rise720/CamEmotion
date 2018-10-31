package com.cac.CamEmotion.model;

import java.util.List;

import com.cac.CamEmotion.util.MemcacheUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaceAnalysisConfigExtend extends FaceAnalysisConfig {
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

	private List<FaceIdentificationConfigExtend> listFaceIdentificationConfig;

	public List<FaceIdentificationConfigExtend> getListFaceIdentificationConfig() {
		return listFaceIdentificationConfig;
	}

	public void setListFaceIdentificationConfig(List<FaceIdentificationConfigExtend> listFaceIdentificationConfig) {
		this.listFaceIdentificationConfig = listFaceIdentificationConfig;
	}

}
