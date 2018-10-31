/**
 * 
 */
package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.model.TeacherInfoTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2018年2月7日
 */
public interface TeacherInfoService {
	
	/**
	 * 
	 * 
	 * 方法功能说明: 分页查询学校信息列表
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
	PageList<TeacherInfoTbl> findBypagination(TeacherInfoTbl record, long currentPage,
			long pageRecorders, String expression, String rule);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询所有老师信息列表
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
	List<TeacherInfoTbl> findAll(TeacherInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询单条老师信息
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
	TeacherInfoTbl findTeacherInfo(Integer teacherId);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 新增老师信息
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
	int insertTeacherInfo(TeacherInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 修改老师信息
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
	int updateTeacherInfo(TeacherInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 删除老师信息
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
	int deleteTeacherInfo(Integer id);

}
