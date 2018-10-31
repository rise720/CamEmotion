package com.cac.CamEmotion.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * 类功能说明: 自定义业务异常类
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年6月14日
 */
public class BIException extends RuntimeException {
	private static final long serialVersionUID = 8987050474057969620L;

	private static Logger logger = LogManager.getLogger(BIException.class);
	
	private String errCode;			// 错误代码 ERR.6xxxx
	private String[] errArgs;		// 错误原因
	
	/**
	 * 默认构造函数
	 * @param cause
	 */
	private BIException(Throwable cause){
		super(cause);
		logger.error("", cause);
	}
	
	/**
	 * 带参默认构造函数
	 * @param errCode 错误代码,错误代码固定为5位,第1位为6
	 * @param errArgs 用于显示错误信息的参数列表
	 */
	public BIException(String errCode){
		this.errCode = errCode;
		
		logger.error("业务异常:" + errCode.toString());
	}
	
	/**
	 * 带参默认构造函数
	 * @param errCode 错误代码,错误代码固定为5位,第1位为6
	 * @param errArgs 用于显示错误信息的参数列表
	 */
	public BIException(String errCode, String... errArgs){
		this.errCode = errCode;
		this.errArgs = errArgs;
		
		logger.error("业务异常:" + errCode.toString());
	}
	
	/**
	 * 带参默认构造函数
	 * @param cause
	 * @param errCode 错误代码,错误代码固定为5位,第1位为6
	 * @param errArgs 用于显示错误信息的参数列表
	 */
	public BIException(Throwable cause, String errCode, String... errArgs){
		super(cause);
		this.errCode = errCode;
		this.errArgs = errArgs;
		
		logger.error("业务异常:" + errCode.toString(), cause);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String[] getErrArgs() {
		return errArgs;
	}

	public void setErrArgs(String[] errArgs) {
		this.errArgs = errArgs;
	}
}
