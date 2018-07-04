package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.pojo.ItemCat;

public interface ItemCatService {
	//定义业务方法
	List<ItemCat> findItemCat();

	//根据parent_id查询商品分类信息
	List<ItemCat> findItemCatByParentId(Long parentId);

	ItemCatResult findItemCatAll();
	ItemCatResult findItemCatAllByCache();
}
