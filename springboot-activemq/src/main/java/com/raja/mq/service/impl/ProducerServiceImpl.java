package com.raja.mq.service.impl;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.raja.mq.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	public void sendMessage(Destination destination, String message) {
		jmsTemplate.convertAndSend(destination, message);
	}
	
	@Override
	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(message);
	}

}
