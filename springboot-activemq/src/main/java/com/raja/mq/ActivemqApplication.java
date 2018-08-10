package com.raja.mq;

import javax.jms.Queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ActivemqApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ActivemqApplication.class, args);
	}
}
