package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	@Autowired
	private ShardedJedisPool jedisPool;
	
	//定义set方法
	public void set(String key,String value){
		//通过池获取对象
		ShardedJedis shardedJedis = jedisPool.getResource();
		//通过jedis操作数据
		shardedJedis.set(key, value);
		//将链接还回池中
		jedisPool.returnResource(shardedJedis);
	}
	
	//定义get方法
	public String get(String key){
		ShardedJedis shardedJedis = jedisPool.getResource();
		String value = shardedJedis.get(key);
		jedisPool.returnResource(shardedJedis);
		return value;
	}
}
