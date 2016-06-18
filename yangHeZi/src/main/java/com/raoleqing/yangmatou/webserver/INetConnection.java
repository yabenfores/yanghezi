package com.raoleqing.yangmatou.webserver;

import java.net.URLConnection;

/**
 * Created by ybin on 2016/5/22.
 */
public class INetConnection {
    public interface iSetHeader {
        void setHeader(URLConnection urlConnection);
    }

    public interface iConnectListener {
        void onStart();
        void onFinish();
        void onSuccess(Result result);
        void onFail(Result result);
    }

    public interface iFileUploadListener extends iConnectListener {
        void onProgress(float percent);
    }

//    public interface iUpdateConnectListenerString extends iConnectListener {
//        void onNoChange(String resultData);
//    }

}
