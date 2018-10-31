package com.cac.CamEmotion.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cac.CamEmotion.service.EmotionServerClient;
import com.cac.CamEmotion.utils.SendEmotionDataInfoThread;

@Controller
public class SocketController {

	@Resource
	MyHandler handler;
	
	@Resource
	EmotionServerClient emotionServerClient;
	

	@PostConstruct
	private void init() {
		SendEmotionDataInfoThread sendEmotionDataInfoThread = new SendEmotionDataInfoThread(handler, emotionServerClient);
		sendEmotionDataInfoThread.start();
	}

	/**
	 * 发送直播命令
	 * @param session
	 * @param userId
	 * @return 0成功 1正在上课不许直播 2发送命令失败 3socket已经存在
	 */
	@RequestMapping("/LiveStart/{userId}")
	public @ResponseBody String LiveStart(HttpSession session, @PathVariable("userId") Integer userId) {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
		System.out.println("登录接口,userId=" + userId);
		session.setAttribute("userId", userId);
		System.out.println(session.getAttribute("userId"));
		boolean result = handler.GetIsClient(userId);
		if (result) {
			return "3";
		}
		int res = emotionServerClient.start();
		return res + "";
	}
	
	/**
	 * 关闭socket连接
	 * @param session
	 * @param userId
	 * @return 0成功 1没有获取到客户端标识  2关闭socket失败 3发送下课命令失败
	 */
	@RequestMapping("/LiveClose")
	public @ResponseBody String LiveClose(HttpSession session) {
		Integer userId = Integer.parseInt(session.getAttribute("userId") + "");
		if (userId == null || userId <= 0) {
			return "1";
		}
		System.out.println("客户端" + userId + "，请求关闭socket");
		boolean result = handler.CloseClient(userId);
		if (!result) {
			return "2";
		}
//		result = emotionServerClient.stop("0");
//		if (!result) {
//			return "3";
//		}
		return "0";
	}
	
}