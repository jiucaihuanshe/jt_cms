package com.jt.dubbox.demo;

import com.jt.dubbox.api.IProcessData;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerThd {
	public static void main(String[] args) {
		sayHello();
	}
	public static void sayHello() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationConsumer.xml" });
		context.start();

		IProcessData demoService = (IProcessData) context.getBean("demoService");
		for (;;) {
			String s = demoService.hello("world");
			System.out.println(s);
		}
	}
}