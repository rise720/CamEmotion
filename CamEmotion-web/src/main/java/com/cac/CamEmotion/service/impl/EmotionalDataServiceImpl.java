package com.cac.CamEmotion.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.dao.EmotionaldataTblMapper;
import com.cac.CamEmotion.dao.EmotiondataStatisticsMapper;
import com.cac.CamEmotion.dao.EmotiondataSummaryTblMapper;
import com.cac.CamEmotion.dao.FacefeatureTblMapper;
import com.cac.CamEmotion.dao.StatisticalStandardTblMapper;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.jsonModel.ComparativeAnalysisModel;
import com.cac.CamEmotion.jsonModel.ComprehensiveEvaluationJson;
import com.cac.CamEmotion.jsonModel.PDFModel;
import com.cac.CamEmotion.jsonModel.ReportData;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotionBubble;
import com.cac.CamEmotion.model.EmotionComparativeAnalysisData;
import com.cac.CamEmotion.model.EmotionaldataTbl;
import com.cac.CamEmotion.model.EmotiondataStatistics;
import com.cac.CamEmotion.model.EmotiondataSummaryTbl;
import com.cac.CamEmotion.model.StatisticalStandardTbl;
import com.cac.CamEmotion.model.StudentInfo;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.cac.CamEmotion.service.StudentsEmotionService;
import com.cac.CamEmotion.utils.PDFUtil;

import sun.misc.BASE64Decoder;

@Service
public class EmotionalDataServiceImpl implements EmotionalDataService {
	private static Logger logger = LogManager.getLogger(EmotionalDataServiceImpl.class);

	// 情绪数据分析的操作服务类
	@Resource
	private EmotionaldataTblMapper emotionaldataDao;
	
	//学生情绪统计服务类
	@Resource
	private StudentsEmotionService studentsEmotionService;

	// 气泡图数据统计服务类
	@Resource
	private EmojiStatisticsService emojiStatisticsService;

	@Resource
	private CurriculumTblMapper curriculumTblDao;

	@Resource
	private EmotiondataStatisticsMapper emotiondataStatisticsDao;

	@Resource
	private StatisticalStandardTblMapper statisticalStandardDao;

	@Resource
	private EmotiondataSummaryTblMapper emotiondataSummaryDao;
	
	@Resource
	private FacefeatureTblMapper facefeatureDao; 

	/**
	 * 
	 * 方法功能说明:
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月13日
	 * @param curriculumid	课程id
	 * @param statisticsType 0:下课后，课堂分析统计及学生个体分析统计，并存数据库	1：查看课堂分析  2：查看学生个体分析一览   3：查产看学生个体分析 4: 合并学生，即：重新统计学生数据 ，并保存数据库 
	 * @return
	 */
	@Override
	public List<EmojiReportData> getEmojiReportData(EmojiQueryModel query) {
		/**
		 * 获取该堂课的场景参数，用于之后的判断处理
		 */
		List<EmojiReportData> resultData = null;
		//先判断该堂课是否下课，如果已经下课则去统计表中查询数据，如果没有下课则去原始表中查找数据
		CurriculumTbl curriculumTbl = curriculumTblDao.selectByPrimaryKey(query.getCurriculumid());
		
		//是否需要重新统计并保存数据库
		int calEmotionToDB = Constants.calEmotionToDB;
		
		//检查该课堂是否已经完成分析统计
		EmotiondataStatistics eStatistics = new EmotiondataStatistics();
		eStatistics.setCurriculumid(query.getCurriculumid());
		int eStatisticsCount = emotiondataStatisticsDao.countByModel(eStatistics);
		
		/**
		 * 根据各个场景的情况，进行相应的处理
		 */
		//场景1: 下课时收到数据统计请求，且没有统计数据。此时则进行课堂及学生分析统计，并存至数据库
		if((calEmotionToDB == 1 || query.getStatisticsType() == 0)  && curriculumTbl.getClassstatus() == 1 && eStatisticsCount == 0){
			logger.info("下课时收到数据统计请求，正在生成统计数据并保存数据库,课程Id:" + query.getCurriculumid());
			//获取基础表情数据	
			List<EmotionaldataTbl> valenceList = emotionaldataDao.selectEmotionaldataList(curriculumTbl, 0);//valence统计集合
			List<EmotionaldataTbl> sourceList = emotionaldataDao.selectEmotionaldataListJson(curriculumTbl);	//情景统计集合
//			List<EmotionaldataTbl> studentActionList = emotionaldataDao.selectStudentActionList(curriculumTbl,0);	//各学生每秒的行为数据集合
			//课堂分析统计
			EmojiReportData emojiReportData = handleEmojiReportData(curriculumTbl, valenceList, sourceList);
			//学生分析统计
//			List<EmojiReportData> studentEmojiReportData = studentsEmotionService.handleStudentEmojiReport(
//					curriculumTbl, valenceList, studentActionList, emojiReportData.getSourceBubbleList());
			//课堂整体分析数据存表
			saveStatisticsDataToDB(query.getCurriculumid(), emojiReportData);
			//学生个体统计数据存表
//			studentsEmotionService.saveStudentsStatisticsDataToDB(curriculumTbl, studentEmojiReportData);
			logger.info("下课时收到数据统计请求，正在生成统计数据并保存数据库-结束");
			return null;
		}
		//场景2： 收到查看课堂分析的请求，且已统计完数据。返回数据库分析数据
		if(query.getStatisticsType() == 1 && eStatisticsCount > 0) {
			logger.info("收到查看课堂分析的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据,课程Id:" + query.getCurriculumid());
			resultData = getEmotionStatisticsFromDB(curriculumTbl);
			logger.info("收到查看课堂分析的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据-结束");
			return resultData;
		}
		//场景3： 收到查看个体学生一览的请求，且已统计完数据。返回数据库分析数据
		if(query.getStatisticsType() == 2 && eStatisticsCount > 0) {
			logger.info("收到查看个体学生一览的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据,课程Id:" + query.getCurriculumid());
			resultData = studentsEmotionService.getStudentsEmotionListFromDB(curriculumTbl);
			logger.info("收到查看个体学生一览的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据-结束");
			return resultData;
		}
		//场景4： 收到查看个体学生分析的请求，且已统计完数据。返回数据库分析数据
		if(query.getStatisticsType() == 3 && eStatisticsCount > 0) {
			logger.info("收到查看个体学生分析的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据,课程Id:" + query.getCurriculumid());
			resultData = studentsEmotionService.getStudentEmotionDetailFromDB(curriculumTbl, query.getStudentId(), 0);
			logger.info("收到查看个体学生分析的请求，且数据库中已存在统计数据。正在读取数据库，生成分析数据-结束");
			return resultData;
		}
		//场景5：上课中收到 查看课堂分析的请求，实时分析返回数据
		if(query.getStatisticsType() == 1 && curriculumTbl.getClassstatus() == 0) {
			logger.info("上课中收到 查看课堂分析的请求，正在实时生成分析数据,课程Id:" + query.getCurriculumid());
			//获取基础表情数据	
			List<EmotionaldataTbl> valenceList = emotionaldataDao.selectEmotionaldataList(curriculumTbl, 0);//valence统计集合
			List<EmotionaldataTbl> sourceList = emotionaldataDao.selectEmotionaldataListJson(curriculumTbl);	//情景统计集合
			//课堂分析统计
			EmojiReportData emojiReportData = handleEmojiReportData(curriculumTbl, valenceList, sourceList);
			//课堂分析统计的参考值生成
			emojiReportData = emojiSummary(curriculumTbl, emojiReportData);
			resultData = new ArrayList<>();
			resultData.add(emojiReportData);
			logger.info("上课中收到 查看课堂分析的请求，正在实时生成分析数据-结束");
			return resultData;
		}
		//场景6：上课中收到 查看个体学生一览的请求，实时分析返回数据
		if(query.getStatisticsType() == 2 && curriculumTbl.getClassstatus() == 0) {
			logger.info("上课中收到查看个体学生一览的请求，正在实时生成分析数据,课程Id:" + query.getCurriculumid());
			//获取基础表情数据	
			List<EmotionaldataTbl> valenceList = emotionaldataDao.selectEmotionaldataList(curriculumTbl, 0);//valence统计集合
			List<EmotionaldataTbl> sourceList = emotionaldataDao.selectEmotionaldataListJson(curriculumTbl);	//情景统计集合
			List<EmotionaldataTbl> studentActionList = emotionaldataDao.selectStudentActionList(curriculumTbl,0);	//各学生每秒的行为数据集合
			//课堂分析统计
			EmojiReportData emojiReportData = handleEmojiReportData(curriculumTbl, valenceList, sourceList);
			//学生分析统计
			resultData = studentsEmotionService.handleStudentEmojiReport(curriculumTbl, valenceList, studentActionList, emojiReportData.getSourceBubbleList());
			logger.info("上课中收到查看个体学生一览的请求，正在实时生成分析数据-结束");
			return resultData;
		}
		//场景7：上课中收到 个体学生分析的请求，实时分析返回数据
		if(query.getStatisticsType() == 3 && curriculumTbl.getClassstatus() == 0) {
			logger.info("上课中 个体学生分析的请求，正在实时生成分析数据,课程Id:" + query.getCurriculumid());
			//获取基础表情数据	
			List<EmotionaldataTbl> valenceList = emotionaldataDao.selectEmotionaldataList(curriculumTbl, 0);//valence统计集合
			List<EmotionaldataTbl> sourceList = emotionaldataDao.selectEmotionaldataListJson(curriculumTbl);	//情景统计集合
			List<EmotionaldataTbl> studentActionList = emotionaldataDao.selectStudentActionList(curriculumTbl,0);	//各学生每秒的行为数据集合
			//课堂分析统计
			EmojiReportData emojiReportData = handleEmojiReportData(curriculumTbl, valenceList, sourceList);
			//学生分析统计
			List<EmojiReportData> studentEmojiReportData = studentsEmotionService.handleStudentEmojiReport(curriculumTbl, valenceList, studentActionList, emojiReportData.getSourceBubbleList());
			List<EmojiReportData> emojiReportDatas = new ArrayList<EmojiReportData>();
			for(EmojiReportData emoji : studentEmojiReportData) {
				if(emoji.getStudentInfo().getStudentId() == query.getStudentId()) {
					emojiReportDatas.add(emoji);
					break;
				}
			}
			logger.info("上课中 个体学生分析的请求，正在实时生成分析数据-结束");
			return emojiReportDatas;
		}
		//场景8：下课后收到合并学生时，重新统计学生数据并保存数据库
		if(query.getStatisticsType() == 4 && curriculumTbl.getClassstatus() == 1) {
			logger.info("下课后收到合并学生时，重新统计学生数据并保存数据库,课程Id:" + query.getCurriculumid());
			EmojiReportData emojiReportData = getEmotionStatisticsFromDB(curriculumTbl).get(0);
			//插入
			//获取基础表情数据	
			List<EmotionaldataTbl> valenceList = emotionaldataDao.selectEmotionaldataList(curriculumTbl, query.getStudentId());	//valence统计集合
			List<EmotionaldataTbl> studentActionList = emotionaldataDao.selectStudentActionList(curriculumTbl,query.getStudentId());	
			
			//学生分析统计
			List<EmojiReportData> studentEmojiReportData = studentsEmotionService.handleStudentEmojiReport(curriculumTbl, valenceList, studentActionList, emojiReportData.getSourceBubbleList());
			//学生个体统计数据存表
			studentsEmotionService.saveStudentsStatisticsDataToDB(curriculumTbl, studentEmojiReportData);
			logger.info("下课后收到合并学生时，重新统计学生数据并保存数据库-结束");
			return studentEmojiReportData;
			
		}
		//其他情况均返回空
		logger.error("当前场景不生成分析数据，请求拒绝。课程Id：" + curriculumTbl.getId() + "  课程状态：" + curriculumTbl.getClassstatus()
		+ " 数据库中统计记录数：" + eStatisticsCount);
		return null;
	}

	@Override
	public List<EmotionaldataTbl> getDateData(List<EmotionaldataTbl> list, int intervaltime) {

		// 图表中y坐标的值（表情）所在的列表
		List<EmotionaldataTbl> yAxisList = new ArrayList<EmotionaldataTbl>();
		/*
		 * 获取课程的最初时间，以该时间作为基准，按分钟统计表情值
		 */
		long begin = list.get(0).getCreatedate().getTime(); // 课程开始时间
		long end = list.get(list.size() - 1).getCreatedate().getTime(); // 课程结束时间
		// 获取课程总计时间（转换为分钟）
		long continueMinutes = ((end - begin) % (1000 * 60) == 0) ? (end - begin) / (1000 * 60)
				: (end - begin) / (1000 * 60) + 1;
		// 统计每一分钟范围内的表情平均值，表情平均值 = 表情总值/个数
		// i指当前分钟，j指当前分钟内表情个数，k指循环至数据库(传参List)的当前条数
		int k = 0;
		for (int i = 1; i <= continueMinutes; i++) {
			int j = 0;
			EmotionaldataTbl yAxis = new EmotionaldataTbl();
			yAxisList.add(yAxis);
			// 初始化三种表情值
			yAxis.setJoy(0d);
			yAxis.setValence(0d);
			yAxis.setContempt(0d);
			// 循环检索数据库值，统计所有在当前分钟内表情值
			for (int ii = k; ii < list.size(); ii++) {
				EmotionaldataTbl emotionTemp = list.get(ii);
				// 如果记录在当前分钟内则统计总值，并记录个数
				long defValue = i * 60000 - (emotionTemp.getCreatedate().getTime() - begin);
				if (defValue > 0 && defValue <= 60000) {
					yAxis.setJoy(Double.valueOf(yAxis.getJoy().doubleValue() + emotionTemp.getJoy().doubleValue()));
					yAxis.setValence(
							Double.valueOf(yAxis.getValence().doubleValue() + emotionTemp.getValence().doubleValue()));
					yAxis.setContempt(Double
							.valueOf(yAxis.getContempt().doubleValue() + emotionTemp.getContempt().doubleValue()));
					j++;
				} else {
					k = ii;
					break;
				}
			}
			// 计算本分钟的平均值，并退出统计
			if (j > 0) {
				yAxis.setJoy(yAxis.getJoy() / j);
				yAxis.setValence(yAxis.getValence() / j);
				yAxis.setContempt(yAxis.getContempt() / j);
			}
		}
		return yAxisList;
	}

	@Override
	public List<EmotionBubble> getBubble(List<EmotionaldataTbl> list, int intervaltime) {

		List<EmotionBubble> emotionBubbleList = new ArrayList<EmotionBubble>();
		/*
		 * 获取课程的最初时间，以该时间作为基准，按分钟统计表情值
		 */
		long begin = list.get(0).getCreatedate().getTime(); // 课程开始时间
		long end = list.get(list.size() - 1).getCreatedate().getTime(); // 课程结束时间
		// 获取课程总计时间（转换为分钟）
		long continueMinutes = ((end - begin) % (1000 * 60) == 0) ? (end - begin) / (1000 * 60)
				: (end - begin) / (1000 * 60) + 1;
		// 统计每一分钟范围内的表情平均值，表情平均值 = 表情总值/个数
		// i指当前分钟，j指当前分钟内表情个数，k指循环至数据库(传参List)的当前条数
		int k = 0;
		// 每分钟循环一次
		for (int i = 1; i <= continueMinutes; i++) {
			int j = 0;

			EmotionBubble emotionBubble = new EmotionBubble();
			EmotionaldataTbl emotionalData = new EmotionaldataTbl();
			EmotionBubble.Time emotionTime = emotionBubble.new Time();
			EmotionBubble.Count emotionCount = emotionBubble.new Count();

			emotionBubble.setEmotionaldataTbl(emotionalData);
			emotionBubble.setCount(emotionCount);
			emotionBubble.setTime(emotionTime);

			emotionBubbleList.add(emotionBubble);
			// 初始化三种表情值
			emotionalData.setJoy(0.0);
			emotionalData.setValence(0.0);
			emotionalData.setContempt(0.0);
			// 初始化三种表情的在当前分钟内的最大值
			double joyTop = 0.0;
			double valenceTop = 0.0;
			double contemptTop = 0.0;

			// 循环检索数据库值，统计所有在当前分钟内表情值
			for (int ii = k; ii < list.size(); ii++) {
				EmotionaldataTbl emotionTemp = list.get(ii);
				// 如果记录在当前分钟内则统计总值，并记录个数
				long defValue = i * 60000 - (emotionTemp.getCreatedate().getTime() - begin);
				if (defValue > 0 && defValue <= 60000) {
					double joyTemp = emotionTemp.getJoy().doubleValue();
					double valenceTemp = emotionTemp.getValence().doubleValue();
					double contemptTemp = emotionTemp.getContempt().doubleValue();

					// 如果值的绝对值小于0.5，则忽略
					joyTemp = Math.abs(joyTemp) < 0.5 ? 0 : joyTemp;
					valenceTemp = Math.abs(valenceTemp) < 0.5 ? 0 : valenceTemp;
					contemptTemp = Math.abs(contemptTemp) < 0.5 ? 0 : contemptTemp;

					emotionalData.setJoy(Double.valueOf(emotionalData.getJoy().doubleValue() + joyTemp));
					emotionalData.setValence(Double.valueOf(emotionalData.getValence().doubleValue() + valenceTemp));
					emotionalData.setContempt(Double.valueOf(emotionalData.getContempt().doubleValue() + contemptTemp));
					// 取最大值
					if (joyTop <= joyTemp) {
						joyTop = joyTemp;
						emotionTime.setJoyTime((emotionTemp.getCreatedate().getTime() - begin) / 60000f);
					}
					if (valenceTop <= valenceTemp) {
						valenceTop = valenceTemp;
						emotionTime.setValenceTime((emotionTemp.getCreatedate().getTime() - begin) / 60000f);
					}
					if (contemptTop <= contemptTemp) {
						contemptTop = contemptTemp;
						emotionTime.setContemptTime((emotionTemp.getCreatedate().getTime() - begin) / 60000f);
					}
					// 累计个数，不统计为0的值
					if (joyTemp != 0)
						emotionCount.setJoyCount(emotionCount.getJoyCount() + 1);
					if (valenceTemp != 0)
						emotionCount.setValenceCount(emotionCount.getValenceCount() + 1);
					if (contemptTemp != 0)
						emotionCount.setContemptCount(emotionCount.getContemptCount() + 1);
					j++;
				} else {
					k = ii;
					break;
				}
			}
			// 计算本分钟的平均值，并退出统计
			if (j > 0) {
				emotionalData.setJoy(emotionalData.getJoy() / j);
				emotionalData.setValence(emotionalData.getValence() / j);
				emotionalData.setContempt(emotionalData.getContempt() / j);
			}
		}
		return emotionBubbleList;
	}

	@Override
	public List<EmojiBubble> getEmojiBubble(CurriculumTbl curriculumTbl, List<EmotionaldataTbl> datas,
			List<Double> valueList, List<Date> timeList, int secondsPerPart, int[] levelLimit, Double maxValue, Double minValue) {
		return emojiStatisticsService.getEmojiBubble(curriculumTbl, datas, valueList, timeList, secondsPerPart, levelLimit, maxValue,
				minValue);
	}

	@Override
	public List<EmojiBubble> getEmojiPie(List<Double> valueList, int[] levelLimit, Double maxValue, Double minValue) {
		return emojiStatisticsService.getEmojiPie(valueList, levelLimit, maxValue, minValue);
	}

	/**
	 * 
	    * @Title: getEmotionStatisticsFromDB  
	    * @Description: 从数据库表中获取表情统计数据
	    * @param @param curriculumTbl
	    * @param @return    参数  
	    * @return List<EmojiReportData>    返回类型  
	    * @throws
	 */
	private List<EmojiReportData> getEmotionStatisticsFromDB(CurriculumTbl curriculumTbl) {
		/**
		 * 通过不同的charttype查找各类数据
		 */
		List<EmotiondataStatistics> dataStatisticsList = null;	//申明查询结果集对象
		EmotiondataStatistics eStatistics = new EmotiondataStatistics();	//查找条件
		eStatistics.setCurriculumid(curriculumTbl.getId());

		//情绪气泡
		eStatistics.setCharttype("valenceBubble");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> valenceBubbleList = handleDataStatistics(dataStatisticsList);
		//情绪饼图
		dataStatisticsList.clear();
		eStatistics.setCharttype("valencePie");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> valencePieList = handleDataStatistics(dataStatisticsList);
		//情景气泡
		dataStatisticsList.clear();
		eStatistics.setCharttype("sourceBubble");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> sourceBubbleList = handleDataStatistics(dataStatisticsList);
		//情景饼图
		dataStatisticsList.clear();
		eStatistics.setCharttype("faceCountPie");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> faceCountPieList = handleDataStatistics(dataStatisticsList);
		
//			// 波动率
//			dataStatisticsList.clear();
//			eStatistics.setCharttype("waveRate");
//			dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
//			valenceVolatilityList = handleDataStatistics(dataStatisticsList);
//
		// 平均值
		dataStatisticsList.clear();
		eStatistics.setCharttype("valenceAverage");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> valenceAverageList = handleDataStatistics(dataStatisticsList);
//
//			// 波动偏差
//			dataStatisticsList.clear();
//			eStatistics.setCharttype("valenceFluctuationDeviation");
//			dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
//			valenceFluctuationDeviationList = handleDataStatistics(dataStatisticsList);
//
//			// 平均振幅
//			dataStatisticsList.clear();
//			eStatistics.setCharttype("valenceMeanAmplitude");
//			dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
//			valenceMeanAmplitudeList = handleDataStatistics(dataStatisticsList);
//
//			// 波动均匀
//			dataStatisticsList.clear();
//			eStatistics.setCharttype("uniformFluctuation");
//			dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
//			uniformFluctuationList = handleDataStatistics(dataStatisticsList);
		
		// 学生参与度CH
		dataStatisticsList.clear();
		eStatistics.setCharttype("participation");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> participationList = handleDataStatistics(dataStatisticsList);
		
		//T行为占有率 RT
		dataStatisticsList.clear();
		eStatistics.setCharttype("tActionRate");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> tActionRateList = handleDataStatistics(dataStatisticsList);
		
		//情感课堂参与度CH
		dataStatisticsList.clear();
		eStatistics.setCharttype("emotionCH");
		dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
		List<EmojiBubble> emotionCHList = handleDataStatistics(dataStatisticsList);
		
		//生成返回结果集
		EmojiReportData emojiReportData = new EmojiReportData(new StudentInfo(0));//学生id为0代表是整堂课
		emojiReportData.setValenceBubbleList(valenceBubbleList);
		emojiReportData.setValencePieList(valencePieList);
		emojiReportData.setSourceBubbleList(sourceBubbleList);
		emojiReportData.setFaceCountPieList(faceCountPieList);
//		emojiReportData.setValenceVolatilityList(valenceVolatilityList);
		emojiReportData.setValenceAverageList(valenceAverageList);
//		emojiReportData.setValenceFluctuationDeviationList(valenceFluctuationDeviationList);
//		emojiReportData.setValenceMeanAmplitudeList(valenceMeanAmplitudeList);
//		emojiReportData.setUniformFluctuationList(uniformFluctuationList);
		
		emojiReportData.setParticipationList(participationList);
		emojiReportData.settActionRateList(tActionRateList);
		emojiReportData.setEmotionCHList(emotionCHList);
		
		//课堂分析统计的参考值生成
		emojiReportData = emojiSummary(curriculumTbl, emojiReportData);
		
		List<EmojiReportData> emojiReportDatas = new ArrayList<EmojiReportData>();
		emojiReportDatas.add(emojiReportData);
		
		return emojiReportDatas;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 数据库查询并处理整个课堂的分析数据
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月13日
	 * @param currid
	 * @return
	 *
	 */	
	private EmojiReportData handleEmojiReportData(CurriculumTbl curriculumTbl, List<EmotionaldataTbl> list, List<EmotionaldataTbl> sourceList) {

		List<EmojiBubble> valenceBubbleList = null;
		List<EmojiBubble> valencePieList = null;

		List<EmojiBubble> sourceBubbleList = null;

		List<EmojiBubble> faceCountPieList = null;

//		List<EmojiBubble> valenceVolatilityList = null;// 波动率
//
		List<EmojiBubble> valenceAverageList = null; // 平均值
//
//		List<EmojiBubble> valenceFluctuationDeviationList = null; // 波动偏差
//
//		List<EmojiBubble> uniformFluctuationList = null; // 波动均匀
		
		List<EmojiBubble> participationList = null; //学生参与度 CH
		
		List<EmojiBubble> tActionRateList = null; //T行为占有率 RT
		
		List<EmojiBubble> emotionCHList = null; //情感课堂参与度 CH
		
		int secondsPerPart = 30;	//气泡图或折线图的单位时间的秒数

		EmojiReportData emojiReportData = new EmojiReportData(new StudentInfo(0));
		try {
			if (null == list || 0 == list.size()) {
				return null;
			}

			// 取得各数据List
			// 积极性
			List<Double> valenceList = new ArrayList<Double>();
			// 时间0
			List<Date> timeList = new ArrayList<Date>();

			for (EmotionaldataTbl emotion : list) {
				valenceList.add(emotion.getValenceview());
				timeList.add(emotion.getCreatedate());
			}

			// 取得积极性的气泡图及饼图
			int[] valenceLevelLimit = { -100, -10, 10, 100 };
			valenceBubbleList = getEmojiBubble(curriculumTbl, list, valenceList, timeList, secondsPerPart, valenceLevelLimit, 100.0,
					-100.0);
			emojiReportData.setValenceBubbleList(valenceBubbleList);

			valencePieList = getEmojiPie(valenceList, valenceLevelLimit, 100.0, -100.0);
			emojiReportData.setValencePieList(valencePieList);

			// 听课、练习报表数据
//			valenceList.clear();
//			timeList.clear();
//			for (EmotionaldataTbl emotion : sourceList) {
//				valenceList.add(emotion.getValenceview());
//				timeList.add(emotion.getCreatedate());
//			}
//			int[] sourceLevelLimit = { -100, 100 };
//			sourceBubbleList = getEmojiBubble(curriculumTbl, sourceList, valenceList, timeList, secondsPerPart, sourceLevelLimit, 100.0,
//					-100.0);
//
//			int faceUp2 = 0;
//			int faceDown2 = 0;
//			int speak = 0;
//
//			List<EmojiBubble> sourceBubbleListTemp = new ArrayList<>();
//
//			String endTimeStr = curriculumTbl.getEndtime();
//			// 未下课时，是没有结束时间的，故取当前时间
//			if (endTimeStr == null || "".equals(endTimeStr)) {
//				endTimeStr = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
//			}
//
//			// 上课时长（精确到分钟）
//			long classTime = (DateUtil.StringToDate(endTimeStr.substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS).getTime()
//					- DateUtil.StringToDate(curriculumTbl.getStarttime().substring(0, 19), DateStyle.YYYY_MM_DD_HH_MM_SS)
//							.getTime())
//					/ (1000 * secondsPerPart) + 1l;
//
//			int bubbleSeq = 0;
//
//			/*
//			 * 情景分析补充：
//			 * 由sourceBubbleList返回的只包含存在表情数据的分钟数，对应情景分析而言，如果一分钟内没有一条表情数据，则认为是练习。
//			 * 以下逻辑即是对此类情况的数据补充 统计范围是从上课开始到结束的每一分钟
//			 */
//			for (int i = 1; i <= classTime; i++) {
//				// 如果某一分钟存在表情数据，则不需要数据补充，判断当前分钟情景是上课或练习
//				if (sourceBubbleList.get(bubbleSeq).getMinutes() == i) {
//					// 单位时间单位帧中人脸数大于2的帧数占单位分钟内总帧数的百分比大于70%【抬头听课】
//					//目前共三种行为： 听课为1  讨论为-1  练习为0
//					if (sourceBubbleList.get(bubbleSeq).getFaceCountPercent() > Constants.FRAME_COUNT_PERCENT) {
//						
//						if(sourceBubbleList.get(bubbleSeq).getSpeakCountPercent() > Constants.SPEAK_COUNT_PERCENT) {
//							sourceBubbleList.get(bubbleSeq).setFaceCount(-1);
//							speak++;
//						}else {
//							sourceBubbleList.get(bubbleSeq).setFaceCount(1);
//							faceUp2++;							
//						}
//						
//					} else {
//						sourceBubbleList.get(bubbleSeq).setFaceCount(0);
//						faceDown2++;
//					}
//					sourceBubbleListTemp.add(sourceBubbleList.get(bubbleSeq));
//					if (bubbleSeq < sourceBubbleList.size() - 1)
//						bubbleSeq++;
//					continue;
//				} else { // 如果某一分钟不存在表情数据，则需要数据补充
//					for (int j = 0; j < sourceLevelLimit.length - 1; j++) {
//						EmojiBubble emojiBubble = new EmojiBubble();
//						emojiBubble.setMinutes(i);
//						emojiBubble.setFaceCount(0);
//						emojiBubble.setLevel(j);
//						emojiBubble.setPercent("0");
//						emojiBubble.setValue(0);
//						emojiBubble.setDateTime(DateUtil.StringToString(
//								DateUtil.addSecond(curriculumTbl.getStarttime().substring(0, 19), (i - 1)*secondsPerPart),
//								DateStyle.YYYY_MM_DD_HH_MM_SS));
//						
//						emojiBubble.setPlayDuration((i - 1)*secondsPerPart);
//
//						sourceBubbleListTemp.add(emojiBubble);
//					}
//					faceDown2++;
//				}
//			}
//			emojiReportData.setSourceBubbleList(sourceBubbleListTemp);

			// 听课 练习环形图
//			DecimalFormat decFmt2 = new DecimalFormat("#.##");
//			EmojiBubble faceUp2B = new EmojiBubble();
//			EmojiBubble faceDown2B = new EmojiBubble();
//			EmojiBubble solutionSpeak = new EmojiBubble();
//			if (faceUp2 + faceDown2 + speak != 0) {
//				faceUp2B.setLevel(2);
//				faceUp2B.setNumber(faceUp2);
//				faceUp2B.setPercent(decFmt2.format(((float) faceUp2 / (float) (faceUp2 + faceDown2 + speak)) * 100));
//
//				faceDown2B.setLevel(1);
//				faceDown2B.setNumber(faceDown2);
//				faceDown2B.setPercent(decFmt2.format(((float) faceDown2 / (faceUp2 + faceDown2 + speak)) * 100));
//				
//				solutionSpeak.setLevel(0);
//				solutionSpeak.setNumber(speak);
//				solutionSpeak.setPercent(decFmt2.format(((float) speak / (faceUp2 + faceDown2 + speak)) * 100));
//			}
//
//			faceCountPieList = new ArrayList<>();
//			faceCountPieList.add(solutionSpeak);
//			faceCountPieList.add(faceDown2B);
//			faceCountPieList.add(faceUp2B);
//			// 听课 练习
//			emojiReportData.setFaceCountPieList(faceCountPieList);

			// **************************************************************************************************************************************************************************
			// 每分钟valence的平均值
			List<Double> valueList = new ArrayList<>();
			for (EmojiBubble emojiBubble : valenceBubbleList) {
				if (emojiBubble.getLevel() == 1) {
					valueList.add(emojiBubble.getAverageValueAll());
				}
			}

			// 平均值
//			double averageVal = averageFun(valueList);
//			valenceAverageList = new ArrayList<>();
//			EmojiBubble emojiBubble_average = new EmojiBubble();
//			emojiBubble_average.setPercent(Double.toString(formatDouble2(averageVal)));
//			valenceAverageList.add(emojiBubble_average);
//			emojiReportData.setValenceAverageList(valenceAverageList);
//			
//			// 学生参与度CH
//			participationList = new ArrayList<>();
//			EmojiBubble emojiBubble_participation = new EmojiBubble();
//			emojiBubble_participation.setPercent(participationFun(sourceBubbleListTemp, classTime));
//			participationList.add(emojiBubble_participation);
//			emojiReportData.setParticipationList(participationList);
//			
//			//T行为占有率 RT
//			tActionRateList = new ArrayList<>();
//			EmojiBubble emojiBubble_tActionRate = new EmojiBubble();
//			emojiBubble_tActionRate.setPercent(tActionRateFun(sourceBubbleListTemp, classTime));
//			tActionRateList.add(emojiBubble_tActionRate);
//			emojiReportData.settActionRateList(tActionRateList);
//			
//			//情感课堂参与度
//			emotionCHList = new ArrayList<>();
//			EmojiBubble emojiBubble_emotionCH = new EmojiBubble();
//			emojiBubble_emotionCH.setPercent(emotionCHFun(valenceBubbleList, emojiBubble_participation, classTime));
//			emotionCHList.add(emojiBubble_emotionCH);
//			emojiReportData.setEmotionCHList(emotionCHList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return emojiReportData;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 求平均值
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月19日
	 * @param list
	 * @return
	 *
	 */
	private double averageFun(List<Double> list) {
		double war = 0;
		if (list != null && list.size() > 0) {
			for (Double d : list) {
				war += d;
			}
			war = war / list.size();
		}
		return war;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 波动率
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月19日
	 * @param list
	 * @param averageVal
	 * @return
	 *
	 */
//	private double waveRateFun(List<Double> list, double averageVal) {
//		int bdCount = 0;
//		double waveRate = 0;
//		for (int i = 1; i < list.size(); i++) {
//			if ((list.get(i) > averageVal && list.get(i - 1) < averageVal)
//					|| (list.get(i) < averageVal && list.get(i - 1) > averageVal)) {
//				bdCount++;
//			}
//		}
//		// 当课堂时长不一致时，统一标准
//		waveRate = formatDouble2(((float) bdCount / (float) list.size()) * 45);
//		return waveRate;
//	}
	
	/**
	 * 方法功能说明：计算 学生参与度CH
	 * @param sourceBubbleListTemp
	 * @param classTime
	 * @return
	 */
	private String participationFun(List<EmojiBubble> sourceBubbleListTemp, long classTime) {
		int count = 0;
		int tmpValue = 0;
		int thisValue = 0;
		for(int i = 0; i < sourceBubbleListTemp.size(); i++) {
			thisValue = sourceBubbleListTemp.get(i).getFaceCount();
			if(i == 0 || tmpValue != thisValue) {
				count++;
				tmpValue = thisValue;
			}
		}
		//课堂参与度CH = 波动总和-1/整节课的统计次数（活跃=1,倾听=0,思考=-1,出现变化则计一次波动）
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format((float)(count - 1)/classTime);
	}
	
	/**
	 * 方法功能说明：计算T行为占有率 RT
	 * 公式： RT = NT/N    NT：T行为次数 ；N：教学采样数
 	 * @param sourceBubbleListTemp
	 * @param classTime
	 * @return
	 */
	private String tActionRateFun(List<EmojiBubble> sourceBubbleListTemp, long classTime) {
		int count = 0;
		//目前共三种行为： 听课为1  讨论为-1  练习为0，其中： T行为只有听课
		for(int i = 0; i < sourceBubbleListTemp.size(); i++) {
			if(sourceBubbleListTemp.get(i).getFaceCount() == 1) {
				count++;
			}
		}
		DecimalFormat df = new DecimalFormat("#.####");
		//课堂参与度CH = 波动总和-1/整节课的统计次数（活跃=1,倾听=0,思考=-1,出现变化则计一次波动）
		return df.format((float)count/classTime);
	}
	
	/**
	 * 方法功能说明：计算情感课堂参与度CH
	 * @param valenceBubbleList
	 * @param classTime
	 * @return
	 */
	private String emotionCHFun(List<EmojiBubble> valenceBubbleList, EmojiBubble participation, long classTime) {
		int count = 0;
		int tmpValue = 0;
		for(int i = 0; i < valenceBubbleList.size(); i++) {
			if(i == 0 || tmpValue != valenceBubbleList.get(i).getAverageValueAllLevel()) {
				count ++;
				tmpValue = valenceBubbleList.get(i).getAverageValueAllLevel();
			}
		}
		//课堂参与度CH = 波动总和-1/整节课的统计次数（活跃=1,倾听=0,思考=-1,出现变化则计一次波动）
		float emotionCH = (float)(count - 1)/classTime;
		emotionCH = (emotionCH + Float.parseFloat(participation.getPercent()))/2;
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(emotionCH);
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 波动偏差
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月19日
	 * @param list
	 * @return
	 *
	 */
//	private double fluctuationDeviationFun(List<Double> list, double averageVal) {
//
//		double sum = 0.0;
//		for (Double value : list) {
//			sum += Math.pow(value - averageVal, 2);
//		}
//		double fluctuationDeviation = Math.sqrt(sum / (double) list.size());
//
//		return fluctuationDeviation;
//	}

	/**
	 * 
	 * 
	 * 方法功能说明: 平均振幅
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月19日
	 * @param list
	 * @param averageVal
	 * @return
	 *
	 */
//	private double meanAmplitudeFun(List<Double> list, double averageVal) {
//		double meanAmplitude = ((Collections.max(list) - Collections.min(list)) / averageVal) * (double) 100;
//		return meanAmplitude;
//	}

	/**
	 * 
	 * 
	 * 方法功能说明: 波动均匀
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月19日
	 * @param list
	 * @return
	 *
	 */
//	private Double uniformFluctuationFun(List<Double> list) {
//		List<Double> data1 = list.subList(0, list.size() / 2);
//		List<Double> data2 = list.subList(list.size() / 2, list.size());
//
//		double averageValue1 = averageFun(data1);
//		double waveRate1 = waveRateFun(data1, averageValue1);
//		double fluctuationDeviation1 = fluctuationDeviationFun(data1, averageValue1);
//
//		double averageValue2 = averageFun(data2);
//		double waveRate2 = waveRateFun(data2, averageValue2);
//		double fluctuationDeviation2 = fluctuationDeviationFun(data2, averageValue2);
//
//		double waveRatel = waveRate1 < waveRate2 ? waveRate1 / waveRate2 : waveRate2 / waveRate1;
//		double fluctuationDeviationl = fluctuationDeviation1 < fluctuationDeviation2
//				? fluctuationDeviation1 / fluctuationDeviation2 : fluctuationDeviation2 / fluctuationDeviation1;
//
//		return (waveRatel + fluctuationDeviationl) / 2;
//	}

	/**
	 * 
	 * 
	 * 方法功能说明: 将EmotiondataStatistics转成EmojiBubble
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月13日
	 * @param dataStatisticsList
	 * @return
	 *
	 */
	private List<EmojiBubble> handleDataStatistics(List<EmotiondataStatistics> dataStatisticsList) {
		List<EmojiBubble> bubbleList = new ArrayList<>();
		EmojiBubble eBubble = null;
		for (EmotiondataStatistics eStatistics : dataStatisticsList) {
			eBubble = new EmojiBubble();
			eBubble.setMinutes(eStatistics.getMinutes());
			eBubble.setDateTime(eStatistics.getDatetime());
			eBubble.setValue(eStatistics.getValue());
			eBubble.setNumber(eStatistics.getNumber());
			eBubble.setPercent(eStatistics.getPercent());
			eBubble.setLevel(eStatistics.getLevel());
			eBubble.setName(eStatistics.getName());
			eBubble.setFaceCount(eStatistics.getFacecount());
			eBubble.setAverageFaceCount(eStatistics.getAveragefacecount());
			eBubble.setAverageValueAll(eStatistics.getAveragevalueall());
			eBubble.setFaceCountPercent(eStatistics.getFacecountpercent());
			eBubble.setPlayDuration(eStatistics.getPlayduration());
			bubbleList.add(eBubble);
		}
		return bubbleList;
	}

	private void saveStatisticsDataToDB(int curriculumid, EmojiReportData emojiReportData) {
		
		List<EmotiondataStatistics> eStatistics = new ArrayList<EmotiondataStatistics>();

		// 将处理的数据插入到统计表中
		for (EmojiBubble emojiBubble : emojiReportData.getValenceBubbleList()) {
			eStatistics.add(statisticsData(curriculumid, "valenceBubble", emojiBubble));
		}
		for (EmojiBubble emojiBubble : emojiReportData.getValencePieList()) {
			eStatistics.add(statisticsData(curriculumid, "valencePie", emojiBubble));
		}
//		for (EmojiBubble emojiBubble : emojiReportData.getSourceBubbleList()) {
//			eStatistics.add(statisticsData(curriculumid, "sourceBubble", emojiBubble));
//		}
//		for (EmojiBubble emojiBubble : emojiReportData.getFaceCountPieList()) {
//			eStatistics.add(statisticsData(curriculumid, "faceCountPie", emojiBubble));
//		}
//		for (EmojiBubble emojiBubble : emojiReportData.getValenceAverageList()) {
//			eStatistics.add(statisticsData(curriculumid, "valenceAverage", emojiBubble));
//		}
//		for (EmojiBubble emojiBubble : emojiReportData.getParticipationList()) {
//			eStatistics.add(statisticsData(curriculumid, "participation", emojiBubble));
//		}
//		for (EmojiBubble emojiBubble : emojiReportData.gettActionRateList()) {
//			eStatistics.add(statisticsData(curriculumid, "tActionRate", emojiBubble));
//		}
//		for (EmojiBubble emojiBubble : emojiReportData.getEmotionCHList()) {
//			eStatistics.add(statisticsData(curriculumid, "emotionCH", emojiBubble));
//		}
//		
		emotiondataStatisticsDao.insertBatch(eStatistics);
	}

	private EmotiondataStatistics statisticsData(int curriculumid, String chartType, EmojiBubble emojiBubble) {
		EmotiondataStatistics eStatistics = new EmotiondataStatistics();
		eStatistics.setCurriculumid(curriculumid);
		eStatistics.setCharttype(chartType);
		eStatistics.setMinutes(emojiBubble.getMinutes());
		eStatistics.setDatetime(emojiBubble.getDateTime());
		eStatistics.setValue(emojiBubble.getValue());
		eStatistics.setNumber(emojiBubble.getNumber());
		eStatistics.setPercent(emojiBubble.getPercent());
		eStatistics.setLevel(emojiBubble.getLevel());
		eStatistics.setName(emojiBubble.getName());
		eStatistics.setFacecount(emojiBubble.getFaceCount());
		eStatistics.setAveragefacecount(emojiBubble.getAverageFaceCount());
		eStatistics.setAveragevalueall(emojiBubble.getAverageValueAll());
		eStatistics.setFacecountpercent(emojiBubble.getFaceCountPercent());
		eStatistics.setPlayduration(emojiBubble.getPlayDuration());
		return eStatistics;
	}

	/**
	 * 
	 * 方法功能说明: 课程对比分析
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月5日
	 * @param analysisModel
	 * @return
	 *
	 */
	@Override
	public List<EmotionComparativeAnalysisData> getComparativeAnalysis(List<ComparativeAnalysisModel> analysisModels) {
		
		List<EmotionComparativeAnalysisData> eAnalysisDatas = null;
		EmotionComparativeAnalysisData eAnalysisData = null;
		List<EmotiondataStatistics> dataStatisticsList = null;
		EmotiondataStatistics eStatistics = null;
		List<CurriculumTbl> curriculumTbls = null;
		
			
		if(analysisModels != null && analysisModels.size() > 0){
			eAnalysisDatas = new ArrayList<EmotionComparativeAnalysisData>();
			curriculumTbls = new ArrayList<>();
			eStatistics = new EmotiondataStatistics();
			
			for (ComparativeAnalysisModel cModel : analysisModels) {
				
				if ("全部".equals(cModel.getGradeNo())) {
					cModel.setGradeNo(null);
				}
				if ("全部".equals(cModel.getClassNature())) {
					cModel.setClassNature(null);
				}
				if ("全部".equals(cModel.getClassNo())) {
					cModel.setClassNo(null);
				}
				
				curriculumTbls.addAll(curriculumTblDao.getLessonInfos(cModel));
			}
			
			for (CurriculumTbl curriculumTbl : curriculumTbls) {
				eAnalysisData = new EmotionComparativeAnalysisData();
				eAnalysisData.setCurriculumid(curriculumTbl.getId());
				eAnalysisData.setCreateTime(DateUtil.StringToString(
						curriculumTbl.getStarttime().substring(0, 19), DateStyle.MM_DD_HH_MM));
				eAnalysisData.setTeacherName(curriculumTbl.getTeachername());
				eAnalysisData.setGradeNo(curriculumTbl.getGradeno());
				eAnalysisData.setClassNo(curriculumTbl.getClassno());
				eAnalysisData.setCourseName(curriculumTbl.getCoursename());
				eAnalysisData.setClassNature(curriculumTbl.getClassNature());
				eAnalysisData.setCourseContents(curriculumTbl.getCoursecontents());
				eAnalysisData.setSchoolName(curriculumTbl.getSchool());
				
				eStatistics.setCurriculumid(curriculumTbl.getId());
				
				eStatistics.setCharttype("valencePie");
				dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
				eAnalysisData.setValencePieList(handleDataStatistics(dataStatisticsList));
				
				dataStatisticsList.clear();
				eStatistics.setCharttype("faceCountPie");
				dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
				eAnalysisData.setFaceCountPieList(handleDataStatistics(dataStatisticsList));
				
				dataStatisticsList.clear();
				eStatistics.setCharttype("waveRate");
				dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
				eAnalysisData.setValenceVolatilityList(handleDataStatistics(dataStatisticsList));
				
				dataStatisticsList.clear();
				eStatistics.setCharttype("valenceFluctuationDeviation");
				dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
				eAnalysisData.setFluctuationDeviationList(handleDataStatistics(dataStatisticsList));
				
				dataStatisticsList.clear();
				eStatistics.setCharttype("uniformFluctuation");
				dataStatisticsList = emotiondataStatisticsDao.getEmotiondataStatistics(eStatistics);
				eAnalysisData.setUniformFluctuationList(handleDataStatistics(dataStatisticsList));
				
				eAnalysisDatas.add(eAnalysisData);
			}
		}
		return eAnalysisDatas;
		}

	public static double formatDouble2(double d) {
		// 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
		BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
		return bg.doubleValue();
	}

	/**
	 * 
	 * 方法功能说明:
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月20日
	 * @param curriculumTbl
	 * @return
	 *
	 */
	@Override
	public ComprehensiveEvaluationJson classComprehensiveEvaluation(CurriculumTbl curriculumTbl) {

		ComprehensiveEvaluationJson cEvaluationJson = new ComprehensiveEvaluationJson();
		cEvaluationJson.setCurriculumid(curriculumTbl.getId());

		

		// 该类型所有课平均值
//		EmotiondataStatisticsView eStatisticsView = new EmotiondataStatisticsView();
//		
//		eStatisticsView.setCoursename(curriculumTbl.getCoursename());
//		eStatisticsView.setClassnature(curriculumTbl.getClassNature());
//		List<EmotiondataStatisticsView> eStatisticsViews = statisticsViewDao.selectByModel(eStatisticsView);
		
		try {
			EmotiondataSummaryTbl eSummaryTbl = new EmotiondataSummaryTbl();
			eSummaryTbl.setCoursename(curriculumTbl.getCoursename());
			eSummaryTbl.setClassnature(curriculumTbl.getClassNature());
			List<EmotiondataSummaryTbl> eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
			
			if(eSummaryTbls == null || eSummaryTbls.size() == 0){
				eSummaryTbl.setCoursename("default");
				eSummaryTbl.setClassnature("default");
				eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
				if(eSummaryTbls == null || eSummaryTbls.size() == 0)
					return null;
			}
			double average_activity = 0;
//			double average_waveRate = 0;
//			double average_fluctuationDeviation = 0;
			double average_scene = 0;

			for (EmotiondataSummaryTbl emotiondataSummaryTbl : eSummaryTbls) {
				if (emotiondataSummaryTbl.getCharttype().equals("valencePie") && emotiondataSummaryTbl.getLevels() != 1) {
					average_activity += Double.parseDouble(emotiondataSummaryTbl.getPercent());
				}

//				if (emotiondataSummaryTbl.getCharttype().equals("waveRate")) {
//					average_waveRate = Double.parseDouble(emotiondataSummaryTbl.getPercent());
//				}
//
//				if (emotiondataSummaryTbl.getCharttype().equals("valenceFluctuationDeviation")) {
//					average_fluctuationDeviation = Double.parseDouble(emotiondataSummaryTbl.getPercent());
//				}

				if (emotiondataSummaryTbl.getCharttype().equals("faceCountPie") && emotiondataSummaryTbl.getLevels() == 0) {
					average_scene = Double.parseDouble(emotiondataSummaryTbl.getPercent());
				}
			}
			
			StatisticalStandardTbl record = new StatisticalStandardTbl();
			record.setCoursename(curriculumTbl.getCoursename());
			record.setClassnature(curriculumTbl.getClassNature());
			// 统计评价标准
			List<StatisticalStandardTbl> standardTbls = statisticalStandardDao.selectByModel(record);
				if(standardTbls == null || standardTbls.isEmpty()){
					record.setCoursename("default");
					record.setClassnature("default");
					standardTbls = statisticalStandardDao.selectByModel(record);
					if(standardTbls == null || standardTbls.isEmpty())
						return null;
				}
			
			Map<String, StatisticalStandardTbl> standardMap = new HashMap<>();
			for (StatisticalStandardTbl statisticalStandardTbl : standardTbls) {
				standardMap.put(statisticalStandardTbl.getItemtype(), statisticalStandardTbl);
			}

			// 本堂课统计结果
			EmotiondataStatistics eRecord = new EmotiondataStatistics();
			eRecord.setCurriculumid(curriculumTbl.getId());
			eRecord.setCharttype("valencePie");
			List<EmotiondataStatistics> valencePies = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);

			eRecord.setCharttype("faceCountPie");
			List<EmotiondataStatistics> faceCountPies = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);

//			eRecord.setCharttype("waveRate");
//			List<EmotiondataStatistics> waveRates = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);
//
//			eRecord.setCharttype("valenceFluctuationDeviation");
//			List<EmotiondataStatistics> valenceFluctuationDeviations = emotiondataStatisticsDao
//					.getEmotiondataStatistics(eRecord);
//
//			eRecord.setCharttype("uniformFluctuation");
//			List<EmotiondataStatistics> uniformFluctuations = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);
			
			eRecord.setCharttype("participation");
			List<EmotiondataStatistics> participations = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);
			
			eRecord.setCharttype("tActionRate");
			List<EmotiondataStatistics> tActionRates = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);
			
			eRecord.setCharttype("emotionCH");
			List<EmotiondataStatistics> emotionCHs = emotiondataStatisticsDao.getEmotiondataStatistics(eRecord);

			// *******************************************评价计算*******************************************************
			// 活跃度
			double activityVal = 0;
			for (EmotiondataStatistics valencePie : valencePies) {
				if (valencePie.getLevel() == 0 || valencePie.getLevel() == 2)
					activityVal += Double.parseDouble(valencePie.getPercent());
			}
			double activityResult = computEvaluate("activityRate", activityVal, average_activity, 0.0);
			cEvaluationJson.setActivityLevel(bracketHandle(activityResult, standardMap, "activityRate"));

//			// 波动率
//			double waveRateResult = computEvaluate("waveRate", Double.parseDouble(waveRates.get(0).getPercent()),
//					average_waveRate, 0.0);
//			cEvaluationJson.setWaveRateLevel(bracketHandle(waveRateResult, standardMap, "waveRate"));
//
//			// 波动偏差
//			double fluctuationDeviationResult = computEvaluate("valenceFluctuationDeviation",
//					Double.parseDouble(valenceFluctuationDeviations.get(0).getPercent()), average_fluctuationDeviation,
//					0.0);
//			cEvaluationJson.setFluctuationDeviationLevel(
//					bracketHandle(fluctuationDeviationResult, standardMap, "valenceFluctuationDeviation"));
//
//			// 波动均匀
//			double uniformFluctuationResult = computEvaluate("uniformFluctuation",
//					Double.parseDouble(uniformFluctuations.get(0).getPercent()), 0.0, 0.0);
//			cEvaluationJson.setUniformFluctuationLevel(
//					bracketHandle(uniformFluctuationResult / 100, standardMap, "uniformFluctuation"));
//
//			// 互动频率
//			double interactionFrequencyResult = computEvaluate("interactionFrequency", waveRateResult,
//					fluctuationDeviationResult, uniformFluctuationResult  / 100);
//			cEvaluationJson.setInteractionFrequencyLevel(
//					bracketHandle(interactionFrequencyResult, standardMap, "interactionFrequency"));

			// 情景
			double sceneResult = computEvaluate("senceRatio", Double.parseDouble(faceCountPies.get(0).getPercent()),
					average_scene, 0.0);
			cEvaluationJson.setSenceRatioLevel(bracketHandle(sceneResult, standardMap, "senceRatio"));
			
			// 课堂参与度
			cEvaluationJson.setEmotionCHLevel(bracketHandle(Double.parseDouble(emotionCHs.get(0).getPercent()), standardMap, "emotionCH"));
			
			//教授类型
			cEvaluationJson.setTeachTypeLevel(teachTypeHandle(Double.parseDouble(tActionRates.get(0).getPercent()), Double.parseDouble(participations.get(0).getPercent()),
					standardMap, "teachType"));
			
		} catch (Exception e) {
			return null;
		}
		return cEvaluationJson;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 换算得分
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月23日
	 * @param standardTbl
	 * @param averageVal
	 * @param currentVal
	 * @return
	 *
	 */
	private double computEvaluate(String type, double value1, double value2, double value3) {
		double ProportionVal = 0;
		switch (type) {
		case "activityRate":
		case "waveRate":
		case "valenceFluctuationDeviation":
			ProportionVal = value1 / (value1 + value2);
			break;
		case "interactionFrequency":
			ProportionVal = (value1 * 2.5 + value2 * 2.5 + value3) / 6;
			break;
		case "senceRatio":
			ProportionVal = value1 < value2 ? value1 / value2 : value2 / value1;
			break;
		case "uniformFluctuation":
			ProportionVal = value1;
			break;
		default:
			ProportionVal = 0;
			break;
		}
		return ProportionVal;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 分档
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月23日
	 * @param ProportionVal
	 * @param standardTbl
	 * @return
	 *
	 */
	private int bracketHandle(double ProportionVal, Map<String, StatisticalStandardTbl> standardMap, String type) {
		StatisticalStandardTbl standardTbl = standardMap.get(type);
//		if (standardTbl == null)
//			standardTbl = standardMap.get("defaultStandard");

		if (ProportionVal < standardTbl.getLevelone()) {
			return 1;
		} else if (ProportionVal >= standardTbl.getLevelone() && ProportionVal < standardTbl.getLeveltwo()) {
			return 2;
		} else if (ProportionVal >= standardTbl.getLeveltwo() && ProportionVal < standardTbl.getLevelthree()) {
			return 3;
		} else if (ProportionVal >= standardTbl.getLevelthree() && ProportionVal < standardTbl.getLevelfour()) {
			return 4;
		} else if (ProportionVal >= standardTbl.getLevelfour()) {
			return 5;
		} else {
			return 0;
		}
	}
	
	/**
	 * 共4档：
	 * 1. 讲解行为占有率(RT)<=0.3	练习型
	 * 2. RT>=0.7				讲授型
	 * 3. 学生参与度（CH）〉= 0.4		对话型
	 * 4. 0.3<RT<0.7,CH<0.4		混合型(探究式教学)
	 * 
	 * @param participation
	 * @param tActionRate
	 * @param standardMap
	 * @param type
	 * @return
	 */
	private int teachTypeHandle(double rt, double ch, Map<String, StatisticalStandardTbl> standardMap, String type) {
		StatisticalStandardTbl standardTbl = standardMap.get(type);
		if (standardTbl == null)
			standardTbl = standardMap.get("defaultStandard");
		
		if(ch >= standardTbl.getLevelthree())
			return 4;
		if(rt >= standardTbl.getLeveltwo())
			return 2;
		if(rt <= standardTbl.getLevelone())
			return 1;
		return 3;
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月24日
	 * @param pdfModel
	 * @return
	 *
	 */
	@Override
	public String createFilePDF(PDFModel pdfModel) {
		String pdfName = "";
		
		CurriculumTbl curriculumTbl = curriculumTblDao.selectByPrimaryKey(pdfModel.getId());
		
		//补充同种类型课堂的平均值
		EmotiondataSummaryTbl eSummaryTbl = new EmotiondataSummaryTbl();
		eSummaryTbl.setCoursename(curriculumTbl.getCoursename());
		eSummaryTbl.setClassnature(curriculumTbl.getClassNature());
		
		List<EmotiondataSummaryTbl> eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
		
		if(eSummaryTbls == null || eSummaryTbls.size() == 0){
			eSummaryTbl.setCoursename("default");
			eSummaryTbl.setClassnature("default");
			eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
		}
		
		//**********************************************************************************************************************
		EmotiondataStatistics eRecord = new EmotiondataStatistics();
		eRecord.setCurriculumid(curriculumTbl.getId());
		
		String imageName = "";
		
        // Base64解码转成File
        try {
        	for (ReportData reportData : pdfModel.getDatas()) {
        		String a = URLDecoder.decode(reportData.getImagePng(),"utf-8");
            	String[] url = a.split(",");  
                String u = url[1];  
    			byte[] byt = new BASE64Decoder().decodeBuffer(u);
    			imageName = "Temp_" + reportData.getName() + "_" + ".png";
    			File file = new File(Constants.DOWNLOAD_TEMP + imageName);
    			OutputStream output = new FileOutputStream(file);
    			BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
    			bufferedOutput.write(byt);
    			bufferedOutput.flush();
    			bufferedOutput.close();
    			reportData.setImageFile(file);
			}
        	
        	//**************************************************************************************************************************
        	
        	/*
        	 *****************************************************整堂课评价信息********************************************************
        	 */
        	pdfModel.setcEvaluation(classComprehensiveEvaluation(curriculumTbl));
        	
        	/*
        	 *****************************************************整堂课评价信息********************************************************
        	 */
        	
        	pdfName = Constants.SYSTEM_NAME + "_" + DateUtil.DateToString(curriculumTbl.getCreatedate(), "yyyyMMddHHmm") + curriculumTbl.getTeachername() + ".pdf";
			PDFUtil.pdf(curriculumTbl,pdfModel,eSummaryTbls, Constants.DOWNLOAD_TEMP + pdfName);
			
		} catch (IOException e) {
			logger.error("生成pdf文件异常：" + e.getMessage());
			e.printStackTrace();
		} 
		return pdfName;
	}
	
	/**
	 * 
	    * @Title: emojiSummary  
	    * @Description: 增加课堂分析数据的参考值部分内容
	    * @param @param curriculumTbl
	    * @param @param emojiReportData
	    * @param @return    参数  
	    * @return EmojiReportData    返回类型  
	    * @throws
	 */
	private EmojiReportData emojiSummary(CurriculumTbl curriculumTbl, EmojiReportData emojiReportData) {
		//补充同种类型课堂的平均值
		EmotiondataSummaryTbl eSummaryTbl = new EmotiondataSummaryTbl();
		eSummaryTbl.setCoursename(curriculumTbl.getCoursename());
		eSummaryTbl.setClassnature(curriculumTbl.getClassNature());
		List<EmotiondataSummaryTbl> eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
		if(eSummaryTbls == null || eSummaryTbls.isEmpty()){
			eSummaryTbl.setCoursename("default");
			eSummaryTbl.setClassnature("default");
			eSummaryTbls = emotiondataSummaryDao.selectByModel(eSummaryTbl);
		}
		
		for (EmotiondataSummaryTbl emotiondataSummaryTbl : eSummaryTbls) {
			//活跃度
			for (EmojiBubble eBubble : emojiReportData.getValencePieList()) {
				if(emotiondataSummaryTbl.getCharttype().equals("valencePie") && emotiondataSummaryTbl.getLevels() == eBubble.getLevel()){
					eBubble.setAveragePercent(emotiondataSummaryTbl.getPercent());
				}
			}
			
			//情景
			for (EmojiBubble eBubble : emojiReportData.getFaceCountPieList()) {
				if(emotiondataSummaryTbl.getCharttype().equals("faceCountPie") && emotiondataSummaryTbl.getLevels() == eBubble.getLevel()){
					eBubble.setAveragePercent(emotiondataSummaryTbl.getPercent());
				}
			}
			
//			//波动率
//			if(emotiondataSummaryTbl.getCharttype().equals("waveRate") 
//					&& emojiReportData.getValenceVolatilityList() != null
//					&& emojiReportData.getValenceVolatilityList().size() > 0){
//				emojiReportData.getValenceVolatilityList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
//			}
//			
//			//波动偏差
//			if(emotiondataSummaryTbl.getCharttype().equals("valenceFluctuationDeviation") 
//					&& emojiReportData.getValenceFluctuationDeviationList() != null
//					&& emojiReportData.getValenceFluctuationDeviationList().size() > 0){
//				emojiReportData.getValenceFluctuationDeviationList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
//			}
//			
//			//波动均衡
//			if(emotiondataSummaryTbl.getCharttype().equals("uniformFluctuation")
//					&& emojiReportData.getUniformFluctuationList() != null
//					&& emojiReportData.getUniformFluctuationList().size() > 0){
//				emojiReportData.getUniformFluctuationList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
//			}
//			
			//平均值
			if(emotiondataSummaryTbl.getCharttype().equals("valenceAverage")
					&& emojiReportData.getValenceAverageList() != null
					&& emojiReportData.getValenceAverageList().size() > 0){
				emojiReportData.getValenceAverageList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
			}
			
			//学生参与度CH
			if(emotiondataSummaryTbl.getCharttype().equals("participation") 
					&& emojiReportData.getParticipationList() != null
					&& emojiReportData.getParticipationList().size() > 0){
				emojiReportData.getParticipationList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
			}
			
			//T行为占有率 RT
			if(emotiondataSummaryTbl.getCharttype().equals("tActionRate") 
					&& emojiReportData.gettActionRateList() != null
					&& emojiReportData.gettActionRateList().size() > 0){
				emojiReportData.gettActionRateList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
			}
			
			//情感课堂参与度CH
			if(emotiondataSummaryTbl.getCharttype().equals("emotionCH") 
					&& emojiReportData.getParticipationList() != null
					&& emojiReportData.getParticipationList().size() > 0){
				emojiReportData.getEmotionCHList().get(0).setAveragePercent(emotiondataSummaryTbl.getPercent());
			}	
		}
		return emojiReportData;
	}
	
	
}
