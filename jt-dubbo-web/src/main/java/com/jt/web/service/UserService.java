package com.jt.web.service;

import com.jt.web.pojo.User;

public interface UserService {
	//用户的注册
	String saveUser(User user);
	
	//根据用户名和密码进行查询
	String findUserByUP(String username, String password);

}
