package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * 类功能说明: 用于气泡球图表的数据模型，代表某分钟的感情值
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2017年9月14日
 */
public class EmojiBubble implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minutes;	//所在分钟
	private String dateTime;	//所在时刻
	private double value;	//该分钟内情绪指数的平均值
	private int number;		//该分钟内情绪指数的数量
	private String percent;	//该分钟内情绪指数的百分比
	private int level;		//情绪等级
	private String name;	//情绪名称
	
	private int faceCount; //该分钟内课堂情景
	
	private double averageFaceCount;//该分钟内人脸数平均值
	
	private double averageValueAll; //该分钟内总的平均值
	
	private int averageValueAllLevel; //该分钟内总平均值的情绪等级
	
	private double faceCountPercent;//每帧中face数大于2（比如）的帧数所占该分钟内所有帧的百分比
	
	private double speakCountPercent;//每帧中说话讨论的帧数所占该单位时间内所有帧的百分比
	
	private int attentionLevel;//注意力等级，目前就两级 0： 分散 1：集中
	
	private int playDuration;//视频跳转位置
	
	private String averagePercent;//同种课堂类型 平均值
	
	private double joy;//喜悦
	
	private double sadness;//悲哀
	
	private double disgust;//厌恶
	
	private double contempt;//蔑视
	
	private double anger;//愤怒
	
	private double surprise;//惊喜
	
	private double fear;//害怕 
	
	public String getAveragePercent() {
		return averagePercent;
	}
	public void setAveragePercent(String averagePercent) {
		this.averagePercent = averagePercent;
	}
	public int getPlayDuration() {
		return playDuration;
	}
	public void setPlayDuration(int playDuration) {
		this.playDuration = playDuration;
	}
	public double getFaceCountPercent() {
		return faceCountPercent;
	}
	public void setFaceCountPercent(double faceCountPercent) {
		this.faceCountPercent = faceCountPercent;
	}
	public double getAverageFaceCount() {
		return averageFaceCount;
	}
	public void setAverageFaceCount(double averageFaceCount) {
		this.averageFaceCount = averageFaceCount;
	}
	public int getFaceCount() {
		return faceCount;
	}
	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}
	public double getAverageValueAll() {
		return averageValueAll;
	}
	public void setAverageValueAll(double averageValueAll) {
		this.averageValueAll = averageValueAll;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public double getSpeakCountPercent() {
		return speakCountPercent;
	}
	public void setSpeakCountPercent(double speakCountPercent) {
		this.speakCountPercent = speakCountPercent;
	}
	public int getAverageValueAllLevel() {
		return averageValueAllLevel;
	}
	public void setAverageValueAllLevel(int averageValueAllLevel) {
		this.averageValueAllLevel = averageValueAllLevel;
	}
	public int getAttentionLevel() {
		return attentionLevel;
	}
	public void setAttentionLevel(int attentionLevel) {
		this.attentionLevel = attentionLevel;
	}
	public double getJoy() {
		return joy;
	}
	public void setJoy(double joy) {
		this.joy = joy;
	}
	public double getSadness() {
		return sadness;
	}
	public void setSadness(double sadness) {
		this.sadness = sadness;
	}
	public double getDisgust() {
		return disgust;
	}
	public void setDisgust(double disgust) {
		this.disgust = disgust;
	}
	public double getContempt() {
		return contempt;
	}
	public void setContempt(double contempt) {
		this.contempt = contempt;
	}
	public double getAnger() {
		return anger;
	}
	public void setAnger(double anger) {
		this.anger = anger;
	}
	public double getSurprise() {
		return surprise;
	}
	public void setSurprise(double surprise) {
		this.surprise = surprise;
	}
	public double getFear() {
		return fear;
	}
	public void setFear(double fear) {
		this.fear = fear;
	}
}
