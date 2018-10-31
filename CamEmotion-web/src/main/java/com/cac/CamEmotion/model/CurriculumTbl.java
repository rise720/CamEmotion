package com.cac.CamEmotion.model;

import java.io.Serializable;
import java.util.Date;

public class CurriculumTbl implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Date createdate;

    private String createuesr;
    
    private Integer teacherid;
    
	private String teachername;

	private String classno;//班级
    
    private Integer classroomId;//班级Id
    
	public Integer getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
	}

	private String notemsg;//备注
    
    //增加
    
    private String starttime;
    
    private String endtime;
    
    private Integer schoolid;
    
	private String school;
    
    private int classstatus;
    
    
    /*
     * 20171103
     */
    
    private String coursename;//课程名称
    
    private String coursecontents;//课程内容
    
    private String searchcontent;//搜索内容
    
    private int framefacecount;//每帧识别的人脸数
    
    /*
     * 20171213
     */
    private String emotionTable;//表名
    
    /*
     * 20180103
     */
    private String classNature;//课程性质
    private String teacherType;//老师类型
    
    /*
     * 20180119
     */
    private String gradeno;//年级
  
   /**
    * 2018-4-4 14:01
    */
    private int curriculumLevel;//课堂评级
    
    /**
     * 2018-5-4 14:01
     */
//    private String statisticsFlag;//课堂评级
    
    private int curriculumStarts;//后台计算状态
    
    private String personTable;//表名
    
    
//	public String getStatisticsFlag() {
//		return statisticsFlag;
//	}
//
//	public void setStatisticsFlag(String statisticsFlag) {
//		this.statisticsFlag = statisticsFlag;
//	}

	public int getCurriculumStarts() {
		return curriculumStarts;
	}

	public void setCurriculumStarts(int curriculumStarts) {
		this.curriculumStarts = curriculumStarts;
	}

	public String getPersonTable() {
		return personTable;
	}

	public void setPersonTable(String personTable) {
		this.personTable = personTable;
	}

	public Integer getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}

    public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}
    
	public int getCurriculumLevel() {
		return curriculumLevel;
	}

	public void setCurriculumLevel(int curriculumLevel) {
		this.curriculumLevel = curriculumLevel;
	}

	public String getGradeno() {
		return gradeno;
	}

	public void setGradeno(String gradeno) {
		this.gradeno = gradeno;
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

	

	public String getEmotionTable() {
		return emotionTable;
	}

	public void setEmotionTable(String emotionTable) {
		this.emotionTable = emotionTable;
	}

	public int getFramefacecount() {
		return framefacecount;
	}

	public void setFramefacecount(int framefacecount) {
		this.framefacecount = framefacecount;
	}

	public String getSearchcontent() {
		return searchcontent;
	}

	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCoursecontents() {
		return coursecontents;
	}

	public void setCoursecontents(String coursecontents) {
		this.coursecontents = coursecontents;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getClassstatus() {
		return classstatus;
	}

	public void setClassstatus(int classstatus) {
		this.classstatus = classstatus;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
	}

	public String getNotemsg() {
		return notemsg;
	}

	public void setNotemsg(String notemsg) {
		this.notemsg = notemsg;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuesr() {
        return createuesr;
    }

    public void setCreateuesr(String createuesr) {
        this.createuesr = createuesr == null ? null : createuesr.trim();
    }
}