/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.util.List;

import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.model.FaceContrastConfigExtend;
import com.cac.CamEmotion.model.FaceIdentificationConfigExtend;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author Chenyang
 * @data 2017年10月13日
 */
public class MemcacheStatus{

	private String hostName;
	private String hostIp;
	
	private int processType;//区别进程类型 0摄像头 1特侦提取进程 2对比进程
	
	private int status;
	
	private List<String> logList;
	
	private String datetime;
	
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public List<String> getLogList() {
		return logList;
	}

	public void setLogList(List<String> logList) {
		this.logList = logList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}
	
	/**
	 * 获取类型
	 * @param obj 配置类
	 * @return
	 */
	public int GetProcessType(Object obj){
		if (obj instanceof FaceAnalysisConfigExtend) {
			return 0;
		} else if (obj instanceof FaceIdentificationConfigExtend) {
			return 1;
		} else if (obj instanceof FaceContrastConfigExtend) {
			return 2;
		} else {
			return -1;
		}
	}

}
