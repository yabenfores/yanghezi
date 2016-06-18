package com.raoleqing.yangmatou.webserver;

import android.content.Context;

import java.util.Iterator;
import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * 数据请求加密
 **/
public class NetWorkBase {
	
	
	public static AsyncHttpClient baseMethod(Context mContext) {

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(11000);// 设置链接超时，如果不设置，默认为10s
		PersistentCookieStore cookie = new PersistentCookieStore(mContext);
		client.setCookieStore(cookie);
		return client;
	}

}
