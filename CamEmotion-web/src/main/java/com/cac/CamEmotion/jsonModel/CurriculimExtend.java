/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;
import java.util.List;

import com.cac.CamEmotion.paging.PageExtend;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author Chenyang
 * @data 2017年9月15日
 */
public class CurriculimExtend extends PageExtend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String name;

    private String createuesr;
    
    private int teacherId;
    
    private String teachername;
    
    private int schoolid;
    
    private String school;
    
    private String classNature;
    
    private String gradeNo;
    
    private String classNo;
    
    private String coursecontents;
    
    private String coursename;
    
    private String begintime;
    
    private String endtime;
    
    private String searchcontent;

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
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

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSearchcontent() {
		return searchcontent;
	}

	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateuesr() {
		return createuesr;
	}

	public void setCreateuesr(String createuesr) {
		this.createuesr = createuesr;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getGradeNo() {
		return gradeNo;
	}

	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	

	

    /**
     * 不查询已经出现的课程id 集合
     */
    private List<Integer> idList;

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
   
}
