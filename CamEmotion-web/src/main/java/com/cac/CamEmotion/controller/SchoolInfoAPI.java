/**
 * 
 */
package com.cac.CamEmotion.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cac.CamEmotion.jsonModel.SchoolExtend;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.SchoolInfoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.SchoolInfoService;
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
@RequestMapping("/rest/SchoolInfoAPI")
public class SchoolInfoAPI {
	private static Logger logger = LogManager.getLogger(SchoolInfoAPI.class);
	
	@Resource
	private SchoolInfoService schoolInfoService;

	/**
	 * 
	 * 
	 * 方法功能说明: 分页查询学校列表
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
	@RequestMapping(value = "findBypagination", method = RequestMethod.POST)
	public Response<PageList<SchoolInfoTbl>> findBypagination(@RequestBody SchoolExtend pe) {
		SchoolInfoTbl schoolInfoTbl = new  SchoolInfoTbl();
		schoolInfoTbl.setSchoolname(pe.getSchoolname());
		PageList<SchoolInfoTbl> schoolInfos = schoolInfoService.findBypagination(
				schoolInfoTbl ,pe.getCurrentPage(), pe.getPageRecorders(),
				"id", "DESC");
		return new Response<PageList<SchoolInfoTbl>>().success(schoolInfos);
	}

	/**
	 * 
	 * 
	 * 方法功能说明:查询所有学校列表
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
	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public Response<List<SchoolInfoTbl>> findAll(@RequestBody(required = false) SchoolInfoTbl schoolInfoTbl) {
		List<SchoolInfoTbl> SchoolInfoTbls = schoolInfoService.findAll(schoolInfoTbl);
		return new Response<List<SchoolInfoTbl>>().success(SchoolInfoTbls);
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 查询单条学校信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月8日
	 * @param schoolId
	 * @return
	 *
	 */
	@RequestMapping(value = "findSchoolInfo", method = RequestMethod.GET)
	public Response<SchoolInfoTbl> findSchoolInfo(Integer schoolId) {
		SchoolInfoTbl SchoolInfoTbl = schoolInfoService.findSchoolInfo(schoolId);
		return new Response<SchoolInfoTbl>().success(SchoolInfoTbl);
	}
	
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
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "saveSchoolInfo", method = RequestMethod.POST)
	public Response<String> saveSchoolInfo(@RequestBody SchoolInfoTbl schoolInfoTbl) {
		int row = schoolInfoService.insertSchoolInfo(schoolInfoTbl);
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row),"");
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 修改学校信息
	 * <P>
	 *     
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月7日
	 * @param schoolInfoTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "modifySchoolInfo", method = RequestMethod.POST)
	public Response<String> modifySchoolInfo(@RequestBody SchoolInfoTbl schoolInfoTbl) {
		int row = schoolInfoService.updateSchoolInfo(schoolInfoTbl);
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row),"");
	}
	
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
	 * @param schoolId
	 * @return
	 *
	 */
	@RequestMapping(value = "deleteSchoolInfo", method = RequestMethod.POST)
	public Response<String> deleteSchoolInfo(@RequestBody SchoolInfoTbl schoolInfoTbl) {
		int row = schoolInfoService.deleteSchoolInfo(schoolInfoTbl.getId());
		if(row == 1)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row),"");
	}
}
