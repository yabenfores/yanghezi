package com.raoleqing.yangmatou.ben;


/**
 * 商品信息
 * **/
public class Goods {

	private int goods_id;//	int	产品ID
	private int goods_commonid;//	int	商品公共表id
	private String goods_name;
	private int gc_id;// id	分类ID
	private int store_id;//int	产品所属店铺ID
	private String store_name;// string	产品所属店铺名称
	private double goods_price;//	商品价格
	private double goods_promotion_price; //	商品促销价
	private int goods_promotion_type;//商品是否促销 0无促销，1团购，2限时折扣
	private double goods_marketprice; //string	商品市场价
	private int goods_storage;//	商品库存
	private String goods_image; //商品图片
	private int goods_salenum; //商品销量
	private int color_id; //颜色规格id
	

	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public double getGoods_promotion_price() {
		return goods_promotion_price;
	}
	public void setGoods_promotion_price(double goods_promotion_price) {
		this.goods_promotion_price = goods_promotion_price;
	}
	public String getGoods_image() {
		return goods_image;
	}
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	public int getGoods_commonid() {
		return goods_commonid;
	}
	public void setGoods_commonid(int goods_commonid) {
		this.goods_commonid = goods_commonid;
	}
	public int getGc_id() {
		return gc_id;
	}
	public void setGc_id(int gc_id) {
		this.gc_id = gc_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public int getGoods_promotion_type() {
		return goods_promotion_type;
	}
	public void setGoods_promotion_type(int goods_promotion_type) {
		this.goods_promotion_type = goods_promotion_type;
	}
	public double getGoods_marketprice() {
		return goods_marketprice;
	}
	public void setGoods_marketprice(double goods_marketprice) {
		this.goods_marketprice = goods_marketprice;
	}
	public int getGoods_storage() {
		return goods_storage;
	}
	public void setGoods_storage(int goods_storage) {
		this.goods_storage = goods_storage;
	}
	public int getGoods_salenum() {
		return goods_salenum;
	}
	public void setGoods_salenum(int goods_salenum) {
		this.goods_salenum = goods_salenum;
	}
	public int getColor_id() {
		return color_id;
	}
	public void setColor_id(int color_id) {
		this.color_id = color_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	
	

}
