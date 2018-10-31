package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.jsonModel.ComparativeAnalysisModel;
import com.cac.CamEmotion.model.CurriculumTbl;

public interface CurriculumTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CurriculumTbl record);

    int insertSelective(CurriculumTbl record);

    CurriculumTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CurriculumTbl record);

    int updateByPrimaryKey(CurriculumTbl record);
    
    int updateByModelSelective(CurriculumTbl record);
    
    /**
     * 
     * 
     * 方法功能说明: 查询正在上课中的课程信息
     * <P>
     *     
     * </P>
     * 
     * @author Chenyang
     * @date 2017年10月11日
     * @param classstatus
     * @return
     *
     */
    List<CurriculumTbl> selectInClassInfo(Integer classstatus);
    
    
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
	List<CurriculumTbl> find(@Param("Curriculum") CurriculumTbl Curriculum,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);

	/**
	 * 合计
	 */
	int selectCurriculumCount(CurriculumTbl record);
	
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询满足条件的课程信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月5日
	 * @param classstatus
	 * @return
	 *
	 */
	List<CurriculumTbl> getLessonInfos(ComparativeAnalysisModel analysisModel);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 更新上课状态
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月29日
	 * @param record
	 * @return
	 *
	 */
	int updateByModelSelectiveDiy(CurriculumTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 更新真实上课时间
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月6日
	 * @param record
	 * @return
	 *
	 */
	int updateByClassStatus(CurriculumTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询满足条件的课堂列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年5月4日
	 * @param record
	 * @return
	 *
	 */
	List<CurriculumTbl> selectByModel(CurriculumTbl record);
	
}