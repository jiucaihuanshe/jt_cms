package com.jt.rabbit.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{

	void updateStatusByDate(Date time);
	//根据orderId查询数据库
	Order findOrderById(String orderId);

}
