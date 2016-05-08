package com.raoleqing.yangmatou.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 登录
 * */
public class loginActivity  extends BaseActivity implements OnClickListener{
	
	private ImageView activity_return;
	private ImageView user_icon;
	
	private TextView login_registered;
	private Button login_but;
	private EditText user_name;
	private EditText user_password;
	private ImageView password_show;
	
	private int passType = 1;
	
	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				viewInfo();
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		setTitleText("登录");
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		user_icon = (ImageView) findViewById(R.id.user_icon);
		login_registered = (TextView) findViewById(R.id.login_registered);
		login_but = (Button) findViewById(R.id.login_but);
		user_name = (EditText) findViewById(R.id.user_name);
		user_password = (EditText) findViewById(R.id.user_password);
		password_show = (ImageView) findViewById(R.id.password_show);

		activity_return.setOnClickListener(this);
		login_registered.setOnClickListener(this);
		login_but.setOnClickListener(this);
		password_show.setOnClickListener(this);
		
		String UserName = UserUitls.getLonginUserName(loginActivity.this);
		if(UserName != null){
			user_name.setText(UserName);
			String member_avatar = SharedPreferencesUtil.getString(loginActivity.this, "member_avatar");
			
			ImageLoader.getInstance().displayImage(member_avatar, user_icon,
					YangMaTouApplication.imageOption(R.drawable.user_icon));
		}
		boolean rememberPassword = SharedPreferencesUtil.getBoolean(loginActivity.this, "remember_password", false);
		if(rememberPassword){
			String UserPassword = SharedPreferencesUtil.getString(loginActivity.this, "user_password");
			if(UserPassword != null){
				user_password.setText(UserPassword);
			}
		}
		
		setProgressVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_return:
			loginActivity.this.onBackPressed();
			break;

		case R.id.login_registered:
			Intent intent = new Intent(loginActivity.this, RegisteredActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.login_but:
			
			getLonginData();
			break;
		case R.id.password_show:
			
			if(passType == 1){
				passType = 2;
				user_password.setInputType(InputType.TYPE_CLASS_TEXT);
			}else{
				user_password.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
				passType = 1;
			}
			
			break;
		default:
			break;
		}

	}
	
	/**
	 * 
	 * **/
	private void getLonginData(){
		
		String userName = user_name.getText().toString();
		if(userName == null || userName.trim().equals("")){
			Toast.makeText(this, "请输入用户名", 1).show();
			return;
		}
		
		String userPassword = user_password.getText().toString();
		if(userPassword == null || userPassword.trim().equals("")){
			Toast.makeText(this, "请输入密码", 1).show();
			return;
		}
		
		//存入用户信息
		SharedPreferencesUtil.putString(loginActivity.this, "user_name", userName);
		SharedPreferencesUtil.putString(loginActivity.this, "user_password", userPassword);
		SharedPreferencesUtil.putBoolean(loginActivity.this, "remember_password", true);
		
		setProgressVisibility(View.VISIBLE);
		userLongin(userName,userPassword);
		
		
	}
	
	/**
	 * 登陆
	 * **/
	private void userLongin(String loginName,String loginPwd){	
		//Home/Users/checkLogin
		

		RequestParams params = new RequestParams();
		params.put("loginName", loginName);
		params.put("loginPwd", loginPwd);

		HttpUtil.post(loginActivity.this, HttpUtil.CHECK_LOGIN, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				ResolveJson(response);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				setProgressVisibility(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

		});
		
	}
	
	
	protected void ResolveJson(JSONObject response) {
		// TODO Auto-generated method stub
		
		System.out.println("登录： " + response);
		

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(loginActivity.this, message, 1).show();
				setProgressVisibility(View.GONE);
				return;
			}

			if (code == 1 || code == 200) {
				JSONObject data = response.optJSONObject("data");
				String member_name = response.optString("member_name");//用户名
				String member_truename = response.optString("member_truename");//真实信名
				String member_card = response.optString("member_card");//身份证
				String member_mobile = response.optString("member_mobile");//手机号码
				String member_email = response.optString("member_email");//邮箱 
				String member_avatar = response.optString("member_avatar");//图象地址
				int member_id = response.optInt("member_id",0);//用户id 
				int wh_id = response.optInt("wh_id",0);//区域id 
				
				SharedPreferencesUtil.putString(loginActivity.this, "member_name", member_name);
				SharedPreferencesUtil.putString(loginActivity.this, "member_truename", member_truename);
				SharedPreferencesUtil.putString(loginActivity.this, "member_card", member_card);
				SharedPreferencesUtil.putString(loginActivity.this, "member_mobile", member_mobile);
				SharedPreferencesUtil.putString(loginActivity.this, "member_email", member_email);
				SharedPreferencesUtil.putString(loginActivity.this, "member_avatar", member_avatar);
				SharedPreferencesUtil.putInt(loginActivity.this, "member_id", member_id);
				SharedPreferencesUtil.putInt(loginActivity.this, "wh_id", wh_id);
				SharedPreferencesUtil.putBoolean(loginActivity.this, "isLongin", true);
				loginActivity.this.onBackPressed();
			}
			Toast.makeText(loginActivity.this, message, 1).show();	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(loginActivity.this, "数据加载失败", 1).show();
		}
		setProgressVisibility(View.GONE);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}


}
