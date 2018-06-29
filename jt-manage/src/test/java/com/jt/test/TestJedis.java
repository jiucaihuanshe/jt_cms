package com.jt.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * 测试redis操作
 * @author Administrator
 *
 */
public class TestJedis {
	@Test
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
}
