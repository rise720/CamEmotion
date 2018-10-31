package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.model.ComprehensiveReportTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 类说明：课堂资源
 * 
 * 
 * @author houpp
 *
 */
public interface ReportService {

	/**
	 * 根据课程id查询每分钟的平均率
	 * 
	 * @param curriculumid
	 * @return
	 */
	List<Double> selectMinutesWaveAverageRate(Integer curriculumid);

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
	public PageList<ComprehensiveReportTbl> GetList(ComprehensiveReportTbl record, long CurrentPage, long pageRecorders, String expression, String rule);

	public List<ComprehensiveReportTbl> GetLists(ComprehensiveReportTbl record, long CurrentPage, long pageRecorders, String expression, String rule);
}
