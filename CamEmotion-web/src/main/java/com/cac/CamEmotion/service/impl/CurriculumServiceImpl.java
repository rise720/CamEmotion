/**
 * 
 */
package com.cac.CamEmotion.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.dao.CurriculumVideoTblMapper;
import com.cac.CamEmotion.dao.EmotionaldataTblMapper;
import com.cac.CamEmotion.dao.EmotiondataStatisticsMapper;
import com.cac.CamEmotion.dao.FacefeatureTblMapper;
import com.cac.CamEmotion.exception.SysException;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.model.EmotionaldataTbl;
import com.cac.CamEmotion.model.EmotiondataStatistics;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.CurriculumService;
import com.cac.CamEmotion.util.DeleteFileUtil;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author Chenyang
 * @data 2017年10月11日
 */
@Service
public class CurriculumServiceImpl implements CurriculumService{
	private static Logger logger = LogManager.getLogger(CurriculumServiceImpl.class);
	
	private Object object = new Object();
	
	@Resource
	private CurriculumTblMapper curriculumDao;
	@Resource
	private CurriculumVideoTblMapper curriculumVideoDao;
	@Resource
	private EmotionaldataTblMapper emotionaldataDao;
	@Resource
	private EmotiondataStatisticsMapper emotiondataStatisticsDao;
	@Resource
	private FacefeatureTblMapper facefeatureTblDao;

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年10月11日
	 * @param record
	 *
	 */
	@Override
	public int updateCurriculum(CurriculumTbl record) {
		return curriculumDao.updateByModelSelective(record);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年10月11日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public List<CurriculumTbl> selectInClassInfo(Integer classstatus) {
		
		return curriculumDao.selectInClassInfo(classstatus);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月5日
	 * @param id
	 * @return
	 *
	 */
	@Override
	public CurriculumTbl selectClassInfo(Integer id) {
		return curriculumDao.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月29日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int updateCurriculumDiy(CurriculumTbl record) {
		int i = 0;
		synchronized (object) {
			i = curriculumDao.updateByModelSelectiveDiy(record);
		}
		return i;
	}

	@Override
	public CurriculumTbl newCurriculum(CurriculumTbl curriculumTbl) {
		try {
			/*
			 * 新增课程
			 */
			// 获取时间
			Date date = new Date();
			// 生成课名： 默认为 course_yyMMdd_HHmm
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmm");
			String name = "course_" + sdf.format(date.getTime());
			curriculumTbl.setCreatedate(new Date());
			curriculumTbl.setName(name);
			curriculumTbl.setFramefacecount(Constants.FRAME_FACE_COUNT);
			curriculumTbl.setClassstatus(0);
			curriculumDao.insert(curriculumTbl);

			// 根据课程id取模，分配表情识别数据存放的表，
			int num = curriculumTbl.getId() % Constants.EMOTION_TABLE_COUNT;
			String emotionTableName = "emotionaldata" + num + "_tbl";
			curriculumTbl.setEmotionTable(emotionTableName);
			String personTable = "emotiondata_students_statistics" + num + "_tbl";
			curriculumTbl.setPersonTable(personTable);
			
			updateCurriculum(curriculumTbl);
		} catch (Exception e) {
			throw new SysException(e, "1", e.getMessage());
		}
		return curriculumTbl;
	}

	/**
	 * 
	 * 方法功能说明: 删除课堂相关信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param id
	 * @return
	 *
	 */
	@Override
	@Transactional
	public int deleteCurriclum(Integer id) {
		int row = 0;
		EmotionaldataTbl emotionaldatatbl = new EmotionaldataTbl();
		
		emotionaldatatbl.setCurriculumid(id);
		CurriculumVideoTbl curriculumVideoTbl = new  CurriculumVideoTbl();
		curriculumVideoTbl.setCurriculumid(id);
		try {
			CurriculumTbl curriculumTbl = curriculumDao.selectByPrimaryKey(id);
			List<CurriculumVideoTbl> videoTbls = curriculumVideoDao.selectCurriculumVideoList(curriculumVideoTbl);
			EmotiondataStatistics eStatistics = null;
			if(curriculumTbl != null){
				eStatistics = new EmotiondataStatistics();
				eStatistics.setCurriculumid(id);
				emotionaldatatbl.setEmotionTableName(curriculumTbl.getEmotionTable());
				//删除课程
				row = curriculumDao.deleteByPrimaryKey(id);
				//删除视频文件记录数据
				row = curriculumVideoDao.deleteByCurriculumId(id);
				//删除视频采集数据
				row = emotionaldataDao.deleteByCurriculumId(emotionaldatatbl);
				//删除视频报表数据
				row = emotiondataStatisticsDao.deleteByModel(eStatistics);
				//删除facefeatureTbl表数据
				row = facefeatureTblDao.deleteByPrimaryCourseId(id);
				//删除视频文件
				String systemp = System.getProperty("user.dir").replace("bin", "");
				for (CurriculumVideoTbl videoTbl : videoTbls) {
					DeleteFileUtil.deleteFile(systemp + "/webapps/"+ Constants.SHARE_PATH +"/" + videoTbl.getVideoimgurl());
				}
			}else{
				row = 70002;//数据不存在
			}
		} catch (Exception e) {
			row = 0;
			e.printStackTrace();
			logger.error("删除课堂相关信息失败");
		}
		return row;
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param curriculum
	 * @return
	 *
	 */
	@Override
	public int updateByClassStatus(CurriculumTbl curriculum) {
		return curriculumDao.updateByClassStatus(curriculum);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年5月4日
	 * @param curriculum
	 * @return
	 *
	 */
	@Override
	public List<CurriculumTbl> findByModel(CurriculumTbl curriculum) {
		return curriculumDao.selectByModel(curriculum);
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
		return new PageUtil<CurriculumTbl>().getListByPage(new PagingCallback<CurriculumTbl>(
				Curriclumtbl, CurrentPage, pageRecorders, expression, rule) {
			@Override
			public int getCount(CurriculumTbl Curriclumtbl) {
				int row = curriculumDao.selectCurriculumCount(Curriclumtbl);
				return row;
			}
			@Override
			public List<CurriculumTbl> queryList(CurriculumTbl Curriclumtbl, long offset, long rows,
					String expression, String rule) {
				List<CurriculumTbl> list = curriculumDao.find(Curriclumtbl, offset, rows, expression,
						rule);
				return list;
			}
		});
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年5月8日
	 * @param cAnalysisModel
	 * @return
	 *
	 */
	@Override
	public PageList<CurriculumTbl> findCurriculums(CurriculumTbl Curriclumtbl, long CurrentPage,
			long pageRecorders, String expression, String rule) {
		return null;
	}

}
