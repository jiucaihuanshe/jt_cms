package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	//实现页面的通用跳转
	@RequestMapping("/page/{param}")
	public String index(@PathVariable String param){
		return param;
	}
}
