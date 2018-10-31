package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.CurriculumEvaluateTblMapper;
import com.cac.CamEmotion.model.CurriculumEvaluateTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.CurriculumEvaluateService;

@Service
public class CurriculumEvaluateServiceImpl implements CurriculumEvaluateService {
	private static Logger logger = LogManager.getLogger(CurriculumEvaluateServiceImpl.class);
	@Resource
	private CurriculumEvaluateTblMapper curriculumEvaluateDao;

	@Override
	public int insert(CurriculumEvaluateTbl record) {
		return curriculumEvaluateDao.insert(record);
	}

	@Override
	public int Update(CurriculumEvaluateTbl record) {
		return curriculumEvaluateDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int DeleteId(Integer id) {
		return curriculumEvaluateDao.deleteByPrimaryKey(id);
	}

	@Override
	public int DeleteCurricId(Integer id) {
		return curriculumEvaluateDao.deleteByCurricIdPrimaryKey(id);
	}

	@Override
	public PageList<CurriculumEvaluateTbl> GetList(CurriculumEvaluateTbl record, long CurrentPage, long pageRecorders,
			String expression, String rule) {
		PageList<CurriculumEvaluateTbl> pagelist = null;
		try {
			// 分页查询
			pagelist = new PageUtil<CurriculumEvaluateTbl>().getListByPage(
					new PagingCallback<CurriculumEvaluateTbl>(record, CurrentPage, pageRecorders, expression, rule) {
						@Override
						public int getCount(CurriculumEvaluateTbl curriclumVideotbl) {
							// TODO Auto-generated method stub
							int row = curriculumEvaluateDao.selectCount(curriclumVideotbl);
							logger.info("返回的数据" + row);
							return row;
						}

						@Override
						public List<CurriculumEvaluateTbl> queryList(CurriculumEvaluateTbl curriclumVideotbl,
								long offset, long rows, String expression, String rule) {
							// TODO Auto-generated method stub
							List<CurriculumEvaluateTbl> list = curriculumEvaluateDao.find(curriclumVideotbl, offset,
									rows, expression, rule);
							logger.info("返回的数据" + ReflectionToStringBuilder.toString(list));
							return list;
						}
					});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return pagelist;
	}

	@Override
	public CurriculumEvaluateTbl SelectByPrimaryKey(Integer id) {
		return curriculumEvaluateDao.selectByPrimaryKey(id);
	}

}
