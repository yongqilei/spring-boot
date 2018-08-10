package com.raja.mq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

	@JmsListener(destination = "order.queue")
	public void receiveQueue(String text) {
		System.out.println("OrderConsumer接收到的消息：" + text);
	}
	
}
