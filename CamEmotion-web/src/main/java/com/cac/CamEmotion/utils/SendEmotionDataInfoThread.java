package com.cac.CamEmotion.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;

import com.cac.CamEmotion.Queue.CurrentQueueFunction;
import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.controller.MyHandler;
import com.cac.CamEmotion.jsonModel.FrameInfo;
import com.cac.CamEmotion.jsonModel.SocketMessage;
import com.cac.CamEmotion.service.EmotionServerClient;
import com.google.gson.Gson;

public class SendEmotionDataInfoThread extends Thread {
	private static Logger logger = LogManager.getLogger(SendEmotionDataInfoThread.class);

	MyHandler handler;
	
	private boolean sendStatus = true;
	
	EmotionServerClient emotionServerClient;
	
	private Map<String, Long> camTimeMap = new HashMap<>();
	private Gson gson = new Gson();
	
	public SendEmotionDataInfoThread(MyHandler handler, EmotionServerClient emotionServerClient) {
		this.handler = handler;
		this.emotionServerClient = emotionServerClient;
	}

	@Override
	public void run() {
		while (sendStatus) {
				try {
					if (handler.GetClientSize() <= 0) {
						Thread.sleep(1 * 1000);
						continue;
					}
					
					
					Thread.sleep(1 * 100);
					Map<String, String> mapFrameData = emotionServerClient.GetFrameData();
					for (Map.Entry<String, String> frameData : mapFrameData.entrySet()) {
						
						String key = frameData.getKey(); 
						String data = frameData.getValue();
						if(Constants.CURR_CAMERAIP.equals(key)){
							
							Long time = camTimeMap.get(key);
							if (StringUtils.isEmpty(data) || data.equals("null")) {
								continue;
							}
							
							FrameInfo frameInfo = new FrameInfo();
							frameInfo = gson.fromJson(data, frameInfo.getClass());
							if (time != null && time > 0) {
								if (frameInfo.getFrameId() <= time) {
									continue;
								}
							}
							frameInfo.setCourseId(Constants.LISTCAMERAREADYMODEL.get(0).getCurriculumId());
							frameInfo.setCameraIp(key);
							List<Integer> pixelList = emotionServerClient.GetPixelMap(key);
							frameInfo.setwPixel(pixelList.get(0));
							frameInfo.sethPixel(pixelList.get(1));
							
							camTimeMap.put(key, frameInfo.getFrameId());
							data = gson.toJson(frameInfo);
							SocketMessage socketMes = new SocketMessage();
							socketMes.setSendCode(2);
							socketMes.setSendMessage(data);
							socketMes.setTimeStamp(new Date().getTime());
//							CurrentQueueFunction.SetSocketMessageQueue(socketMes);
							handler.sendMessageToAllUsers(new TextMessage(gson.toJson(socketMes)));
						}
					}
//					SocketMessage socketMes = new SocketMessage();
//					socketMes = CurrentQueueFunction.GetSocketMessageQueue();
//					
//					if (socketMes != null) {
//						handler.sendMessageToAllUsers(new TextMessage(gson.toJson(socketMes)));
//					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error(e);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			
		}
	}

}
