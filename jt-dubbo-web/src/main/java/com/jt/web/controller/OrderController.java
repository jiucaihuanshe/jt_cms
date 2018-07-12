package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.service.CartService;
import com.jt.dubbo.service.OrderService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	//url:/order/create.html
	@RequestMapping("/create")
	public String create(Model model){
		Long userId=UserThreadLocal.getUesr().getId();
		//由于页面中需要进行购物车信息展现，所以需要查询数据
		List<Cart> carts = cartService.findCartByUserId(userId);
		//需要将数据转向到页面中
		model.addAttribute("carts", carts);
		//订单的确认页面
		return "order-cart";
	}
	
	//新增订单
	//url:http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		//为订单补齐数据
		Long userId = UserThreadLocal.getUesr().getId();
		order.setUserId(userId);
		try {
			String orderId = orderService.saveOrder(order);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "新增失败");
		}
	}
	
	/**
	 * 当订单查询成功后，返回orderId，需要根据orderId查询订单
	 * 数据。需要三张表关联查询，将获取的数据存入request域中，
	 * 方便程序调用
	 */
	//url:/order/success.html?id=1231231213  get提交
	@RequestMapping("/success")
	public String findOrderById(@RequestParam("id") String orderId,Model model){
		//通过Dubbo的方式获取数据
		Order order = orderService.findOrderById(orderId);
		model.addAttribute("order", order);
		return "success";
	}
}
