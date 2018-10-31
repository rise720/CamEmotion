package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;

import com.cac.CamEmotion.paging.PageExtend;

public class UserDataExtend extends PageExtend implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;          	// id
    private String nickname;		// 昵称
    private String cright;			// 权限
    private String useraccount;		// 账户
    
    
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCright() {
		return cright;
	}
	public void setCright(String cright) {
		this.cright = cright;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	
}
