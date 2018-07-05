package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

public interface ItemService {
	//查询全部商品信息
	List<Item> findAll();

	EasyUIResult findItemByPage(int page, int rows);

	String findItemNameById(Long itemId);

	//新增商品信息
	void saveItem(Item item,String desc);

	//修改商品信息
	void updateItem(Item item, String desc);

	//删除商品信息
	void deleteItems(Long[] ids);

	//修改状态信息，上下架
	void updateStatus(Long[] ids, int status);

	//查询商品描述信息
	ItemDesc findItemDesc(Long itemId);

	//根据商品id进行查询
	Item findItemById(Long itemId);
}
