package com.tedu.jsoup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tedu.jsoup.mapper.UserMapper;
import com.tedu.jsoup.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> selectUserAll() {
		
		return userMapper.selectUserAll();
	}
}
