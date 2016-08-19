package com.raoleqing.yangmatou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.raoleqing.yangmatou.ui.user.CircularActivity;
import com.raoleqing.yangmatou.ui.user.SendOutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class JPushRegister extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            try {
                JSONObject object = new JSONObject(extras);
                switch (object.optInt("msg_grouptype")) {
                    case 1:
                        Intent intent1 = new Intent(context, CircularActivity.class);
                        intent1.putExtra("msg_groupid", object.optInt("msg_groupid"));
                        intent1.putExtra("msg_grouptype", object.optInt("msg_grouptype"));
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context, SendOutActivity.class);
                        intent2.putExtra("msg_groupid", object.optInt("msg_groupid"));
                        intent2.putExtra("msg_grouptype", object.optInt("msg_grouptype"));
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
