package com.raoleqing.yangmatou.webserver;

import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import org.json.JSONObject;

/**
 * Created by ybin on 2016/5/10.
 */
public class NetHelper {

    public static void Users(NetConnectionInterface.iConnectListener3 connectListener){
        new BaseNetConnection(Constant.USERS, NetParams.HttpMethod.Post,true,connectListener);
        }



}
