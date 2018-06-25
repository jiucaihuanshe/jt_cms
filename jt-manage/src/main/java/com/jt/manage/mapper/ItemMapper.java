package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{
	//查询全部商品信息
	List<Item> findAll();

	int findItemCount();

	/**
	 * 分页查询数据begin代表起始位置 rows加载数据量
	 * Mybatis中只允许传递单个数据，如果需要传递多个数据时，需要进行
	 * 封装，一般采用Map封装结果 添加@Param("begin")
	 * @param begin
	 * @param rows
	 * @return
	 */
	List<Item> findItemByPage(@Param("begin")int begin, @Param("rows")int rows);

	//查询商品分类名称
	String findItemNameById(Long itemId);

	//批量修改状态
	void updateStatus(@Param("ids")Long[] ids, @Param("status")int status);
}
