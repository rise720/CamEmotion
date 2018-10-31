package com.cac.CamEmotion.model;
/**
 * 
 * 类功能说明: 员工号系统号暂存类
 * <P>
 *     
 * </P>
 *
 * @author	zhangfei
 * @data	2016年10月20日
 */
public class UserTblId{
	//系统号
    private String sysid;

    //员工号
    private String userid;
    
    public UserTblId(){
    	
    }
    
    public UserTblId(String sysid, String userid){
    	this.sysid = sysid;
    	this.userid = userid;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid == null ? null : sysid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}