package com.jt.manage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Item> findAll(){
		return itemService.findAll();
	}
	
	//http://localhost:8091/item/query?page=1&rows=20
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(int page,int rows){
		return itemService.findItemByPage(page,rows);
	}
	
	//采用response方式回传数据
	//url /item/cat/queryItemName
	/**
	 * 为什么配置了全站乱码解决还要配置UTF_8
	 * 由于ajax是异步提交.在源码有不同的两项配置
	 * 其中一个定义了UTF_8编码
	 * 另外的一个工具类中定义了ISO-8859-1
	 * 如果不是异步提交则通过过滤器后设置utf-8保证数据有效
	 * @param itemId
	 * @param response
	 */
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String queryItemName(Long itemId,HttpServletResponse response){
		return itemService.findItemNameById(itemId);
	}
	//两种不同的方式解决页面乱码问题
	/*@RequestMapping("/cat/queryItemName")
	public void queryItemCatName(Long itemCatId,HttpServletResponse response){
		String name = itemService.findItemNameById(itemCatId);
		//解决页面乱码问题
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
