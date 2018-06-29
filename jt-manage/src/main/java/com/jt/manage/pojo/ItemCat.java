package com.jt.manage.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;
/**
 * 实体类中属性都需要使用包装类
 * 
 * 通用Mapper的配置
 * 1.匹配对应的表
 * 2.匹配id
 * 3.如果是自增需要设定
 * 
 * 作用：单表的增删改查不用写sql
 * @author Administrator
 *
 */
@Table(name="tb_item_cat")
//忽略未知属性 在爬虫时经常使用
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemCat extends BasePojo{
	@Id	//主键定义
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//主键自增
	private Long id;	//商品分类id
	private Long parentId;	//商品分类的父级id
	private String name;	//商品分类名称
	private Integer status;	//1正常 2删除
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//定义是否为上级
	
	/**
	 * 为了实现树形结构满足格式要求添加getXXX方法
	 * API中的描述信息
	 * 树控件读取URL。子节点的加载依赖于父节点的状态。当展开一个封闭的节点，
	 * 如果节点没有加载子节点，它将会把节点id的值作为http请求参数并命名为'id'，
	 * 通过URL发送到服务器上面检索子节点。
	 * 
	 * 实现思路：
	 * 	1.当第一次展开节点信息时，由于没有点击节点信息，所以不会传递Id值，这时
	 * 需要一个默认值id=0用来加载一级菜单
	 * 	2.如果鼠标点击一级菜单，会将当前的一级菜单的Id值进行传递，查询当前一级
	 * 菜单下的所有二级菜单
	 * 	3.如果鼠标点击二级菜单，会将当前的二级菜单的Id值进行传递，查询当前二级
	 * 菜单下的所有三级菜单
	 * @return
	 */
	public String getText(){
		return name;
	}
	//控制节点是否关闭
	//state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	public String getState(){
		return isParent?"closed":"open";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
