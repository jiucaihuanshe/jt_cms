package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;

public interface ItemService {
	//查询全部商品信息
	List<Item> findAll();

	EasyUIResult findItemByPage(int page, int rows);

	String findItemNameById(Long itemId);

	//新增商品信息
	void saveItem(Item item);

	//修改商品信息
	void updateItem(Item item);

	void deleteItems(Long[] ids);

	void updateStatus(Long[] ids, int status);
}
