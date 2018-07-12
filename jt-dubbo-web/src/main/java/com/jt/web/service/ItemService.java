package com.jt.web.service;

import com.jt.web.pojo.Item;

public interface ItemService {

	Item findItemById(Long itemId);
	Item findItemByIdCache(Long itemId);

}
