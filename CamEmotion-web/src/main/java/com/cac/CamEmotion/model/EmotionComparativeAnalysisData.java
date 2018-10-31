/**
 * 
 */
package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年1月8日
 */
public class EmotionComparativeAnalysisData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer curriculumid;
	
	private String createTime;
	
	private Integer schoolId;
	
	private String schoolName;
	
	private Integer teacherId;
	
	private String teacherName;
	
	private String courseContents;
	
	private String courseName;
	
	private String classNo;
	
	private String classNature;
	
	private String teacherType;
	
	private String averageValueAll;//该堂课valence平均值
	
	private List<EmojiBubble> valencePieList; // 积极性表情饼图数据列表
	
	private List<EmojiBubble> faceCountPieList; // 人脸数饼图数据列表
	
	private List<EmojiBubble> valenceAverageList; // 整堂课平均值
	
	private List<EmojiBubble> valenceVolatilityList; // 整堂课波动率
	
	private List<EmojiBubble> fluctuationDeviationList; // 整堂课波动偏差
	
	private List<EmojiBubble> uniformFluctuationList; // 整堂课波动均衡
	
	
	private String gradeNo;//年级
	
	private String curriculumType;//课程性质，授课、非授课
	
	
	
	public List<EmojiBubble> getValenceVolatilityList() {
		return valenceVolatilityList;
	}

	public void setValenceVolatilityList(List<EmojiBubble> valenceVolatilityList) {
		this.valenceVolatilityList = valenceVolatilityList;
	}

	public List<EmojiBubble> getFluctuationDeviationList() {
		return fluctuationDeviationList;
	}

	public void setFluctuationDeviationList(List<EmojiBubble> fluctuationDeviationList) {
		this.fluctuationDeviationList = fluctuationDeviationList;
	}

	public List<EmojiBubble> getUniformFluctuationList() {
		return uniformFluctuationList;
	}

	public void setUniformFluctuationList(List<EmojiBubble> uniformFluctuationList) {
		this.uniformFluctuationList = uniformFluctuationList;
	}

	public List<EmojiBubble> getValenceAverageList() {
		return valenceAverageList;
	}

	public void setValenceAverageList(List<EmojiBubble> valenceAverageList) {
		this.valenceAverageList = valenceAverageList;
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

	public String getCurriculumType() {
		return curriculumType;
	}

	public void setCurriculumType(String curriculumType) {
		this.curriculumType = curriculumType;
	}

	public String getGradeNo() {
		return gradeNo;
	}

	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}

	public String getAverageValueAll() {
		return averageValueAll;
	}

	public void setAverageValueAll(String averageValueAll) {
		this.averageValueAll = averageValueAll;
	}

	public Integer getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(Integer curriculumid) {
		this.curriculumid = curriculumid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseContents() {
		return courseContents;
	}

	public void setCourseContents(String courseContents) {
		this.courseContents = courseContents;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getClassNature() {
		return classNature;
	}

	public void setClassNature(String classNature) {
		this.classNature = classNature;
	}

	public String getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}

	public List<EmojiBubble> getValencePieList() {
		return valencePieList;
	}

	public void setValencePieList(List<EmojiBubble> valencePieList) {
		this.valencePieList = valencePieList;
	}

	public List<EmojiBubble> getFaceCountPieList() {
		return faceCountPieList;
	}

	public void setFaceCountPieList(List<EmojiBubble> faceCountPieList) {
		this.faceCountPieList = faceCountPieList;
	}
	

}
