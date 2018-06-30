package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	// 定义格式转化工具
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private RedisService redisService;
	
	//通过单节点实现缓存操作,但是不实用
	//@Autowired
	//private Jedis jedis;

	@Override
	public List<ItemCat> findItemCat() {
		// 调用通用Mapper的方法
		// 如果查询全部数据则不需要设定参数直接为null
		return itemCatMapper.select(null);
	}
	
	/**
	 * 将商品分类信息插入到缓存中 思路： redis中通过key-value 
	 * 1.当用户查询数据时首先应该从缓存中获取数据
	 * 2.如果数据不为null,则代表缓存中有该数据,数据类型为JSON 
	 * 	a.需要将JSON串转化为对象后返回给用户
	 * 3.如果数据为null,表示缓存中没有该数据,则需要连接数据库进行查询. 
	 * 	a.将查询到的数据,转化为JSON存入redis中,方便下次使用.
	 */
	@Override
	public List<ItemCat> findItemCatByParentId(Long parentId) {
		// 1.定义查询的key值
		String key = "ITEM_CAT_" + parentId;
		// 2.根据key值查询缓存数据
		String dataJSON = redisService.get(key);
		// 最后定义公用的List集合
		List<ItemCat> itemCatList = new ArrayList<>();
		try {
			// 3.判断返回值是否含有数据
			if (StringUtils.isEmpty(dataJSON)) {
				// 证明缓存中没有数据,则通过数据查询数据
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId); // 设置父级id
				itemCat.setStatus(1); // 设置为正常的数据1
				itemCatList = itemCatMapper.select(itemCat);
				// 将返回数据转化为JSON串 [{},{},{}]
				String jsonResult = objectMapper.writeValueAsString(itemCatList);
				// 将数据存入缓存中
				redisService.set(key, jsonResult);
			} else {
				// 表示数据不为空 需要将JSON串转化为List<ItemCat>集合对象
				// [{},{},{}] {id:1,name:"tom"}
				ItemCat[] itemCats = objectMapper.readValue(dataJSON, ItemCat[].class);
				for (ItemCat itemCat : itemCats) {
					// 向集合中赋值
					itemCatList.add(itemCat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatList;
	}
}
		/*@Override
		public List<ItemCat> findItemCatByParentId(Long parentId) {
			// 证明缓存中没有数据,则通过数据查询数据
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId); // 设置父级id
			itemCat.setStatus(1); // 设置为正常的数据1
			List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
			return itemCatList;
		 */
		/**
		 * 通用Mapper中带参数的查询 如果需要带参数查询，只需要将参数set到具体的对象中即可。 实现思路：
		 * 通用Mapper会将对象中不为null的数据当做where条件进行查询 lg:select id,parent_id... from
		 * tb_item_cat where parent_id = 0 and status=1
		 */

		// 测试缓存是否成功
		// System.out.println(jedis.get("tomcat"));
	

