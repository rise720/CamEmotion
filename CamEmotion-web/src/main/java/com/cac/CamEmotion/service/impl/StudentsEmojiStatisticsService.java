package com.cac.CamEmotion.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmotionaldataTbl;

import sun.security.krb5.internal.PAData.SaltAndParams;

/**
 * 
 * 类功能说明: 学生情绪图表生成服务类
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2018年7月9日
 */
@Service
public class StudentsEmojiStatisticsService {
	private static Logger logger = LogManager.getLogger(StudentsEmojiStatisticsService.class);
	
	//每位学生的valence时序列表，按单位时间统计
	private Map<Integer, List<EmojiBubble>> studentsEmojiMap;
	//单位时间内的valence临时数据集合
	private Map<Integer, List<EmojiBubble>> studentsEmojiMapTmp;
	//行为数据集合
	private Map<Integer, List<EmojiBubble>> actionBubbleMap;
	private int nowTime;
	private int secondsPerPart; //每段时间的秒数
	private int[] levelLimit;	//level阈值数组
	private Double maxValue;	//最大值
	private Double minValue;	//最小值
	
	/**
	 * 
	    * @Title: getStudentEmojiBubbleList  
	    * @Description: 统计各个学生情绪气泡时序（某个单位时间未捕捉到人脸，则该单位时间的记录不存在）
	    * @param @param curriculumTbl
	    * @param @param emotiondataList
	    * @param @param secondsPerPart
	    * @param @param levelLimit
	    * @param @param maxValue
	    * @param @param minValue
	    * @param @return    参数  
	    * @return Map<Long,List<EmojiBubble>>    返回类型  
	    * @throws
	 */
	public Map<Integer, List<EmojiBubble>> getStudentEmojiBubbleList(CurriculumTbl curriculumTbl,List<EmotionaldataTbl> emotiondataList, int secondsPerPart, int[] levelLimit, Double maxValue, Double minValue) {
		
		try {
			//单位时间的秒数
			this.secondsPerPart = secondsPerPart;
			this.levelLimit = levelLimit;
			this.maxValue = maxValue;
			this.minValue = minValue;
			studentsEmojiMap = new LinkedHashMap<Integer, List<EmojiBubble>>();
			studentsEmojiMapTmp = null;
			
			//上课开始时间，精确到秒
			long startTime = DateUtil.StringToDate(curriculumTbl.getStarttime().substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime();
			
			/*
			 * 循环逻辑
			 */
			for(int i = 0; i < emotiondataList.size(); i++){
				//当前记录的信息
				EmotionaldataTbl emotionaldataTbl = emotiondataList.get(i);
				//当前记录的学生Id
				int studentId = emotionaldataTbl.getFacecategory();
				nowTime = (int)((emotiondataList.get(i).getCreatedate().getTime() - startTime)/(1000 * secondsPerPart)) + 1;
				nowTime = nowTime < 1 ? 1: nowTime; //老课程存在开课时间滞后的情况
				//单位时间内的valence临时数据集合为空（第一次循环）或者 到了下一个单位时间时，统计该分钟，并新增下个时间的临时数据集合
				if(studentsEmojiMapTmp == null || get1stElement(studentsEmojiMapTmp).getMinutes() != nowTime) {
					sum();
 					studentsEmojiMapTmp = new LinkedHashMap<Integer, List<EmojiBubble>>();
				}
				//数据集合中如果不包含当前学生的bubbleList信息，则先新增
				if(!studentsEmojiMapTmp.containsKey(studentId)) {
					addBubble(emotionaldataTbl);
				}
				//添加数据
				addUp(studentsEmojiMapTmp.get(studentId), emotionaldataTbl);
				//如果已经是最后条记录，则统计
				if(i + 1 ==  emotiondataList.size()){
					sum();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return studentsEmojiMap;
	}
	
	/**
	 * 
	    * @Title: get1stElement  
	    * @Description: 获取临时数据集合的第一条记录 
	    * @param @param studentsEmojiMapTmp
	    * @param @return    参数  
	    * @return EmojiBubble    返回类型  
	    * @throws
	 */
	private EmojiBubble get1stElement(Map<Integer, List<EmojiBubble>> studentsEmojiMapTmp) {
		for(Map.Entry<Integer, List<EmojiBubble>> entry : studentsEmojiMapTmp.entrySet()) {
			return entry.getValue().get(0);
		}
		return null;
	}
	
	/**
	 * 
	    * @Title: getStudentAttentionBubbleList  
	    * @Description: 统计每单位时间学生的行为气泡时序（某个单位时间未捕捉到人脸，则该单位时间的记录仍然生成）
	    * @param @param curriculumTbl
	    * @param @param emotiondataList
	    * @param @param secondsPerPart
	    * @param @param levelLimit
	    * @param @param maxValue
	    * @param @param minValue
	    * @param @return    参数  
	    * @return Map<Long,List<EmojiBubble>>    返回类型  
	    * @throws
	 */
	public Map<Integer, List<EmojiBubble>> getStudentActionBubbleList(CurriculumTbl curriculumTbl,List<EmotionaldataTbl> emotiondataList, List<EmojiBubble> wholeClassBubbleList, List<Integer> studentIdList, int secondsPerPart) {
		
		try {
			//单位时间的秒数
			this.secondsPerPart = secondsPerPart;
			studentsEmojiMap = new LinkedHashMap<Integer, List<EmojiBubble>>();
			
			//上课开始时间，精确到秒
			long startTime = DateUtil.StringToDate(curriculumTbl.getStarttime().substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime();
			//下课时间作为结束时间，精确到秒;如果当前未下课，则取当前时间为统计的结束时间
			long endTime;
			if(curriculumTbl.getEndtime() == null || "".equals(curriculumTbl.getEndtime())){
				endTime = System.currentTimeMillis();
			}else {
				endTime = DateUtil.StringToDate(curriculumTbl.getEndtime().substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime();
			}
			//上课时长共有几段单位时间
			long classTime = (endTime - startTime) / (1000 * secondsPerPart) + 1l;
			
			//预先生成好单位时间内的各学生行为数据
			createActionBubbleMap(startTime, classTime, wholeClassBubbleList, studentIdList);
					
			/*
			 * 循环逻辑： 遍历基础表情数据，设置各个学生的行为数据
			 */
			for(int i = 0; i < emotiondataList.size(); i++){
				//当前记录的信息
				EmotionaldataTbl emotionaldataTbl = emotiondataList.get(i);
				//当前记录的学生Id
				int studentId = emotionaldataTbl.getFacecategory();
				//获取当前时间是属于第几个单位时间
				nowTime = (int)((emotiondataList.get(i).getCreatedate().getTime() - startTime)/(1000 * secondsPerPart)) + 1;
				nowTime = nowTime < 1 ? 1: nowTime; //老课程存在开课时间滞后的情况
				
				if(!actionBubbleMap.containsKey(studentId)) {
					logger.error("学生注意力统计，未匹配到学生ID！faceCategory：" + studentId);
					continue;
				}
				List<EmojiBubble> actionBubbleList = actionBubbleMap.get(studentId);
				
				for(EmojiBubble bubble : actionBubbleList) {
					if(bubble.getMinutes() == nowTime) {
						//每一秒，只要有一张抬头的帧，就代表这一秒是抬头
						if(emotionaldataTbl.getPitch() != null && emotionaldataTbl.getPitch() > 0){
							//临时存放抬头的秒数
							bubble.setFaceCountPercent(bubble.getFaceCountPercent() + 1);
						}
						//每一秒，只要有一张张嘴的帧，就代表这一秒是讨论
						if(emotionaldataTbl.getMouthopen() != null && emotionaldataTbl.getMouthopen() > 0) {
							//临时存放张嘴的秒数
							bubble.setSpeakCountPercent(bubble.getSpeakCountPercent() + 1);
						}
					}
				}
			}
			
			//统计各学生各单位时间的行为数据
			sumActionBubbleMap();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return actionBubbleMap;
	}
	
	/**
	 * 
	    * @Title: sumActionBubbleMap  
	    * @Description: 统计各学生各单位时间的行为数据
	    * @param     参数  
	    * @return void    返回类型  
	    * @throws
	 */
	private void sumActionBubbleMap() {
		DecimalFormat decFmt = new DecimalFormat("#.##");
		for(Map.Entry<Integer, List<EmojiBubble>> entry : actionBubbleMap.entrySet())	{
			
			//先合计单位时间内，该学生的各总计值
			for(EmojiBubble bubble: entry.getValue()) {
				//抬头的百分比
				double faceCountPercent = Double.parseDouble(decFmt.format(bubble.getFaceCountPercent() / (float)secondsPerPart * 100));
				bubble.setFaceCountPercent(faceCountPercent);
				//讨论的百分比
				double speakCountPercent = Double.parseDouble(decFmt.format(bubble.getSpeakCountPercent() / (float)secondsPerPart * 100));
				bubble.setSpeakCountPercent(speakCountPercent);
				
				
				/*
				 * 判断学生在当前单位时间内的行为
				 */
				int facecountTmp = 0;	//学生行为临时变量，默认是练习
				if(faceCountPercent > Constants.STUDENT_FACECOUNT_PERCENT) {
					if(speakCountPercent > Constants.STUDENT_SPEAK_PERCENT)
						facecountTmp = -1;	//讨论
					else
						facecountTmp = 1;	//听课
				}
				
				/*
				 * 判断学生在当前单位时间内的注意力是否集中
				 * 判断逻辑： 如果整个班级在听课，学生在听课则认为注意力集中，反之分散
				 * 讨论作为听课来处理
				 */
				if((bubble.getFaceCount() == 0 && facecountTmp != 0) ||(bubble.getFaceCount() != 0 && facecountTmp == 0))
					bubble.setAttentionLevel(0);
				
				//将临时设置的班级行为改为个人行为
				bubble.setFaceCount(facecountTmp);
			}
		}
	}

	/**
	 * 
	    * @Title: createActionBubbleList  
	    * @Description: 根据单位时间的次数及学生id列表，生成数据集合  
	    * @param @param classTime
	    * @param @param studentIdList    参数  
	    * @return void    返回类型  
	    * @throws
	 */
	private void createActionBubbleMap(long startTime, long classTime, List<EmojiBubble> wholeClassBubbleList, List<Integer> studentIdList) {
		actionBubbleMap = new LinkedHashMap<Integer, List<EmojiBubble>>();
		for(int i = 0; i< studentIdList.size(); i++) {
			List<EmojiBubble> emojiBubbleList = new ArrayList<EmojiBubble>();
			for(int j = 0; j < wholeClassBubbleList.size(); j++) {
				EmojiBubble wholeClassBubble = wholeClassBubbleList.get(j);
				EmojiBubble emojiBubble = new EmojiBubble();
				emojiBubble.setMinutes(wholeClassBubble.getMinutes());
				emojiBubble.setPlayDuration((wholeClassBubble.getMinutes()-1) * secondsPerPart);
				emojiBubble.setFaceCount(wholeClassBubble.getFaceCount());	//设置学生的默认行为为课堂整体行为
				emojiBubble.setFaceCountPercent(0);
				emojiBubble.setSpeakCountPercent(0);
				emojiBubble.setPercent("0");
				emojiBubble.setValue(0);
				emojiBubble.setAttentionLevel(1);//这个学生默认注意力为集中
				emojiBubble.setDateTime(wholeClassBubble.getDateTime());
				emojiBubbleList.add(emojiBubble);
			}
			actionBubbleMap.put(Integer.valueOf(studentIdList.get(i)), emojiBubbleList);
		}
	}

	/**
	 * 
	 * 方法功能说明: 新增记录
	 * <P>
	 * 根据范围数组计算level有几档。例如：数组[0,40,60,100]代表共3档，[0，40]一档，（40，60]一档，（60，100]一档    
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月15日
	 */
	private void addBubble(EmotionaldataTbl emotionaldataTbl){
		/*
		 * 新增新的统计记录
		 * 1.计算视频跳转秒数playDuration，第一分钟不跳，第二分钟开始跳转nowMinute-1分钟 ，再减去视频开始时间的秒数
		 * 2.获取当前分钟的实际时分
		 */
		int playDuration = (nowTime - 1) * secondsPerPart;
		String nowDateTime = DateUtil.DateToString(emotionaldataTbl.getCreatedate(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		
		//几档逻辑就是数组数量-1
		int levelNumber = levelLimit.length - 1;
		ArrayList<EmojiBubble> bubbleList = new ArrayList<EmojiBubble>();
		
		for(int j = 0; j < levelNumber; j++){ 
			EmojiBubble bubble = new EmojiBubble();
			bubble.setLevel(j);
			bubble.setMinutes(nowTime);
			bubble.setPlayDuration(playDuration);
			bubble.setDateTime(nowDateTime);
			bubbleList.add(bubble);
		}
		studentsEmojiMapTmp.put(emotionaldataTbl.getFacecategory(), bubbleList);
	}
	
	/**
	 * 
	 * 方法功能说明: 计算level值
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月14日
	 * @param value
	 * @param levelLimit
	 * @return
	 */
	private int calLevel(double value, int[] levelLimit){
		for(int i = 0; i < levelLimit.length - 1; i++){
			if(value >= levelLimit[i] && value <= levelLimit[i+1]){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * 方法功能说明: 累计值
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月14日
	 * @param value
	 * @param levelLimit
	 */
	private void addUp(List<EmojiBubble> bubbleList, EmotionaldataTbl emotionaldataTbl){
		Double value = emotionaldataTbl.getValenceview();
		//所有值范围均调整成（0-100）
//		value = 100 * (value - minValue.doubleValue()) / (maxValue.doubleValue() - minValue.doubleValue());
		/*
		 * 公式:
		 * 
		 *   实际值   -  实际最小值
		 * --------------------------   X   (显示最大值 - 显示最小值） + 显示最小值
		 * 实际最大值 -  实际最小值
		 * 
		 * 
		 */
		value = (value - minValue) / (maxValue - minValue) * (levelLimit[levelLimit.length-1] - levelLimit[0]) + levelLimit[0];
		
		//计算当前记录属于第几档，之后将该记录统计至属于该档的结果中
		int nowLevel = calLevel(value, levelLimit);
		EmojiBubble bubble = bubbleList.get(nowLevel);
		//次数累加1
		bubble.setNumber(bubble.getNumber() + 1);
		//分钟设定
		bubble.setMinutes(nowTime);
		//统计总值
		bubble.setValue(bubble.getValue() + value);
		//七种表情
		double emotionData;
		if(bubble.getJoy() < (emotionData = emotionaldataTbl.getJoy()))
			bubble.setJoy(emotionData);
		if(bubble.getSadness() < (emotionData = emotionaldataTbl.getSadness()))
			bubble.setSadness(emotionData);
		if(bubble.getDisgust() < (emotionData = emotionaldataTbl.getDisgust()))
			bubble.setDisgust(emotionData);
		if(bubble.getContempt() < (emotionData = emotionaldataTbl.getContempt()))
			bubble.setContempt(emotionData);
		if(bubble.getAnger() < (emotionData = emotionaldataTbl.getAnger()))
			bubble.setAnger(emotionData);
		if(bubble.getSurprise() < (emotionData = emotionaldataTbl.getSurprise()))
			bubble.setSurprise(emotionaldataTbl.getSurprise());
		if(bubble.getFear() < (emotionData = emotionaldataTbl.getFear()))
			bubble.setFear(emotionData);
	}
	
	/**
	 * 
	 * 方法功能说明: 统计
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月14日
	 */
	private void sum(){
		DecimalFormat decFmt = new DecimalFormat("#.##");
		//如果临时Map为空，则不进行统计
		if(null == studentsEmojiMapTmp || 0 == studentsEmojiMapTmp.size())
			return;
		//遍历临时Map中所有学生的该单位时间的集合，统计完后，保存至主Map
		for(Map.Entry<Integer, List<EmojiBubble>> entry:studentsEmojiMapTmp.entrySet()) {
			double allValue = 0;
			int allCount = 0;
			
			//先合计单位时间内，该学生的各总计值
			for(EmojiBubble bubble: entry.getValue()) {
				allValue += bubble.getValue();
				allCount += bubble.getNumber();
			}
			
			//根据总计值计算各平均值
			double averageValueAll = Double.parseDouble(decFmt.format(allValue / allCount));
			int averageValueAllLevel = calLevel(averageValueAll, levelLimit);
			
			//赋值至各单元bubble中
			for(EmojiBubble bubble: entry.getValue()){
				//平均值  = 总计/个数 取整
				if(bubble.getNumber() > 0)
					bubble.setValue(bubble.getValue()/ bubble.getNumber());
				//百分比 = 个数 / 总个数
				bubble.setPercent(decFmt.format(((float)bubble.getNumber() / (float)allCount) * 100));
				bubble.setAverageValueAll(averageValueAll);
				bubble.setAverageValueAllLevel(averageValueAllLevel);
			}
			
			//保存至总Map
			if(studentsEmojiMap.containsKey(entry.getKey())) {
				studentsEmojiMap.get(entry.getKey()).addAll(entry.getValue());
			}else {
				studentsEmojiMap.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * 
	 * 方法功能说明: 饼图数据获取
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月15日
	 * @param valueList
	 * @param levelLimit
	 * @return
	 */
	public Map<Integer, List<EmojiBubble>> getStudentEmojiPie(CurriculumTbl curriculumTbl,List<EmotionaldataTbl> emotiondataList, int[] levelLimit, Double maxValue, Double minValue) {
		this.levelLimit = levelLimit;
		this.maxValue = maxValue;
		this.minValue = minValue;
		
		studentsEmojiMapTmp = new LinkedHashMap<Integer, List<EmojiBubble>>();
		studentsEmojiMap = new LinkedHashMap<Integer, List<EmojiBubble>>();
		
		for(int i = 0; i < emotiondataList.size(); i++){
			//当前记录的信息
			EmotionaldataTbl emotionaldataTbl = emotiondataList.get(i);
			//当前记录的学生Id
			int studentId = emotionaldataTbl.getFacecategory();
			
			//判断是否存在该学生的情绪列表
			if(!studentsEmojiMapTmp.containsKey(studentId)) {
				addBubble(emotionaldataTbl);			
			}
			
			addUp(studentsEmojiMapTmp.get(studentId), emotionaldataTbl);
		}
		sum();
		return studentsEmojiMap;
	}

	/**
	 * 
	    * @Title: getStudentActionPieList  
	    * @Description: 根据学生行为的气泡时序数据生成饼图数据
	    * @param @param curriculumTbl
	    * @param @param actionBubbleMap
	    * @param @return    参数  
	    * @return Map<Long,List<EmojiBubble>>    返回类型  
	    * @throws
	 */
	public Map<Integer, List<EmojiBubble>> getStudentActionPieList(CurriculumTbl curriculumTbl,
			Map<Integer, List<EmojiBubble>> actionBubbleMap) {
		Map<Integer, List<EmojiBubble>> studentActionPieMap = new LinkedHashMap<Integer, List<EmojiBubble>>();
		for(Map.Entry<Integer, List<EmojiBubble>> entry : actionBubbleMap.entrySet()) {
			//初始化五个传出所需的bubble对象
			EmojiBubble actionListenBubble = new EmojiBubble();
			EmojiBubble actionPracticeBubble = new EmojiBubble();
			EmojiBubble actionDiscussBubble = new EmojiBubble();
			EmojiBubble attentionFocusBubble = new EmojiBubble();
			EmojiBubble attentionDistractionBubble = new EmojiBubble();			
			
			//各情景的计数器
			int actionListen = 0;
			int actionPractice = 0;
			int actionDiscuss = 0;
			//各注意力的计数器
			int attentionFocus = 0;
			int attentionDistraction = 0;
			
			//遍历并累计各行为值
			for(EmojiBubble emojiBubble : entry.getValue()) {
				switch(emojiBubble.getFaceCount()) {
				case -1:
					actionPractice++;
					break;
				case 0:
					actionDiscuss++;
					break;
				case 1:
					actionListen++;
					break;
				}
				switch(emojiBubble.getAttentionLevel()) {
				case 0:
					attentionDistraction++;
					break;
				case 1:
					attentionFocus++;
					break;
				}
			}
			List<EmojiBubble> emojiBubbleList = new ArrayList<EmojiBubble>();
			DecimalFormat decFmt2 = new DecimalFormat("#.##");
			if(actionListen + actionPractice + actionDiscuss != 0) {
				actionListenBubble.setLevel(2);
				actionListenBubble.setNumber(actionListen);
				actionListenBubble.setPercent(decFmt2.format(((float) actionListen / (float) (actionListen + actionPractice + actionDiscuss)) * 100));
				
				actionPracticeBubble.setLevel(1);
				actionPracticeBubble.setNumber(actionPractice);
				actionPracticeBubble.setPercent(decFmt2.format(((float) actionPractice / (float) (actionListen + actionPractice + actionDiscuss)) * 100));
				
				actionDiscussBubble.setLevel(0);
				actionDiscussBubble.setNumber(actionDiscuss);
				actionDiscussBubble.setPercent(decFmt2.format(((float) actionDiscuss / (float) (actionListen + actionPractice + actionDiscuss)) * 100));
				
				emojiBubbleList.add(actionListenBubble);
				emojiBubbleList.add(actionPracticeBubble);
				emojiBubbleList.add(actionDiscussBubble);
			}
			
			if(attentionFocus + attentionDistraction != 0) {
				attentionFocusBubble.setLevel(11);
				attentionFocusBubble.setNumber(attentionFocus);
				attentionFocusBubble.setPercent(decFmt2.format(((float) attentionFocus / (float) (attentionFocus + attentionDistraction)) * 100));
				
				attentionDistractionBubble.setLevel(10);
				attentionDistractionBubble.setNumber(attentionDistraction);
				attentionDistractionBubble.setPercent(decFmt2.format(((float) attentionDistraction / (float) (attentionFocus + attentionDistraction)) * 100));
				
				emojiBubbleList.add(attentionFocusBubble);
				emojiBubbleList.add(attentionDistractionBubble);
			}
			
			studentActionPieMap.put(entry.getKey(), emojiBubbleList);
		}
		return studentActionPieMap;
	}
}