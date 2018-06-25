package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	
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

	/**
	 * 思考：
	 * 1.item表的主键是自增的，所以只有当itm数据插入到数据库中时才能查询item的Id值
	 * 2.如果获取不到item的Id只能通过传入的数据进行where条件的拼接，但是这样的做法
	 * 不能一定满足需求，可能出现重复数据
	 */
	//业务层处理补全数据
	@Override
	public void saveItem(Item item,String desc) {
		//商品表的新增
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		//sql
		//在这里spring帮我们自动查询了最大的id值
		
		//商品描述表的新增
		ItemDesc itemDesc = new ItemDesc();
		/**
		 * 说明：由于通用Mapper会在插入数据时会进行id的最大值查询，将
		 * 结果自动映射到对象中，所以item.getId能够获取数据
		 * Executing: SELECT LAST_INSERT_ID()
		 */
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	/**
	 * 商品描述信息的修改
	 * 思路：
	 * 	1.应该讲数据补齐，为ItemDesc对象添加itemId和修改时间
	 * 	2.通过通用Mapper采用动态更新的方式更新
	 */
	@Override
	public void updateItem(Item item,String desc) {
		//准备修改时间
		item.setUpdated(new Date());
		//因为这个方法可以判断数据是否为null,数据为null则不参与修改。
		//动态修改
		itemMapper.updateByPrimaryKeySelective(item);
		
		//实现商品描述信息的修改
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());//获取商品的id
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public void deleteItems(Long[] ids) {
		//删除商品表数据
		itemMapper.deleteByIDS(ids);
		//删除商品描述信息
		itemDescMapper.deleteByIDS(ids);
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids,status);
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
}
