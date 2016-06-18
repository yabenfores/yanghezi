package com.raoleqing.yangmatou.webserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLConnection;

/**
 * Created by ybin on 2016/5/10.
 */

    public class NetConnectionInterface {
        public interface iSetHeader {
            void setHeader(URLConnection urlConnection);
        }

        public interface iConnectListener {
            void onSuccess(String result);

            void onFail(String result);
        }

        public interface iConnectListener2 extends iConnectListener {
            void onStart();

            void onFinish();

            void onSuccess(String result);

            void onFail(String result);
        }

        public interface iConnectListener3 {
            void onStart();

            void onFinish();

            void onSuccess(JSONObject result) ;

            void onFail(JSONObject result);
        }
        public interface iConnectListener4 {
            void onStart();

            void onFinish();

            void onSuccess(JSONArray result);

            void onFail(JSONArray result);
        }

        public interface iUpdateConnectListener extends iConnectListener {
            void onNoChange(String result);
        }

    }

