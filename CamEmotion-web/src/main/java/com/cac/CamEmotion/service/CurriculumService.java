/**
 * 
 */
package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.jsonModel.ComparativeAnalysisModel;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.model.EmotionaldataTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author Chenyang
 * @data 2017年10月11日
 */
public interface CurriculumService {
	
	
	public int updateCurriculum(CurriculumTbl record);
	
	public int updateCurriculumDiy(CurriculumTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询上课中的课程信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年10月11日
	 * @param record
	 * @return
	 *
	 */
	public List<CurriculumTbl> selectInClassInfo(Integer classstatus);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询单堂课程信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月13日
	 * @param id
	 * @return
	 *
	 */
	public CurriculumTbl selectClassInfo(Integer id);
	
	/**
	 * 
	 * 方法功能说明:	新增课程
	 * <P>
	 * 后台程序开启时调用，表示新增课程
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年8月16日
	 * @return  返回的是主键
	 */
	public CurriculumTbl newCurriculum(CurriculumTbl curriculum);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 
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
	public int deleteCurriclum(Integer id);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 修改课堂状态
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param curriculum
	 * @return
	 *
	 */
	public int updateByClassStatus(CurriculumTbl curriculum);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询课程
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年9月14日
	 * @param Curriclumtbl
	 * @param CurrentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 * @return
	 *
	 */
	public PageList<CurriculumTbl> selectAllCurriclum(CurriculumTbl Curriclumtbl, long CurrentPage,
			long pageRecorders, String expression, String rule);
	
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
	 * @param curriculum
	 * @return
	 *
	 */
	public List<CurriculumTbl> findByModel(CurriculumTbl curriculum);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 获取课堂列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年5月8日
	 * @param cAnalysisModel
	 * @return
	 *
	 */
	public PageList<CurriculumTbl> findCurriculums(CurriculumTbl Curriclumtbl, long CurrentPage,
			long pageRecorders, String expression, String rule);
}
