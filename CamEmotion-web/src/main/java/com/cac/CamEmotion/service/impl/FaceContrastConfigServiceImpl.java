package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.FaceContrastConfigMapper;
import com.cac.CamEmotion.model.FaceContrastConfig;
import com.cac.CamEmotion.model.FaceContrastConfigExtend;
import com.cac.CamEmotion.service.FaceContrastConfigService;

@Service
public class FaceContrastConfigServiceImpl implements FaceContrastConfigService{

	@Resource
	FaceContrastConfigMapper faceContrastConfigDao;
	
	@Override
	public int deleteByPrimaryKey(Integer hostno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(FaceContrastConfig record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(FaceContrastConfig record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FaceContrastConfig selectByPrimaryKey(Integer hostno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(FaceContrastConfig record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(FaceContrastConfig record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FaceContrastConfigExtend> findList(Integer hostno) {
		// TODO Auto-generated method stub
		return null;
	}
}