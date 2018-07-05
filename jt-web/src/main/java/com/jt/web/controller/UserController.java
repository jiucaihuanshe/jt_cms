package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	//user/register.html 实现登录和注册页面跳转
	//user/login.html

	@RequestMapping("/{param}")
	public String module(@PathVariable String param){
		//转向用户登录和注册页面
		return param;
	}
}
