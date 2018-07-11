package com.jt.dubbox.service.impl;

import com.jt.dubbox.api.IProcessData;

public class ProcessDataImpl implements IProcessData {
	public String hello(String name) {
		return "service2 hello : " + name;
	}
}