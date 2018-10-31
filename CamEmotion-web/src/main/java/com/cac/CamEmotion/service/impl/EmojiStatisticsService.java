package com.cac.CamEmotion.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmotionaldataTbl;

/**
 * 
 * 类功能说明: 图表数据统计服务类
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2017年9月14日
 */
@Service
public class EmojiStatisticsService {
	private static Logger logger = LogManager.getLogger(EmojiStatisticsService.class);
	//每分钟的记录总数
	private int countPerMinute;
	//传出的气泡图数据列表
	private List<EmojiBubble> totalBubbleList;
	//每一分钟的统计均生成level档数的结果
	private List<EmojiBubble> bubbleList;
	private int nowMinute;
	private String nowDateTime;
	private int playDuration = 0;
	private int faceCount;//每分钟内单位帧中face数大于2的帧数
	private int speakCount;//单位时间内，单位帧中说话人数大于4的帧数
	private String startTimeStr;//开始时间
	private String endTimeStr;//结束时间
	private int secondsPerPart; //每段时间的秒数
	
	/**
	 * 
	 * @param curriculumTbl		课程信息
	 * @param datas				基础表情数据列表
	 * @param valueList					
	 * @param timeList
	 * @param secondsPerPart
	 * @param levelLimit
	 * @param maxValue
	 * @param minValue
	 * @return
	 */
	public List<EmojiBubble> getEmojiBubble(CurriculumTbl curriculumTbl,List<EmotionaldataTbl> datas, List<Double> valueList, List<Date> timeList, int secondsPerPart, int[] levelLimit, Double maxValue, Double minValue) {
		/*
		 * 初始化
		 */
		//传出的气泡图数据列表
		try {
			//上课时长（精确到分钟）
			//获取上课开始时间 yyyy-MM-dd HH:mm:ss
			this.secondsPerPart = secondsPerPart;
			startTimeStr =curriculumTbl.getStarttime();
			endTimeStr = curriculumTbl.getEndtime();
			
			if(endTimeStr == null || "".equals(endTimeStr)){
				endTimeStr = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
			}
//			classTime  = (DateUtil.StringToDate(endTimeStr.substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime() - 
//			DateUtil.StringToDate(curriculumTbl.getStarttime().substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime())/(1000*secondsPerPart) + 1l;
			
			totalBubbleList = new ArrayList<EmojiBubble>();
			bubbleList = null;
			
			

			//获取上课开始时间，精确到秒
			long startMinute = DateUtil.StringToDate(startTimeStr.substring(0, 19), 
					DateStyle.YYYY_MM_DD_HH_MM_SS).getTime();

			
			//初始统计的分钟，是从第1分钟开始统计
			int calMinute = 0;
			countPerMinute = 0;
			
			/*
			 * 循环逻辑
			 */
			for(int i = 0; i < valueList.size(); i++){
				
				
				/*
				 * 计算当前记录属于第几分钟的记录， 0分钟属于第1分钟的记录，1.2分钟属于第2 分钟的记录。因此需要+1 
				 * 如果开始上课时间是9：00：40，那么9：00：50属于第一分钟，9：01：00属于第二分钟，9：02：01属于第三分钟
				 * 即无论开始上课时间是第几秒，均按照整分钟（开始上课时间去秒数，取分钟）进行计算当前属于第几分钟
				 */				
				nowMinute = (int)((timeList.get(i).getTime() - startMinute)/(1000 * secondsPerPart)) + 1;
				nowMinute = nowMinute < 1 ? 1: nowMinute; //老课程存在开课时间滞后的情况
				
				/*
				 * 逻辑：同一分钟内的记录做统计，不同level的归在相应的level中统计；
				 * 由于有平均值以及百分比，需要先把同一分钟的记录值累计，直到收起后，再统计
				 * 分几种情况：
				 * 1. 读到的是同一分钟的记录且不是记录列表（valueList）的最后一条记录，只做累计
				 * 2. 读到的是同一分钟的记录且是记录列表（valueList）的最后一条记录，先做累计，再做统计
				 * 3. 读到的是不同分钟的记录且不是记录列表（valueList）的最后一条记录,先将上一分钟的记录做统计；再新增新的记录，再做累计
				 * 4. 读到的是不同分钟的记录且是记录列表（valueList）的最后一条记录,先将上一分钟的记录做统计；再新增新的记录，再做累计，最后做统计
				 */
				if(calMinute == nowMinute){
					addUp(datas.get(i),valueList.get(i), levelLimit, maxValue, minValue);
				}else{
				
					sum(levelLimit);
					/*
					 * 新增新的统计记录
					 * 1.计算视频跳转秒数playDuration，第一分钟不跳，第二分钟开始跳转nowMinute-1分钟 ，再减去视频开始时间的秒数
					 * 2.获取当前分钟的实际时分
					 */
					playDuration = (nowMinute - 1) * secondsPerPart;
					//nowDateTime = DateUtil.DateToString(timeList.get(i), DateStyle.YYYY_MM_DD_HH_MM_SS);
					nowDateTime = DateUtil.DateToString(new Date(startMinute + playDuration * 1000),DateStyle.YYYY_MM_DD_HH_MM_SS);
					addBubble(levelLimit);
					calMinute = nowMinute;
					countPerMinute = 0;
					addUp(datas.get(i),valueList.get(i), levelLimit, maxValue, minValue);
				}
				
				if(i + 1 ==  valueList.size()){
					sum(levelLimit);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalBubbleList;
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
	private void addBubble(int[] levelLimit){
		//几档逻辑就是数组数量-1
		int levelNumber = levelLimit.length - 1;
		bubbleList = new ArrayList<EmojiBubble>();
		faceCount = 0;
		speakCount = 0;
		
		for(int j = 0; j < levelNumber; j++){
			EmojiBubble bubble = new EmojiBubble();
			bubble.setLevel(j);
			bubble.setMinutes(nowMinute);
			bubble.setPlayDuration(playDuration);
			bubble.setDateTime(nowDateTime);
			bubbleList.add(bubble);
		}
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
	private void addUp(EmotionaldataTbl emotionaldataTbl ,Double value, int[] levelLimit, Double maxValue, Double minValue){
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
		//记录数 +1 
		countPerMinute ++;
		//分钟设定
		bubble.setMinutes(nowMinute);
		//视频播放位置
		bubble.setPlayDuration(playDuration);
		//统计总值
		bubble.setValue(bubble.getValue() + value);
		
		if(emotionaldataTbl != null){
//			frameSet.add(emotionaldataTbl.getFrameid());
			//单位帧中人脸数小于等于2的帧数
//			if(emotionaldataTbl.getFaceCount() <= Constants.FACE_COUNT_SS){
//				faceCount++;
//			}
			//单位帧中人脸数大于2的帧数
			if(emotionaldataTbl.getFaceCount() > Constants.FACE_COUNT_SS){
				faceCount++;
			}
			if(emotionaldataTbl.getSpeak() != null && emotionaldataTbl.getSpeak() > Constants.SPEAK_COUNT_SS) {
				speakCount++;
			}
		}
		
		
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
	private void sum(int[] levelLimit){
		//如果当前分钟与目前统计的记录分钟不一致，则先分析统计完目前统计的分钟记录(平均值/百分比)。再新增新的统计记录
		if(null != bubbleList && 0 < bubbleList.size()){			
			DecimalFormat decFmt = new DecimalFormat("#.##");
			DecimalFormat decFmt2 = new DecimalFormat("#.##");
			double allValue = 0;
			int allCount = 0;
			for(EmojiBubble bubble: bubbleList){
				allValue += bubble.getValue();
				allCount += bubble.getNumber();
				
				//平均值  = 总计/个数 取整
				if(bubble.getNumber() > 0)
					bubble.setValue(bubble.getValue()/ bubble.getNumber());

				//百分比 = 个数 / 总个数
				bubble.setPercent(decFmt.format(((float)bubble.getNumber() / (float)countPerMinute) * 100));
				
			}
			double averageValueAll = Double.parseDouble(decFmt2.format(allValue / allCount));
			double averageFaceCount = Double.parseDouble(decFmt2.format((float)allCount / secondsPerPart));
			double faceCountPercent = Double.parseDouble(decFmt2.format(((float)faceCount / (float)secondsPerPart * 100)));
			double speakCountPercent = Double.parseDouble(decFmt2.format(((float)speakCount / (float)secondsPerPart * 100)));
			int averageValueAllLevel = calLevel(averageValueAll, levelLimit);
			for(EmojiBubble bubble: bubbleList){
				bubble.setAverageValueAll(averageValueAll);
				bubble.setAverageFaceCount(averageFaceCount);
				bubble.setFaceCountPercent(faceCountPercent);
				bubble.setSpeakCountPercent(speakCountPercent);
				bubble.setAverageValueAllLevel(averageValueAllLevel);
			}
			
			//存至总列表
			totalBubbleList.addAll(bubbleList);
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
	public List<EmojiBubble> getEmojiPie(List<Double> valueList, int[] levelLimit, Double maxValue, Double minValue) {
		totalBubbleList = new ArrayList<EmojiBubble>();
		bubbleList = null;
		//新增新的统计记录
		addBubble(levelLimit);
		countPerMinute = 0;
		for(int i = 0; i < valueList.size(); i++){
			addUp(null,valueList.get(i), levelLimit, maxValue, minValue);
		}
		sum(levelLimit);
		return totalBubbleList;
	}
}
