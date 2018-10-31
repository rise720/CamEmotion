package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 类说明：课堂资源
 * 
 * 
 * @author houpp
 *
 */
public interface CurriclumVideoService {
	/**
	 * 方法说明：返回某一节课的文件资源
	 * 
	 * @param CurriclumVideotbl
	 * @return
	 */
	public List<CurriculumVideoTbl> CurriclumVideoData(CurriculumVideoTbl CurriclumVideotbl);

	/**
	 * 方法功能说明: 按条件分页查询
	 * 
	 * @param CurriclumVideotbl
	 * @param CurrentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 * @return
	 */
	public PageList<CurriculumVideoTbl> selectAllCurriclumVideo(CurriculumVideoTbl CurriclumVideotbl, long CurrentPage,
			long pageRecorders, String expression, String rule);
	
	
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
}
