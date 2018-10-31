package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmotiondataStudentsStatistics;

public interface EmotiondataStudentsStatisticsMapper {
    int deleteByModel(@Param("emotiondataStudentsStatistics")EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);

    int insert(@Param("emotiondataStudentsStatistics")EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);

    int insertSelective(@Param("emotiondataStudentsStatistics")EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);
    
    int batchInsert(@Param("emotionStudents") List<EmotiondataStudentsStatistics> record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);
    
    List<EmotiondataStudentsStatistics> selective(@Param("emotiondataStudentsStatistics")EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);
    
    List<Integer> getStudentsList(CurriculumTbl curriculumTbl);

//    int updateByPrimaryKeySelective(EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);
//
//    int updateByPrimaryKey(EmotiondataStudentsStatistics record, @Param("curriculumTbl")CurriculumTbl curriculumTbl);
    
}