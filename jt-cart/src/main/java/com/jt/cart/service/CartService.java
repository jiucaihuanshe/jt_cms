package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {
	//根据UserId查询购物车信息
	List<Cart> findCartByUserId(Long userId);
	//修改商品数据
	void updateCartNum(Cart cart);
	//删除购物车商品信息
	void deleteCart(Long userId, Long itemId);
	//新增商品
	void saveCart(Cart cart);
}
