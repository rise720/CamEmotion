package com.cac.CamEmotion.dao;

import com.cac.CamEmotion.model.SystemConfig;

public interface SystemConfigMapper {
    int deleteByPrimaryKey(String webip);

    int insert(SystemConfig record);

    int insertSelective(SystemConfig record);

    SystemConfig selectByPrimaryKey(String webip);

    SystemConfig selectSystemConfig();

    int updateByPrimaryKeySelective(SystemConfig record);

    int updateByPrimaryKey(SystemConfig record);
}