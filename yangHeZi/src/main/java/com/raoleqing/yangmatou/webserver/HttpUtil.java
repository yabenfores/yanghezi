package com.raoleqing.yangmatou.webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import internal.org.apache.http.entity.mime.MultipartEntity;
import internal.org.apache.http.entity.mime.content.FileBody;

/*
 * 网络请求
 * **/
public class HttpUtil extends NetWorkBase {

    public final static String urlString = "http://114.67.59.57/app/index.php/";
    public final static String imageUrl = "http://114.67.59.57/app/index.php/";
    public final static String KEY = "Value";

    public final static String GET_ONE_CAT = "Home/Index/getOneCat";//首页一级分类
    public final static String GET_CAT_LIST = "Home/Cate/getCatList";//分类查看

    public final static String CATE_GET_ONE_CAT = "Home/Cate/getOneCat";//二页，一级分类
    public final static String ADV_MANAGE = "Home/Index/advManage";//首页广 告
    public final static String GET_STORE_LIST = "Home/Index/getStoreList";//店铺列表
    public final static String GET_PAVILION = "Home/Index/getPavilion";//国家管


    public final static String GOODS_LIST = "Home/Cate/goodsList";//产品列表
    public final static String GOODS_DETAILS = "Home/Goods/goodsDetails";//产品详情
    public final static String GOODS_REVIEW = "Home/Goods/goodsReview";//产品评价
    public final static String GOODS_SCANNING = "Home/Goods/goodsDetails";//扫描商品二维码


    public final static String CHECK_LOGIN = "Home/Login/Index";//登录
    public final static String FAVORITES_SHOP_STORE = "Home/Index/favoritesstore";//关注
    public final static String CANCEL_SHOP_STORE = "Home/Index/cancelStore";//取消关注
    public final static String FAVORITES_PORDUCT_STORE = "Home/Users/favoritespro";//关注
    public final static String CANCEL_PORDUCT_STORE = "Home/Users/cancelPro";//取消关注

    public final static String GET_DEFAULT_ADDRESS = "Home/Orders/getDefaultAddress";//拿到发货地址
    public final static String DEL_ADDRESS = "/Home/Address/del_Address";//册除地址
    public final static String ADD_ADDRESS = "Home/Address/add_Address";//添加地址
    public final static String EDIT_ADDRESS = "Home/Address/edit_Address";//编辑地址


    // 用一个完整url获取一个string对象
    public static void get(Context context, String method, AsyncHttpResponseHandler res) {
        String urlConnection = urlString + method;
        System.out.println("请求数据1： " + urlConnection);
        baseMethod(context).get(urlConnection, res);


    }


    // 不带参数，获取json对象或者数组
    public static void get(Context context, String method, JsonHttpResponseHandler res) {
        String urlConnection = urlString + method;
        System.out.println("请求数据2： " + urlConnection);
        baseMethod(context).get(urlConnection, res);
    }

    // 下载数据使用，会返回byte数据
    public static void get(Context context, String method, BinaryHttpResponseHandler bHandler) {
        String urlConnection = urlString + method;
        System.out.println("请求数据3： " + urlConnection);
        baseMethod(context).get(urlConnection, bHandler);
    }


    // url里面带参数
    public static void get(Context context, Map<String, Object> map, String method, AsyncHttpResponseHandler res) {
        String urlConnection = urlString + stringConnection(method, map);
        System.out.println("请求数据4： " + urlConnection);
        baseMethod(context).get(urlConnection, res);
    }


    // 带参数，获取json对象或者数组
    public static void get(Context context, String method, Map<String, Object> map, JsonHttpResponseHandler res) {
        String urlConnection = urlString + stringConnection(method, map);
        System.out.println("请求数据5： " + urlConnection);
        baseMethod(context).get(urlConnection, res);
    }


    /**
     * post
     */
    public static AsyncHttpClient post(Context context, String method, RequestParams req, AsyncHttpResponseHandler res) {

        AsyncHttpClient client = baseMethod(context);
        client.setTimeout(30000);
        String urlConnection = urlString + method;
        System.out.println("请求数据6： " + urlConnection);
        client.post(urlConnection, req, res);
        return client;
    }

    /**
     * post
     */
    public static AsyncHttpClient post(Context context, String method, RequestParams req, JsonHttpResponseHandler res) {

        AsyncHttpClient client = baseMethod(context);
        client.setTimeout(30000);
        String urlConnection = urlString + method;
        System.out.println("请求数据： " + urlConnection);
        System.out.println("参数： " + req.toString());
        client.post(urlConnection, null, res);
        return client;
    }
    public static AsyncHttpClient post1(Context context, String method, RequestParams req, JsonHttpResponseHandler res) {
        AsyncHttpClient client = baseMethod(context);
        client.setTimeout(30000);
        String auth = SharedPreferencesUtil.getString(BaseActivity.getAppContext(),"Authorization");
        client.addHeader("Authorization", auth);
        String urlConnection = urlString + method;
        System.out.println("请求数据： " + urlConnection);
        System.out.println("参数： " + req.toString());
        client.post(urlConnection, null, res);
        return client;

    }

    private static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * post
     */
    public static AsyncHttpClient post(Context context, String method, JsonHttpResponseHandler res) {

        AsyncHttpClient client = baseMethod(context);
        client.setTimeout(30000);
        String urlConnection = urlString + method;
        System.out.println("请求数据7： " + urlConnection);
        client.post(urlConnection, res);
        return client;
    }


    /**
     * 连接url
     **/
    private static String stringConnection(String method, Map<String, Object> map) {

        // 先排序
        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());

        // 排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                // return (o2.getValue() - o1.getValue());
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        // 排序后
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer01 = new StringBuffer();
        for (int i = 0; i < infoIds.size(); i++) {
            Entry<String, Object> entry = infoIds.get(i);

            String key = (String) entry.getKey();
            String val = entry.getValue().toString();

            buffer.append(key);
            buffer.append(val);
            buffer01.append("&" + key + "=" + val);
        }
        String urlConnection = buffer01.toString();
        return urlConnection;
    }

}
