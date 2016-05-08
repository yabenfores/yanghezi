package com.raoleqing.yangmatou.ben;

public class Order {
	
	private String order_id;
	private double goods_amount;//商品价格
	private double order_amount;//订单总价
	private String store_name;//
	private int order_state;//
	private String pay_sn;//630514571270380090;
	private String createtime;
	private double shipping_fee;//邮费
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public double getGoods_amount() {
		return goods_amount;
	}
	public void setGoods_amount(double goods_amount) {
		this.goods_amount = goods_amount;
	}
	public double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public int getOrder_state() {
		return order_state;
	}
	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	public String getPay_sn() {
		return pay_sn;
	}
	public void setPay_sn(String pay_sn) {
		this.pay_sn = pay_sn;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public double getShipping_fee() {
		return shipping_fee;
	}
	public void setShipping_fee(double shipping_fee) {
		this.shipping_fee = shipping_fee;
	}
	
	
	
	

}
