/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;

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
public class ComprehensiveReportExtend extends PageExtend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String context;
	private Integer curriculumid;
	
	private Integer curriculumLevel;
	
	private String coursename;
	
	private String classNature;
	
	public Integer getCurriculumLevel() {
		return curriculumLevel;
	}

	public void setCurriculumLevel(Integer curriculumLevel) {
		this.curriculumLevel = curriculumLevel;
	}

	public Integer getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(Integer curriculumid) {
		this.curriculumid = curriculumid;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getClassNature() {
		return classNature;
	}

	public void setClassNature(String classNature) {
		this.classNature = classNature;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	
	
}
