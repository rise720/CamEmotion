package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类功能说明: 报表用数据收集
 * <P>
 * 
 * </P>
 *
 * @author jinwh
 * @data 2017年9月15日
 */
public class EmojiReportData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentInfo studentInfo;			//学生信息，ID为0的情况下，说明此类是整个课堂信息
	private List<EmojiBubble> valenceBubbleList; // 积极性表情气泡图数据列表
	private List<EmojiBubble> valencePieList; // 积极性表情饼图数据列表
	private List<EmojiBubble> attentionPieList; // 专注度表情饼图数据列表
	private List<EmojiBubble> tiredBubbleList; // 打瞌睡表情气泡图数据列表
	private List<EmojiBubble> tiredPieList; // 打瞌睡表情饼图数据列表

	private List<EmojiBubble> sourceBubbleList; // 使用原始valance表情气泡数据列表（人脸数折线图数据列表）
	private List<EmojiBubble> faceCountPieList; // 人脸数饼图数据列表

	private List<EmojiBubble> joyBubbleList; // joy折线图数据列表
	private List<EmojiBubble> sadnessBubbleList; // sadness折线图数据列表
	private List<EmojiBubble> disgustBubbleList; // disgust折线图数据列表
	private List<EmojiBubble> contemptBubbleList; // contempt折线图数据列表
	private List<EmojiBubble> angerBubbleList; // anger折线图数据列表
	private List<EmojiBubble> surpriseBubbleList; // surprise折线图数据列表
	private List<EmojiBubble> fearBubbleList; // fear折线图数据列表
	private List<EmojiBubble> browFurrowBubbleList; // browFurrow折线图数据列表
	
	private List<EmojiBubble> emotionBubbleList; //7种情绪气泡图数据列表

	
	public EmojiReportData(StudentInfo studentInfo) {
		super();
		this.studentInfo = studentInfo;
	}
	/**
	 * 2018-01-05 15:45 chenyang
	 */
	private List<EmojiBubble> comparativeAnalysisList;// 对比分析数据列表
	
//	/**
//	 * @author chenyang
//	 * 2018-02-27 10:12
//	 */
//	private List<EmojiBubble> valenceVolatilityList; //波动率
	/**
	 * @author chenyang
	 * 2018-04-04 17:16
	 */
	private List<EmojiBubble> valenceAverageList; //平均值
//	/**
//	 * @author chenyang
//	 * 2018-04-04 17:16
//	 */
//	private List<EmojiBubble> valenceFluctuationDeviationList; //波动偏差
//	/**
//	 * @author chenyang
//	 * 2018-04-04 17:16
//	 */
//	private List<EmojiBubble> valenceMeanAmplitudeList; //平均振幅
//	/**
//	 * @author chenyang
//	 * 2018-04-19 17:16
//	 */
//	private List<EmojiBubble> uniformFluctuationList; //波动均匀
	
	private List<EmojiBubble> participationList; //学生参与度
	private List<EmojiBubble> tActionRateList; //T行为占有率 RT
	private List<EmojiBubble> emotionCHList; //情感课堂参与度
	
//	public List<EmojiBubble> getUniformFluctuationList() {
//		return uniformFluctuationList;
//	}
//
//	public void setUniformFluctuationList(List<EmojiBubble> uniformFluctuationList) {
//		this.uniformFluctuationList = uniformFluctuationList;
//	}

	public List<EmojiBubble> getValenceAverageList() {
		return valenceAverageList;
	}

	public void setValenceAverageList(List<EmojiBubble> valenceAverageList) {
		this.valenceAverageList = valenceAverageList;
	}
//
//	public List<EmojiBubble> getValenceFluctuationDeviationList() {
//		return valenceFluctuationDeviationList;
//	}
//
//	public void setValenceFluctuationDeviationList(List<EmojiBubble> valenceFluctuationDeviationList) {
//		this.valenceFluctuationDeviationList = valenceFluctuationDeviationList;
//	}
//
//	public List<EmojiBubble> getValenceMeanAmplitudeList() {
//		return valenceMeanAmplitudeList;
//	}
//
//	public void setValenceMeanAmplitudeList(List<EmojiBubble> valenceMeanAmplitudeList) {
//		this.valenceMeanAmplitudeList = valenceMeanAmplitudeList;
//	}
//
//	public List<EmojiBubble> getValenceVolatilityList() {
//		return valenceVolatilityList;
//	}
//
//	public void setValenceVolatilityList(List<EmojiBubble> valenceVolatilityList) {
//		this.valenceVolatilityList = valenceVolatilityList;
//	}

	public List<EmojiBubble> getComparativeAnalysisList() {
		return comparativeAnalysisList;
	}

	public void setComparativeAnalysisList(List<EmojiBubble> comparativeAnalysisList) {
		this.comparativeAnalysisList = comparativeAnalysisList;
	}

	public List<EmojiBubble> getJoyBubbleList() {
		return joyBubbleList;
	}

	public void setJoyBubbleList(List<EmojiBubble> joyBubbleList) {
		this.joyBubbleList = joyBubbleList;
	}

	public List<EmojiBubble> getSadnessBubbleList() {
		return sadnessBubbleList;
	}

	public void setSadnessBubbleList(List<EmojiBubble> sadnessBubbleList) {
		this.sadnessBubbleList = sadnessBubbleList;
	}

	public List<EmojiBubble> getDisgustBubbleList() {
		return disgustBubbleList;
	}

	public void setDisgustBubbleList(List<EmojiBubble> disgustBubbleList) {
		this.disgustBubbleList = disgustBubbleList;
	}

	public List<EmojiBubble> getContemptBubbleList() {
		return contemptBubbleList;
	}

	public void setContemptBubbleList(List<EmojiBubble> contemptBubbleList) {
		this.contemptBubbleList = contemptBubbleList;
	}

	public List<EmojiBubble> getAngerBubbleList() {
		return angerBubbleList;
	}

	public void setAngerBubbleList(List<EmojiBubble> angerBubbleList) {
		this.angerBubbleList = angerBubbleList;
	}

	public List<EmojiBubble> getSurpriseBubbleList() {
		return surpriseBubbleList;
	}

	public void setSurpriseBubbleList(List<EmojiBubble> surpriseBubbleList) {
		this.surpriseBubbleList = surpriseBubbleList;
	}

	public List<EmojiBubble> getFearBubbleList() {
		return fearBubbleList;
	}

	public void setFearBubbleList(List<EmojiBubble> fearBubbleList) {
		this.fearBubbleList = fearBubbleList;
	}

	public List<EmojiBubble> getBrowFurrowBubbleList() {
		return browFurrowBubbleList;
	}

	public void setBrowFurrowBubbleList(List<EmojiBubble> browFurrowBubbleList) {
		this.browFurrowBubbleList = browFurrowBubbleList;
	}

	public List<EmojiBubble> getFaceCountPieList() {
		return faceCountPieList;
	}

	public void setFaceCountPieList(List<EmojiBubble> faceCountPieList) {
		this.faceCountPieList = faceCountPieList;
	}

	public List<EmojiBubble> getSourceBubbleList() {
		return sourceBubbleList;
	}

	public void setSourceBubbleList(List<EmojiBubble> sourceBubbleList) {
		this.sourceBubbleList = sourceBubbleList;
	}

	public List<EmojiBubble> getValenceBubbleList() {
		return valenceBubbleList;
	}

	public void setValenceBubbleList(List<EmojiBubble> valenceBubbleList) {
		this.valenceBubbleList = valenceBubbleList;
	}

	public List<EmojiBubble> getValencePieList() {
		return valencePieList;
	}

	public void setValencePieList(List<EmojiBubble> valencePieList) {
		this.valencePieList = valencePieList;
	}

	public List<EmojiBubble> getAttentionPieList() {
		return attentionPieList;
	}

	public void setAttentionPieList(List<EmojiBubble> attentionPieList) {
		this.attentionPieList = attentionPieList;
	}

	public List<EmojiBubble> getTiredBubbleList() {
		return tiredBubbleList;
	}

	public void setTiredBubbleList(List<EmojiBubble> tiredBubbleList) {
		this.tiredBubbleList = tiredBubbleList;
	}

	public List<EmojiBubble> getTiredPieList() {
		return tiredPieList;
	}

	public void setTiredPieList(List<EmojiBubble> tiredPieList) {
		this.tiredPieList = tiredPieList;
	}

	public List<EmojiBubble> getParticipationList() {
		return participationList;
	}

	public void setParticipationList(List<EmojiBubble> participationList) {
		this.participationList = participationList;
	}

	public List<EmojiBubble> gettActionRateList() {
		return tActionRateList;
	}

	public void settActionRateList(List<EmojiBubble> tActionRateList) {
		this.tActionRateList = tActionRateList;
	}

	public List<EmojiBubble> getEmotionCHList() {
		return emotionCHList;
	}

	public void setEmotionCHList(List<EmojiBubble> emotionCHList) {
		this.emotionCHList = emotionCHList;
	}

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public List<EmojiBubble> getEmotionBubbleList() {
		return emotionBubbleList;
	}

	public void setEmotionBubbleList(List<EmojiBubble> emotionBubbleList) {
		this.emotionBubbleList = emotionBubbleList;
	}
}
