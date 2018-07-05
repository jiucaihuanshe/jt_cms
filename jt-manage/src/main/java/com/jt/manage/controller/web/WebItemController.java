package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;
	//http://manage.jt.com/web/item/+itemId;
	@RequestMapping("/{itemId}")
	@ResponseBody	//将Item对象以JSON的形式返回
	public Item findItemById(@PathVariable Long itemId){
		Item item = itemService.findItemById(itemId);
		return item;
	}
}
