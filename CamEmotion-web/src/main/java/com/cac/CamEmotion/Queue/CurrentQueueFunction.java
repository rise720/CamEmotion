package com.cac.CamEmotion.Queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.SocketMessage;

public class CurrentQueueFunction {
	private static Logger logger = LogManager.getLogger(CurrentQueueFunction.class);

	private static ConcurrentLinkedQueue<SocketMessage> linkedTransferQueue = new ConcurrentLinkedQueue<SocketMessage>();


	/**
	 * 添加到队列
	 * 
	 * @param socketMessage
	 */
	public static void SetSocketMessageQueue(SocketMessage v) {
		try {
			linkedTransferQueue.add(v);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 从队列获取第一个帧，并删除
	 * 
	 * @return
	 */
	public static SocketMessage GetSocketMessageQueue() {
		try {
			if (linkedTransferQueue.size() > 0) {
				SocketMessage v = linkedTransferQueue.peek();
				if(v != null && (System.currentTimeMillis() - v.getTimeStamp()) > Constants.SOCKET_QUEUE_TIME * 1000) {
					v = linkedTransferQueue.poll();
					return v;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 清空队列
	 */
	public static void ClearSocketMessageQueue() {
		try {
			if (linkedTransferQueue.size() > 0) {
				linkedTransferQueue.clear();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
