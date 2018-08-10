package com.raja.mq.service.impl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.raja.mq.service.ActiveMQService;

@Service
public class ActiveMQServiceImpl implements ActiveMQService {

	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	@Value("${spring.activemq.broker-url}")
	private String brokenUrl;
	
	@Value("${spring.activemq.user}")
	private String user;
	
	@Value("${spring.activemq.password}")
	private String password;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	/**
	 * Topic形式发送消息
	 */
	@Override
	public void sendMessage(String topic, String message) {
		Connection connection = null;
		Session session;
		Destination destination;
//		jmsTemplate.convertAndSend(destination, message);
		MessageProducer producer;
		try{
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic(topic);
			// 得到消息生成者
			producer = session.createProducer(destination);
			// 设置不持久化，实际根据项目决定
//			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage textMsg = session.createTextMessage();
			textMsg.setText(message);
			producer.send(destination, textMsg);
			session.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != connection) {
					connection.close();
				}
			} catch(Throwable ignore) {
				ignore.printStackTrace();
			}
		}
	}


}
