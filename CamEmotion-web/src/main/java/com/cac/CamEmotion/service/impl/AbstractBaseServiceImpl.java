package com.cac.CamEmotion.service.impl;

import java.io.Serializable;

import com.cac.CamEmotion.dao.BaseMapper;
import com.cac.CamEmotion.service.BaseService;

public abstract class AbstractBaseServiceImpl<T> implements BaseService<T> {
	private BaseMapper<T> baseDao;

	public void setBaseDao(BaseMapper<T> baseDao) {
		this.baseDao = baseDao;
	}

	public abstract void init();

	public int insert(T o) {
		return baseDao.insert(o);
	}

	public int deleteByPrimaryKey(Serializable id) {
		return baseDao.deleteByPrimaryKey(id);
	}

	public int insertSelective(T o) {
		return baseDao.insertSelective(o);
	}

	public T selectByPrimaryKey(Serializable id) {
		return baseDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(T o) {
		return baseDao.updateByPrimaryKeySelective(o);
	}

	public int updateByPrimaryKey(T o) {
		return baseDao.updateByPrimaryKey(o);
	}
}
