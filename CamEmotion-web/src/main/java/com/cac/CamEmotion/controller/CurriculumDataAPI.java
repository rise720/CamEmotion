package com.cac.CamEmotion.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.jsonModel.CurriculimExtend;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.CurriclumVideoService;
import com.cac.CamEmotion.service.CurriculumService;

@RestController
@RequestMapping("/rest/CurriculumData")
public class CurriculumDataAPI {
//	private static Logger logger = LogManager.getLogger(CurriculumDataAPI.class);

	@Resource
	private CurriclumVideoService CurriculumVideoService;

	@Resource
	private CurriculumService curriculumService;

	/**
	 * 接口说明：返回某节课程的接口
	 * 
	 * @param request
	 * @param curriculumid
	 * @return
	 */
	@RequestMapping(value = "/curriculumVideoList")
	public Response<List<CurriculumVideoTbl>> curriculumVideoList(HttpServletRequest request, @RequestBody CurriculumVideoTbl curriculumVideoTbl) {
		try {
			return new Response<List<CurriculumVideoTbl>>()
					.success(CurriculumVideoService.CurriclumVideoData(curriculumVideoTbl));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new Response<List<CurriculumVideoTbl>>().failure();
		}
	}
	/**
	 * 
	 * 
	 * 方法功能说明:
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年9月14日
	 * @param request
	 * @param pagenumber
	 * @param pagerow
	 * @param curriculumid
	 * @return
	 *
	 */
	@RequestMapping(value = "/getCurriculumList", method = RequestMethod.POST)
	public Response<PageList<CurriculumTbl>> CurriculumAll(HttpServletRequest request, @RequestBody CurriculimExtend pe) {

		try {
			CurriculumTbl curriculumTbl = new CurriculumTbl();
			if (pe.getId() != null && pe.getId().trim().length() > 0 && StringUtils.isNumeric(pe.getId().trim())) {
				curriculumTbl.setId(Integer.parseInt(pe.getId()));
			}

			String searchcontent_sql = "";
			if (pe.getSearchcontent() != null && !pe.getSearchcontent().isEmpty()) {
				if (pe.getSearchcontent().indexOf(" ") != -1) {
					String[] Searchcontents = pe.getSearchcontent().split(" ");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else if (pe.getSearchcontent().indexOf(",") != -1) {
					String[] Searchcontents = pe.getSearchcontent().split(",");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else {
					searchcontent_sql += " AND INSTR(searchcontent,'" + pe.getSearchcontent() + "')>0 ";
				}
			}
			//过滤已经存在的id 不查询出来
			if (pe.getIdList() != null && pe.getIdList().size() > 0) {
				for (Integer id : pe.getIdList()) {
					searchcontent_sql += " AND id !=" + id + " ";
				}
			}

			curriculumTbl.setSearchcontent(searchcontent_sql);
			
			curriculumTbl.setSchoolid(pe.getSchoolid());
			curriculumTbl.setTeacherid(pe.getTeacherId());
			curriculumTbl.setCoursename(pe.getCoursename());
			curriculumTbl.setCoursecontents(pe.getCoursecontents());
			curriculumTbl.setStarttime(pe.getBegintime());
			curriculumTbl.setEndtime(pe.getEndtime());
			
			if ("全部".equals(pe.getGradeNo())) {
				curriculumTbl.setGradeno(null);
			}
			if ("全部".equals(pe.getClassNature())) {
				curriculumTbl.setClassNature(null);
			}
			if ("全部".equals(pe.getClassNo())) {
				curriculumTbl.setClassno(null);
			}
			
			PageList<CurriculumTbl> List = curriculumService.selectAllCurriclum(curriculumTbl, pe.getCurrentPage(),
					pe.getPageRecorders(), "id", "DESC");
			return new Response<PageList<CurriculumTbl>>().success(List);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<PageList<CurriculumTbl>>().failure();
		}
	}
	/**
	 * 
	 * 
	 * 方法功能说明: 查询单堂课程信息
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月12日
	 * @param id
	 * @return
	 *
	 */
	@RequestMapping(value = "/findCurriculumTbl")
	public Response<CurriculumTbl> findCurriculumTbl(Integer id) {
		CurriculumTbl curriculumTbl = curriculumService.selectClassInfo(id);
		return new Response<CurriculumTbl>().success(curriculumTbl);
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 删除课堂信息以及相关信息
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param curriculumTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/deleteCurriculumTbl")
	public Response<String> deleteCurriculumTbl(@RequestBody CurriculumTbl curriculumTbl){
		int row = curriculumService.deleteCurriclum(curriculumTbl.getId());
		if(row > 0)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row));	
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 设置课堂优质类型
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月4日
	 * @param curriculumTbl
	 * @return
	 *
	 */
	@RequestMapping(value = "/setCurriculumLevel")
	public Response<String> setCurriculumLevel(@RequestBody CurriculumTbl curriculumTbl){
		curriculumTbl.setClassstatus(1);
		int row = curriculumService.updateByClassStatus(curriculumTbl);
		if(row > 0)
			return new Response<String>().success(Integer.toString(row));
		return new Response<String>().failure(Integer.toString(row));	
	}
	
	public Response<String> getWholeEvaluate(@RequestBody CurriculumTbl curriculumTbl){
		
		return new Response<String>().success();
	}
}
