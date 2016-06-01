package com.raoleqing.yangmatou.ben;

import java.io.Serializable;

public class ShowShat implements Serializable {

    private int geval_id;//评价id
    private String geval_goodsname;//评价商品
    private int geval_scores;//评价分数
    private String geval_content;//评价内容
    private int geval_isanonymous;//是否匿名	0-否，1-是，判断用户名是否用*号代替

    public int getGeval_id() {
        return geval_id;
    }

    public void setGeval_id(int geval_id) {
        this.geval_id = geval_id;
    }

    public String getGeval_goodsname() {
        return geval_goodsname;
    }

    public void setGeval_goodsname(String geval_goodsname) {
        this.geval_goodsname = geval_goodsname;
    }

    public int getGeval_scores() {
        return geval_scores;
    }

    public void setGeval_scores(int geval_scores) {
        this.geval_scores = geval_scores;
    }

    public String getGeval_content() {
        return geval_content;
    }

    public void setGeval_content(String geval_content) {
        this.geval_content = geval_content;
    }

    public int getGeval_isanonymous() {
        return geval_isanonymous;
    }

    public void setGeval_isanonymous(int geval_isanonymous) {
        this.geval_isanonymous = geval_isanonymous;
    }

    public long getGeval_addtime() {
        return geval_addtime;
    }

    public void setGeval_addtime(long geval_addtime) {
        this.geval_addtime = geval_addtime;
    }

    public String getGeval_storename() {
        return geval_storename;
    }

    public void setGeval_storename(String geval_storename) {
        this.geval_storename = geval_storename;
    }

    public String getGeval_image() {
        return geval_image;
    }

    public void setGeval_image(String geval_image) {
        this.geval_image = geval_image;
    }

    public int getGeval_comment_num() {
        return geval_comment_num;
    }

    public void setGeval_comment_num(int geval_comment_num) {
        this.geval_comment_num = geval_comment_num;
    }

    public int getGeval_like_num() {
        return geval_like_num;
    }

    public void setGeval_like_num(int geval_like_num) {
        this.geval_like_num = geval_like_num;
    }

    public String getStore_label() {
        return store_label;
    }

    public void setStore_label(String store_label) {
        this.store_label = store_label;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(int pagetotal) {
        this.pagetotal = pagetotal;
    }

    private long geval_addtime;//评价时间戳
    private String geval_storename;//店铺名称
    private String geval_image;//评价图片	以；号分割，需要拆开
    private int geval_comment_num;//评论数量

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    private int is_like;//是否点赞
    private int geval_like_num;//点赞数
    private String store_label;//店铺logo
    private String member_avatar;//用户头像
    private String member_name;//用户昵称
    private int pagetotal;//评价总页面数
//    geval_id	int	评价id
//    geval_goodsname	string	评价商品
//    geval_scores	int	评价分数
//    geval_content	string	评价内容
//    geval_isanonymous	int	是否匿名	0-否，1-是，判断用户名是否用*号代替
//    geval_addtime	int	评价时间戳	需要现在时间计算评论的时长
//    geval_storename	string	店铺名称
//    geval_image	string	评价图片	以；号分割，需要拆开
//    geval_comment_num	int	评论数量
//    geval_like_num	int	点赞数
//    is_like	int	是否点赞
//    store_label	String	店铺logo
//    member_avatar	String	用户头像
//    member_name	String	用户昵称
//    pagetotal	int	评价总页面数


}
