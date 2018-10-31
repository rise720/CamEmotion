package com.cac.CamEmotion.service;

import com.cac.CamEmotion.model.CameraReadyModel;

/**
 * 检查所有机器是否准备完毕类
 * @author zhangfei
 *
 */
public interface CameraReadyCheckService {

	/**
	 * 添加课堂对应的机器信息
	 * @param model
	 */
	public void PushCameraReadyModel(CameraReadyModel model);

	/**
	 * 检测机器是否准备完毕
	 */
	public void CheckCameraReady();
}
