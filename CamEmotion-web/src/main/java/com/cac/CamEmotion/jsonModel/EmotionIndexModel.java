/**
 * 
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
 * @data 2018年1月30日
 */
public class EmotionIndexModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double bdCount;
	private double maxVal;
	private double minVal;
	private double averageVal;
	public double getBdCount() {
		return bdCount;
	}
	public void setBdCount(double bdCount) {
		this.bdCount = bdCount;
	}
	public double getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(double maxVal) {
		this.maxVal = maxVal;
	}
	public double getMinVal() {
		return minVal;
	}
	public void setMinVal(double minVal) {
		this.minVal = minVal;
	}
	public double getAverageVal() {
		return averageVal;
	}
	public void setAverageVal(double averageVal) {
		this.averageVal = averageVal;
	}
	
}
