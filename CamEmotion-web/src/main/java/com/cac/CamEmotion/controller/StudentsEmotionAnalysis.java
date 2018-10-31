package com.cac.CamEmotion.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.http.I18NMessageUtil;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotiondataStudentsStatistics;
import com.cac.CamEmotion.service.CurriculumService;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.cac.CamEmotion.service.StudentsEmotionService;

@RestController
@RequestMapping("/rest/StudentsEmotionAnalysis")
public class StudentsEmotionAnalysis {
	private static Logger logger = LogManager.getLogger(StudentsEmotionAnalysis.class);

	/**
	 * 表情数据分析管理
	 */	
	@Resource
	private CurriculumService curriculumService;
	
	@Resource
	private EmotionalDataService emotionalDataService;
	
	@Resource
	private StudentsEmotionService studentsEmotionService;

	
	/**
	 * 
	 * 方法功能说明: 根据课程ID，获取学生情绪列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年5月2日
	 * @return
	 */
	@RequestMapping(value = "/getStudentsEmotionList")

	public Response<List<EmojiReportData>> getStudentsEmotionList(HttpServletRequest request, int curriculumid){
		try{
			
			List<EmojiReportData> studentsList = emotionalDataService.getEmojiReportData(new EmojiQueryModel(2, curriculumid, 0));

			return new Response<List<EmojiReportData>>().success(studentsList);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new Response<List<EmojiReportData>>().failure("生成统计数据失败");
		}
	}
	
	/**
	 * 
	 * 方法功能说明: 根据课程ID，学生ID，获取学生个体分析
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年5月2日
	 * @return
	 */
	@RequestMapping(value = "/getStudentAnalysis")
	public Response<EmojiReportData> getStudentAnalysis(HttpServletRequest request, int curriculumid, int studentid){
		try{
			List<EmojiReportData> student = emotionalDataService.getEmojiReportData(new EmojiQueryModel(3, curriculumid, studentid));
			if(student == null || student.size() == 0){
				return new Response<EmojiReportData>().failure(I18NMessageUtil.getMessage(request, "")); 
			}
			return new Response<EmojiReportData>().success(student.get(0));
		}catch(Exception e){
			logger.error(e.getMessage());
			return new Response<EmojiReportData>().failure("生成统计数据失败");
		}
	}
	
	/**
	 * 
	 * 方法功能说明: 删除学生情绪信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年5月2日
	 * @return
	 */
	@RequestMapping(value = "/deleteStudentsEmotion")
	public Response<String> deleteStudentsEmotion(HttpServletRequest request, @RequestBody EmotiondataStudentsStatistics studentsStatistics){
		try{
			String deleteSuccess = studentsEmotionService.deleteStudentsEmotion(studentsStatistics,0);
			if(!"0".equals(deleteSuccess)){
				return new Response<String>().failure(I18NMessageUtil.getMessage(request, deleteSuccess)); 
			}
			return new Response<String>().success(deleteSuccess);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new Response<String>().failure("生成统计数据失败");
		}
	}
	
	/**
	 * 
	    * @Title: deleteStudentsEmotion  
	    * @Description: 合并学生情绪信息
	    * @param @param request
	    * @param @param student
	    * @param @return    参数  
	    * @return Response<StudentEmotionData>    返回类型  
	    * @throws
	 */
	@RequestMapping(value = "/mergeStudentsEmotion")
	public Response<String> mergeStudentsEmotion(HttpServletRequest request, @RequestBody List<EmotiondataStudentsStatistics> studentsStatistics){
		try{
			String mergeSuccess = "";
			if(studentsStatistics == null || studentsStatistics.size() == 0){
				return new Response<String>().failure(I18NMessageUtil.getMessage(request, "参数为空")); 
			}else{
				mergeSuccess = studentsEmotionService.mergeStudentsEmotion(studentsStatistics);
			}
			
			if(!"0".equals(mergeSuccess)){
				return new Response<String>().failure(I18NMessageUtil.getMessage(request, "")); 
			}
			return new Response<String>().success(mergeSuccess);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new Response<String>().failure("生成统计数据失败");
		}
	}
	
}