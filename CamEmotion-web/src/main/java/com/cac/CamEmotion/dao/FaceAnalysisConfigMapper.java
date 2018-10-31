package com.cac.CamEmotion.dao;

import java.util.List;

import com.cac.CamEmotion.model.FaceAnalysisConfig;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;

public interface FaceAnalysisConfigMapper {
    int deleteByPrimaryKey(Integer hostno);

    int insert(FaceAnalysisConfig record);

    int insertSelective(FaceAnalysisConfig record);

    FaceAnalysisConfig selectByPrimaryKey(Integer hostno);

    int updateByPrimaryKeySelective(FaceAnalysisConfig record);

    int updateByPrimaryKey(FaceAnalysisConfig record);
    
    /**
     * 根据教室id更新课程id
     * @param record
     * @return 变更条数
     */
    int updateByPrimaryClassroomId(FaceAnalysisConfig record);
    
    List<FaceAnalysisConfigExtend> findList(FaceAnalysisConfig record);
}