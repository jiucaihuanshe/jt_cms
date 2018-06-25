package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.jt.manage.pojo.User;

public interface UserMapper {
	/**
	 * 注意事项：
	 * 要不使用注解形式，要么使用映射文件形式，二者不能同时存在。
	 * 同时存在解析时会报错。
	 * @return
	 */
	//查询全部的用户信息
	//@Select(value="select * from user")
	List<User> findAll();
	
	/*@Insert(value="insert into user(id,name) values(#{id},#{name})")
	#{id},#{name}这里跟映射文件mapper.xml一样，映射文件mapper怎么写这里也怎么写
	int id，String name. 因为mybatis不能多值传递，需要进行封装。要么封装成map要么封装成对象，这里传递对象User
	void insertUser(User user);*/
}
