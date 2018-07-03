package com.jt.test;

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

/**
 * 测试redis操作
 * @author Administrator
 *
 */
public class TestJedis {
	public  void test01() {
		/**
		 * 创建Jedis工具类
		 * 参数说明：
		 * 	host：表示redis的主机ip
		 * 	port：表示redis的端口
		 */
		Jedis jedis = new Jedis("192.168.56.132", 6379);
		
		//向redis中插入数据
		jedis.set("tomcat", "tomcat猫");
		System.out.println(jedis.get("tomcat"));
	}
	
	/**
	 * 参数说明：
	 * 	1.poolConfig	设置redis服务的配置工具类(例如设定最大连接数,最小空间数量)
	 * 	2.shards	表示包含redis的节点的配置项List集合
	 * 思路：
	 * 	现在需要同时管理3台redis节点。首先应该定义redis的池。
	 *  在池中包含了很多redis的节点信息。如果需要操作redis则先从池中获取
	 *  某个节点的链接/通过该链接实行数据的新增和查询。
	 *  
	 *  1.定义连接池时需要设定池的大小.(因为有默认值)
	 *  2.需要管理哪些节点信息
	 */
	//测试分片技术
	public void test02(){
		//JedisPoolConfig
		//定义redis的配置	PoolConfig是过期类型
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);//表示最大连接数
		poolConfig.setMinIdle(5);//表示最小空闲数据
		//定义redis多个节点信息
		List<JedisShardInfo> list = new ArrayList<>();
		//为集合添加参数
		list.add(new JedisShardInfo("192.168.56.132", 6379));
		list.add(new JedisShardInfo("192.168.56.132", 6380));
		list.add(new JedisShardInfo("192.168.56.132", 6381));
		//定义redis分片连接池
		ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, list);
		//获取连接操作redis
		ShardedJedis shardedJedis = jedisPool.getResource();
		shardedJedis.set("tom", "tomcat猫");//表示数据的赋值
		System.out.println(shardedJedis.get("tom"));//表示从redis中获取数据
	}
	//多台哨兵redis测试
	public void test03(){
		//2.定义哨兵set集合
		Set<String> sets = new HashSet<>();
		//3.向哨兵中加入哨兵节点
		sets.add(new HostAndPort("192.168.56.132", 26379).toString());
		sets.add(new HostAndPort("192.168.56.132", 26380).toString());
		sets.add(new HostAndPort("192.168.56.132", 26381).toString());
		//1.定义哨兵连接池参数，编辑哨兵名称
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sets);
		//4.插入数据
		Jedis jedis = sentinelPool.getResource();
		jedis.set("1804", "多台哨兵测试");
		System.out.println(jedis.get("1804"));
	}
}
