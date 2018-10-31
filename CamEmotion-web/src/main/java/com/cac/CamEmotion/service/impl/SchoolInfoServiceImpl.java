/**
 * 
 */
package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.SchoolInfoTblMapper;
import com.cac.CamEmotion.model.SchoolInfoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.SchoolInfoService;

/**
 * 
 * 类功能说明: 学校学校管理服务类
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年2月7日
 */
@Service
public class SchoolInfoServiceImpl implements SchoolInfoService{
	private static Logger logger = LogManager.getLogger(SchoolInfoServiceImpl.class);
	private Object object = new Object();
	@Resource
	private SchoolInfoTblMapper schoolInfoDao;
	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param record
	 * @param CurrentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 * @return
	 *
	 */
	@Override
	public PageList<SchoolInfoTbl> findBypagination(SchoolInfoTbl record, long currentPage, long pageRecorders,
			String expression, String rule) {
		return new PageUtil<SchoolInfoTbl>().getListByPage(new PagingCallback<SchoolInfoTbl>(
				record, currentPage, pageRecorders, expression, rule) {
			@Override
			public int getCount(SchoolInfoTbl schoolInfoTbl) {
				return schoolInfoDao.findCount(schoolInfoTbl);
			}
			@Override
			public List<SchoolInfoTbl> queryList(SchoolInfoTbl record, long offset, long rows,
					String expression, String rule) {
				return schoolInfoDao.findBypagination(record, offset, rows, expression, rule);
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
	 * @date 2018年2月7日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public List<SchoolInfoTbl> findAll(SchoolInfoTbl record) {
		return schoolInfoDao.findAll(record);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int insertSchoolInfo(SchoolInfoTbl record) {
		return schoolInfoDao.insertSelective(record);
	}

	/**
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int updateSchoolInfo(SchoolInfoTbl record) {
		int row = 0;
		synchronized (object) {
			if(schoolInfoDao.selectByPrimaryKey(record.getId()) != null){
				row = schoolInfoDao.updateByPrimaryKeySelective(record);
			}else{
				row = 60002;//数据不存在
			}
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
	 * @date 2018年2月7日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int deleteSchoolInfo(Integer id) {
		int row = 0;
		synchronized (object) {
			try {
				if(schoolInfoDao.selectByPrimaryKey(id) != null){
					row = schoolInfoDao.deleteByPrimaryKey(id);
				}else{
					row = 60002;//数据不存在
				}
			} catch (Exception e) {
				logger.error("删除学校信息异常：" + e.getMessage());
				row = 10001;//异常
			}
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
	 * @date 2018年2月7日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public SchoolInfoTbl findSchoolInfo(Integer schoolId) {
		return schoolInfoDao.selectByPrimaryKey(schoolId);
	}
}
