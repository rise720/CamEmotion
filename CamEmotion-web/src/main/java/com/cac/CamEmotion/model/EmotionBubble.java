package com.cac.CamEmotion.model;

/**
 * 
 * 类功能说明: 用于气泡球图表的数据模型
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2017年8月31日
 */
public class EmotionBubble {
	private EmotionaldataTbl emotionaldataTbl;		//各情感系数的平均值
	private Count count;							//各情感采样数
	private Time time;								//各情感发生最高值得所在时间
	
	public EmotionBubble(){
		
	}
	public EmotionBubble(EmotionaldataTbl emotionaldataTbl, Count count, Time time){
		this.emotionaldataTbl = emotionaldataTbl;
		this.count = count;
		this.time = time;
	}
	
	public EmotionaldataTbl getEmotionaldataTbl() {
		return emotionaldataTbl;
	}
	public void setEmotionaldataTbl(EmotionaldataTbl emotionaldataTbl) {
		this.emotionaldataTbl = emotionaldataTbl;
	}
	public Count getCount() {
		return count;
	}
	public void setCount(Count count) {
		this.count = count;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public class Count{
		private int valenceCount;
		private int joyCount;
		private int contemptCount;
		public int getValenceCount() {
			return valenceCount;
		}
		public void setValenceCount(int valenceCount) {
			this.valenceCount = valenceCount;
		}
		public int getJoyCount() {
			return joyCount;
		}
		public void setJoyCount(int joyCount) {
			this.joyCount = joyCount;
		}
		public int getContemptCount() {
			return contemptCount;
		}
		public void setContemptCount(int contemptCount) {
			this.contemptCount = contemptCount;
		}	
	}
	public class Time{
		private float valenceTime;
		private float joyTime;
		private float contemptTime;
		public float getValenceTime() {
			return valenceTime;
		}
		public void setValenceTime(float valenceTime) {
			this.valenceTime = valenceTime;
		}
		public float getJoyTime() {
			return joyTime;
		}
		public void setJoyTime(float joyTime) {
			this.joyTime = joyTime;
		}
		public float getContemptTime() {
			return contemptTime;
		}
		public void setContemptTime(float contemptTime) {
			this.contemptTime = contemptTime;
		}
	}
}
