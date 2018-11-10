package com.cac.CamEmotion.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cac.CamEmotion.Queue.CurrentQueueFunction;
import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.SocketMessage;
import com.cac.CamEmotion.service.EmotionServerClient;
import com.google.gson.Gson;

@Component
public class MyHandler extends TextWebSocketHandler {
	@Resource
	EmotionServerClient emotionServerClient;
	
	private static Logger logger = LogManager.getLogger(MyHandler.class);
	// 在线用户列表
	private static final Map<Integer, WebSocketSession> users;
	// 用户标识
	private static final String CLIENT_ID = "userId";

	private static Gson gson = new Gson();

	static {
		users = new HashMap<>();
	}

	/**
	 * 与客户端建立连接
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("客户端" + session.getLocalAddress() + "成功与建立连接");
		Integer userId = getClientId(session);
		System.out.println(userId);
		if (userId != null) {
			users.put(userId, session);
			SocketMessage socketMes = new SocketMessage();
			socketMes.setSendCode(1);
			socketMes.setSendMessage("客户端" + session.getLocalAddress() + "成功与服务器建立socket连接");
			session.sendMessage(new TextMessage(gson.toJson(socketMes)));
			System.out.println(userId);
			System.out.println(session);
			CurrentQueueFunction.ClearSocketMessageQueue();
		}
	}

	/**
	 * 接收客户端消息
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		String sendMes = message.getPayload();
		SocketMessage socketMes = new SocketMessage();
		socketMes = gson.fromJson(sendMes, socketMes.getClass());
		System.out.println(socketMes.getSendMessage());
		if(socketMes.getSendCode() == 5)
			Constants.CURR_CAMERAIP = socketMes.getSendMessage();
		
		socketMes.setSendCode(1);
		socketMes.setSendMessage("你好" + session.getLocalAddress() + "客户端,我已经收到你发送的信息");
		WebSocketMessage<?> message1 = new TextMessage(gson.toJson(socketMes));
		try {
			session.sendMessage(message1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送信息给指定用户
	 * 
	 * @param clientId
	 * @param message
	 * @return
	 */
	public boolean sendMessageToUser(Integer clientId, TextMessage message) {
		if (users.get(clientId) == null)
			return false;
		WebSocketSession session = users.get(clientId);
		System.out.println("sendMessage:" + session);
		if (!session.isOpen())
			return false;
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 广播信息
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessageToAllUsers(TextMessage message) {
		boolean allSendSuccess = true;
		Set<Integer> clientIds = users.keySet();
		WebSocketSession session = null;
		for (Integer clientId : clientIds) {
			try {
				session = users.get(clientId);
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
				allSendSuccess = false;
			}
		}

		return allSendSuccess;
	}

	/**
	 * socket连接出错
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("客户端" + session.getLocalAddress() + "连接出错");
		users.remove(getClientId(session));
	}

	/**
	 * socket连接已经关闭
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("客户端" + session.getLocalAddress() + "连接已关闭：" + status);
		users.remove(getClientId(session));
//		emotionServerClient.stop("0");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 服务端主动关闭与客户端的连接
	 * 
	 * @param userId
	 *            客户端标识
	 * @return
	 */
	public boolean CloseClient(Integer userId) {
		try {
			WebSocketSession session = users.get(userId);
			if (session == null) {
				logger.error("没有查询到客户端信息");
				return true;
			}
			if (session.isOpen()) {
				session.close(CloseStatus.NORMAL);
			}
		} catch (IOException e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 关闭所有客户端
	 * @return 
	 */
	public boolean  CloseAllClient(){
		if (users.size() > 0) {
			Set<Integer> clientIdList = users.keySet();
			for (Integer clientId : clientIdList) {
				if (!CloseClient(clientId)) {
					return false;
				}		
			}
		}
		return true;
	}
	
	/**
	 * 获取连接中的客户端个数
	 * 
	 * @return
	 */
	public int GetClientSize() {
		try {
			return users.size();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 查询key是否存在
	 * 
	 * @param userId
	 * @return
	 */
	public boolean GetIsClient(int userId) {
		return users.containsKey(userId);
	}

	/**
	 * 获取用户标识
	 * 
	 * @param session
	 * @return
	 */
	private Integer getClientId(WebSocketSession session) {
		try {
			Object obj = session.getHandshakeAttributes().get(CLIENT_ID);
			Integer clientId = obj == null ? null : Integer.parseInt(obj.toString());
			return clientId;
		} catch (Exception e) {
			return null;
		}
	}

}