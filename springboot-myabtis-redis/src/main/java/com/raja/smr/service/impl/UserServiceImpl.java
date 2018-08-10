package com.raja.smr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.raja.smr.entity.User;
import com.raja.smr.mapper.UserMapper;
import com.raja.smr.service.RedisService;
import com.raja.smr.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public List<User> findUsers() {
		return userMapper.selectAllUser();
	}

	@Override
	public User findUserByName(String username) {
//		User user =  (User) redisTemplate.opsForValue().get("user_" + username);
//		if(user == null) {
//			LOGGER.info("----------缓存中无数据，将从数据库中获取！----------");
//			user =  userMapper.selectUserByName(username);
//			redisTemplate.opsForValue().set("user_" + username, user);
//			return user;
//		}
//		LOGGER.info("----------从缓存中获取到数据，没有走数据库！----------");
//		
//		return user;
		User user = redisService.get("user_" + username, User.class);
		if(user == null) {
			LOGGER.info("----------缓存中无数据，将从数据库中获取！----------");
			user = userMapper.selectUserByName(username);
			redisService.set("user_" + username, user, 0);
			return user;
		}
		LOGGER.info("----------从缓存中获取到数据，没有走数据库！----------");
		return user;
	}

}
