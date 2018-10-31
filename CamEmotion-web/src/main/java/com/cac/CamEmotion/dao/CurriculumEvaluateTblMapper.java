package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.CurriculumEvaluateTbl;

public interface CurriculumEvaluateTblMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByCurricIdPrimaryKey(Integer id);

    int insert(CurriculumEvaluateTbl record);

    int insertSelective(CurriculumEvaluateTbl record);

    CurriculumEvaluateTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CurriculumEvaluateTbl record);

    int updateByPrimaryKey(CurriculumEvaluateTbl record);
    
    
    /**
	 * 课程的 分页逻辑
	 * 
	 * @param record
	 * @param limit_offset
	 *            偏离量
	 * @param limit_rows
	 *            显示行数
	 * @param orderBy_expression
	 *            排序字段
	 * @param orderBy_rule
	 *            排序规则
	 * @return
	 */
	List<CurriculumEvaluateTbl> find(@Param("CurriculumEvaluate") CurriculumEvaluateTbl Curriculum,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);

	/**
	 * 合计
	 */
	int selectCount(CurriculumEvaluateTbl record);
}