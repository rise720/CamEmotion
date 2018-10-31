package com.cac.CamEmotion.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.ReportTblMapper;
import com.cac.CamEmotion.model.ComprehensiveReportTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	private static Logger logger = LogManager.getLogger(ReportServiceImpl.class);

	@Resource
	ReportTblMapper reportDao;

	@Override
	public List<Double> selectMinutesWaveAverageRate(Integer curriculumid) {
		return reportDao.selectMinutesWaveAverageRate(curriculumid);
	}

	@Override
	public PageList<ComprehensiveReportTbl> GetList(ComprehensiveReportTbl record, long CurrentPage, long pageRecorders,
			String expression, String rule) {
		PageList<ComprehensiveReportTbl> pagelist = null;
		try {
			// 分页查询
			pagelist = new PageUtil<ComprehensiveReportTbl>().getListByPage(
					new PagingCallback<ComprehensiveReportTbl>(record, CurrentPage, pageRecorders, expression, rule) {
						@Override
						public int getCount(ComprehensiveReportTbl curriclumVideotbl) {
							int row = reportDao.selectCount(curriclumVideotbl);
							logger.info("返回的数据条数：" + row);
							return row;
						}

						@Override
						public List<ComprehensiveReportTbl> queryList(ComprehensiveReportTbl curriclumVideotbl, long offset, long rows, String expression, String rule) {
							List<ComprehensiveReportTbl> list = reportDao.find(curriclumVideotbl, offset, rows, expression, rule);
//							list = GetWave(list);
							return list;
						}
					});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return pagelist;
	}

//	private List<ComprehensiveReportTbl> GetWave(List<ComprehensiveReportTbl> list) {
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).setPuzzledRate((double)Math.round(list.get(i).getPuzzledRate() * 100) / 100);
//				list.get(i).setCalmRate((double)Math.round(list.get(i).getCalmRate() * 100) / 100);
//				list.get(i).setExcitementRate((double)Math.round(list.get(i).getExcitementRate() * 100) / 100);
//				List<Double> warList = reportDao.selectMinutesWaveAverageRate(list.get(i).getId());
//				if (warList != null && warList.size() > 0) {
//					double war = GetWaveAverageRate(warList);
//					// 波动次数
//					double bdCount = 0;
//					for (int j = 1; j < warList.size(); j++) {
//						if ((warList.get(j) > war && warList.get(j - 1) < war) || (warList.get(j) < war && warList.get(j - 1) > war)) {
//							bdCount++;
//						}
//					}
//					// 当课堂时长不一致时，统一标准
//					bdCount = ((bdCount / warList.size()) * 45);
//					list.get(i).setWaveRate((double)Math.round(bdCount * 100) / 100);
//				}
//			}
//		}
//		return list;
//	}

	private double GetWaveAverageRate(List<Double> list) {
		double war = 0;
		if (list != null && list.size() > 0) {
			for (Double d : list) {
				war += d;
			}
			war = war / list.size();
		}
		return war;
	}

	@Override
	public List<ComprehensiveReportTbl> GetLists(ComprehensiveReportTbl record, long CurrentPage, long pageRecorders, String expression, String rule) {
		try {
			List<ComprehensiveReportTbl> list = reportDao.find(record, CurrentPage, pageRecorders, expression, rule);
//			list = GetWave(list);
			return list;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ArrayList<ComprehensiveReportTbl>() ;			
	}
}
