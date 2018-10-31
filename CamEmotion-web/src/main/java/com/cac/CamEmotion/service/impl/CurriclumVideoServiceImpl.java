package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.dao.CurriculumVideoTblMapper;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.CurriclumVideoService;

@Service
public class CurriclumVideoServiceImpl implements CurriclumVideoService {
	private static Logger logger = LogManager.getLogger(CurriclumVideoServiceImpl.class);

	// 课程回放视频操作服务类
	@Resource
	private CurriculumVideoTblMapper CurriculumVideoDao;
	
	@Resource
	private CurriculumTblMapper CurriculumDao;

	@Override
	public List<CurriculumVideoTbl> CurriclumVideoData(CurriculumVideoTbl CurriclumVideotbl) {
		List<CurriculumVideoTbl> videoTbls = CurriculumVideoDao.selectCurriculumVideoList(CurriclumVideotbl);
		if(videoTbls != null){
			for (int i = 0; i < videoTbls.size(); i++) {
				videoTbls.get(i).setVideoimgurl(Constants.SHARE_PATH + videoTbls.get(i).getVideoimgurl());
			}
		}
		return videoTbls;
	}

	@Override
	public PageList<CurriculumVideoTbl> selectAllCurriclumVideo(CurriculumVideoTbl curriclumVideotbl, long CurrentPage,
			long pageRecorders, String expression, String rule) {
		
		PageList<CurriculumVideoTbl> pagelist= null;
		try {
			// 分页查询
			pagelist = new PageUtil<CurriculumVideoTbl>().getListByPage(new PagingCallback<CurriculumVideoTbl>(
					curriclumVideotbl, CurrentPage, pageRecorders, expression, rule) {
				@Override
				public int getCount(CurriculumVideoTbl curriclumVideotbl) {
					// TODO Auto-generated method stub
					int row = CurriculumVideoDao.selectCurriculumVideoCount(curriclumVideotbl);
					logger.info("返回的数据" + row);
					return row;
				}
				@Override
				public List<CurriculumVideoTbl> queryList(CurriculumVideoTbl curriclumVideotbl, long offset, long rows,
						String expression, String rule) {
					// TODO Auto-generated method stub
					List<CurriculumVideoTbl> list = CurriculumVideoDao.find(curriclumVideotbl, offset, rows, expression,
							rule);
					logger.info("返回的数据" + ReflectionToStringBuilder.toString(list));
					return list;
				}
			});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}		
		return pagelist;
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年9月14日
	 * @param Curriclumtbl
	 * @param CurrentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 * @return
	 *
	 */
	@Override
	public PageList<CurriculumTbl> selectAllCurriclum(CurriculumTbl Curriclumtbl, long CurrentPage, long pageRecorders,
			String expression, String rule) {
		// TODO Auto-generated method stub
		return new PageUtil<CurriculumTbl>().getListByPage(new PagingCallback<CurriculumTbl>(
				Curriclumtbl, CurrentPage, pageRecorders, expression, rule) {
			@Override
			public int getCount(CurriculumTbl Curriclumtbl) {
				int row = CurriculumDao.selectCurriculumCount(Curriclumtbl);
				return row;
			}
			@Override
			public List<CurriculumTbl> queryList(CurriculumTbl Curriclumtbl, long offset, long rows,
					String expression, String rule) {
				List<CurriculumTbl> list = CurriculumDao.find(Curriclumtbl, offset, rows, expression,
						rule);
				return list;
			}
		});
	}
}
