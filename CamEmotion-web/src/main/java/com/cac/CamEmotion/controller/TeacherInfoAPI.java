/**
 * 
 */
package com.cac.CamEmotion.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cac.CamEmotion.jsonModel.TeacherExtend;
import com.cac.CamEmotion.model.TeacherInfoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.TeacherInfoService;
import com.cac.CamEmotion.springmvc.model.Response;

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
@RestController
@RequestMapping("/rest/TeacherInfoAPI")
public class TeacherInfoAPI {
	private static Logger logger = LogManager.getLogger(TeacherInfoAPI.class);

	@Resource
	private TeacherInfoService teacherInfoService;

	/**
	 * 
	 * 
	 * 方法功能说明: 分页查询老师列表
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/findBypagination", method = RequestMethod.POST)
	public Response<PageList<TeacherInfoTbl>> findBypagination(@RequestBody TeacherExtend pe) {
		TeacherInfoTbl teacherInfoTbl = new TeacherInfoTbl();
		teacherInfoTbl.setTeachername(pe.getTeachername());
		PageList<TeacherInfoTbl> teacherInfos = teacherInfoService.findBypagination(teacherInfoTbl, pe.getCurrentPage(),
				pe.getPageRecorders(), "id", "DESC");
		return new Response<PageList<TeacherInfoTbl>>().success(teacherInfos);
	}

	/**
	 * 
	 * 
	 * 方法功能说明:查询所有老师列表
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public Response<List<TeacherInfoTbl>> findAll(@RequestBody(required = false) TeacherInfoTbl teacherInfoTbl) {
		List<TeacherInfoTbl> teacherInfoTbls = teacherInfoService.findAll(teacherInfoTbl);
		return new Response<List<TeacherInfoTbl>>().success(teacherInfoTbls);
	}
	/**
	 * 
	 * 
	 * 方法功能说明:查询单条老师
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/findTeacherInfo", method = RequestMethod.POST)
	public Response<TeacherInfoTbl> findTeacherInfo(@RequestBody TeacherInfoTbl teacherInfoTbl) {
		if (teacherInfoTbl == null || teacherInfoTbl.getId() == null || teacherInfoTbl.getId() <= 0) {
			return new Response<TeacherInfoTbl>().failure("300", "参数有误");
		}
		TeacherInfoTbl teacherInfo = teacherInfoService.findTeacherInfo(teacherInfoTbl.getId());
		if (teacherInfo == null) {
			return new Response<TeacherInfoTbl>().failure("501", "该教师不存在");
		}
		return new Response<TeacherInfoTbl>().success(teacherInfo);
	}

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
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/saveTeacherInfo", method = RequestMethod.POST)
	public Response<String> saveTeacherInfo(@RequestBody TeacherInfoTbl teacherInfoTbl) {
		int row = teacherInfoService.insertTeacherInfo(teacherInfoTbl);
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row), "");
	}

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
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "modifyTeacherInfo", method = RequestMethod.POST)
	public Response<String> modifyTeacherInfo(@RequestBody TeacherInfoTbl teacherInfoTbl) {
		int row = teacherInfoService.updateTeacherInfo(teacherInfoTbl);
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row),"");
	}

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
	 * @param schoolId
	 * @return
	 *
	 */
	@RequestMapping(value = "deleteTeacherInfo", method = RequestMethod.POST)
	public Response<String> deleteTeacherInfo(@RequestBody TeacherInfoTbl teacherInfoTbl) {
		int row = teacherInfoService.deleteTeacherInfo(teacherInfoTbl.getId());
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row),"");
	}
}
