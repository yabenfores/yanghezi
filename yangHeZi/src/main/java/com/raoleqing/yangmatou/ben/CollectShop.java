package com.raoleqing.yangmatou.ben;

import java.util.List;

public class CollectShop {
    private int id;
    private int store_id;
    private String store_name;
    private String title;
    private String content;
    private long create_time;
    private int state;
    private String address;
    private String fans;
    private String store_img;
    private int attention;
    private List<Goods> goods_list;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStore_id() {
        return store_id;
    }
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
    public String getStore_name() {
        return store_name;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getCreate_time() {
        return create_time;
    }
    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getFans() {
        return fans;
    }
    public void setFans(String fans) {
        this.fans = fans;
    }
    public String getImg() {
        return store_img;
    }
    public void setImg(String img) {
        this.store_img = img;
    }
    public int getAttention() {
        return attention;
    }
    public void setAttention(int attention) {
        this.attention = attention;
    }
    public List<Goods> getGoods_list() {
        return goods_list;
    }
    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }
}
