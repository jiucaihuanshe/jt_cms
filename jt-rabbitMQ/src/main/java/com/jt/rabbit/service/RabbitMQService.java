package com.jt.rabbit.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.rabbit.mapper.OrderItemMapper;
import com.jt.rabbit.mapper.OrderMapper;
import com.jt.rabbit.mapper.OrderShippingMapper;

public class RabbitMQService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	/**
	 * 消息队列的实现
	 * 1.返回值类型String 返回orderId
	 */
	public void saveOrder(Order order) {
		//获取orderId
		String orderId = order.getOrderId();
		//封装order对象
		order.setStatus(1);	//状态为1表示 未支付
		order.setCreated(new Date());
		order.setUpdated(order.getCreated());
		orderMapper.insert(order);
		
		//封装orderItem对象
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem orderItem : orderItems){
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		
		//封装orderShipping对象
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(order.getCreated());
		orderShipping.setUpdated(order.getCreated());
		orderShippingMapper.insert(orderShipping);
		
		System.out.println("消息队列执行成功！");
	}
}
