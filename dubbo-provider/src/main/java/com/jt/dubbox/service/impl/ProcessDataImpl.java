package com.jt.dubbox.service.impl;

import com.jt.dubbox.api.IProcessData;

public class ProcessDataImpl implements IProcessData {
	public String hello(String name) {
		//我是一个服务端程序，cart购物车系统 order系统
		return "service1 hello : " + name;
	}
}