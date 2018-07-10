package com.jt.order.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Order对象
 */
import com.jt.common.po.BasePojo;
@Table(name="tb_order")
public class Order extends BasePojo{
	/**
	 * orderItems是由于对象的依赖关系引入的,
	 * order表中没有对应的字段,需要将该字段进行忽略.
	 */
	//一对多关联关系
	@Transient	//忽略该字段进行映射 该注解应用于mybatis中的通用Mapper
	private List<OrderItem> orderItems;
	//一对一
	@Transient
	private OrderShipping orderShipping;
	@Id
	private String orderId;			//订单号：登录用户id+当前时间戳
	private String payment;			//实付金额
	private Integer paymentType;	//支付类型 1.在线支付 2.货到付款
	private String postFee;			//邮费
	private Integer status;			//状态 1.未付款 2.已付款 3.未发货 4.已发货 5.交易成功 6.交易关闭
	private Date paymentTime;		//支付时间
	private Date consignTime;		//发货时间
	private Date endTime;			//交易完成时间
	private Date closeTime;			//交易关闭时间
	private String shippingName;	//物流名称
	private String shippingCode;	//物流单号
	private Long userId;			//用户id
	private String buyerMessage;	//买家留言
	private String buyerNick;		//买家昵称
	private Integer buyerRate;		//是否评价
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public Integer getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
