package com.cac.CamEmotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.dao.UserDataTblMapper;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.model.UserDataTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.paging.PageUtil;
import com.cac.CamEmotion.paging.PagingCallback;
import com.cac.CamEmotion.service.UserDataSercive;

@Service
public class UserDataServiceImpl implements UserDataSercive {
	private static Logger logger = LogManager.getLogger(UserDataServiceImpl.class);
	
	private Object objectUpdate = new Object();
	private Object objectDelete = new Object();

	/**
	 * 用户信息服务类
	 */
	@Resource
	private UserDataTblMapper UserData;

	/**
	 * 方法功能说明: 获取个人信息
	 * 
	 * 
	 * @param userTbl
	 *            用户对象
	 * @return
	 */
	@Override
	public UserDataTbl getUserData(UserDataTbl userTbl) {
		return UserData.selectByUseraccountKey(userTbl);
	}

	/**
	 * 方法功能说明：添加个人信息
	 * 
	 * 
	 * 
	 * @param userTbl
	 *            用户对象
	 * @return
	 */
	@Override
	public int insertUserData(UserDataTbl userTbl) {
		// TODO Auto-generated method stub
		return UserData.insert(userTbl);
	}

	/**
	 * 方法功能说明：修改个人信息
	 * 
	 * 
	 * 
	 * @param userTbl
	 *            用户对象
	 * @return
	 */
	@Override
	public int updateUserData(UserDataTbl userTbl) {
		int i = 0 ;
		synchronized (objectUpdate) {
			if (UserData.selectByPrimaryKey(userTbl.getId())!= null)
				i = UserData.updateByPrimaryKeySelective(userTbl);
		}
		return i;
	}

	/**
	 * 方法功能说明：删除个人信息
	 * 
	 * 
	 * 
	 * @param userTbl
	 *            用户对象
	 * @return
	 */
	@Override
	public int deleteUserData(int userID) {
		int i = 0;
		synchronized (objectDelete) {
		if (UserData.selectByPrimaryKey(userID) != null)
			i = UserData.deleteByPrimaryKey(userID);
		}
		return i;
	}

	/**
	 * 
	 * 方法功能说明: 查询用户列表
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author houpp
	 * @date 2017年12月06日
	 * @param userTbl
	 *            查询条件
	 * @param CurrentPage
	 *            第几页
	 * @param pageRecorders
	 *            每页记录数
	 * @param expression
	 *            排序字段
	 * @param rule
	 *            排序方式
	 * @return
	 */
	@Override
	public PageList<UserDataTbl> find(UserDataTbl userTbl, long CurrentPage, long pageRecorders, String expression,
			String rule) {
		// TODO Auto-generated method stub
		PageList<UserDataTbl> pagelist = null;
		try {
			// 分页查询
			pagelist = new PageUtil<UserDataTbl>().getListByPage(
					new PagingCallback<UserDataTbl>(userTbl, CurrentPage, pageRecorders, expression, rule) {
						@Override
						public int getCount(UserDataTbl userTbl) {
							// TODO Auto-generated method stub
							int row = UserData.selectCurriculumCount(userTbl);
							logger.info("返回的数据" + row);
							return row;
						}

						@Override
						public List<UserDataTbl> queryList(UserDataTbl userTbl, long offset, long rows,
								String expression, String rule) {
							// TODO Auto-generated method stub
							List<UserDataTbl> list = UserData.find(userTbl, offset, rows, expression, rule);
							logger.info("返回的数据" + ReflectionToStringBuilder.toString(list));
							return list;
						}
					});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return pagelist;
	}

}
