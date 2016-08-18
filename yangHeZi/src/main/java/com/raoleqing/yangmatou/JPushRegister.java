package com.raoleqing.yangmatou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.raoleqing.yangmatou.common.YangHeZiApplication;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class JPushRegister extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		try {
			JSONObject object=new JSONObject(extras);
			switch (object.optInt("type")) {
				case 1:
					break;
				case 2:
					break;
				default:
					context.startActivity(new Intent(YangHeZiApplication.getAppContext(),StartActivity.class));
					break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
