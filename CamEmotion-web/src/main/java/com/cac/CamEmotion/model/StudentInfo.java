package com.cac.CamEmotion.model;

/**
 * 
    * @ClassName: StudentInfo  
    * @Description: 对应某堂课的学生个体信息 
    * @author JinWH  
    * @date 2018年7月6日  
    *
 */
public class StudentInfo {
	private int studentId;			//学生ID
	private String profileImage;	//学生头像图片信息
	private int curriculumid;		//所在课程ID
	
	public StudentInfo(int studentId) {
		super();
		this.studentId = studentId;
	}
	public StudentInfo(int studentId, String profileImage, int curriculumid) {
		super();
		this.studentId = studentId;
		this.profileImage = profileImage;
		this.curriculumid = curriculumid;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public int getCurriculumid() {
		return curriculumid;
	}
	public void setCurriculumid(int curriculumid) {
		this.curriculumid = curriculumid;
	}
}
