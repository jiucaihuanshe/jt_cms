package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	//加入required=false
	//说明：如果spring容器中有该对象则注入，如果没有该对象则不管
	@Autowired(required=false)
	private JedisSentinelPool jedisSentinelPool;
	
	public void set(String key,String value){
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.set(key, value);
		jedisSentinelPool.returnResource(jedis);
	}
	
	public String get(String key){
		Jedis jedis = jedisSentinelPool.getResource();
		String value = jedis.get(key);
		jedisSentinelPool.returnResource(jedis);
		return value;
	}
	
	
	
	
//	@Autowired
//	private ShardedJedisPool jedisPool;
	
	/*//分片的set/get方法
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
	}*/
}
