package com.jt.manage.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;

@RequestMapping("/web")
public class WebItemCatControllerTest {
	//http://manage.jt.com/web/itemcat/all?callback=category.getDataService
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public ItemCatResult findItemCatAll(){
		ItemCatResult catResult = new ItemCatResult();
		List<ItemCatData> list = new ArrayList<>();
		//定义一级菜单
		ItemCatData itemCatData = new ItemCatData();
		itemCatData.setUrl("/products/74.html");
		itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>家用电器</a>");
		
		//保存二级菜单
		List<ItemCatData> list2 = new ArrayList<>();
		ItemCatData itemCatData2 = new ItemCatData();
		itemCatData2.setUrl("products/89");
		itemCatData2.setName("生活电器");
		
		ItemCatData itemCatData2_1 = new ItemCatData();
		itemCatData2_1.setUrl("products/90");
		itemCatData2_1.setName("电脑办公");
		
		//保存三级菜单
		List<String> list3 = new ArrayList<>();
		list3.add("/products/91|冷风扇");
		list3.add("/products/92|净化器");
		list3.add("/products/93|饮水机");
		
		List<String> list4 = new ArrayList<>();
		list4.add("/products/94|吸尘器");
		list4.add("/products/95|加湿器");
		list4.add("/products/96|除尘器");
		
		itemCatData2.setItems(list3);
		itemCatData2_1.setItems(list4);
		
		list2.add(itemCatData2);
		list2.add(itemCatData2_1);
		
		itemCatData.setItems(list2);
		list.add(itemCatData);
		//返回	callback(ItemCatJSON数据)
		catResult.setItemCats(list);
		return catResult;
	}
}
