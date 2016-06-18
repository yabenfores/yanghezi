package com.raoleqing.yangmatou.ben;

public class Shop {

    private int goods_id;
    private int goods_salenum;//销售数量
    private int goods_storage;//库存

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(int goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public int getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public int getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(int transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_jingle() {
        return goods_jingle;
    }

    public void setGoods_jingle(String goods_jingle) {
        this.goods_jingle = goods_jingle;
    }

    public String getFlag_name() {
        return flag_name;
    }

    public void setFlag_name(String flag_name) {
        this.flag_name = flag_name;
    }

    public String getFlag_imgSrc() {
        return flag_imgSrc;
    }

    public void setFlag_imgSrc(String flag_imgSrc) {
        this.flag_imgSrc = flag_imgSrc;
    }

    public String getGoods_images() {
        return goods_images;
    }

    public void setGoods_images(String goods_images) {
        this.goods_images = goods_images;
    }

    public String getComment_total() {
        return comment_total;
    }

    public void setComment_total(String comment_total) {
        this.comment_total = comment_total;
    }

    public String getGoods_publictime() {
        return goods_publictime;
    }

    public void setGoods_publictime(String goods_publictime) {
        this.goods_publictime = goods_publictime;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Double getGoods_marketprice() {
        return goods_marketprice;
    }

    public void setGoods_marketprice(Double goods_marketprice) {
        this.goods_marketprice = goods_marketprice;
    }

    private int is_favorite;//是否收藏
    private int transaction_type;//1-一般贸易类型，2-跨境贸易类型,3-直邮贸易类型
    private String goods_name;
    private String goods_jingle;//商品描述
    private String flag_name;//国家
    private String flag_imgSrc;//国家
    private String goods_images="";//；相隔
    private String comment_total;//评论数
    private String goods_publictime;//时间差
    private Double goods_price;
    private Double goods_marketprice;//市场价
}
