package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.SystemConfig;

public interface SystemConfigService {
	
	public SystemConfig find(String webIp);
	
	public int insertSelective(String webIp);
	
	public int deleteByPrimaryKey(String webIp);
}
