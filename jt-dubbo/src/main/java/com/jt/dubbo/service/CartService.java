package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface CartService {
	//根据userId查询购物车信息
	List<Cart> findCartByUserId(Long userId);
	//修改购物车数量
	void updateCartNum(Long userId, Long itemId, Integer num);
	//删除购物车信息
	void deleteCart(Long userId, Long itemId);
	//添加到购物车
	void saveCart(Cart cart);
}
