package com.cac.CamEmotion.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.jsonModel.ClassnoModel;
import com.cac.CamEmotion.jsonModel.MemcacheStatus;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.service.CurriculumService;
import com.cac.CamEmotion.service.EmotionServerClient;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.cac.CamEmotion.service.FaceContrastConfigService;
import com.cac.CamEmotion.utils.DeleteDirectory;
import com.cac.CamEmotion.utils.SendEmotionDataInfoThread;

/**
 * 
 * 类说明: 停启服务相关API
 * <P>
 * 
 * </P>
 *
 * @author zhangsh
 * @date 2017年9月1日
 */
@RestController
@RequestMapping("/Server")
public class ServerApi {
	private Logger logger = LogManager.getLogger(ServerApi.class);

	@Resource
	private EmotionServerClient emotionServerClient;
	@Resource
	private CurriculumService curriculumService;
	@Resource
	private EmotionalDataService emotionalDataService;
	@Resource
	private FaceContrastConfigService faceContrastConfigService;
	
	/**
	 * 
	 * 方法说明: 开启服务
	 * <P>
	 * 
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年9月1日
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public Response<ClassnoModel> start(HttpServletRequest request,@RequestBody CurriculumTbl curriculumTbl) {
		logger.info("----- 收到上课请求 -------");
		Constants.isInClassing = true;
		curriculumTbl.setClassroomId(1);
		ClassnoModel classnoModel = emotionServerClient.start(curriculumTbl);
		return new Response<ClassnoModel>().success(classnoModel);
	}

	/**
	 * 
	 * 方法说明: 停止服务
	 * <P>
	 * 
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年9月1日
	 * @param request
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public Response<String> stop(HttpServletRequest request,@RequestBody CurriculumTbl curriculum) {
		logger.info("----- 收到下课请求 -------");
		Constants.isInClassing = false;
		if (curriculum.getId() != null && curriculum.getId() > 0) {
			curriculum.setEndtime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			curriculum.setClassstatus(1);
			if (curriculumService.updateCurriculumDiy(curriculum) > 0) {
				emotionServerClient.stop(curriculum.getId());
				
				//下课 统计数据
				emotionServerClient.CloseCourse(curriculum.getId());
				
				//删除临时文件
				String deletepath = Constants.PROJECT_PATH + "webapps" + Constants.SHARE_PATH + "FaceImage/" + curriculum.getId();
				DeleteDirectory.DOSDeleteDir(deletepath);
				logger.info("-----删除文件成功---------");
				
			} else {
				return new Response<String>().success("1");
			}
		}
		return new Response<String>().success();
	}

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
	 * @return
	 *
	 */
	@RequestMapping(value = "/findInClassInfo", method = RequestMethod.POST)
	public Response<List<CurriculumTbl>> findInClassInfo() {
		List<CurriculumTbl> curriculumList = curriculumService.selectInClassInfo(0);
		return new Response<List<CurriculumTbl>>().success(curriculumList);
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 获取线程状态
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年10月12日
	 * @return
	 *
	 */
	@RequestMapping(value = "/getMemcacheStatus", method = RequestMethod.POST)
	public Response<List<MemcacheStatus>> getThreadStatus(@RequestBody CurriculumTbl curriculumTbl) {
		return new Response<List<MemcacheStatus>>().success(emotionServerClient.checkMemcacheStatus(curriculumTbl));
	}
	
	/**
	 * 课程结束
	 * 供C端调用接口通知web服务器本节课程对比结束和录制进程视频复制完毕
	 * @param courseId 课程Id
	 */
	@RequestMapping(value = "/CloseCourse", method = RequestMethod.POST)
	public Response<String> CloseCourse(Integer courseId) {
		logger.info("收到对比服务的课程结束指令，courseId:" + courseId);
		boolean res = false;
		if (courseId != null && courseId > 0) {
			res = emotionServerClient.CloseCourse(courseId);
		}
		return new Response<String>().success(res ? "操作成功" : "操作失败");
	}
	
	/**
	 * 供C端调用接口通知web服务器对比进程健康状态
	 * @param contrastIp 对比进程IP
	 */
	@RequestMapping(value = "/ContrastStatus", method = RequestMethod.POST)
	public Response<String> ContrastStatus(String contrastIp) {
		if (contrastIp != null) {
			emotionServerClient.ContrastStatus(contrastIp, new Date().getTime());
		}
		return new Response<String>().success();
	}
}