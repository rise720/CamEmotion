package com.cac.CamEmotion.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.SampleMapper;
import com.cac.CamEmotion.model.Sample;
import com.cac.CamEmotion.service.SampleService;

/**
 * 
 * 类功能说明: 用户管理服务接口实现类
 * <P>
 * 
 * </P>
 *
 * @author zhangfei
 * @data 2016年10月20日
 */
@Service
public class SampleServiceImpl extends AbstractBaseServiceImpl<Sample> implements SampleService {

	@Resource
	private SampleMapper sampleDao;

	@Override
	@PostConstruct
	public void init() {
		setBaseDao(sampleDao);
	} 
}
