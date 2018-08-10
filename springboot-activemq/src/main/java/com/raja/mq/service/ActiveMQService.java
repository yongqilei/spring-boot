package com.raja.mq.service;

import javax.jms.Destination;

public interface ActiveMQService {

	
	/**
	 * 指定消息队列
	 * @param destination
	 * @param message
	 */
	public void sendMessage(String topic, final String message);
	
	
	
}
