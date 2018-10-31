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
 * @data 2018年1月5日
 */
public class ComparativeAnalysisModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer curriculumid;
	private String staticType;
	private Integer schoolId;
	private String schoolName;
	private Integer teacherId;
	private String teacherName;
	private String coursename;
	private String classNature;//课堂性质
	private String coursecontents;
	private String begintime;
	private String endtime;
	private String gradeNo;
	private String classNo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCurriculumid() {
		return curriculumid;
	}
	public void setCurriculumid(Integer curriculumid) {
		this.curriculumid = curriculumid;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getStaticType() {
		return staticType;
	}
	public void setStaticType(String staticType) {
		this.staticType = staticType;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getClassNature() {
		return classNature;
	}
	public void setClassNature(String classNature) {
		this.classNature = classNature;
	}
	public String getCoursecontents() {
		return coursecontents;
	}
	public void setCoursecontents(String coursecontents) {
		this.coursecontents = coursecontents;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
