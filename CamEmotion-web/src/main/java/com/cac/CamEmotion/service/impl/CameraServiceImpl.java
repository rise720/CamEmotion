package com.cac.CamEmotion.service.impl;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.service.CameraService;

/**
 * 
 * 类功能说明: 摄像头服务
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2018年1月30日
 */
@Service
public class CameraServiceImpl implements CameraService{
	private FFmpegFrameGrabber grabber;
	
	@Override
	public boolean isConnect(String ip){
		boolean isConnect = true;
		try{
			grabber = FFmpegFrameGrabber.createDefault(ip);
			grabber.setOption("rtsp_transport", "tcp");
			grabber.start();
			if(grabber.grabFrame() == null)
				isConnect = false;
			else
				isConnect = true;
			grabber.stop();
		}catch(org.bytedeco.javacv.FrameGrabber.Exception e){
			isConnect = false;
		}
		return isConnect;
	}
}
