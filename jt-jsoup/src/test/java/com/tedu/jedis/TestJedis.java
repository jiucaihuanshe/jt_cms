package com.tedu.jedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestJedis {
	@Test	//分片
	public void shard(){
		
		
		//构造各个节点链接信息，host和port
		List<JedisShardInfo> infoList = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("192.168.56.7",7000);
		//info1.setPassword("123456");
		infoList.add(info1);
		JedisShardInfo info2 = new JedisShardInfo("192.168.56.7",7001);
		infoList.add(info2);
		JedisShardInfo info3 = new JedisShardInfo("192.168.56.7",7002);
		infoList.add(info3);
		
		//分片jedis
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(500);	//最大链接数
		
		ShardedJedisPool pool = new ShardedJedisPool(config, infoList);
		//ShardedJedis jedis = new ShardedJedis(infoList);
		ShardedJedis jedis = pool.getResource();	//从pool中获取
		for(int i=0;i<10;i++){
			jedis.set("n"+i, "t"+i);
		}
		System.out.println(jedis.get("n9"));
		jedis.close();
	}
}
