package com.jt.web.util;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	
	//private static int aa = 10;
	//private static User user = new User();
	//如果使用静态的成员变量容易引发线程安全性问题
	

	/*1.初始化ThreadLocal对象
		T泛型的种类:
			1.直接写对象 User.那么这个ThreadLocal只能存放一个值User对象
			
			2.如果想存入多个数据 使用Map
	*/
	private static ThreadLocal<User> userThreadLocal = 
			new ThreadLocal<User>();
	
	
	//存入ThreadLocal的方法
	public static void setUser(User user){
		userThreadLocal.set(user);
	}
	
	//获取数据
	public static User getUesr(){
		return userThreadLocal.get();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
