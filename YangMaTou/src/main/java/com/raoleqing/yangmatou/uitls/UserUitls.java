package com.raoleqing.yangmatou.uitls;

import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.login.loginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class UserUitls {
	
	public static int exitBut = 1;//有退出动作没有： 1为没有，2为有

	/**
	 * 是否登陆
	 */
	public static boolean isLongin(Context context) {

		return SharedPreferencesUtil.getBoolean(context, "isLongin", false);

	}

	/**
	 * 拿到登陆用户名
	 **/
	public static String getLonginUserName(Context context) {

		String member_email = SharedPreferencesUtil.getString(context, "member_email");
		if (member_email != null && !member_email.equals("")) {
			return member_email;
		}
		String member_mobile = SharedPreferencesUtil.getString(context, "member_mobile");
		if (member_mobile != null && !member_mobile.equals("")) {
			return member_mobile;
		}
		String user_name = SharedPreferencesUtil.getString(context, "user_name");
		if (user_name != null && !user_name.equals("")) {
			return user_name;
		}

		return null;

	}

	/**
	 * 登陆
	 **/
	public static void longInDialog(final Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("您还没有登录，确定登录？").setTitle("提示")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(context, loginActivity.class);
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					}
				}).setNegativeButton("取消", null).show();

	}
	

}
