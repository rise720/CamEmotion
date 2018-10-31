package com.cac.CamEmotion.dao;

import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.FacefeatureTbl;

public interface FacefeatureTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FacefeatureTbl record);

    int insertSelective(FacefeatureTbl record);

    FacefeatureTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FacefeatureTbl record);

    int updateByPrimaryKeyWithBLOBs(FacefeatureTbl record);

    int updateByPrimaryKey(FacefeatureTbl record);
    
    int initUpdate(CurriculumTbl curriculumTbl);
    
    /**
     * 
     * 
     * 方法功能说明: 
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年7月10日
     * @param record
     * @return
     *
     */
    int updateFacefeature(FacefeatureTbl record);
    
    /**
     * 
     * 
     * 方法功能说明: 
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年7月10日
     * @param record
     * @return
     *
     */
    int deleteFacefeature(FacefeatureTbl record);
    
    /**
     * 
     * 
     * 方法功能说明: 根据课程Id删除表数据
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年7月10日
     * @param courseId 课程Id
     * @return
     *
     */
    int deleteByPrimaryCourseId(Integer courseId);
}