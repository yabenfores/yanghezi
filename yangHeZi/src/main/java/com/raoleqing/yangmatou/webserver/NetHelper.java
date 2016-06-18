package com.raoleqing.yangmatou.webserver;

import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mining.app.zxing.decoding.Intents;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;

import org.json.JSONObject;

import java.io.File;


/**
 * Created by ybin on 2016/5/10.
 */
public class NetHelper {

    //用户信息
    public static void Users(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.USERS, NetParams.HttpMethod.Post, true, connectListener);
    }

    //以及分类
    public static void getOneCat(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ONECAT, NetParams.HttpMethod.Post, false, connectListener);
    }

    //二级分类三级分类数据
    public static void getCatList(String cid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.CATLIST, NetParams.HttpMethod.Post, false, connectListener, "cid", cid);
    }

    //商店列表
    public static void getStoreList(String pag, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.STORELIST, NetParams.HttpMethod.Post, true, connectListener, "p", pag);
    }

    //广告
    public static void advManage(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ADVMANAGE, NetParams.HttpMethod.Get, false, connectListener);
    }

    //用户订单
    public static void member_order(String state_type,String page, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MEMBER_ORDER, NetParams.HttpMethod.Post, true, connectListener, "state_type", state_type,"page",page);
    }

    //修改密码
    public static void editUserPass(String loginpas, String member_password, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.EDITUSERPASS, NetParams.HttpMethod.Post, true, connectListener, "loginpas", loginpas, "member_password", member_password);
    }

    public static void goodsDetails(String goods_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GOODS_DETAILS, NetParams.HttpMethod.Post, false, connectListener, "goods_id", goods_id);
    }
    public static void goodsDetails(String goods_serial,String search_type, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GOODS_DETAILS, NetParams.HttpMethod.Post, false, connectListener, "goods_serial", goods_serial,"search_type",search_type);
    }

    public static void goodsReview(String goods_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GOODS_REVIEW, NetParams.HttpMethod.Post, false, connectListener, "goods_id", goods_id);
    }

    //获取默认地址
    public static void getDefaultAddress(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GETDEFAULTADDRESS, NetParams.HttpMethod.Post, true, connectListener);
    }

    //税
    public static void getTax(String pid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GETTAX, NetParams.HttpMethod.Post, true, connectListener, "pid", pid);
    }

    //关注
    public static void favoritesstore(String fid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.FAVORITESSTORE, NetParams.HttpMethod.Post, true, connectListener, "fid", fid);
    }

    //取消关注
    public static void cancelStore(String fid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.CANCELSTORE, NetParams.HttpMethod.Post, true, connectListener, "fid", fid);
    }

    //关注列表
    public static void member_fslist(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MEMBER_FSLIST, NetParams.HttpMethod.Post, true, connectListener);
    }

    //退出登录
    public static void Logout(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.LOGOUT, NetParams.HttpMethod.Post, true, connectListener);
    }

    //获取退货
    public static void getRefundList(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GETREFUNDLIST, NetParams.HttpMethod.Post, true, connectListener);
    }

    //商品列表
    public static void goodsList(String cid, String p, String key, String order, String keyword, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.GOODSLIST, NetParams.HttpMethod.Post, true, connectListener, "cid", cid, "p", p, "key", key, "order", order, "keyword", keyword);
    }

    //获取验证码
    public static void MsgInfo(String phone, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MSGINFO, NetParams.HttpMethod.Post, true, connectListener, "member_mobile", phone);
    }

    //
    public static void bindUserMobile(String phone, String verification_code, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.BINDUSERMOBILE, NetParams.HttpMethod.Post, true, connectListener, "member_mobile", phone, "verification_code", verification_code);
    }

    //获取环信帐号
    public static void customerIndex(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.CUSTOMERINDEX, NetParams.HttpMethod.Post, true, connectListener);
    }

    //收藏商品
    public static void favoritespro(String fid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(HttpUtil.FAVORITES_PORDUCT_STORE, NetParams.HttpMethod.Post, true, connectListener, "fid", fid);
    }

    //取消收藏商品
    public static void cancelPro(String fid, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(HttpUtil.CANCEL_PORDUCT_STORE, NetParams.HttpMethod.Post, true, connectListener, "fid", fid);
    }

    //关于
    public static void aboutYhz(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ABOUTYHZ, NetParams.HttpMethod.Post, false, connectListener);
    }

    //热卖
    public static void flashSale(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.FLASHSALE, NetParams.HttpMethod.Post, false, connectListener);
    }

    //取消订单
    public static void ordercancel(String order_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ORDERCANCEL, NetParams.HttpMethod.Post, true, connectListener, "order_id", order_id);
    }

    //提交订单
    public static void submitOrder(String wh_id, String transaction_type, String goods_array, String order_message, String sfzno, String payment_id, String address_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.SUBMITORDER, NetParams.HttpMethod.Post, true, connectListener, "wh_id", wh_id, "transaction_type", transaction_type, "goods_array", goods_array, "order_message", order_message, "sfzno", sfzno, "payment_id", payment_id, "address_id", address_id);
    }

    //编辑地址
    public static void edit_Address(String address_id, String true_name, String area_id, String city_id, String area_info, String address, String tel_phone, String is_default, String mob_phone, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(HttpUtil.EDIT_ADDRESS, NetParams.HttpMethod.Post, true, connectListener, "address_id", address_id, "true_name", true_name, "area_id", area_id, "city_id", city_id, "area_info", area_info, "address", address, "tel_phone", tel_phone, "is_default", is_default, "mob_phone", mob_phone);
    }

    //添加地址
    public static void add_Address(String true_name, String area_id, String city_id, String area_info, String address, String tel_phone, String is_default, String mob_phone, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(HttpUtil.ADD_ADDRESS, NetParams.HttpMethod.Post, true, connectListener, "true_name", true_name, "area_id", area_id, "city_id", city_id, "area_info", area_info, "address", address, "tel_phone", tel_phone, "is_default", is_default, "mob_phone", mob_phone);
    }

    //修改昵称
    public static void editUserName(String member_name, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.EDITUSERNAME, NetParams.HttpMethod.Post, true, connectListener, "member_name", member_name);
    }

    //删除地址
    public static void del_address(String address_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(HttpUtil.DEL_ADDRESS, NetParams.HttpMethod.Post, true, connectListener, "address_id", address_id);
    }
    //秀一秀
    public static void Evaluate(String dotype, String page, String city_id, String brand_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.EVALUATE, NetParams.HttpMethod.Post, true, connectListener, "dotype", dotype, "page", page, "city_id", city_id, "brand_id", brand_id);
    }
    //秀一秀广告
    public static void adImginfo(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ADIMGINFO, NetParams.HttpMethod.Post, false, connectListener);
    }

    //秀一秀国家
    public static void Flags(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.FLAGS, NetParams.HttpMethod.Post, false, connectListener);
    }

    //进口税
    public static void tariff(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.TARIFF, NetParams.HttpMethod.Post, true, connectListener);
    }

    //继续支付
    public static void order_conpay(String order_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ORDER_CONPAY, NetParams.HttpMethod.Post, true, connectListener, "order_id", order_id);
    }

    //品牌
    public static void Brands(String brand_initial, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.BRANDS, NetParams.HttpMethod.Post, false, connectListener, "brand_initial", brand_initial);
    }

    //商店
    public static void Store(String store_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.STORE, NetParams.HttpMethod.Post, true, connectListener, "store_id", store_id);
    }

    //商店
    public static void Storegoods(String store_id, String gc_id, String page, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.STOREGOODS, NetParams.HttpMethod.Post, true, connectListener, "store_id", store_id, "gc_id", gc_id, "page", page);
    }

    //评论列表
    public static void Comments(String geval_id,String page, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.COMMENTS, NetParams.HttpMethod.Post, true, connectListener, "geval_id", geval_id,"page",page);
    }
    //回复
    public static void Commentdo(String geval_id,String comment_context, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.COMMENTDO, NetParams.HttpMethod.Post, true, connectListener, "geval_id", geval_id,"comment_context",comment_context);
    }
    //评论
    public static void member_evaluate(String order_id,String store_desccredit,String store_servicecredit ,String store_deliverycredit ,String evaluate_comment,String anony,String geval_images, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MEMBER_EVALUATE, NetParams.HttpMethod.Post, true, connectListener, "order_id", order_id,"store_desccredit",store_desccredit,"store_servicecredit",store_servicecredit,"store_deliverycredit",store_deliverycredit,"evaluate_comment",evaluate_comment,"anony",anony,"geval_images",geval_images);
    }
    //点赞
    public static void likedo(String geval_id,String dotype, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.LIKEDO, NetParams.HttpMethod.Post, true, connectListener, "geval_id", geval_id,"dotype",dotype);
    }
    //验证手机
    public static void CheckMember(String member_mobile, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.CHECKMEMBER, NetParams.HttpMethod.Post, false, connectListener, "dotype", "1","member_mobile",member_mobile);
    }
    //验证身份证
    public static void CheckMemberId(String member_card, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.CHECKMEMBER, NetParams.HttpMethod.Post, false, connectListener, "dotype", "2","member_card",member_card);
    }
    //注册
    public static void Register(String member_mobile,String member_card,String member_truename,String member_passwd,String code, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.REGISTER, NetParams.HttpMethod.Post, true, connectListener, "member_mobile", member_mobile,"member_card",member_card,"member_truename",member_truename,"member_passwd",member_passwd,"code",code);
    }
    //确认收货
    public static void orderreceive(String order_id, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.ORDERRECEIVE, NetParams.HttpMethod.Post, true, connectListener, "order_id", order_id);
    }
    //重置密码
    public static void ResetPwd(String member_mobile,String verification_code,String member_passwd, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.RESETPWD, NetParams.HttpMethod.Post, false, connectListener, "member_mobile", member_mobile,"verification_code",verification_code,"member_passwd",member_passwd);
    }
    //修改身份证
    public static void modfiycard(String member_card, String member_mobile,String verification_code, NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MODFIYCARD, NetParams.HttpMethod.Post, true, connectListener, "member_card", member_card,"member_mobile",member_mobile,"verification_code",verification_code);
    }
    //提交退换货数据
    public static void AfterSales(String order_id, String refund_type ,String quantity, String reason_id,String buyer_message,String pic_info,NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.AfterSales, NetParams.HttpMethod.Post, true, connectListener, "order_id", order_id,"refund_type",refund_type,"quantity",quantity,"reason_id",reason_id,"buyer_message",buyer_message,"pic_info",pic_info);
    }

    //支付列表
    public static void payList(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.PAYLIST, NetParams.HttpMethod.Post, true, connectListener);
    }
    //退换原因
    public static void AfterReason(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.AFTERREASON, NetParams.HttpMethod.Post, true, connectListener);
    }
    //退换原因
    public static void MessageInfo(NetConnectionInterface.iConnectListener3 connectListener) {
        new BaseNetConnection(Constant.MESSAGEINFO, NetParams.HttpMethod.Post, true, connectListener);
    }


}
