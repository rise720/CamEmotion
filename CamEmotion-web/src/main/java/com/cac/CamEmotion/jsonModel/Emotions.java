package com.cac.CamEmotion.jsonModel;

public class Emotions {
	private double anger;

	private double contempt;

	private double disgust;

	private double engagement;

	private double fear;

	private double joy;

	private double sadness;

	private double surprise;

	private double valence;
	
	private double valenceView;
	
	public double getValenceView() {
		return valenceView;
	}
	public void setValenceView(double valenceView) {
		this.valenceView = valenceView;
	}
	public void setAnger(double anger){
	this.anger = anger;
	}
	public double getAnger(){
	return this.anger;
	}
	public void setContempt(double contempt){
	this.contempt = contempt;
	}
	public double getContempt(){
	return this.contempt;
	}
	public void setDisgust(double disgust){
	this.disgust = disgust;
	}
	public double getDisgust(){
	return this.disgust;
	}
	public void setEngagement(double engagement){
	this.engagement = engagement;
	}
	public double getEngagement(){
	return this.engagement;
	}
	public void setFear(double fear){
	this.fear = fear;
	}
	public double getFear(){
	return this.fear;
	}
	public void setJoy(double joy){
	this.joy = joy;
	}
	public double getJoy(){
	return this.joy;
	}
	public void setSadness(double sadness){
	this.sadness = sadness;
	}
	public double getSadness(){
	return this.sadness;
	}
	public void setSurprise(double surprise){
	this.surprise = surprise;
	}
	public double getSurprise(){
	return this.surprise;
	}
	public void setValence(double valence){
	this.valence = valence;
	}
	public double getValence(){
	return this.valence;
	}
}
