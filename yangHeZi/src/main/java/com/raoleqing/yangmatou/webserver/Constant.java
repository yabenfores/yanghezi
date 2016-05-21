package com.raoleqing.yangmatou.webserver;

/**
 * Created by ybin on 2016/5/10.
 */
public class Constant {
    public static final String API_BASE = "http://114.67.59.57/app/index.php/";
    public static final String REGULAR_PHONE = "^1[3-9]\\d{9}$";   // 手机号码正则表达式
    public static final String CHARSET = "utf-8";
    public static final String NET_STATUS = "code";
    public static final String NET_STATUS_SUCCESS = "1";
    public static final String NET_STATUS_SUCCESS1 = "200";
    public static final String INFO = "message";
    public static final String DATA = "data";
    public static final String LOGIN ="Home/Login/Index";
    public static final String USERS ="/Home/Users/Index";
    public static final String ONECAT ="/Home/Cate/getOneCat";//首页一级分类
    public static final String CATLIST ="/Home/Cate/getCatList";//二级级分类
    public static final String STORELIST ="/Home/Index/getStoreList";//店铺列表
    public static final String ADVMANAGE ="Home/Index/advManage";//首页广 告
    public final static String MEMBER_ORDER = "Home/Users/member_order";//获取订单
    public final static String EDITUSERPASS = "Home/Users/editUserPass";//修改密码
    public final static String GOODS_DETAILS = "Home/Goods/goodsDetails";//产品详情
    public final static String GOODS_REVIEW = "Home/Goods/goodsReview";//产品评价
    public final static String GETDEFAULTADDRESS = "Home/Users/getDefaultAddress";//默认地址
    public final static String GETTAX = "Home/Orders/getTax";//税
    public final static String FAVORITESSTORE = "/Home/Users/favoritesstore";//关注店铺
    public final static String CANCELSTORE = "/Home/Users/cancelStore";//取消关注店铺
    public final static String REVIEWIMG = "/Home/Users/reviewImg";//上传评论图片
    public final static String MEMBER_FSLIST = "/Home/Users/member_fslist";//获取关注列表
    public final static String LOGOUT="/Home/Login/Logout";//退出登录
    public final static String GETREFUNDLIST="/Home/Users/getRefundList";//退出登录
    public final static String GOODSLIST="/Home/Cate/goodsList";//退出登录
    public final static String MSGINFO="/Home/MsgInfo/Index";//获取短信
    public final static String BINDUSERMOBILE="/Home/Users/bindUserMobile";//绑定手机
    public final static String CUSTOMERINDEX="/Home/Customer/customerIndex";//绑定手机

}
