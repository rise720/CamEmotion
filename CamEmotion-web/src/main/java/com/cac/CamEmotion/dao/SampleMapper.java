package com.cac.CamEmotion.dao;

import com.cac.CamEmotion.model.Sample;

public interface SampleMapper extends BaseMapper<Sample> {
	int deleteByPrimaryKey(Long id);

	int insert(Sample record);

	int insertSelective(Sample record);

	Sample selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Sample record);

	int updateByPrimaryKey(Sample record);
}