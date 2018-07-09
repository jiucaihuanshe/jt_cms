package com.jt.web.util;

import com.jt.web.pojo.User;

/**
 * 在当前线程内部实现数据的共享.
 * 说明:在一个方法的执行,就是一个线程.从Controller-Service-Mapper-映射文件.
 * 使用ThreadLocal可以在任何地点,获取线程内的数据.这样的方式就是ThreadLocal实现数据共享.
 * @author Administrator
 *
 */
public class UserThreadLocal {
	/**
	 * 1.初始化ThreadLocal对象
	 * ThreadLocal<T>
	 * T泛型的种类：
	 * 	1.直接写对象User。那么这个TreadLocal只能存放一个值User对象
	 * 	2.如果想存入多个数据 使用Map
	 */
	private static ThreadLocal<User> userThreadLocal=new ThreadLocal<>();
	
	//存入ThreadLocal的方法
	public static void setUser(User user){
		userThreadLocal.set(user);
	}
	//获取数据
	public static User getUser(){
		return userThreadLocal.get();
	}
}
