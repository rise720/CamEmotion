package com.cac.CamEmotion.jsonModel;

public class Measurements {
	private double interocularDistance;

	private Orientation orientation;

	public void setInterocularDistance(double interocularDistance){
	this.interocularDistance = interocularDistance;
	}
	public double getInterocularDistance(){
	return this.interocularDistance;
	}
	public void setOrientation(Orientation orientation){
	this.orientation = orientation;
	}
	public Orientation getOrientation(){
	return this.orientation;
	}
}
