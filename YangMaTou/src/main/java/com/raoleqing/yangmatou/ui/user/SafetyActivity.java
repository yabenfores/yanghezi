package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.address.AddressActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 账号安全
 **/
public class SafetyActivity extends BaseActivity implements OnClickListener {

	private ImageView activity_return;
	private LinearLayout safety_bound_phone;
	private LinearLayout safety_set_password;
	private TextView user_name;
	private TextView user_card;
	private TextView safety_address;
	private Button exit_account;

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
		setContentView(R.layout.safety_activity);
		setTitleText(" 账户安全");
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		safety_bound_phone = (LinearLayout) findViewById(R.id.safety_bound_phone);
		safety_set_password = (LinearLayout) findViewById(R.id.safety_set_password);
		user_name = (TextView) findViewById(R.id.user_name);
		safety_address = (TextView) findViewById(R.id.safety_address);
		user_card = (TextView) findViewById(R.id.user_card);
		exit_account = (Button) findViewById(R.id.exit_account);
		
		String member_name = SharedPreferencesUtil.getString(SafetyActivity.this, "member_name");
		String member_card = SharedPreferencesUtil.getString(SafetyActivity.this, "member_card");
		user_name.setText(member_name);
		user_card.setText(member_card);

		activity_return.setOnClickListener(this);
		safety_bound_phone.setOnClickListener(this);
		safety_set_password.setOnClickListener(this);
		safety_address.setOnClickListener(this);
		exit_account.setOnClickListener(this);

		getData();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			SafetyActivity.this.onBackPressed();
			break;
		case R.id.safety_set_password:

			Intent intent01 = new Intent(SafetyActivity.this, setPassword.class);
			startActivity(intent01);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			//
			break;
		case R.id.safety_bound_phone:

			Intent intent02 = new Intent(SafetyActivity.this, BoundPhone.class);
			startActivity(intent02);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			//
			break;
		case R.id.safety_address:
			
			Intent intent03 = new Intent(SafetyActivity.this, AddressActivity.class);
			startActivity(intent03);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			
			break;
		case R.id.exit_account:
			showExitAccountDialog();
			break;
		default:
			break;
		}

	}

	private void getData() {
		// TODO Auto-generated method stub

		setProgressVisibility(View.GONE);

	}
	
	private void showExitAccountDialog() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder builder = new AlertDialog.Builder(SafetyActivity.this);
		builder.setMessage("您确定要退出当前账号？").setTitle("提示")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferencesUtil.putBoolean(SafetyActivity.this, "isLongin", false);
				UserUitls.exitBut = 2;
				SafetyActivity.this.onBackPressed();
			}
		}).setNegativeButton("取消", null).show();
		
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}

}
