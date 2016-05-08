package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.showwhat.CountryActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutActivity extends BaseActivity implements OnClickListener {
	
	
	private ImageView country_return;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		type = this.getIntent().getIntExtra("type", 0);
		if(type != 0){
			setTitleText("关于进口税说明");
		}else{
			setTitleText("关于洋盒子");
		}
		
		viewInfo();
	}

	protected void viewInfo() {

		country_return = (ImageView) findViewById(R.id.country_return);

		setProgressVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.brands_return:
			AboutActivity.this.onBackPressed();
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
