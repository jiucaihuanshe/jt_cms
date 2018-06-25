package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	//byName 通过bean的Id注入
	//byType 通过Class类型注入
	//指定ID进行注入
	//@Qualifier(value="")
	//Mapper接口不能直接实例化，其实spring通过mybatis为其创建代理对象进行注入
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

}
