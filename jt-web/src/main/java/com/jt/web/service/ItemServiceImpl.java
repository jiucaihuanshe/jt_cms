package com.jt.web.service;

import org.springframework.stereotype.Service;

import com.jt.web.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService{

	/**
	 * 前台项目没有连接数据库，所以查询操作依赖后台
	 */
	@Override
	public Item findItemById(Long itemId) {
		return null;
	}

}
