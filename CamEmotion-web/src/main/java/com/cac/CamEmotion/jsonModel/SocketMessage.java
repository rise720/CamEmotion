package com.cac.CamEmotion.jsonModel;

public class SocketMessage {
	/**
	 * 消息类型
	 * 0、关闭连接消息
	 * 1、回应消息
	 * 2、服务端发送的表情数据
	 */
	private Integer SendCode;

	/**
	 * 消息内容
	 */
	private String SendMessage;

	/**
	 * 时间戳
	 */
	private Long timeStamp;

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getSendCode() {
		return SendCode;
	}

	public void setSendCode(Integer sendCode) {
		SendCode = sendCode;
	}

	public String getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(String sendMessage) {
		SendMessage = sendMessage;
	}
}
