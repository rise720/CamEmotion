package com.cac.CamEmotion.model;

/**
 * 
    * @ClassName: EmojiQueryModel  
    * @Description: 查询情绪统计数据用的查询条件对象
    * @author JinWH  
    * @date 2018年7月13日  
    *
 */
public class EmojiQueryModel {
	
	private int statisticsType;
	private int curriculumid;
	private int studentId;
		
	public EmojiQueryModel(int statisticsType, int curriculumid, int studentId) {
		super();
		this.statisticsType = statisticsType;
		this.curriculumid = curriculumid;
		this.studentId = studentId;
	}
	
	public int getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(int statisticsType) {
		this.statisticsType = statisticsType;
	}
	public int getCurriculumid() {
		return curriculumid;
	}
	public void setCurriculumid(int curriculumid) {
		this.curriculumid = curriculumid;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
}
