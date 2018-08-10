package com.raja.mq.config.jms;

import javax.jms.ConnectionFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.activemq.web.AjaxServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
@EnableJms
public class JmsConfig {

	@Bean("jmsQueueListenerContainerFactory")
	public JmsListenerContainerFactory jmsQueueListenerContainerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		// 设置连接数
		factory.setConcurrency("3-10");
		// 重连间隔时间
		factory.setRecoveryInterval(1000L);
		factory.setPubSubDomain(false);
		
		return factory;
	}
	
	@Bean("jmsNormalTopicListenerContainerFactory")
	public JmsListenerContainerFactory jmsNormalTopicListenerContainerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		//重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(true);
        return factory;
	}
	
	@Bean("jmsPersistentListenerContainerFactory")
	public JmsListenerContainerFactory jmsPersistentListenerContainerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		//重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(true);
        
        // 给订阅者一个名字，并开启持久化订阅
        factory.setClientId("client_id");
        factory.setSubscriptionDurable(true);
        
        return factory;
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new AjaxServlet());
		registration.setEnabled(true);
		registration.addUrlMappings("/amq/*");
		return registration;
	}
	
	public ServletContextInitializer servletContextInitializer() {
		return new ServletContextInitializer() {
			
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				// TODO Auto-generated method stub
				servletContext.setInitParameter("org.apache.activemq.brokenURL", "tcp://192.168.60.128:61616");
			}
		};
	}
}
