/**
 * 
 */
package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.TeacherInfoTblMapper;
import com.cac.CamEmotion.model.SchoolInfoTbl;
import com.cac.CamEmotion.model.TeacherInfoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.TeacherInfoService;

/**
 * 
 * 类功能说明: 老师管理服务类
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年2月7日
 */
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService{
	private static Logger logger = LogManager.getLogger(TeacherInfoServiceImpl.class);
	private Object object = new Object();
	
	@Resource
	private TeacherInfoTblMapper teacherInfoDao;
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
	 * @param currentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 * @return
	 *
	 */
	@Override
	public PageList<TeacherInfoTbl> findBypagination(TeacherInfoTbl record, long currentPage, long pageRecorders,
			String expression, String rule) {
		return new PageUtil<TeacherInfoTbl>().getListByPage(new PagingCallback<TeacherInfoTbl>(
				record, currentPage, pageRecorders, expression, rule) {
			@Override
			public int getCount(TeacherInfoTbl record) {
				return teacherInfoDao.findCount(record);
			}
			@Override
			public List<TeacherInfoTbl> queryList(TeacherInfoTbl record, long offset, long rows,
					String expression, String rule) {
				return teacherInfoDao.findBypagination(record, offset, rows, expression, rule);
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
	public List<TeacherInfoTbl> findAll(TeacherInfoTbl record) {
		return teacherInfoDao.findAll(record);
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
	public int insertTeacherInfo(TeacherInfoTbl record) {
		return teacherInfoDao.insertSelective(record);
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
	public int updateTeacherInfo(TeacherInfoTbl record) {
		int row = 0;
		synchronized (object) {
			if(teacherInfoDao.selectByPrimaryKey(record.getId()) != null){
				row = teacherInfoDao.updateByPrimaryKeySelective(record);
			}else{
				row = 70002;//数据不存在
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
	 * @param id
	 * @return
	 *
	 */
	@Override
	public int deleteTeacherInfo(Integer id) {
		int row = 0;
		synchronized (object) {
			if(teacherInfoDao.selectByPrimaryKey(id) != null){
				row = teacherInfoDao.deleteByPrimaryKey(id);
			}else{
				row = 70002;//数据不存在
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
	 * @param teacherId
	 * @return
	 *
	 */
	@Override
	public TeacherInfoTbl findTeacherInfo(Integer teacherId) {
		return teacherInfoDao.selectByPrimaryKey(teacherId);
	}
}
