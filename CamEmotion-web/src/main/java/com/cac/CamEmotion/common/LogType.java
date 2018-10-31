package com.cac.CamEmotion.common;

/**
 * 
 * 类说明: 操作类型
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月18日
 */
public enum LogType {
	SYSLOG((short)0, "系统日志"), 
	OPELOG((short)1, "操作日志");

	private short type;	// 操作日志类型
	private String description;	// 描述

	private LogType(short type, String description) {
		this.type = type;
		this.description = description;
	}

	public short value(){
		return type;
	}
	
 	public String GetDescription() {
		return description;
	}
}
