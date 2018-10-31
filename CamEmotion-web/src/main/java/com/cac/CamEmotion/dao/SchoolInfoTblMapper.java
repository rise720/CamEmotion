package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.SchoolInfoTbl;

public interface SchoolInfoTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolInfoTbl record);

    int insertSelective(SchoolInfoTbl record);

    SchoolInfoTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolInfoTbl record);

    int updateByPrimaryKey(SchoolInfoTbl record);
    
    List<SchoolInfoTbl> findBypagination(@Param("schoolInfo") SchoolInfoTbl schoolInfoTbl,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);
    
    int findCount(SchoolInfoTbl schoolInfoTbl);
    
    List<SchoolInfoTbl> findAll(SchoolInfoTbl schoolInfoTbl);
}