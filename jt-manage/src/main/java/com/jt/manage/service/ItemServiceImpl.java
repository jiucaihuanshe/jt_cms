package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Override
	public List<Item> findAll(){
		return itemMapper.findAll();
	}
	
	//{"total":2000,"rows":[{},{},{}]}
	@Override
	public EasyUIResult findItemByPage(int page, int rows) {
		//查询商品记录总数
		int total = itemMapper.findItemCount();
		int begin = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(begin,rows);
		return new EasyUIResult(total, itemList);
	}

	@Override
	public String findItemNameById(Long itemId) {
		return itemMapper.findItemNameById(itemId);
	}
}
