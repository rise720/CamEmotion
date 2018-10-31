package com.cac.CamEmotion.model;

import java.util.Date;

public class TeacherInfoTbl {
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

    private Date createdate;

    private String createuser;

    private Date updatedate;

    private String updateuser;

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
        this.teachername = teachername == null ? null : teachername.trim();
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
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
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

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }
}