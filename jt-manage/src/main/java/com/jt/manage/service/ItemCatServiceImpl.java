package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Override
	public List<ItemCat> findItemCat() {
		//调用通用Mapper的方法
		//如果查询全部数据则不需要设定参数直接为null
		return itemCatMapper.select(null);
	}
	@Override
	public List<ItemCat> findItemCatByParentId(Long parentId) {
		/**
		 * 通用Mapper中带参数的查询
		 * 如果需要带参数查询，只需要将参数set到具体的对象中即可。
		 * 实现思路：
		 * 	通用Mapper会将对象中不为null的数据当做where条件进行查询
		 * 	lg:select id,parent_id...
		 * 			from tb_item_cat where parent_id = 0 and status=1
		 */
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);	//设置父级id
		itemCat.setStatus(1);		//设置为正常的数据1
		return itemCatMapper.select(itemCat);
	}
}
