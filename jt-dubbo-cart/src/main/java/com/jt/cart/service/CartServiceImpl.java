package com.jt.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.cart.mapper.CartMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.CartService;

public class CartServiceImpl implements CartService {
	@Autowired
	private CartMapper cartMapper;
	//根据Dubbo接口实现查询方法
	//如果需要对象进行远程传输，必须实现序列化
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}

}
