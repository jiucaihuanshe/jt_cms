package com.tedu.jsoup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.jsoup.pojo.User;
import com.tedu.jsoup.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		
		return userService.selectUserAll();
	}
}
