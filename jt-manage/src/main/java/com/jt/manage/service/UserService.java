package com.jt.manage.service;

import java.util.List;

import com.jt.manage.pojo.User;

public interface UserService {
	//定义service层的接口文件
	List<User> findAll();
}
