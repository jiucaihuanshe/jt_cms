package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

@Controller	//WebApplicationContext springmvc中所特有的容器
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 定义浏览器的访问路径接收
	 * 1、通过前端控制器将用户的url转发到具体的Controller中。
	 * 2、通过转发查找最匹配的RequestMapping。
	 * 3、进行业务操作。controller--service--mybatis依次调用
	 * 4、准备返回数据。(JSON串/页面名称)
	 */
	
	//要求：返回userList页面
	@RequestMapping("/findAll")
	public String findList(Model model){
		//经过业务层处理获取数据
		List<User> userList = userService.findAll();
		//通过model对象将数据存入request域中
		model.addAttribute("userList", userList);
		//页面跳转
		return "userList";
		/**
		 * springmvc将返回的字符串经过视图解析器进行数据的拼接
		 * prefix+userList+suffix
		 * 路径：/WEB-INF/views/userList.jsp
		 */
	}
}
