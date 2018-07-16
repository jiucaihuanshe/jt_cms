package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Item;

public interface SearchService {
	List<Item> findItemByKey(String keyword);
}
