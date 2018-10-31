package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.ComprehensiveReportTbl;

public interface ReportTblMapper {

	/**
	 * 根据课程id查询每分钟的平均率
	 * 
	 * @param curriculumid
	 * @return
	 */
	List<Double> selectMinutesWaveAverageRate(Integer curriculumid);

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
	List<ComprehensiveReportTbl> find(@Param("ComprehensiveReport") ComprehensiveReportTbl Curriculum,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression, @Param("orderBy_rule") String orderBy_rule);

	/**
	 * 合计
	 */
	int selectCount(ComprehensiveReportTbl record);
}