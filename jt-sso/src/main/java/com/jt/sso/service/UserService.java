package com.jt.sso.service;

import com.jt.sso.pojo.User;

public interface UserService {
	//校验数据是否存在
	Boolean findCheckUser(String param, Integer type);
	//用户新增
	String saveUser(User user);
	//根据用户名和密码进行用户登录
	String findUserByUP(String username, String password);
}
