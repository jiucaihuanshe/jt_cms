package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.ItemCat;

public interface ItemCatMapper extends SysMapper<ItemCat>{
	//传统需要在接口中编辑很多的接口方法方便调用
	/**
	 * 当前的通用Mapper对象操作的就是ItemCat对象，操作的那张表就在pojo中的ItemCat进行了详细的定义
	 * 一般情况下不需要编辑其他的接口方法，除非有特定的业务
	 * 
	 * 规则:使用通用Mapper通常适用于单表操作.
	 * 如果是多表关联需要自己编写sql
	 */
}
