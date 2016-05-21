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
        new BaseNetConnection(Constant.STORELIST, NetParams.HttpMethod.Post,true,connectListener,"pag",pag);
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



}
