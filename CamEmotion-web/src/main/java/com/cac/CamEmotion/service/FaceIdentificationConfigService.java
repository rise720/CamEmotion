package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.FaceIdentificationConfig;

public interface FaceIdentificationConfigService {
    int deleteByPrimaryKey(Integer hostno);

    int deleteByPrimaryAnalysisNo(Integer analysisNo);
    
    int insert(FaceIdentificationConfig record);

    int insertSelective(FaceIdentificationConfig record);

    FaceIdentificationConfig selectByPrimaryKey(Integer hostno);

    int updateByPrimaryKeySelective(FaceIdentificationConfig record);
    
    int updateByPrimaryAnalysisNoSelective(FaceIdentificationConfig record);

    int updateByPrimaryKey(FaceIdentificationConfig record);
}