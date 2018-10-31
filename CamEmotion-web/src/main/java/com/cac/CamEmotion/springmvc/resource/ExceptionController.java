package com.cac.CamEmotion.springmvc.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.http.HttpInfoUtil;
import com.cac.CamEmotion.springmvc.model.Response;

/**
 * 
 * 类说明: 共通请求错误返回数据定义
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年4月21日
 */
@RestController
@RequestMapping("/api")
public class ExceptionController {
	private static Logger logger = LogManager.getLogger(ExceptionController.class);
	
	/**
	 * 
	 * 方法说明: 用户未登录
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年4月20日
	 * @param request
	 * @param errMsg 异常信息
	 * @return
	 */
	@RequestMapping("/notlogged")
	public Response notLogged(HttpServletRequest request, HttpServletResponse response) {
		logger.warn("用户未登录，访问请求被拒绝!");
		return HttpInfoUtil.send(request, response, "common.message.err_60004", null);
	}
	
	/**
	 * 
	 * 方法说明: 
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @param request
	 * @return
	 */
	@RequestMapping("/nopermission")
	public Response nopermission(HttpServletRequest request, HttpServletResponse response) {
		logger.warn("无权访问，访问请求被拒绝!");
		return HttpInfoUtil.send(request, response, "common.message.err_60005", String.valueOf(request.getAttribute("accessUrl")));
	}
	
	/**
	 * 
	 * 方法说明: 请求参数非法(指sql注入方面)
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @param request
	 * @return
	 */
	@RequestMapping("/antiSQL")
	public Response antiSQL(HttpServletRequest request, HttpServletResponse response) {
		logger.warn("请求参数非法!");
		return HttpInfoUtil.send(request, response, "common.message.err_90011", "请求参数非法!");
	}
	
	/**
	 * 
	 * 方法说明: 404错误
	 * <P>
	 *     容器级404错误，通过此path返回json格式的错误
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年4月21日
	 * @param request
	 * @return
	 */
	@RequestMapping("/error_404")
	public Response error_404(HttpServletRequest request, HttpServletResponse response){
		logger.warn("访问的url不存在，访问请求被拒绝!" );
		response.setStatus(200);	// 重置返回码为200
		return HttpInfoUtil.send(request, response, "common.message.err_404", null);
	}
	
	/**
	 * 
	 * 方法说明: 500错误
	 * <P>
	 *     容器级500错误，通过此path返回json格式的错误
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年4月21日
	 * @param request
	 * @return
	 */
	@RequestMapping("/error_500")
	public Response error_500(HttpServletRequest request, HttpServletResponse response){
		logger.warn("访问的url发生内部服务错误，访问请求被拒绝!");
		response.setStatus(200);	// 重置返回码为200
		return HttpInfoUtil.send(request, response, "common.message.err_500", null);
	}
}
