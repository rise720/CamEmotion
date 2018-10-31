package com.cac.CamEmotion.model;

import java.util.Date;
/**
 * 
 * 类功能说明:用户信息类
 * <P>
 *     
 * </P>
 *
 * @author	zhangfei
 * @data	2016年10月20日
 */
public class UserTbl extends UserTblId{
	//用户名称
    private String name;
    //角色
    private Integer role;
    //状态
    private Integer status;
    //创建日期
    private Date createdate;
    //创建人员
    private String createuser;
    //修改日期
    private Date updatedate;
    //修改人员
    private String updateuser;
    //时间戳
    private Date timestamp;
    
    /* 登录传入参数 */
	private String uu_session_id;
	private String parmtimestamp;	// 时间
	private String parmsign;		// md5加密串

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

	public String getUu_session_id() {
		return uu_session_id;
	}

	public void setUu_session_id(String uu_session_id) {
		this.uu_session_id = uu_session_id;
	}

	public String getParmtimestamp() {
		return parmtimestamp;
	}

	public void setParmtimestamp(String parmtimestamp) {
		this.parmtimestamp = parmtimestamp;
	}

	public String getParmsign() {
		return parmsign;
	}

	public void setParmsign(String parmsign) {
		this.parmsign = parmsign;
	}
}