package com.cac.CamEmotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.SystemConfigMapper;
import com.cac.CamEmotion.model.SystemConfig;
import com.cac.CamEmotion.service.SystemConfigService;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	@Resource
	private SystemConfigMapper systemConfigDao;

	@Override
	public SystemConfig find(String webIp) {
		return systemConfigDao.selectByPrimaryKey(webIp);
	}

	@Override
	public int insertSelective(String webIp) {
		SystemConfig record = new SystemConfig();
		if (webIp != null && webIp.length() > 0) {
			record.setWebip(webIp);
			return systemConfigDao.insert(record);
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String webIp) {
		return systemConfigDao.deleteByPrimaryKey(webIp);
	}

}