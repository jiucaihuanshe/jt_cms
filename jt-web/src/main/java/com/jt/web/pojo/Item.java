package com.jt.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;
@JsonIgnoreProperties(ignoreUnknown=true)	//忽略未知字符
public class Item extends BasePojo{
	private Long id;	//商品id号
	private String title;	//商品标题
	private String sellPoint;	//商品卖点信息 因为开启了驼峰规则，(sell_point)下划线可以去掉，后面字母大写
	private Long price;		//商品价格
	private Integer num;	//商品数量
	private String barcode;	//条形码
	private String image;	//商品图片信息
	private Long cid;		//商品分类id
	private Integer status;	//1.正常 2.下架 3.删除
	
	//由于页面需要对图片进行展现，通过数据的形式item.images[0]
	//由于页面中只需要展示第一张图片所以编辑该方法。
	public String[] getImages(){
		return image.split(",");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
