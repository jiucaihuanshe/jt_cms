package com.jt.dubbo.service;

import com.jt.dubbo.pojo.Order;

public interface OrderService {
	//新增订单
	String saveOrder(Order order);
	//根据orderId查询订单数据
	Order findOrderById(String orderId);

}
