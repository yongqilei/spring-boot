package com.raja.smr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.raja.smr.service.RedisService;

import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.JedisCluster;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisCluster jedisCluster;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);
	
	@Override
	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public String set(String key, String value, int seconds) {
		String response = jedisCluster.set(key, value);
		if(seconds != 0) {
			jedisCluster.expire(key, seconds);
		}
		return response;
	}
	
	public String set(String key, Object value, int seconds) {
		String response = jedisCluster.set(key, JSON.toJSONString(value));
		if(seconds > 0) {
			jedisCluster.expire(key, seconds);
		}
		return response;
	}

	@Override
	public String getSet(String key, String value, int seconds) {
		String set = jedisCluster.getSet(key, value);
		if(seconds != 0) {
			jedisCluster.expire(key, seconds);
		}
		return set;
	}

	@Override
	public String get(String key) {
		String result = jedisCluster.get(key);
		return result;
	}
	
	public <T> T get(String key, Class<T> clazz) {
		String result = jedisCluster.get(key);
		T t = JSON.parseObject(result, clazz);
		return t;
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, byte[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean del(String key) {
		LOGGER.info("RedisService:删除缓存数据 key:{" + key + "}");
		return jedisCluster.del(key) > 0;
	}

	@Override
	public boolean lock(String key, int expireSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock(String key) {
		// TODO Auto-generated method stub
		
	}

}
