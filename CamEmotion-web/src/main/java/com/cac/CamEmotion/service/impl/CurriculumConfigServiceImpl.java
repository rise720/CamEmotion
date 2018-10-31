package com.cac.CamEmotion.service.impl;

import javax.annotation.Resource;

import com.cac.CamEmotion.dao.CurriculumConfigTblMapper;
import com.cac.CamEmotion.model.CurriculumConfigTbl;
import com.cac.CamEmotion.service.CurriculumConfigService;

public class CurriculumConfigServiceImpl implements CurriculumConfigService {
	@Resource
	private CurriculumConfigTblMapper curriculumConfigTblDao;

	@Override
	public int deleteByPrimaryKey(Integer curriculumid) {
		return curriculumConfigTblDao.deleteByPrimaryKey(curriculumid);
	}

	@Override
	public int insert(CurriculumConfigTbl record) {
		return curriculumConfigTblDao.insert(record);
	}

	@Override
	public int insertSelective(CurriculumConfigTbl record) {
		return curriculumConfigTblDao.insertSelective(record);
	}

	@Override
	public CurriculumConfigTbl selectByPrimaryKey(Integer curriculumid) {
		return curriculumConfigTblDao.selectByPrimaryKey(curriculumid);
	}

	@Override
	public int updateByPrimaryKeySelective(CurriculumConfigTbl record) {
		return curriculumConfigTblDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CurriculumConfigTbl record) {
		return curriculumConfigTblDao.updateByPrimaryKey(record);
	}
}