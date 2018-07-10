package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	//http://order.jt.com/order/create
	//添加订单信息，返回orderId
	
	/**
	 * 对象与JSON转化的说明：
	 * 1.对象如果需要进行远程传输，需要转化为JSON串
	 * 2.如果在方法内部使用，最好转化为对象，转为对象后方便获取数据
	 * 总结：传递时使用JSON，使用时转为对象
	 * @param orderJSON
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON){
		try {
			//将JSON串转化为对象
			Order order = objectMapper.readValue(orderJSON, Order.class);
			String orderId = orderService.saveOrder(order);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "新增失败");
		}
	}
	
	//url:"http://order.jt.com/order/query/"+orderId;
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId){
		return orderService.findOrderById(orderId);
	}
}
