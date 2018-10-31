package com.cac.CamEmotion.Camera;

/**
 * 
 * 类功能说明: 摄像头模型
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2017年8月9日
 */
public class Camera {
	private int id;			//唯一编号
	private int name;		//名称
	private String type;	//类型（0：本地	1：网络）
	private String ip;		//网络摄像头的IP地址
	private int num;		//本地摄像头的编号以0,1,2..命名
	
	private int webSocketPort;	// webSocket端口
	
	public Camera(int id){
		this.id = id;
	}
	
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getWebSocketPort() {
		return webSocketPort;
	}

	public void setWebSocketPort(int webSocketPort) {
		this.webSocketPort = webSocketPort;
	}
}
