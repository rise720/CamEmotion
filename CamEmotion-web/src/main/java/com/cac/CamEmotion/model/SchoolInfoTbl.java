package com.cac.CamEmotion.model;

import java.util.Date;

public class SchoolInfoTbl {
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

    private Date createtime;

    private String createuser;

    private Date updatetime;

    private String updateuser;

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
        this.schoolname = schoolname == null ? null : schoolname.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress == null ? null : fulladdress.trim();
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
        this.subject = subject == null ? null : subject.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }
}