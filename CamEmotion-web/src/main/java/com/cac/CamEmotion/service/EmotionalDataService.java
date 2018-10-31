package com.cac.CamEmotion.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import com.cac.CamEmotion.jsonModel.ComparativeAnalysisModel;
import com.cac.CamEmotion.jsonModel.ComprehensiveEvaluationJson;
import com.cac.CamEmotion.jsonModel.PDFModel;
import com.cac.CamEmotion.jsonModel.PieChartModel;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiBubble;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotionBubble;
import com.cac.CamEmotion.model.EmotionComparativeAnalysisData;
import com.cac.CamEmotion.model.EmotionaldataTbl;

/**
 * 类功能说明:采集数据分析管理
 * 
 * 
 * @author houpp
 *
 */
public interface EmotionalDataService {

//	/**
//	 * 方法说明：根据提供的时段返回一个 和 课程ID 返回对应的数据集
//	 * 			 按要求查询人脸识别数据（主要是 情景统计使用，过滤 valence 以及七种情绪值为0的数据）
//	 * 
//	 * @param EmotionaldataTbl
//	 *            数据分析对象
//	 * 
//	 * @return
//	 */
	
	/**
	 * 
	 * 方法功能说明:	加工从数据库获取的表情列表，转换成某课程持续时间内，每1分钟的表情列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年8月18日
	 * @param list
	 * @param intervaltime
	 * @return
	 */
	public List<EmotionaldataTbl> getDateData(List<EmotionaldataTbl> list, int intervaltime);

	/**
	 * 
	 * 方法功能说明: 加工从数据库获取的表情列表，转换成某课程持续时间内，每1分钟的表情气泡图列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年8月31日
	 * @param list
	 * @param intervaltime
	 * @return
	 */
	public List<EmotionBubble> getBubble(List<EmotionaldataTbl> list, int intervaltime);
	
	/**
	 * 
	 * 方法功能说明: 获取情绪气泡的数据列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月14日
	 * @return
	 */
	public List<EmojiBubble> getEmojiBubble(CurriculumTbl curriculumTbl,List<EmotionaldataTbl> datas,List<Double> 
	valueList, List<Date> timeList, int secondsPerPart, int[] level, Double maxValue, Double minValue);
	
	/**
	 * 
	 * 方法功能说明: 获取情绪饼图的数据列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月14日
	 * @param valueList
	 * @param timeList
	 * @param levelLimit
	 * @return
	 */
	public List<EmojiBubble> getEmojiPie(List<Double> valueList, int[] levelLimit, Double maxValue, Double minValue);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 获取统计结果
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月13日
	 * @param currid
	 * @param statisticsType   0:下课后，课堂分析统计及学生个体分析统计，并存数据库	1：查看课堂分析  2：查看学生个体分析一览   3：查产看学生个体分析
	 * 
	 * @return
	 *
	 */
	public List<EmojiReportData> getEmojiReportData(EmojiQueryModel query);
	/**
	 * 
	 * 
	 * 方法功能说明: 获取对比分析数据
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月8日
	 * @param analysisModel
	 * @return
	 *
	 */
	public List<EmotionComparativeAnalysisData> getComparativeAnalysis(List<ComparativeAnalysisModel> analysisModels);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 课堂综合评价
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
	public ComprehensiveEvaluationJson classComprehensiveEvaluation(CurriculumTbl curriculumTbl);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 生成PDF文件
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
	public String createFilePDF(PDFModel pdfModel);
	
}
