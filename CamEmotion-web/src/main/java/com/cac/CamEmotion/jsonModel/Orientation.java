package com.cac.CamEmotion.jsonModel;

public class Orientation {
	private double pitch;

	private double roll;

	private double yaw;

	public void setPitch(double pitch){
	this.pitch = pitch;
	}
	public double getPitch(){
	return this.pitch;
	}
	public void setRoll(double roll){
	this.roll = roll;
	}
	public double getRoll(){
	return this.roll;
	}
	public void setYaw(double yaw){
	this.yaw = yaw;
	}
	public double getYaw(){
	return this.yaw;
	}
}
