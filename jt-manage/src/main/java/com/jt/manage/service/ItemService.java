package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;

public interface ItemService {
	//查询全部商品信息
	List<Item> findAll();

	EasyUIResult findItemByPage(int page, int rows);

	String findItemNameById(Long itemId);
}
