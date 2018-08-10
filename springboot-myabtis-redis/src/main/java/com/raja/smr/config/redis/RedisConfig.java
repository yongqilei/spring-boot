package com.raja.smr.config.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

//	@Value("${spring.redis.host}")
//	private String host;
//	
//	@Value("${spring.redis.port}")
//	private int port;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;
	
	@Value("${spring.redis.jedis.pool.max-wait}")
	private String maxWait;
	
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;
	
	@Value("${spring.redis.timeout}")
	private String timeout;
	
	@Value("${spring.redis.cluster.nodes}")
	private String nodes;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMinIdle(minIdle);
		int l = Integer.parseInt(maxWait.substring(0, maxWait.length() - 2));
		jedisPoolConfig.setMaxWaitMillis(l);
		return jedisPoolConfig;
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
		connectionFactory.afterPropertiesSet();
		
		return connectionFactory;
	}
	
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		Set<RedisNode> nodeSet = new HashSet<>();
		String[] servers = nodes.split(",");
		for(String node : servers) {
			String[] split = node.split(":");
			nodeSet.add(new RedisNode(split[0], Integer.parseInt(split[1])));
		}
		redisClusterConfiguration.setClusterNodes(nodeSet);
		return redisClusterConfiguration;
	}
	
	@Bean
	public JedisCluster jedisCluster(JedisPoolConfig jedisPoolConfig, RedisClusterConfiguration redisClusterConfiguration) {
		Set<HostAndPort> nodeSet = new HashSet<>();
		String[] servers = nodes.split(",");
		for(String node : servers) {
			String[] split = node.split(":");
			nodeSet.add(new HostAndPort(split[0], Integer.parseInt(split[1])));
		}
		JedisCluster jedisCluster = new JedisCluster(nodeSet);
		return jedisCluster;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		RedisSerializer serializer = new StringRedisSerializer();
		
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setHashKeySerializer(serializer);
		
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.afterPropertiesSet();
		
		return redisTemplate;
	}
	
}
