package com.cac.CamEmotion.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.jsonModel.UserDataExtend;
import com.cac.CamEmotion.model.UserDataTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.UserDataSercive;
import com.cac.CamEmotion.springmvc.model.Response;
import com.cac.CamEmotion.util.MD5;

@RestController
@RequestMapping("/rest/UserData")
public class UserDataController {
	private static Logger logger = LogManager.getLogger(UserDataController.class);

	@Resource
	private UserDataSercive UserDataSercive;

	/**
	 * 方法功能说明：登录的接口
	 * 
	 * @param request
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public Response<UserDataTbl> getUserInfo(HttpServletRequest request, @RequestBody UserDataTbl userDataTbl) {
		UserDataTbl user2 = null;
		try {
			UserDataTbl user = new UserDataTbl();
			user.setUseraccount(userDataTbl.getUseraccount());
			user.setCpassword(MD5.encodeByMd5(userDataTbl.getCpassword()));
			user2 = UserDataSercive.getUserData(user);
			if (user2 == null) {
				return new Response<UserDataTbl>().failure("0003", "账号密码错误");
			} else {
				if (!user2.getCpassword().toString().equals(MD5.encodeByMd5(userDataTbl.getCpassword()).toString())) {
					return new Response<UserDataTbl>().failure("0003", "账号密码错误");
				}
			}
			return new Response<UserDataTbl>().success(user2);
		} catch (Exception e) {
			logger.error("登录失败！",e);
			return new Response<UserDataTbl>().failure("","");
		}
	}

	/**
	 * 方法功能说明：添加用户
	 * 
	 * 
	 * @param request
	 * @param userAccount
	 *            账号
	 * @param passWord
	 *            密码
	 * @param nickName
	 *            昵称
	 * @param cRight
	 *            权限
	 * @param createUser
	 *            创建人
	 * @return
	 */
	@RequestMapping(value = "/InsertUser", method = RequestMethod.POST)
	public Response<String> InsertUser(HttpServletRequest request, @RequestBody UserDataTbl user) {
		try {
			user.setCpassword(MD5.encodeByMd5(user.getCpassword()));
			if (UserDataSercive.getUserData(user) == null) {
				if (UserDataSercive.insertUserData(user) > 0)
					return new Response<String>().success("添加完成");
				else
					return new Response<String>().failure("0001", "添加失败");
			} else {
				return new Response<String>().failure("0002", "账号已存在");
			}
		} catch (Exception e) {
			logger.error(e);
			return new Response<String>().failure("0003", e.getMessage());
		}
	}

	/**
	 * 方法功能说明：更新用户
	 * 
	 * @param request
	 * @param userAccount
	 *            账号
	 * @param passWord
	 *            密码
	 * @param nickName
	 *            昵称
	 * @param cRight
	 *            权限
	 * @param updateUser
	 *            创建人
	 * @return
	 */
	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
	public Response<String> UpdateUser(HttpServletRequest request, @RequestBody UserDataTbl user) {
		try {
			if (user == null || user.getId() == null || user.getId() <= 0) {
				return new Response<String>().failure("0000", "参数有误");
			}
			user.setCpassword(MD5.encodeByMd5(user.getCpassword()));
			if (UserDataSercive.updateUserData(user) > 0)
				return new Response<String>().success("修改成功");
			else
				return new Response<String>().failure("0002", "该条数据已经不存在");
		} catch (Exception e) {
			logger.error(e);
			return new Response<String>().failure("0003", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/SelectUser", method = RequestMethod.POST)
	public Response<UserDataTbl> SelectUser(HttpServletRequest request, @RequestBody UserDataTbl user){
		try {
			if (user == null || user.getId() == null || user.getId() <= 0) {
				return new Response<UserDataTbl>().failure("0000", "参数有误");
			}
			UserDataTbl userDataTbl = new UserDataTbl();
			userDataTbl = UserDataSercive.getUserData(user);
			if (userDataTbl != null)
				return new Response<UserDataTbl>().success(userDataTbl);
			else
				return new Response<UserDataTbl>().failure("0002", "该条数据已经不存在");
		} catch (Exception e) {
			logger.error(e);
			return new Response<UserDataTbl>().failure("0003", e.getMessage());
		}
	}

	/**
	 * 方法功能说明：删除用户
	 * 
	 * @param request
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
	public Response<String> DeleteUser(HttpServletRequest request, @RequestBody UserDataTbl user) {
		try {
			if (user == null || user.getId() == null || user.getId() <= 0) {
				return new Response<String>().failure("0000", "参数有误");
			}
			if (UserDataSercive.deleteUserData(user.getId()) > 0)
				return new Response<String>().success("删除成功");
			else
				return new Response<String>().failure("0001", "该条数据已经不存在");
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<String>().failure("0000","系统异常");
		}
	}

	/**
	 * 方法功能说明：用户列表的接口
	 * 
	 * @param request
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping(value = "/Userlist", method = RequestMethod.POST)
	public Response<PageList<UserDataTbl>> Userlist(HttpServletRequest request, @RequestBody UserDataExtend UserData) {
		logger.info(UserData.toString());
		UserDataTbl userdata = new UserDataTbl();
		userdata.setUseraccount(UserData.getUseraccount());

		PageList<UserDataTbl> PageUserData = UserDataSercive.find(userdata, UserData.getCurrentPage(),
				UserData.getPageRecorders(), "id", "DESC");
		return new Response<PageList<UserDataTbl>>().success(PageUserData);
	}
}
