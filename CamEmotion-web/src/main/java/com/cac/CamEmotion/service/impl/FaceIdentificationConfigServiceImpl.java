package com.cac.CamEmotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.FaceIdentificationConfigMapper;
import com.cac.CamEmotion.model.FaceIdentificationConfig;
import com.cac.CamEmotion.service.FaceIdentificationConfigService;

@Service
public class FaceIdentificationConfigServiceImpl implements FaceIdentificationConfigService {

	@Resource
	private FaceIdentificationConfigMapper faceIdentificationConfigDao;

	@Override
	public int deleteByPrimaryKey(Integer hostno) {
		return faceIdentificationConfigDao.deleteByPrimaryKey(hostno);
	}

	@Override
	public int insert(FaceIdentificationConfig record) {
		return faceIdentificationConfigDao.insert(record);
	}

	@Override
	public int insertSelective(FaceIdentificationConfig record) {
		return faceIdentificationConfigDao.insertSelective(record);
	}

	@Override
	public FaceIdentificationConfig selectByPrimaryKey(Integer hostno) {
		return faceIdentificationConfigDao.selectByPrimaryKey(hostno);
	}

	@Override
	public int updateByPrimaryKeySelective(FaceIdentificationConfig record) {
		return faceIdentificationConfigDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FaceIdentificationConfig record) {
		return faceIdentificationConfigDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryAnalysisNoSelective(FaceIdentificationConfig record) {
		return faceIdentificationConfigDao.updateByPrimaryAnalysisNoSelective(record);
	}

	@Override
	public int deleteByPrimaryAnalysisNo(Integer analysisNo) {
		return faceIdentificationConfigDao.deleteByPrimaryAnalysisNo(analysisNo);
	}

}