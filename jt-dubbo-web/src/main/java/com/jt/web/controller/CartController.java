package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.CartService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/show")
	public String findCartById(Model model){
		Long userId = UserThreadLocal.getUesr().getId();
		//通过dubbo的方式访问程序
		List<Cart> cartList = cartService.findCartByUserId(userId);
		
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//url: http://www.jt.com/service/cart/update/num/1474391948/5
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
		Long userId= UserThreadLocal.getUesr().getId();
		//将userId,itemId,num通过rpc传递到购物车后台中
		cartService.updateCartNum(userId,itemId,num);
		/**
		 * 返回空串的原因是因为页面中没有对返回值做校验，
		 * 返回空串只是告诉程序我已经执行完成，没有其他含义
		 */
		return "";
	}
	
	//url:http://www.jt.com/cart/delete/1474391948.html
	//购物车删除
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		Long userId = UserThreadLocal.getUesr().getId();
		//删除购物车数据
		cartService.deleteCart(userId,itemId);
		//页面应该跳转到购物车列表页面
		return "redirect:/cart/show.html";
	}
	
	//新增购物车	/cart/add/1474391948.html
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		Long userId = UserThreadLocal.getUesr().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		//必然会报 如果写成addCart 则会报readOnly	//这个事务控制了
		//应该与声明式事务处理的方法名称一致
		cartService.saveCart(cart);
		//转向到用户购物车页面
		return "redirect:/cart/show.html";
	}
}
