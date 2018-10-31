package com.cac.CamEmotion.jsonModel;

public class FaceInfo {

	/**
	 * 脸Id
	 */
	private Integer id;
	private Emotions emotions;
	private OriginalPoint originalPoint;

	public OriginalPoint getOriginalPoint() {
		return originalPoint;
	}

	public void setOriginalPoint(OriginalPoint originalPoint) {
		this.originalPoint = originalPoint;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Emotions getEmotions() {
		return emotions;
	}

	public void setEmotions(Emotions emotions) {
		this.emotions = emotions;
	}

//	private List<Point> pointList;
//
//	public List<Point> getPointList() {
//		return pointList;
//	}
//
//	public void setPointList(List<Point> pointList) {
//		this.pointList = pointList;
//	}

}
