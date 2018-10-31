package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.FaceAnalysisConfigMapper;
import com.cac.CamEmotion.model.FaceAnalysisConfig;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.service.FaceAnalysisConfigService;

@Service
public class FaceAnalysisConfigServiceImpl implements FaceAnalysisConfigService {
	@Resource
	private FaceAnalysisConfigMapper faceAnalysisConfigDao;
	@Override
	public int deleteByPrimaryKey(Integer hostno) {
		return faceAnalysisConfigDao.deleteByPrimaryKey(hostno);
	}

	@Override
	public int insert(FaceAnalysisConfig record) {
		return faceAnalysisConfigDao.insert(record);
	}

	@Override
	public int insertSelective(FaceAnalysisConfig record) {
		return faceAnalysisConfigDao.insertSelective(record);
	}

	@Override
	public FaceAnalysisConfig selectByPrimaryKey(Integer hostno) {
		return faceAnalysisConfigDao.selectByPrimaryKey(hostno);
	}

	@Override
	public int updateByPrimaryKeySelective(FaceAnalysisConfig record) {
		return faceAnalysisConfigDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FaceAnalysisConfig record) {
		return faceAnalysisConfigDao.updateByPrimaryKey(record);
	}

	@Override
	public List<FaceAnalysisConfigExtend> findList(FaceAnalysisConfig record) {
		return faceAnalysisConfigDao.findList(record);
	}
    
}