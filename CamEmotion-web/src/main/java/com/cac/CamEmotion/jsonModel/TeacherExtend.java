/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;
import java.util.Date;

import com.cac.CamEmotion.paging.PageExtend;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年2月7日
 */
public class TeacherExtend extends PageExtend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String teachername;

    private Integer teachersex;

    private Integer education;

    private String birthdate;

    private String startworkdate;

    private String headimgurl;

    private Integer staffsources;

    private Integer seriesstatus;

    private Integer fulltimenormalschoolstatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public Integer getTeachersex() {
		return teachersex;
	}

	public void setTeachersex(Integer teachersex) {
		this.teachersex = teachersex;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getStartworkdate() {
		return startworkdate;
	}

	public void setStartworkdate(String startworkdate) {
		this.startworkdate = startworkdate;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getStaffsources() {
		return staffsources;
	}

	public void setStaffsources(Integer staffsources) {
		this.staffsources = staffsources;
	}

	public Integer getSeriesstatus() {
		return seriesstatus;
	}

	public void setSeriesstatus(Integer seriesstatus) {
		this.seriesstatus = seriesstatus;
	}

	public Integer getFulltimenormalschoolstatus() {
		return fulltimenormalschoolstatus;
	}

	public void setFulltimenormalschoolstatus(Integer fulltimenormalschoolstatus) {
		this.fulltimenormalschoolstatus = fulltimenormalschoolstatus;
	}
    
}
