package com.raoleqing.yangmatou.webserver;

import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
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
    public static void Users(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.USERS, NetParams.HttpMethod.Post,true,connectListener);
        }
    //以及分类
    public static void getOneCat(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ONECAT, NetParams.HttpMethod.Post,false,connectListener);
        }
    //二级分类三级分类数据
    public static void getCatList(String cid,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.CATLIST, NetParams.HttpMethod.Post,false,connectListener,"cid",cid);
        }
    //商店列表
    public static void getStoreList(String pag,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.STORELIST, NetParams.HttpMethod.Post,true,connectListener,"p",pag);
        }
    //广告
    public static void advManage(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ADVMANAGE, NetParams.HttpMethod.Get,false,connectListener);
        }
    //用户订单
    public static void member_order(String state_type ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.MEMBER_ORDER, NetParams.HttpMethod.Post,true,connectListener,"state_type",state_type);
        }
    //修改密码
    public static void editUserPass(String loginpas ,String member_password,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.EDITUSERPASS, NetParams.HttpMethod.Post,true,connectListener,"loginpas",loginpas,"member_password",member_password);
        }
    public static void goodsDetails(String goods_id ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GOODS_DETAILS, NetParams.HttpMethod.Post,false,connectListener,"goods_id",goods_id);
        }
    public static void goodsReview(String goods_id ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GOODS_REVIEW, NetParams.HttpMethod.Post,false,connectListener,"goods_id",goods_id);
        }
    //获取默认地址
    public static void getDefaultAddress(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GETDEFAULTADDRESS, NetParams.HttpMethod.Post,true,connectListener);
        }
    //税
    public static void getTax(String pid ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GETTAX, NetParams.HttpMethod.Post,true,connectListener,"pid",pid);
        }
    //关注
    public static void favoritesstore(String fid ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.FAVORITESSTORE, NetParams.HttpMethod.Post,true,connectListener,"fid",fid);
        }
    //取消关注
    public static void cancelStore(String fid ,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.CANCELSTORE, NetParams.HttpMethod.Post,true,connectListener,"fid",fid);
        }
    //关注列表
    public static void member_fslist(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.MEMBER_FSLIST, NetParams.HttpMethod.Post,true,connectListener);
        }
    //退出登录
    public static void Logout(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.LOGOUT, NetParams.HttpMethod.Post,true,connectListener);
    }
    //获取退货
    public static void getRefundList(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GETREFUNDLIST, NetParams.HttpMethod.Post,true,connectListener);
    }
    //商品列表
    public static void goodsList(String cid,String p,String key,String order,String keyword,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.GOODSLIST, NetParams.HttpMethod.Post,true,connectListener,"cid",cid,"p",p,"key",key,"order",order,"keyword",keyword);
    }
    //获取验证码
    public static void MsgInfo(String phone,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.MSGINFO, NetParams.HttpMethod.Post,true,connectListener,"member_mobile",phone);
    }
    //获取验证码
    public static void bindUserMobile(String phone,String verification_code,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.BINDUSERMOBILE, NetParams.HttpMethod.Post,true,connectListener,"member_mobile",phone,"verification_code",verification_code);
    }
    //获取环信帐号
    public static void customerIndex(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.CUSTOMERINDEX, NetParams.HttpMethod.Post,true,connectListener);
    }
    //收藏商品
    public static void favoritespro(String fid,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(HttpUtil.FAVORITES_PORDUCT_STORE, NetParams.HttpMethod.Post,true,connectListener,"fid",fid);
    }
    //取消收藏商品
    public static void cancelPro(String fid,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(HttpUtil.CANCEL_PORDUCT_STORE, NetParams.HttpMethod.Post,true,connectListener,"fid",fid);
    }
    //关于
    public static void aboutYhz(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ABOUTYHZ, NetParams.HttpMethod.Post,false,connectListener);
    }
    //热卖
    public static void flashSale(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.FLASHSALE, NetParams.HttpMethod.Post,false,connectListener);
    }
    //取消订单
    public static void ordercancel(String order_id,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ORDERCANCEL, NetParams.HttpMethod.Post,true,connectListener,"order_id",order_id);
    }
    //提交订单
    public static void submitOrder(String wh_id,String quantity,String pid,String order_message,String sfzno,String pay_type,String address_id,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.SUBMITORDER, NetParams.HttpMethod.Post,true,connectListener,"wh_id",wh_id,"quantity",quantity,"pid",pid,"order_message",order_message,"sfzno",sfzno,"pay_type",pay_type,"address_id",address_id);
    }
    //编辑地址
    public static void edit_Address(String address_id,String true_name,String area_id,String city_id,String area_info,String address,String tel_phone,String is_default,String mob_phone,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(HttpUtil.EDIT_ADDRESS, NetParams.HttpMethod.Post,true,connectListener,"address_id",address_id,"true_name",true_name,"area_id",area_id,"city_id",city_id,"area_info",area_info,"address",address,"tel_phone",tel_phone,"is_default",is_default,"mob_phone",mob_phone);
    }
    //添加地址
    public static void add_Address(String true_name,String area_id,String city_id,String area_info,String address,String tel_phone,String is_default,String mob_phone,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(HttpUtil.ADD_ADDRESS, NetParams.HttpMethod.Post,true,connectListener,"true_name",true_name,"area_id",area_id,"city_id",city_id,"area_info",area_info,"address",address,"tel_phone",tel_phone,"is_default",is_default,"mob_phone",mob_phone);
    }
    //修改昵称
    public static void editUserName(String member_name,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.EDITUSERNAME, NetParams.HttpMethod.Post,false,connectListener,"member_name",member_name);
    }
    //删除地址
    public static void del_address(String address_id,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(HttpUtil.DEL_ADDRESS, NetParams.HttpMethod.Post,true,connectListener,"address_id",address_id);
    }
    //秀一秀
    public static void Evaluate(String dotype,String page,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.EVALUATE, NetParams.HttpMethod.Post,false,connectListener,"dotype",dotype,"page",page);
    }

    //秀一秀广告
    public static void adImginfo(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ADIMGINFO, NetParams.HttpMethod.Post,false,connectListener);
    }
    //秀一秀国家
    public static void Flags(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.FLAGS, NetParams.HttpMethod.Post,false,connectListener);
    }
    //进口税
    public static void tariff(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.TARIFF, NetParams.HttpMethod.Post,true,connectListener);
    }
    //继续支付
    public static void order_conpay(String order_id, NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.ORDER_CONPAY, NetParams.HttpMethod.Post,true,connectListener,"order_id",order_id);
    }
    //品牌
    public static void Brands(String brand_initial, NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.BRANDS, NetParams.HttpMethod.Post,false,connectListener,"brand_initial",brand_initial);
    }
    //商店
    public static void Store(String store_id, NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.STORE, NetParams.HttpMethod.Post,true,connectListener,"store_id",store_id);
    }
    //商店
    public static void Storegoods(String store_id, String gc_id,String page,NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.STOREGOODS, NetParams.HttpMethod.Post,true,connectListener,"store_id",store_id,"gc_id",gc_id,"page",page);
    }



}
