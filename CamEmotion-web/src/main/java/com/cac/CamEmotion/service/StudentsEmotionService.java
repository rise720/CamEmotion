package com.cac.CamEmotion.service;

import java.util.List;
import java.util.Map;

import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotionaldataTbl;
import com.cac.CamEmotion.model.EmotiondataStudentsStatistics;
import com.cac.CamEmotion.model.EmotiondataStudentsStatistics;

/**
 * 
    * @ClassName: StudentsEmotionService  
    * @Description: 学生表情服务接口
    * @author JinWH  
    * @date 2018年5月2日  
    *
 */
public interface StudentsEmotionService {

	/**
	 *
	    * @Title: getStudentsEmotionList  
	    * @Description: 获取学生情绪列表，为防止数据量过大，不包含时序值
	    * @param @param curriculumid
	    * @param @return    参数  
	    * @return List<StudentEmotionData>    返回类型  
	    * @throws
	 */
	public List<EmojiReportData> getStudentsEmotionListFromDB(CurriculumTbl curriculumTbl);
	
	/**
	 * 
	    * @Title: getStudentEmotionDetail  
	    * @Description: 获取学生情绪明细 
	    * @param @param curriculumid
	    * @param @param studentid
	    * @param infotype 0: 请求全部数据 	1：请求饼图数据	2：请求气泡图数据
	    * @param @return    参数  
	    * @return StudentEmotionData    返回类型  
	    * @throws
	 */
	public List<EmojiReportData> getStudentEmotionDetailFromDB(CurriculumTbl curriculumTbl, int studentid, int infoType);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年8月6日
	 * @param studentsStatistics
	 * @param deleteType 默认是0
	 * @return
	 *
	 */
	public String deleteStudentsEmotion(EmotiondataStudentsStatistics studentsStatistics,int deleteType);
	
	/**
	 * 
	    * @Title: mergeStudentsEmotion  
	    * @Description: 合并学生情绪信息
	    * @param @param curriculumid
	    * @param @param studentid
	    * @param @return    参数  
	    * @return boolean    返回类型  
	    * @throws
	 */
	public String mergeStudentsEmotion(List<EmotiondataStudentsStatistics> studentsStatistics);
	
	/**
	 * 
	    * @Title: handleStudentEmojiReport  
	    * @Description: 统计指定课的学生情绪信息
	    * @param @param curriculumTbl
	    * @param @param valenceList
	    * @return List<EmojiReportData>    返回类型  
	    * @throws
	 */
	public List<EmojiReportData> handleStudentEmojiReport(CurriculumTbl curriculumTbl, List<EmotionaldataTbl> valenceList, List<EmotionaldataTbl> studentActionList, List<EmojiBubble> sourceBubbleList);

	/**
	 * 
	    * @Title: saveStudentsStatisticsDataToDB  
	    * @Description: 保存学生个体统计数据至数据库
	    * @param @param curriculumTbl
	    * @param @param studentEmojiReportData    参数  
	    * @return void    返回类型  
	    * @throws
	 */
	public void saveStudentsStatisticsDataToDB(CurriculumTbl curriculumTbl,
			List<EmojiReportData> studentEmojiReportData);	
}
