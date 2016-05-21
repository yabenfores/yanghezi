package com.raoleqing.yangmatou.ben;

import java.io.Serializable;

public class Address implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int address_id;	//int	地址id	
	private int member_id;	//Int	用户id	
	private String true_name;	//string	姓名	
	private int area_id;	//int	地区id	
	private int city_id;	//string	市级id	
	private String area_info;	//String	地区内容	
	private String address; 	//string	地址	
	private String mob_phone;	//string;	电话	
	private int is_default;	 //是否默认地址	1 默认
	
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getArea_info() {
		return area_info;
	}
	public void setArea_info(String area_info) {
		this.area_info = area_info;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMob_phone() {
		return mob_phone;
	}
	public void setMob_phone(String mob_phone) {
		this.mob_phone = mob_phone;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	
	
	

}
