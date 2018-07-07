package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

public interface UserMapper extends SysMapper<User>{
	//查询数据是否存在	将数据封装为Map
	//传递2个参数mybatis不允许，需要加注解@Param("") 这样mybatis可以自动的解析这两个数据
			//					key			value
	int findCheckUser(@Param("param")String param, @Param("cloumn")String cloumn);

	User selectUserByUP(@Param("username")String username, @Param("password")String mD5Password);
}
