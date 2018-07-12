package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterInfoCache;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("${ITEMKEY}")
	private String itemKey; //获取数据ITEM_
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 前台项目没有连接数据库,所以查询操作依赖后台.
	 * 
	 */
	@Override
	public Item findItemById(Long itemId) {
		String uri = "http://manage.jt.com/web/item/"+itemId;
		Map<String, String> map = new HashMap<String,String>();
		try {
			//通过后台查询Item对象的JSON数据
			String restJSON = httpClientService.doPost(uri,map);
			
			Item item = objectMapper.readValue(restJSON,Item.class);
			
			return item;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return null;
	}
	
	
	/**
	 * 1.先查询缓存.如果缓存中有数据则先将JSON数据转化为对象之后返回
	 * 2.如果缓存中没有改数据,则先远程调用,之后将数据存入缓存中
	 * @param itemId
	 * @return
	 */
	@Override
	public Item findItemByIdCache(Long itemId) {
		//形成唯一的标识 ITEM_1474391960
		String key = itemKey+itemId;
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
