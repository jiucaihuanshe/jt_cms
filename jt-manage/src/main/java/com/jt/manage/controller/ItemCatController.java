package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/itemcat/findItemCat")
	@ResponseBody
	public List<ItemCat> findItemCat(){
		return itemCatService.findItemCat();
	}
	
	//url:http://localhost:8091/item/cat/list
	/**
	 * 通过@ResponseBody将数据转化为JSON数据时其实就是调用了对象
	 * 中的getXXX()方法
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<ItemCat> findItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		//Long id=0L;	//定义一级菜单 动态传参是删除
		//表示查询所有的一级菜单
		List<ItemCat> itemCatList = itemCatService.findItemCatByParentId(parentId);
		
		//通过工具类展现JSON	JSON工具类
		/*ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonData = objectMapper.writeValueAsString(itemCatList);
			System.out.println(jsonData);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}*/
		return itemCatList;
	}
}
