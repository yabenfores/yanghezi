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
    public static final String USERS ="Home/Users/Index";
    public static final String ONECAT ="Home/Cate/getOneCat";//首页一级分类
    public static final String CATLIST ="Home/Cate/getCatList";//二级级分类
    public static final String STORELIST ="Home/Index/getStoreList";//店铺列表
    public static final String ADVMANAGE ="Home/Index/advManage";//首页广 告
    public final static String MEMBER_ORDER = "Home/Users/member_order";//获取订单
    public final static String EDITUSERPASS = "Home/Users/editUserPass";//修改密码
    public final static String GOODS_DETAILS = "Home/Goods/goodsDetails";//产品详情
    public final static String GOODS_REVIEW = "Home/Goods/goodsReview";//产品评价
    public final static String GETDEFAULTADDRESS = "Home/Users/getDefaultAddress";//默认地址
    public final static String GETTAX = "Home/Orders/getTax";//税
    public final static String FAVORITESSTORE = "Home/Users/favoritesstore";//关注店铺
    public final static String CANCELSTORE = "Home/Users/cancelStore";//取消关注店铺
    public final static String REVIEWIMG = "Home/Users/reviewImg";//上传评论图片
    public final static String MEMBER_FSLIST = "Home/Users/member_fslist";//获取关注列表
    public final static String LOGOUT="Home/Login/Logout";//退出登录
    public final static String GETREFUNDLIST="Home/Users/RefundList";//退货列表
    public final static String GOODSLIST="Home/Cate/goodsList";//退出登录
    public final static String MSGINFO="Home/MsgInfo/Index";//获取短信
    public final static String BINDUSERMOBILE="Home/Users/bindUserMobile";//绑定手机
    public final static String CUSTOMERINDEX="Home/Customer/customerIndex";//绑定手机
    public final static String MEMBER_AVATAR="Home/Users/member_avatar";//修改头像
    public final static String ABOUTYHZ="Home/Index/aboutYhz";//关于
    public final static String FLASHSALE="Home/Index/flashSale";//热卖
    public final static String ORDERCANCEL="Home/Orders/ordercancel";//取消订单
    public final static String SUBMITORDER="Home/Orders/submitOrder";//提交订单
    public final static String EDITUSERNAME="Home/Users/editUserName";//修改昵称
    public final static String EVALUATE="Home/Evaluate/Index";//秀一秀
    public final static String ADIMGINFO="Home/Evaluate/AdimgInfo";//秀一秀广告
    public final static String ORDER_CONPAY="Home/Orders/order_conpay";//继续支付
    public final static String FLAGS="Home/Evaluate/Flags";//国家
    public final static String TARIFF="Home/Orders/tariff";//进口税
    public final static String BRANDS="Home/Evaluate/Brands";//品牌
    public final static String STORE="Home/Store/Index";//商店
    public final static String STOREGOODS="Home/Store/Storegoods";//商店商品列表
    public final static String COMMENTS="Home/Evaluate/Comments";//评论列表
    public final static String COMMENTDO="Home/Users/Commentdo";//评论
    public final static String MEMBER_EVALUATE="Home/Users/member_evaluate";//评论
    public final static String LIKEDO="Home/Users/likedo";//点赞
    public final static String CHECKMEMBER="Home/Register/CheckMember";//验证
    public final static String REGISTER="Home/Register/Index";//注册
    public final static String DELIVERY="Home/Orders/delivery";//发货方式
    public final static String ORDERRECEIVE="Home/Orders/orderreceive";//
    public final static String RESETPWD="Home/Index/ResetPwd";//发货方式
    public final static String MODFIYCARD="Home/Users/modfiycard";//修改身份证
    public final static String AfterSales="Home/Orders/AfterSales";//提交退换货数据
    public final static String Upload="Home/Upload/Index";//退换照片
    public final static String PAYLIST="Home/Orders/payList";//支付列表
    public final static String AFTERREASON="Home/Orders/AfterReason";//退换原因
    public final static String MESSAGEINFO="Home/MessageInfo/Index";//消息列表

}
