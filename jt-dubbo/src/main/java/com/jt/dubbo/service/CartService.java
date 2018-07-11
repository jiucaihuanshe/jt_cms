package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface CartService {
	//根据userId查询购物车信息
	List<Cart> findCartByUserId(Long userId);
}
