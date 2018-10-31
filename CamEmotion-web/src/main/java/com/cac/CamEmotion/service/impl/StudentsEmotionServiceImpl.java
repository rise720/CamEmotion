package com.cac.CamEmotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.dao.EmotiondataStudentsStatisticsMapper;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotionaldataTbl;
import com.cac.CamEmotion.dao.FacefeatureTblMapper;
import com.cac.CamEmotion.model.EmotiondataStudentsStatistics;
import com.cac.CamEmotion.model.StudentInfo;
import com.cac.CamEmotion.model.FacefeatureTbl;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.cac.CamEmotion.service.StudentsEmotionService;


@Service
public class StudentsEmotionServiceImpl implements StudentsEmotionService {

	private static Logger logger = LogManager.getLogger(StudentsEmotionServiceImpl.class);

	@Resource
	private EmotiondataStudentsStatisticsMapper emotiondataStudentsStatisticsDao;
	
	@Resource
	private StudentsEmojiStatisticsService studentsEmojiStatisticsService;

	@Resource
	private FacefeatureTblMapper facefeatureDao;
	
	@Resource
	private CurriculumTblMapper curriculumDao;
	
	@Resource
	private EmotionalDataService emotionalDataService;
	
	
	//0: 请求全部数据 	1：请求饼图数据	2：请求气泡图数据
	@Override
	public List<EmojiReportData> getStudentEmotionDetailFromDB(CurriculumTbl curriculumTbl, int studentId, int infoType) {
		//根据学生id列表，先预创建返回数据列表
		List<EmojiReportData> emojiReportDataList = new ArrayList<EmojiReportData>();
		
		//查询结果
		List<EmotiondataStudentsStatistics> resultList = new ArrayList<EmotiondataStudentsStatistics>();
		//查询条件
		EmotiondataStudentsStatistics eStatistics = new EmotiondataStudentsStatistics();
		eStatistics.setCurriculumid(curriculumTbl.getId());
		eStatistics.setStudentid(studentId);
		
		//学生信息
		resultList.clear();
		eStatistics.setCharttype("studentInfo");
		resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
		if(resultList.size() != 1) {
			logger.error("getStudentsEmotionList获取学生信息失败！学生ID：" + studentId + " 课程ID："+ curriculumTbl.getId());
			return null;
		}
		//根据学生信息创建该学生的表情统计数据对象
		EmojiReportData emojiReportData = new EmojiReportData(new StudentInfo(resultList.get(0).getStudentid(), resultList.get(0).getValue(), curriculumTbl.getId())); 
		
		/*
		 * 根据传入的类型判断需要统计的结果数据
		 * 0: 请求全部数据 	1：请求饼图数据	2：请求气泡图数据
		 */
		if(infoType == 0 || infoType == 1) {
			//情绪饼图
			resultList.clear();
			eStatistics.setCharttype("valencePie");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setValencePieList(handleDataStatistics(resultList));
			
			//情景饼图
			resultList.clear();
			eStatistics.setCharttype("faceCountPie");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setFaceCountPieList(handleDataStatistics(resultList));	
			
			//注意力饼图
			resultList.clear();
			eStatistics.setCharttype("attentionPie");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setAttentionPieList(handleDataStatistics(resultList));
			
		}
		if(infoType == 0 || infoType == 2) {
			
			//情绪气泡
			resultList.clear();
			eStatistics.setCharttype("valenceBubble");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setValenceBubbleList(handleDataStatistics(resultList));
			
			//情景及注意力气泡
			resultList.clear();
			eStatistics.setCharttype("sourceBubble");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setSourceBubbleList(handleDataStatistics(resultList));	
			
			//情绪气泡图
			resultList.clear();
			eStatistics.setCharttype("emotionBubble");
			resultList = emotiondataStudentsStatisticsDao.selective(eStatistics, curriculumTbl);
			emojiReportData.setEmotionBubbleList(handleDataStatistics(resultList));
		}
		
		emojiReportDataList.add(emojiReportData);			
		return emojiReportDataList;
	}

	@Override
	public List<EmojiReportData> getStudentsEmotionListFromDB(CurriculumTbl curriculumTbl) {
		
		//获取该堂课所有学生的id列表
		List<Integer> studentIdList = emotiondataStudentsStatisticsDao.getStudentsList(curriculumTbl);
		//根据学生id列表，先预创建返回数据列表
		List<EmojiReportData> emojiReportDataList = new ArrayList<EmojiReportData>();
		//根据学生id，获取表情数据
		for(Integer studentId : studentIdList) {
			List<EmojiReportData> studentDataTmp = getStudentEmotionDetailFromDB(curriculumTbl, studentId, 1);
			if(studentDataTmp != null && studentDataTmp.size() > 0)
				emojiReportDataList.addAll(studentDataTmp);
		}
		return emojiReportDataList;
	}
	
	private List<EmojiBubble> handleDataStatistics(List<EmotiondataStudentsStatistics> eStatisticsList) {
		List<EmojiBubble> bubbleList = new ArrayList<>();
		EmojiBubble eBubble = null;
		for (EmotiondataStudentsStatistics eStatistics : eStatisticsList) {
			eBubble = new EmojiBubble();
			eBubble.setMinutes(eStatistics.getLasttime());
			eBubble.setDateTime(eStatistics.getCurrenttime());
			eBubble.setValue(Double.parseDouble(eStatistics.getValue()));
			eBubble.setAverageValueAll(eStatistics.getAverageValue() == null? 0 : Double.parseDouble(eStatistics.getAverageValue()));
			eBubble.setNumber(eStatistics.getNumber());
			eBubble.setPercent(eStatistics.getPercent());
			eBubble.setLevel(eStatistics.getLevel());
			eBubble.setFaceCount(eStatistics.getActionLevel());
			eBubble.setAttentionLevel(eStatistics.getAttentionLevel());
			eBubble.setPlayDuration(eStatistics.getPlayduration());
			
			eBubble.setFear(Double.parseDouble(eStatistics.getFear()));
			eBubble.setJoy(Double.parseDouble(eStatistics.getJoy()));
			eBubble.setDisgust(Double.parseDouble(eStatistics.getDisgust()));
			eBubble.setContempt(Double.parseDouble(eStatistics.getContempt()));
			eBubble.setSurprise(Double.parseDouble(eStatistics.getSurprise()));
			eBubble.setAnger(Double.parseDouble(eStatistics.getAnger()));
			eBubble.setSadness(Double.parseDouble(eStatistics.getSadness()));
			
			bubbleList.add(eBubble);
		}
		return bubbleList;
	}

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
	@Override
	@Transactional
	public String deleteStudentsEmotion(EmotiondataStudentsStatistics studentsStatistics,int deleteType) {
		try {
			CurriculumTbl curriculumTbl = new  CurriculumTbl();
			curriculumTbl.setId(studentsStatistics.getCurriculumid());
			curriculumTbl.setClassstatus(1);
			curriculumTbl.setCurriculumStarts(1);
			curriculumTbl = curriculumDao.selectByModel(curriculumTbl).get(0);
			if(curriculumTbl.getClassstatus() == 1){
				int i = emotiondataStudentsStatisticsDao.deleteByModel(studentsStatistics, curriculumTbl);
				if(deleteType == 0){
					FacefeatureTbl facefeatureTbl = new FacefeatureTbl();
					facefeatureTbl.setOriginfacecategory(studentsStatistics.getStudentid());
					facefeatureTbl.setCourseid(studentsStatistics.getCurriculumid());
					facefeatureTbl.setIsdel(1);
					i = facefeatureDao.deleteFacefeature(facefeatureTbl);
				}
			}else{
				return "1";
			}
			
		} catch (Exception e) {
			logger.error("删除学生失败" + e.getMessage());
			return "-1";
		}
		return "0";
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 合并学生信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年7月10日
	 * @param studentEmotionList
	 * @return
	 *
	 */
	@Override
	public List<EmojiReportData> handleStudentEmojiReport(CurriculumTbl curriculumTbl, List<EmotionaldataTbl> emotiondataList, List<EmotionaldataTbl> studentActionList, List<EmojiBubble> sourceBubbleList) {
		
		/*
		 * 初始化返回参数
		 */
		Map<Integer, EmojiReportData> emojiReportDataMap = new HashMap<Integer, EmojiReportData>();
		List<Integer> studentIdList = new ArrayList<Integer>();
		for(EmotionaldataTbl emotiondata : studentActionList) {
			if(!emojiReportDataMap.containsKey(emotiondata.getFacecategory())) {
				EmojiReportData emojiReportData = new EmojiReportData(new StudentInfo(emotiondata.getFacecategory(), emotiondata.getFaceimageaddr(),curriculumTbl.getId()));
				emojiReportDataMap.put(emotiondata.getFacecategory(), emojiReportData);
				studentIdList.add(emotiondata.getFacecategory());	
			}
		}
		
		int secondsPerPart = 30;	//气泡图或折线图的单位时间的秒数
		/**
		 * valence的气泡图
		 */
		int[] valenceLevelLimit = { -100, -10, 10, 100 };
		Map<Integer, List<EmojiBubble>> valenceBubbleMap = studentsEmojiStatisticsService.getStudentEmojiBubbleList(curriculumTbl, emotiondataList, secondsPerPart, valenceLevelLimit, 100.0,
				-100.0);
		for(Map.Entry<Integer, List<EmojiBubble>> entry: valenceBubbleMap.entrySet()) {
			if(!emojiReportDataMap.containsKey(entry.getKey())) {
				logger.error("统计Valence气泡时序图数据未匹配到指定的学生ID！ faceCetegory:"+ entry.getKey());
				continue;
			}
			emojiReportDataMap.get(entry.getKey()).setValenceBubbleList(entry.getValue());	
		}
		
		/**
		 * 七种情绪的气泡图
		 */
		int[] emotionLevelLimit = { 0, 100 };
		Map<Integer, List<EmojiBubble>> emotionBubbleMap = studentsEmojiStatisticsService.getStudentEmojiBubbleList(curriculumTbl, emotiondataList, 1, emotionLevelLimit, 100.0,
				0.0);
		for(Map.Entry<Integer, List<EmojiBubble>> entry: emotionBubbleMap.entrySet()) {
			if(!emojiReportDataMap.containsKey(entry.getKey())) {
				logger.error("统计7种情绪气泡时序图数据未匹配到指定的学生ID！ faceCetegory:"+ entry.getKey());
				continue;
			}
			emojiReportDataMap.get(entry.getKey()).setEmotionBubbleList(entry.getValue());	
		}
		
		/**
		 * Valence饼图数据
		 */
		Map<Integer, List<EmojiBubble>> valencePieMap = studentsEmojiStatisticsService.getStudentEmojiPie(curriculumTbl, emotiondataList, valenceLevelLimit, 100.0, -100.0);
		for(Map.Entry<Integer, List<EmojiBubble>> entry: valencePieMap.entrySet()) {
			if(!emojiReportDataMap.containsKey(entry.getKey())) {
				logger.error("统计Valence饼图数据未匹配到指定的学生ID！ faceCetegory:"+ entry.getKey());
				continue;
			}
			emojiReportDataMap.get(entry.getKey()).setValencePieList(entry.getValue());	
		}
			
		/**
		 * 学生行为的气泡时序数据
		 */
		Map<Integer, List<EmojiBubble>> actionBubbleMap = studentsEmojiStatisticsService.getStudentActionBubbleList(curriculumTbl, studentActionList, sourceBubbleList, studentIdList, secondsPerPart);
		for(Map.Entry<Integer, List<EmojiBubble>> entry: actionBubbleMap.entrySet()) {
			if(!emojiReportDataMap.containsKey(entry.getKey())) {
				logger.error("统计学生行为的气泡时序图数据未匹配到指定的学生ID！ faceCetegory:"+ entry.getKey());
				continue;
			}
			emojiReportDataMap.get(entry.getKey()).setSourceBubbleList(entry.getValue());	
		}
		
		/**
		 * 学生行为的饼图
		 */
		Map<Integer, List<EmojiBubble>> actionPieMap = studentsEmojiStatisticsService.getStudentActionPieList(curriculumTbl, actionBubbleMap);
		for(Map.Entry<Integer, List<EmojiBubble>> entry: actionPieMap.entrySet()) {
			if(!emojiReportDataMap.containsKey(entry.getKey())) {
				logger.error("统计学生行为的饼图数据未匹配到指定的学生ID！ faceCetegory:"+ entry.getKey());
				continue;
			} 
			//将行为与注意力的bubbleList进行拆分
			List<EmojiBubble> actionPieList = new ArrayList<EmojiBubble>();
			List<EmojiBubble> attentionPieList = new ArrayList<EmojiBubble>();
			for(EmojiBubble bubble: entry.getValue()) {
				if(bubble.getLevel() < 10)
					actionPieList.add(bubble);
				else
					attentionPieList.add(bubble);
			}
			emojiReportDataMap.get(entry.getKey()).setFaceCountPieList(actionPieList);
			emojiReportDataMap.get(entry.getKey()).setAttentionPieList(attentionPieList);
		}
		
		return new ArrayList<EmojiReportData>(emojiReportDataMap.values());
	}

	public String mergeStudentsEmotion(List<EmotiondataStudentsStatistics> studentsStatistics) {
		CurriculumTbl curriculumTbl = new  CurriculumTbl();
		curriculumTbl.setId(studentsStatistics.get(0).getCurriculumid());
		curriculumTbl.setClassstatus(1);
		curriculumTbl.setCurriculumStarts(1);
		curriculumTbl = curriculumDao.selectByModel(curriculumTbl).get(0);
		if(curriculumTbl.getClassstatus() == 1){
			//修改关系
			FacefeatureTbl facefeatureTbl = new FacefeatureTbl();
			facefeatureTbl.setCourseid(studentsStatistics.get(0).getCurriculumid());
			facefeatureTbl.setOriginfacecategory(studentsStatistics.get(0).getStudentid());
			
			String facecategorys = "";
			for (EmotiondataStudentsStatistics eStatistics : studentsStatistics) {
				facecategorys += eStatistics.getStudentid() + ",";
			}
			if(facecategorys.length() > 0){
				facecategorys = facecategorys.substring(0, facecategorys.length() - 1);
			}
			facefeatureTbl.setFacecategorys(facecategorys);
			int resultB = facefeatureDao.updateFacefeature(facefeatureTbl);
			
			//删除指定学生
			EmotiondataStudentsStatistics statistics = new EmotiondataStudentsStatistics();
			statistics.setCurriculumid(studentsStatistics.get(0).getCurriculumid());
			statistics.setStudentids(facecategorys);
			deleteStudentsEmotion(statistics,1);
			
			if(resultB > 0){
				//重新统计学生报表
				emotionalDataService.getEmojiReportData(new EmojiQueryModel(4,studentsStatistics.get(0).getCurriculumid(),studentsStatistics.get(0).getStudentid()));
				return "0";
			}
		}else{
			return "-1";
		}
		
		return "1";
	}

//	@Override
//	public void saveStudentsStatisticsDataToDB(CurriculumTbl curriculumTbl,
//			List<EmojiReportData> studentEmojiReportData) {
//		
////		//遍历所有学生数据，插入到统计表中
////		for(EmojiReportData emojiReportData : studentEmojiReportData) {
////			//valence气泡
////			for (EmojiBubble emojiBubble : emojiReportData.getValenceBubbleList()) {
////				saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "valenceBubble", emojiBubble);
////			}
////			//valence饼图
////			for (EmojiBubble emojiBubble : emojiReportData.getValencePieList()) {
////				saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "valencePie", emojiBubble);
////			}
////			//情景及注意力气泡
////			for (EmojiBubble emojiBubble : emojiReportData.getSourceBubbleList()) {
////				saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "sourceBubble", emojiBubble);
////			}
////			//情景饼图
////			for (EmojiBubble emojiBubble : emojiReportData.getFaceCountPieList()) {
////				saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "faceCountPie", emojiBubble);
////			}
////			//注意力饼图
////			for (EmojiBubble emojiBubble : emojiReportData.getAttentionPieList()) {
////				saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "attentionPie", emojiBubble);
////			}
////			
////			//头像数据
////			saveStatisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "studentInfo", null);
////		}
//		
//		saveStudentsStatisticsDataToDB2(curriculumTbl,studentEmojiReportData);
//		
//	}
//
//	private void saveStatisticsData(CurriculumTbl curriculumTbl, StudentInfo studentInfo, String chartType,
//			EmojiBubble emojiBubble) {
//		
//		EmotiondataStudentsStatistics eStatistics = new EmotiondataStudentsStatistics();
//		eStatistics.setCurriculumid(curriculumTbl.getId());
//		eStatistics.setStudentid(studentInfo.getStudentId());
//		eStatistics.setCharttype(chartType);
//		
//		switch(chartType) {
//		case "studentInfo":
//			String sharePath = Constants.SHARE_PATH + "FaceImage/" + curriculumTbl.getId() + "/" + studentInfo.getStudentId() + ".png";
//			eStatistics.setValue(sharePath);
//			break;
//		default:
//			eStatistics.setLasttime(emojiBubble.getMinutes());
//			eStatistics.setCurrenttime(emojiBubble.getDateTime());
//			eStatistics.setValue(String.valueOf(emojiBubble.getValue()));
//			eStatistics.setNumber(emojiBubble.getNumber());
//			eStatistics.setPercent(emojiBubble.getPercent());
//			eStatistics.setLevel(emojiBubble.getLevel());
//			eStatistics.setActionLevel(emojiBubble.getFaceCount());
//			eStatistics.setAttentionLevel(emojiBubble.getAttentionLevel());
//			eStatistics.setPlayduration(emojiBubble.getPlayDuration());
//			break;
//		}
//		emotiondataStudentsStatisticsDao.insertSelective(eStatistics, curriculumTbl);
//	}
	
	
	
	public void saveStudentsStatisticsDataToDB(CurriculumTbl curriculumTbl,
			List<EmojiReportData> studentEmojiReportData) {
		
		List<EmotiondataStudentsStatistics> eStatistics = new ArrayList<EmotiondataStudentsStatistics>();
		int i = 0;
		//遍历所有学生数据，插入到统计表中
		for(EmojiReportData emojiReportData : studentEmojiReportData) {
			//valence气泡
			if(emojiReportData.getValenceBubbleList() != null && emojiReportData.getValencePieList() != null){
				for (EmojiBubble emojiBubble : emojiReportData.getValenceBubbleList()) {
					eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "valenceBubble", emojiBubble));
				}
				//valence饼图
				for (EmojiBubble emojiBubble : emojiReportData.getValencePieList()) {
					eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "valencePie", emojiBubble));
				}
				//7种情绪气泡
				for (EmojiBubble emojiBubble : emojiReportData.getEmotionBubbleList()) {
					eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "emotionBubble", emojiBubble));
				}
			}
			//情景及注意力气泡
			for (EmojiBubble emojiBubble : emojiReportData.getSourceBubbleList()) {
				eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "sourceBubble", emojiBubble));
			}
			//情景饼图
			for (EmojiBubble emojiBubble : emojiReportData.getFaceCountPieList()) {
				eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "faceCountPie", emojiBubble));
			}
			//注意力饼图
			for (EmojiBubble emojiBubble : emojiReportData.getAttentionPieList()) {
				eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "attentionPie", emojiBubble));
			}
			
			//头像数据
			eStatistics.add(statisticsData(curriculumTbl, emojiReportData.getStudentInfo(), "studentInfo", null));
		}
		
		
		emotiondataStudentsStatisticsDao.batchInsert(eStatistics, curriculumTbl);
	}
	
	
	private EmotiondataStudentsStatistics statisticsData(CurriculumTbl curriculumTbl, StudentInfo studentInfo, String chartType,
			EmojiBubble emojiBubble) {
		
		EmotiondataStudentsStatistics eStatistics = new EmotiondataStudentsStatistics();
		eStatistics.setCurriculumid(curriculumTbl.getId());
		eStatistics.setStudentid(studentInfo.getStudentId());
		eStatistics.setCharttype(chartType);
		
		switch(chartType) {
		case "studentInfo":
			String sharePath = Constants.SHARE_PATH + "FaceImage/" + curriculumTbl.getId() + "/" + studentInfo.getStudentId() + ".png";
			eStatistics.setValue(sharePath);
			break;
		default:
			eStatistics.setLasttime(emojiBubble.getMinutes());
			eStatistics.setCurrenttime(emojiBubble.getDateTime());
			eStatistics.setValue(String.valueOf(emojiBubble.getValue()));
			eStatistics.setJoy(String.valueOf(emojiBubble.getJoy()));
			eStatistics.setSadness(String.valueOf(emojiBubble.getSadness()));
			eStatistics.setDisgust(String.valueOf(emojiBubble.getDisgust()));
			eStatistics.setContempt(String.valueOf(emojiBubble.getContempt()));
			eStatistics.setAnger(String.valueOf(emojiBubble.getAnger()));
			eStatistics.setSurprise(String.valueOf(emojiBubble.getSurprise()));
			eStatistics.setFear(String.valueOf(emojiBubble.getFear()));
			eStatistics.setNumber(emojiBubble.getNumber());
			eStatistics.setPercent(emojiBubble.getPercent());
			eStatistics.setLevel(emojiBubble.getLevel());
			eStatistics.setActionLevel(emojiBubble.getFaceCount());
			eStatistics.setAttentionLevel(emojiBubble.getAttentionLevel());
			eStatistics.setPlayduration(emojiBubble.getPlayDuration());
			break;
		}
		return eStatistics;
	}
	
	
}
