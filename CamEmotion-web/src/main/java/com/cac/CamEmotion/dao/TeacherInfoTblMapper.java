package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.TeacherInfoTbl;

public interface TeacherInfoTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherInfoTbl record);

    int insertSelective(TeacherInfoTbl record);

    TeacherInfoTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherInfoTbl record);

    int updateByPrimaryKey(TeacherInfoTbl record);
    
    List<TeacherInfoTbl> findBypagination(@Param("teacherInfo") TeacherInfoTbl teacherInfoTbl,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);
    
    int findCount(TeacherInfoTbl teacherInfoTbl);
    
    List<TeacherInfoTbl> findAll(TeacherInfoTbl teacherInfoTbl);
}