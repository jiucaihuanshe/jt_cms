package com.jt.spring.test;

import org.springframework.beans.factory.FactoryBean;
/**
 * 当容器启动时,当扫描到UserFactory时，根据实现的接口，会自动的调用getObject()方法，最终获取实例化对象。
 * 将实例化对象保存到Spring容器中，通过依赖注入的形式，实现对象的注入。
 * @author Administrator
 * 写段小代码，手动启动spring容器，applicationContext context = new ClassPathXmlApplicationContext("...xml") 紧接着
 * 通过context的getBean方法一取值，完成。
 */
public class UserFactory implements FactoryBean<String> {

	//表示获取工厂生产的实例化对象
	@Override
	public String getObject() throws Exception {
		//在该方法中，准备实例化对象
		
		return "1804班";
	}
	
	//表示对象的返回类型
	@Override
	public Class<?> getObjectType() {
		return null;
	}

	//表示是否为单例对象
	@Override
	public boolean isSingleton() {
		return false;
	}

}
