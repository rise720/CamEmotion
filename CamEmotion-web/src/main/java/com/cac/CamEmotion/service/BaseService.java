package com.cac.CamEmotion.service;

import java.io.Serializable;

public interface BaseService<T> {
	public int insert(T o);
	
	public int deleteByPrimaryKey(Serializable id);

	public int insertSelective(T o);

	public T selectByPrimaryKey(Serializable id);

	public int updateByPrimaryKeySelective(T o);

	public int updateByPrimaryKey(T o);

}
