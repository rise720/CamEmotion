/**  
* <p>Title: ComprehensiveEvaluationJson.java</p>  
* <p>Description: </p>  
* <p>Copyright (c) 2018 CAC ShangHai Corporation</p>  
* <p>Company: www.cacshanghai.com</p>  
* @author chenyang  
* @date 2018年4月20日  
* @version 1.1  
*/  
package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年4月20日
 */
public class ComprehensiveEvaluationJson implements Serializable{

	/**
	*date 2018年4月20日
	*/
	private static final long serialVersionUID = 1L;
	
	private int curriculumid;
	
	private int activityLevel;//活跃度等级
	
//	private int waveRateLevel;//波动率等级
//	
//	private int FluctuationDeviationLevel;//波动偏差等级
//	
//	private int uniformFluctuationLevel;//波动均匀等级
//	
//	private int interactionFrequencyLevel;//互动平率等级
	
	private int senceRatioLevel;//情景等级
	
	private int teachTypeLevel;//授课类型
	
	private int emotionCHLevel; //课堂参与度
	
	private String evaluationDescription;// 类型描述
	

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	}

//	public int getWaveRateLevel() {
//		return waveRateLevel;
//	}
//
//	public void setWaveRateLevel(int waveRateLevel) {
//		this.waveRateLevel = waveRateLevel;
//	}
//
//	public int getFluctuationDeviationLevel() {
//		return FluctuationDeviationLevel;
//	}
//
//	public void setFluctuationDeviationLevel(int fluctuationDeviationLevel) {
//		FluctuationDeviationLevel = fluctuationDeviationLevel;
//	}
//
//	public int getUniformFluctuationLevel() {
//		return uniformFluctuationLevel;
//	}
//
//	public void setUniformFluctuationLevel(int uniformFluctuationLevel) {
//		this.uniformFluctuationLevel = uniformFluctuationLevel;
//	}
//
//	public int getInteractionFrequencyLevel() {
//		return interactionFrequencyLevel;
//	}
//
//	public void setInteractionFrequencyLevel(int interactionFrequencyLevel) {
//		this.interactionFrequencyLevel = interactionFrequencyLevel;
//	}

	public int getSenceRatioLevel() {
		return senceRatioLevel;
	}

	public void setSenceRatioLevel(int senceRatioLevel) {
		this.senceRatioLevel = senceRatioLevel;
	}

	public int getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(int curriculumid) {
		this.curriculumid = curriculumid;
	}

	public String getEvaluationDescription() {
		return evaluationDescription;
	}

	public void setEvaluationDescription(String evaluationDescription) {
		this.evaluationDescription = evaluationDescription;
	}

	public int getTeachTypeLevel() {
		return teachTypeLevel;
	}

	public void setTeachTypeLevel(int teachTypeLevel) {
		this.teachTypeLevel = teachTypeLevel;
	}

	public int getEmotionCHLevel() {
		return emotionCHLevel;
	}

	public void setEmotionCHLevel(int emotionCHLevel) {
		this.emotionCHLevel = emotionCHLevel;
	}	
}
