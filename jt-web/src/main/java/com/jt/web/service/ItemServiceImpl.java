package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 前台项目没有连接数据库，所以查询操作依赖后台
	 */
	@Override
	public Item findItemById(Long itemId) {
		String uri = "http://manage.jt.com/web/item/"+itemId;
		try {
			String restJson = httpClientService.doGet(uri);
			Item item = objectMapper.readValue(restJson, Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 1.先查询缓存。如果缓存中有数据则先将JSON数据转化为对象之后返回
	 * 2.如果缓存中没有该数据，则先远程调用，之后将数据存入缓存中。
	 */
	public Item findItemByIdCache(Long itemId){
		//形成唯一的标识
		String key = "ITEM_"+itemId;
		String jsonResult = jedisCluster.get(key);
		try {
			if(StringUtils.isEmpty(jsonResult)){
				//进行远程调用
				Item item = findItemById(itemId);
				String jsonData = objectMapper.writeValueAsString(item);
				jedisCluster.set(key, jsonData);
				return item;
			}else{
				Item item = objectMapper.readValue(jsonResult, Item.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
