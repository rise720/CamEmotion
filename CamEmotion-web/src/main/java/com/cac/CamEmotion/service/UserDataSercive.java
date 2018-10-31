package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.model.UserDataTbl;
import com.cac.CamEmotion.paging.PageList;

/**
 * 用户信息
 * @author houpp
 *
 */
public interface UserDataSercive {
	
	/**
	 * 方法功能说明: 获取个人信息
	 * 
	 * 
	 * @param userTbl 用户对象
	 * @return
	 */
	public UserDataTbl getUserData(UserDataTbl userTbl);
	
	/**
	 * 方法功能说明：添加个人信息
	 * 
	 * 
	 * 
	 * @param userTbl 用户对象
	 * @return
	 */
	public int insertUserData(UserDataTbl userTbl);
	
	/**
	 * 方法功能说明：修改个人信息
	 * 
	 * 
	 * 
	 * @param userTbl 用户对象
	 * @return
	 */
	public int updateUserData(UserDataTbl userTbl);
	
	/**
	 * 方法功能说明：删除个人信息
	 * 
	 * @param userID
	 * @return
	 */
	public int deleteUserData(int userID);
	
	/**
	 * 
	 * 方法功能说明: 查询用户列表
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author houpp
	 * @date 2017年12月06日
	 * @param userTbl 查询条件
	 * @param CurrentPage 第几页
	 * @param pageRecorders 每页记录数
	 * @param expression 排序字段
	 * @param rule 排序方式
	 * @return
	 */
	public PageList<UserDataTbl> find(UserDataTbl userTbl, long CurrentPage, long pageRecorders, String expression, String rule);	
}
