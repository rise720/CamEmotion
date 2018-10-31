package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.CurriculumEvaluateTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 类说明：课堂资源
 * 
 * 
 * @author houpp
 *
 */
public interface CurriculumEvaluateService {

	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public CurriculumEvaluateTbl SelectByPrimaryKey(Integer id);

	/**
	 * 插入数据
	 * 
	 * @param record
	 * @return
	 */
	public int insert(CurriculumEvaluateTbl record);

	/**
	 * 修改数据
	 * 
	 * @param record
	 * @return
	 */
	public int Update(CurriculumEvaluateTbl record);

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int DeleteId(Integer id);

	/**
	 * 根据课程Id删除数据
	 * 
	 * @param curricId
	 * @return
	 */
	public int DeleteCurricId(Integer curricId);

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
	public PageList<CurriculumEvaluateTbl> GetList(CurriculumEvaluateTbl record, long CurrentPage, long pageRecorders,
			String expression, String rule);
}
