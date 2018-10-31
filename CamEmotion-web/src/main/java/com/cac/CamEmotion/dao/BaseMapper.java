package com.cac.CamEmotion.dao;

import java.io.Serializable;

public interface BaseMapper<T> {
	int deleteByPrimaryKey(Serializable id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(Serializable id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);
}
