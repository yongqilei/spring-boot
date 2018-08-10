package com.raja.mq.service;

import javax.jms.Destination;


public interface ProducerService {

	public void sendMessage(Destination destination, String message);
	
	/**
	 * 向默认消息队列发送消息
	 * @param message
	 */
	public void sendMessage(String message);
}
