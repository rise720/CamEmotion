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
 * @author chenyang
 * @data 2018年2月7日
 */
public class SchoolExtend extends PageExtend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String schoolname;

    private String province;

    private String city;

    private String district;

    private String fulladdress;

    private Integer educationlevel;

    private Integer schoolyears;

    private Integer classnamedrule;

    private Integer classcount;

    private String subject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFulladdress() {
		return fulladdress;
	}

	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}

	public Integer getEducationlevel() {
		return educationlevel;
	}

	public void setEducationlevel(Integer educationlevel) {
		this.educationlevel = educationlevel;
	}

	public Integer getSchoolyears() {
		return schoolyears;
	}

	public void setSchoolyears(Integer schoolyears) {
		this.schoolyears = schoolyears;
	}

	public Integer getClassnamedrule() {
		return classnamedrule;
	}

	public void setClassnamedrule(Integer classnamedrule) {
		this.classnamedrule = classnamedrule;
	}

	public Integer getClasscount() {
		return classcount;
	}

	public void setClasscount(Integer classcount) {
		this.classcount = classcount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	

}
