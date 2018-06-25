package com.jt.manage.service;

import java.util.Date;
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

	//业务层处理补全数据
	@Override
	public void saveItem(Item item) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
	}

	@Override
	public void updateItem(Item item) {
		item.setUpdated(new Date());
		//因为这个方法可以判断数据是否为null,数据为null则不参与修改。
		itemMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public void deleteItems(Long[] ids) {
		itemMapper.deleteByIDS(ids);
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids,status);
	}
}
