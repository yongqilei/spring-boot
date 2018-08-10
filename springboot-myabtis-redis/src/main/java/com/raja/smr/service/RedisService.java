package com.raja.smr.service;

import java.util.List;

import redis.clients.jedis.GeoRadiusResponse;

public interface RedisService {

	boolean exists(String key);
	
	String set(String key, String value, int seconds);
	
	String set(String key, Object value, int seconds);
	
	String getSet(String key, String value, int seconds);
	
	String get(String key);
	
	<T> T get(String key, Class<T> clazz);
	
	Long geoadd(String key, double longitude, double latitude, byte[] obj);
	
	List<GeoRadiusResponse> georadius(String key, double longitude, double latitude);
	
	boolean del(String key);
	
	boolean lock(String key, int expireSeconds);
	
	void unlock(String key);
}
