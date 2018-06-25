package com.jt.manage.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//编辑logger对象，方便日志输出
	private static final Logger logger = Logger.getLogger(ItemController.class);
	
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

	//url:http://localhost:8091/item/save
	/**
	 * 新增商品思路:
	 * 	1.编辑请求接收@RequestMapping
	 * 	2.接收form表单数据
	 * 	3.新增商品信息
	 * 		3.1调用service
	 * 		3.2调用通用mapper实现入库操作
	 * 	4.返回JSON数据并且返回状态码
	 */
	/**
	 * 说明：由于数据表的定义我们不能将desc属性直接写到Item对象中，
	 * 所以通过String desc 进行参数的接收
	 * 新增的思路：
	 * 	1.添加新增参数，实现数据的传递String desc
	 *  2.通过Controller调用Service完成多表同时操作
	 *  	注意事项：
	 *  		1.如果是多张表同时"更新"，切记事务控制
	 *  		2.如果使用spring控制食物，切记方法名称不能乱写，应该和事务策略一致。
	 * @param item
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			//添加新增成功日志
			logger.info("~~~~~~~~商品新增成功"+item.getId());
			return SysResult.oK();	//表示新增成功
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!"+e.getMessage());
			return SysResult.build(201, "新增商品失败");
		}
	}
	
	//url:http://localhost:8091/item/update
	/**
	 * 修改商品
	 * 思路：
	 * 1.编辑请求接收@RequestMapping
	 * 2.接收form表单数据
	 * 3.修改商品信息
	 * 	3.1调用service
	 * 	3.2调用通用mapper实现入库操作
	 * 4.返回JSON数据并且返回状态码
	 */
	/**
	 * 通过参数desc接收商品描述信息
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	//如果数据库正常页面没有响应检测返回数据是否为JSON
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			//添加修改成功日志
			logger.info("~~~~~~~~~"+item.getId());
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!"+e.getMessage());
			return SysResult.build(201, "修改商品失败");
		}
	}
	
	//url:http://localhost:8091/item/delete
	/**
	 * 商品删除
	 * 1.获取请求接收@RequestMapping
	 * 2.接收form表单ids
	 * 3.删除商品
	 * 	3.1调用service
	 *  3.2调用通用mapper实现删除操作
	 *  
	 * 由于SpringMVC可以实现动态的赋值，如果数据是以","号分割，可以直接
	 * 采用数组形式接收
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try {
			itemService.deleteItems(ids);
			logger.info("~~~~~~~~~商品删除成功！"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("！！！！！！！！"+e.getMessage());
			return SysResult.build(201, "商品删除失败");
		}
	}
	
	//url:/item/reshelf
	/**
	 * 商品上架
	 * 思路：
	 * 编辑一个通用状态的修改方法参数(ids,status)
	 * ids表示需要的数据
	 * status表示修改后的状态码
	 */
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult updateReshelf(Long[] ids){
		int status =1;	//上架
		try {
			itemService.updateStatus(ids,status);
			logger.info("~~~~~~~~"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!"+e.getMessage());
			return SysResult.build(201, "商品上架成功");
		}
	}
	
	//url:http://localhost:8091/item/instock
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult updateInstock(Long[] ids){
		int status=2;	//下架
		try {
			itemService.updateStatus(ids, status);
			logger.info("~~~~~~~~~~"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!"+e.getMessage());
			return SysResult.build(201, "商品下架失败");
		}
	}
	
	//url: http://localhost:8091/item/query/item/desc/1474391947
	/**
	 * 采用restFul跳转页面其中参数采用{param}进行包裹.之后采用@PathVariable注解获取数据
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable("itemId") Long itemId){
		try {
			ItemDesc itemDesc = itemService.findItemDesc(itemId);
			logger.info("~~~~~~~查询数据成功"+itemDesc.getItemId());
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!"+e.getMessage());
			return SysResult.build(201, "商品描述信息查询失败");
		}
	}
}
