package com.cac.CamEmotion.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cac.CamEmotion.http.I18NMessageUtil;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.service.BaseService;

/**
 * 
 * 
 * 类说明: controller层的顶级抽象类, 实现 CURD 等基本的数据访问方法
 * <P>
 * 1. controller层类如果有后台数据访问, 继承此类
 * </P>
 *
 * @author zhangsh
 * @data 2017年1月18日
 */
public abstract class AbstractBaseAPI<T> {
	private static Logger logger = LogManager.getLogger(AbstractBaseAPI.class);

	protected BaseService<T> baseService;

	public abstract void init();

	/**
	 * 
	 * 
	 * 方法说明: 返回所有数据列表对象
	 * <P>
	 * 1. request的请求方法为get, 方法地址为"/rest/xxx" 2. 暂未未实现分页查询和按条件查询
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<List<T>> getList() {
		logger.info("on call getList()....");
		// TODO 功能未实现
		return new Response<List<T>>().success();
	}

	/**
	 * 
	 * 
	 * 方法说明: 返回指定id的数据对象
	 * <P>
	 * 1. request的请求方法为get, 方法地址为"/rest/xxx/{id}" 2. 请求url中的{id}做为本方法的查询参数
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response<T> get(HttpServletRequest request, @PathVariable Long id) {
		System.out.println(request.getHeader("Origin"));
		logger.info("on call get()...., id = " + id);
		return new Response<T>().success(baseService.selectByPrimaryKey(id));
	}

	/**
	 * 
	 * 
	 * 方法说明: 保存数据
	 * <P>
	 * 1. request的请求方法为post, 方法地址为"/rest/xxx" 2. @RequestBody
	 * 表示从request的body中获取数据,并自动注入到 T 对象 3. @Valid 表示对表单数据进行校验
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<T> post(HttpServletRequest request, @RequestBody @Valid T o) {
		logger.info("on call post()....");
		int result = baseService.insert(o);
		if (result > 0) {
			return new Response<T>().success();
		} else {
			return new Response<T>().failure(I18NMessageUtil.getMessage(request, "err.60030"));
		}
	}

	/**
	 * 
	 * 
	 * 方法说明: 按指定的id更新数据
	 * <P>
	 * 1. request的请求方法为PUT, 方法地址为"/rest/xxx" 2. 请求url中的{id}做为本方法的参数
	 * 3. @RequestBody 表示从request的body中获取数据,并自动注入到 T 对象 4. @Valid 表示对表单数据进行校验
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @param id
	 * @param o
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response<T> put(HttpServletRequest request, @PathVariable Long id, @RequestBody @Valid T o) {
		logger.info("on call put()....");
		int result = baseService.updateByPrimaryKeySelective(o);
		if (result > 0) {
			return new Response<T>().success();
		} else {
			return new Response<T>().failure(I18NMessageUtil.getMessage(request, "err.60031"));
		}
	}

	/**
	 * 
	 * 
	 * 方法说明: 按指定的id删除数据
	 * <P>
	 * 1. request的请求方法为DELETE, 方法地址为"/rest/xxx" 2. 请求url中的{id}做为本方法的参数
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response<T> delete(HttpServletRequest request, @PathVariable Long id) {
		logger.info("on call delete()....");
		int result = baseService.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Response<T>().success();
		} else {
			return new Response<T>().failure(I18NMessageUtil.getMessage(request, "err.60033"));
		}
	}
}
