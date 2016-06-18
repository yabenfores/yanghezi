package com.raoleqing.yangmatou.ui.aftermarket;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShopAdapter;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AftermarketActivity  extends BaseActivity implements OnClickListener{
	
	private ImageView activity_return;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_activity);

		Intent intent = this.getIntent();
	
		setTitleText("申请售后");
		viewInfo();
	
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		
		activity_return.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			AftermarketActivity.this.onBackPressed();
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
