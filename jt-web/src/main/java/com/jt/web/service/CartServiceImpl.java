package com.jt.web.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		String uri = "http://cart.jt.com/cart/query/"+userId;
		try {
			String resultJSON = httpClient.doGet(uri);
			/**
			 * 数据data的数据类型是Object，需要转化为List集合，
			 * 但是objectMapper.readValue(resultJSON,SysReslut.class)转化会失败
			 * 通过objectMapper直接获取json的属性 
			 * 要求：JSON串必须是对象的JSON	{id:1,name:tom}
			 * 而不能是List/Array的JSON	[value1,value2]
			 */
			//{id:1,name:tom}
			JsonNode jsonNode = objectMapper.readTree(resultJSON);
			//将JSON数据转化为String类型的字符串[{},{},{}]
			String cartListJSON = jsonNode.get("data").asText();
			//将cartList的JSON数据转化为List集合
			Cart[] carts = objectMapper.readValue(cartListJSON, Cart[].class);
			List<Cart> cartList= Arrays.asList(carts);
			return cartList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateCartNum(Cart cart) {
		//定义http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
		String uri = "http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
		try {
			httpClient.doGet(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		//定义uri的路径拼接 http://cart.jt.com/cart/delete/2/6
		String uri = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		try {
			httpClient.doGet(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCart(Cart cart) {
		String uri="http://cart.jt.com/cart/save";
		//使用Map封装数据
		Map<String, String> map = new HashMap<>();
		map.put("userId", cart.getUserId()+"");
		map.put("itemId", cart.getItemId()+"");
		map.put("itemTitle", cart.getItemTitle());
		map.put("itemImage", cart.getItemImage());
		map.put("itemPrice", cart.getItemPrice()+"");
		map.put("num", cart.getNum()+"");
		try {
			httpClient.doPost(uri,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
