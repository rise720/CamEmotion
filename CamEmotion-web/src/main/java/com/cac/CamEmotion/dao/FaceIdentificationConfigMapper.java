package com.cac.CamEmotion.dao;

import java.util.List;

import com.cac.CamEmotion.model.FaceIdentificationConfig;
import com.cac.CamEmotion.model.FaceIdentificationConfigExtend;

public interface FaceIdentificationConfigMapper {
	int deleteByPrimaryKey(Integer hostno);
	
	int deleteByPrimaryAnalysisNo(Integer analysisNo);

	int insert(FaceIdentificationConfig record);

	int insertSelective(FaceIdentificationConfig record);

	FaceIdentificationConfig selectByPrimaryKey(Integer hostno);

	int updateByPrimaryKeySelective(FaceIdentificationConfig record);
	
	int updateByPrimaryAnalysisNoSelective(FaceIdentificationConfig record);

	int updateByPrimaryKey(FaceIdentificationConfig record);

	List<FaceIdentificationConfigExtend> findList(Integer analysisNo);
}