package com.cac.CamEmotion.springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cac.CamEmotion.exception.BIException;
import com.cac.CamEmotion.exception.SysException;
import com.cac.CamEmotion.http.HttpInfoUtil;
import com.cac.CamEmotion.springmvc.model.Response;

/**
 * 
 * 
 * 类说明: 统一异常处理
 * <P>
 *    1. 常见异常的处理, 并进行统一封装处理,返回为Response的json的数据
 *    2. 异常方法从上至下进行匹配, 找到匹配方法之后,忽略后续的异常定义
 * </P>
 *
 * @author zhangsh
 * @data 2017年1月17日
 */
@ControllerAdvice // 此注解的类,spring mvc 自动扫描带有@ExceptionHandler的方法, 并将这些方法设置为全局异常处理
@ResponseBody	// 表示该类的所有方法表示返回值序列化为JSON或XML字符串
public class ExceptionAdvice {
	private static Logger logger = LogManager.getLogger(ExceptionAdvice.class);
	
	/**
	 * 
	 * 
	 * 方法说明: 400 错误.参数解析失败
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param req
	 * @param e
	 * @return 
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Response handleHttpMessageNotReadableException(
			HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e){
		logger.error("参数解析失败", e);
		return HttpInfoUtil.send(request, response, "参数解析失败", null);
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 400 错误.表单校验失败
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月18日
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response handleValidationException(
			HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e){
		logger.error("表单数据校验不通过", e);
		return HttpInfoUtil.send(request, response, "表单数据校验不通过", null);
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 405 错误
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response handleHttpRequestMethodNotSupportedException(
			HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e){
		logger.error("不支持当前请求方法", e);
		return HttpInfoUtil.send(request, response, "不支持当前请求方法", null);
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 400错误.发生系统级异常的统一拦截处理
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(SysException.class)
	public Response handleSysException(HttpServletRequest request, HttpServletResponse response, SysException e){
		logger.warn("发生系统级异常!", e);
		return HttpInfoUtil.send(request, response, e.getErrCode(), e.getErrArgs().toString());
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 400 错误.发生业务级异常的统一拦截处理
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BIException.class)
	public Response handleBIException(HttpServletRequest request, HttpServletResponse response, BIException e){
		return HttpInfoUtil.send(request, response, e.getErrCode(), e.getErrArgs().toString());
	}
	
	/**
	 * 
	 * 
	 * 方法说明: 400 错误.未定义异常
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年1月17日
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Exception.class)
	public Response handleException(HttpServletRequest request, HttpServletResponse response, Exception e){
		logger.warn("发生异常!", e);
		return HttpInfoUtil.send(request, response, "发生异常!", null);
	}
}
