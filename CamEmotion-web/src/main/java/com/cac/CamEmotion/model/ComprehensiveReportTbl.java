package com.cac.CamEmotion.model;

public class ComprehensiveReportTbl extends CurriculumTbl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 教育级别
	 */
	private int educationLevel;
	/**
	 * 听课率
	 */
	private String attendanceRate;
	/**
	 * 练习率
	 */
	private String practiceRate;
	
	/**
	 * 讨论率
	 */
	private String discussionRate;
	/**
	 * 困惑率
	 */
	private String puzzledRate;
	/**
	 * 平静率
	 */
	private String calmRate;
	/**
	 * 兴奋率
	 */
	private String excitementRate;
	/**
	 * 波动率
	 */
	private String waveRate;
	/**
	 * 条件
	 */
	private String searchcontent;
	/**
	 * @author chenyang
	 * @date 2018-04-08 10:42
	 */
	/**
	 * 平均值
	 */
	private String valenceAverage;
	/**
	 * 波动偏差
	 */
	private String valenceFluctuationDeviation;
	/**
	 * 平均振幅
	 */
	private String valenceMeanAmplitude;
	
	/**
	 * 平均振幅
	 */
	private String uniformFluctuation;
	
	/**
	 * 课堂参与度
	 */
	private String emotionCH;

	/**
	 * 课堂级别 目前即为 是否为优质课标示
	 */
	private int curriculumLevel;
	
	public String getUniformFluctuation() {
		return uniformFluctuation;
	}
	public void setUniformFluctuation(String uniformFluctuation) {
		this.uniformFluctuation = uniformFluctuation;
	}
	public int getCurriculumLevel() {
		return curriculumLevel;
	}
	public void setCurriculumLevel(int curriculumLevel) {
		this.curriculumLevel = curriculumLevel;
	}
	public String getValenceAverage() {
		return valenceAverage;
	}
	public void setValenceAverage(String valenceAverage) {
		this.valenceAverage = valenceAverage;
	}
	public String getValenceFluctuationDeviation() {
		return valenceFluctuationDeviation;
	}
	public void setValenceFluctuationDeviation(String valenceFluctuationDeviation) {
		this.valenceFluctuationDeviation = valenceFluctuationDeviation;
	}
	public String getValenceMeanAmplitude() {
		return valenceMeanAmplitude;
	}
	public void setValenceMeanAmplitude(String valenceMeanAmplitude) {
		this.valenceMeanAmplitude = valenceMeanAmplitude;
	}
	public int getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(int educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getSearchcontent() {
		return searchcontent;
	}
	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}
	public String getAttendanceRate() {
		return attendanceRate;
	}
	public void setAttendanceRate(String attendanceRate) {
		this.attendanceRate = attendanceRate;
	}
	public String getPracticeRate() {
		return practiceRate;
	}
	public void setPracticeRate(String practiceRate) {
		this.practiceRate = practiceRate;
	}
	public String getPuzzledRate() {
		return puzzledRate;
	}
	public void setPuzzledRate(String puzzledRate) {
		this.puzzledRate = puzzledRate;
	}
	public String getCalmRate() {
		return calmRate;
	}
	public void setCalmRate(String calmRate) {
		this.calmRate = calmRate;
	}
	public String getExcitementRate() {
		return excitementRate;
	}
	public void setExcitementRate(String excitementRate) {
		this.excitementRate = excitementRate;
	}
	public String getWaveRate() {
		return waveRate;
	}
	public void setWaveRate(String waveRate) {
		this.waveRate = waveRate;
	}
	public String getDiscussionRate() {
		return discussionRate;
	}
	public void setDiscussionRate(String discussionRate) {
		this.discussionRate = discussionRate;
	}
	public String getEmotionCH() {
		return emotionCH;
	}
	public void setEmotionCH(String emotionCH) {
		this.emotionCH = emotionCH;
	}
	
}
