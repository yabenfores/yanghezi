package com.raoleqing.yangmatou.ben;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Order {
	
	private String order_id;
	private double goods_amount;//商品价格
	private double order_amount;//订单总价
	private String store_name;//
	private int refund_state;//1，2待审核,3为已完成,
	private int refund_type;//1为退款,2为退货，3为换货

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	public String getStore_label() {
		return store_label;
	}

	public void setStore_label(String store_label) {
		this.store_label = store_label;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public int getEvaluation_state() {
		return evaluation_state;
	}

	public void setEvaluation_state(int evaluation_state) {
		this.evaluation_state = evaluation_state;
	}

	public int getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(int refund_state) {
		this.refund_state = refund_state;
	}

	public int getRefund_type() {
		return refund_type;
	}

	public void setRefund_type(int refund_type) {
		this.refund_type = refund_type;
	}

	private int evaluation_state;
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public String getLineposttax() {
		return lineposttax;
	}

	public void setLineposttax(String lineposttax) {
		this.lineposttax = lineposttax;
	}

	public JSONObject getExtend_order_goods() {
		return extend_order_goods;
	}

	public void setExtend_order_goods(JSONObject extend_order_goods) {
		this.extend_order_goods = extend_order_goods;
	}

	private long add_time;//创建时间
	private String store_label;//头像
	private int order_state;//
	private int goods_num;//数量
	private String pay_sn;//630514571270380090;
	private String lineposttax;//行邮税;

	private JSONObject extend_order_goods;
	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	private String order_sn;//630514571270380090;
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
