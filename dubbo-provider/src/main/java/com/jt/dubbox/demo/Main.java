package com.jt.dubbox.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationProvider.xml" });
		
		//注意不能用ApplicationContext接口
		context.start();
		System.out.println("service1 started!");
		System.out.println("按任意键退出");
		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
	}
}