package com.cac.CamEmotion.model;

public class TokenTbl {
	// 刷新Token 24小时有效 1次有效
	private String RefreshToken;
	// 临时token 2小时有效 多次有效
	private String AccessToken;

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
}