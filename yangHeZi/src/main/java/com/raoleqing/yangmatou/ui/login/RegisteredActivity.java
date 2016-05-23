package com.raoleqing.yangmatou.ui.login;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 登录
 * */
public class RegisteredActivity  extends BaseActivity implements OnClickListener{
	
	private ImageView activity_return;
	
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
		setContentView(R.layout.registered_activity);

		setTitleText("注册");
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
	

		activity_return.setOnClickListener(this);
		
		setProgressVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_return:
			RegisteredActivity.this.onBackPressed();
			break;

		default:
			break;
		}

	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}


}