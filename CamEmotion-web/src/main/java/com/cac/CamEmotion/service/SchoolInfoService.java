/**
 * 
 */
package com.cac.CamEmotion.service;

import java.util.List;

import com.cac.CamEmotion.model.SchoolInfoTbl;
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
public interface SchoolInfoService {
	
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
	PageList<SchoolInfoTbl> findBypagination(SchoolInfoTbl record, long currentPage,
			long pageRecorders, String expression, String rule);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询所有学校信息列表
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
	List<SchoolInfoTbl> findAll(SchoolInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询所有学校信息列表
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
	SchoolInfoTbl findSchoolInfo(Integer schoolId);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 新增学校信息
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
	int insertSchoolInfo(SchoolInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 修改学校信息
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
	int updateSchoolInfo(SchoolInfoTbl record);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 删除学校信息
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
	int deleteSchoolInfo(Integer id);
	

}
