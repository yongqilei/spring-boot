package com.raja.mq.controller;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.mq.service.ActiveMQService;
import com.raja.mq.service.ProducerService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private ProducerService producerService;
	
	@Autowired
	private ActiveMQService activemqService;
	
	@RequestMapping("/message/{msg}")
	public Object order(@PathVariable("msg") String msg) {
		Destination destination = new ActiveMQQueue("order.queue");
		producerService.sendMessage(destination, msg);
		
		return null;
	}
}
