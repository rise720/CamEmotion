package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.CurriculumConfigTbl;

public interface CurriculumConfigService {
	 int deleteByPrimaryKey(Integer curriculumid);

	    int insert(CurriculumConfigTbl record);

	    int insertSelective(CurriculumConfigTbl record);

	    CurriculumConfigTbl selectByPrimaryKey(Integer curriculumid);

	    int updateByPrimaryKeySelective(CurriculumConfigTbl record);

	    int updateByPrimaryKey(CurriculumConfigTbl record);
}