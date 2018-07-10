package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	/**
	 * 1.转向订单确认页面
	 * 2.url /order/create.html
	 */
	@RequestMapping("/create")
	public String create(Model model){
		//根据当前的用户信息，获取当前的购物车信息
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> carts = cartService.findCartByUserId(userId);
		model.addAttribute("carts", carts);
		//转向订单确认页面
		return "order-cart";
	}
	
	//http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		try {
			//获取userId
			Long userId = UserThreadLocal.getUser().getId();
			order.setUserId(userId);
			String orderId = orderService.saveOrder(order);
			//如果程序执行一切正确，返回的是orderId,如果执行有问题，返回null
			if(!StringUtils.isEmpty(orderId)){
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();//给程序员看的
		}
		return SysResult.build(201, "新增失败");
	}
	
	//url: /order/success.html
	@RequestMapping("/success")
	public String success(@RequestParam("id") Long orderId,Model model){
		Order order = orderService.findOrderById(orderId);
		model.addAttribute("order", order);
		//转向成功页面
		return "success";
	}
}
