package com.cac.CamEmotion.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.jsonModel.CurriculimEvaluateExtend;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.CurriculumEvaluateTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.CurriculumEvaluateService;

@RestController
@RequestMapping("/rest/CurriculumEvaluate")
public class CurriculumEvaluateAPI {
	private static Logger logger = LogManager.getLogger(CurriculumEvaluateAPI.class);

	@Resource
	CurriculumEvaluateService currEvaService;

	/**
	 * 操作一条记录
	 * 
	 * @param request
	 * @param pe
	 * @return
	 */
	@RequestMapping(value = "/Save", method = RequestMethod.POST)
	public Response<String> Save(HttpServletRequest request, @RequestBody CurriculumEvaluateTbl record) {
		try {
			String result = CheckParam(record);
			if (result.length() > 0) {
				logger.error(result);
				return new Response<String>().failure(result);
			}
			if (record.getId() == null || record.getId() <= 0) {
				return Insert(record);
			} else {
				return Update(record);
			}
		} catch (Exception e) {
			logger.error(e);
			return new Response<String>().failure(e.getMessage());
		}

	}

	/**
	 * 根据Id删除一条记录
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/DeleteId", method = RequestMethod.POST)
	public Response<String> DeleteId(HttpServletRequest request, @RequestBody CurriculumEvaluateTbl record) {
		try {
			if (record == null || record.getId() == null || record.getId() <= 0) {
				return new Response<String>().failure("参数有误");
			}
			int result = currEvaService.DeleteId(record.getId());
			if (result > 0) {
				return new Response<String>().success("成功删除" + result + "条数据");
			} else {
				return new Response<String>().failure("没有找到该数据");
			}
		} catch (Exception e) {
			logger.error(e);
			return new Response<String>().failure(e.getMessage());
		}
	}

	/**
	 * 根据课程Id删除记录
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/DeleteCurricId", method = RequestMethod.POST)
	public Response<String> DeleteCurricId(HttpServletRequest request, @RequestBody CurriculumEvaluateTbl record) {
		try {
			if (record == null || record.getCurriculumid() == null || record.getCurriculumid() <= 0) {
				return new Response<String>().failure("参数有误");
			}
			int result = currEvaService.DeleteCurricId(record.getCurriculumid());
			if (result > 0) {
				return new Response<String>().success("成功删除" + result + "条数据");
			} else {
				return new Response<String>().failure("没有找到该数据");
			}
		} catch (Exception e) {
			logger.error(e);
			return new Response<String>().failure(e.getMessage());
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/GetList", method = RequestMethod.POST)
	public Response<PageList<CurriculumEvaluateTbl>> GetList(HttpServletRequest request,
			@RequestBody CurriculimEvaluateExtend pe) {
		try {
			CurriculumEvaluateTbl curriculumTbl = new CurriculumEvaluateTbl();
			if (pe.getCurriculumid() != null && pe.getCurriculumid() > 0) {
				curriculumTbl.setCurriculumid(pe.getCurriculumid());
			}
			if (pe.getId() != null && pe.getId() > 0) {
				curriculumTbl.setId(pe.getId());
			}
			PageList<CurriculumEvaluateTbl> list = currEvaService.GetList(curriculumTbl, pe.getCurrentPage(),
					pe.getPageRecorders(), "id", "DESC");
			return new Response<PageList<CurriculumEvaluateTbl>>().success(list);
		} catch (Exception e) {
			logger.error(e);
			return new Response<PageList<CurriculumEvaluateTbl>>().failure();
		}
	}

	/**
	 * 根据id查询
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/GetModel", method = RequestMethod.POST)
	public Response<CurriculumEvaluateTbl> GetModel(@RequestBody CurriculumEvaluateTbl record) {
		try {
			if (record == null || record.getId() == null || record.getId() <= 0) {
				return new Response<CurriculumEvaluateTbl>().failure("参数有误");
			}
			CurriculumEvaluateTbl model = currEvaService.SelectByPrimaryKey(record.getId());
			return new Response<CurriculumEvaluateTbl>().success(model);
		} catch (Exception e) {
			logger.error(e);
			return new Response<CurriculumEvaluateTbl>().failure(e.getMessage());
		}
	}

	/**
	 * 保存参数检查
	 * 
	 * @param record
	 * @return
	 */
	private String CheckParam(CurriculumEvaluateTbl record) {
		if (record == null) {
			return "参数有误";
		}
		if (record.getCurriculumid() == null || record.getCurriculumid() <= 0) {
			return "课程Id有误";
		}
		if (record.getEvaluatorinfo() == null) {
			return "请填写评价人基本信息";
		}
		int infoLength = (record.getEvaluatorinfo() + "").length();
		if (infoLength <= 0) {
			return "请填写评价人基本信息";
		}
		if (infoLength > 80) {
			return "评价人基本信息过长";
		}
		if (record.getEvaluatecontent() != null) {
			infoLength = (record.getEvaluatecontent() + "").length();
			if (infoLength > 520) {
				return "简评过长";
			}
			infoLength = (record.getFileaddr() + "").length();
			if (infoLength > 520) {
				return "文件地址过长";
			}
		}
		if (record.getFileaddr() != null) {
			infoLength = (record.getFileaddr() + "").length();
			if (infoLength > 520) {
				return "文件地址过长";
			}
			return infoLength <= 0 ? "" : (record.getFiletype() < 0 ? "文件类型有误" : "");
		}
		return "";
	}

	/**
	 * 新增一条数据
	 * 
	 * @param record
	 * @return
	 */
	private Response<String> Insert(CurriculumEvaluateTbl record) {
		int result = currEvaService.insert(record);
		if (result > 0) {
			return new Response<String>().success("添加成功");
		} else {
			return new Response<String>().failure("添加失败");
		}

	}

	/**
	 * 修改一条数据
	 * 
	 * @param record
	 * @return
	 */
	private Response<String> Update(CurriculumEvaluateTbl record) {
		int result = currEvaService.Update(record);
		if (result > 0) {
			return new Response<String>().success("成功修改" + result + "条数据");
		} else {
			return new Response<String>().failure("没有找到该数据");
		}
	}

}