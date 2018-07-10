package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public String saveOrder(Order order) {
		String uri = "http://order.jt.com/order/create";
		try {
			//将user转化为json数据
			String orderJSON = objectMapper.writeValueAsString(order);
			//准备Map进行数据传输
			Map<String, String> param = new HashMap<>();
			param.put("orderJSON", orderJSON);
			String resultJSON = httpClient.doPost(uri, param);
			//将resultJSON对象转化为SysResult对象
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			//判断远程的返回值是否正确
			if(sysResult.getStatus()==200){
				String orderId=  (String) sysResult.getData();
				return orderId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Order findOrderById(Long orderId) {
		try {
			String uri = "http://order.jt.com/order/query/"+orderId;
			String orderJSON = httpClient.doGet(uri);
			return objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
