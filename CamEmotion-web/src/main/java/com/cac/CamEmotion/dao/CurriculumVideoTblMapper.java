package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.paging.PageList;

public interface CurriculumVideoTblMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CurriculumVideoTbl record);

	int insertSelective(CurriculumVideoTbl record);

	CurriculumVideoTbl selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CurriculumVideoTbl record);

	int updateByPrimaryKey(CurriculumVideoTbl record);

	/**
	 * 获取某一节课的视频资源
	 */
	List<CurriculumVideoTbl> selectCurriculumVideoList(CurriculumVideoTbl record);

	/**
	 * 课程视频的 分页逻辑
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
	List<CurriculumVideoTbl> find(@Param("Curriculum") CurriculumVideoTbl Curriculum,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);

	/**
	 * 合计
	 */
	int selectCurriculumVideoCount(CurriculumVideoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 按课堂id删除对应视频记录
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param id
	 * @return
	 *
	 */
	int deleteByCurriculumId(Integer id);
}