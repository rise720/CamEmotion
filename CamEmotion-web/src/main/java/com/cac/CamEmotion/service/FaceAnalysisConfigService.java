package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.model.FaceAnalysisConfig;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;

public interface FaceAnalysisConfigService {
    int deleteByPrimaryKey(Integer hostno);

    int insert(FaceAnalysisConfig record);

    int insertSelective(FaceAnalysisConfig record);

    FaceAnalysisConfig selectByPrimaryKey(Integer hostno);

    int updateByPrimaryKeySelective(FaceAnalysisConfig record);

    int updateByPrimaryKey(FaceAnalysisConfig record);

    List<FaceAnalysisConfigExtend> findList(FaceAnalysisConfig record);
}