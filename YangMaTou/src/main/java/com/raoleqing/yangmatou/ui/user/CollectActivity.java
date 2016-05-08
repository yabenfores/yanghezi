package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.order.AllOrderFragment;
import com.raoleqing.yangmatou.ui.order.OrderActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CollectActivity extends BaseActivity implements OnClickListener{
	
	
	private ImageView country_return;
	private LinearLayout collect_itemt01;
	private LinearLayout collect_itemt02;
	
	
	private int contentIndex = 0;
	private FragmentManager manager;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_activity);

		setTitleText("收藏");
		manager = getSupportFragmentManager();
		viewInfo();
	}

	protected void viewInfo() {

		country_return = (ImageView) findViewById(R.id.activity_return);
		collect_itemt01 = (LinearLayout) findViewById(R.id.collect_itemt01);
		collect_itemt02 = (LinearLayout) findViewById(R.id.collect_itemt02);
		
		country_return.setOnClickListener(this);
		collect_itemt01.setOnClickListener(this);
		collect_itemt02.setOnClickListener(this);
		
		
		setView(0);
	

		setProgressVisibility(View.GONE);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.brands_return:
			CollectActivity.this.onBackPressed();
			break;
			
		case R.id.collect_itemt01:

			if (contentIndex != 0) {
				setView(0);
			}
			break;
		case R.id.collect_itemt02:
			
			if (contentIndex != 0) {
				setView(0);
			}
			break;

		default:
			break;
		}

	}
	

	
	private void setView(int i) {
		// TODO Auto-generated method stub

		transaction = manager.beginTransaction();

		switch (i) {
		
		case 0:
			contentIndex = 0;
			

			Fragment fragment01 = CollectShopFragment.newInstance();
			transaction.replace(R.id.collect_content, fragment01, "CollectShopFragment");
			transaction.commit();

			break;
		case 1:
			contentIndex = 1;

			
			
			Fragment fragment02 = CollectGoodsFragment.newInstance();
			transaction.replace(R.id.collect_content, fragment02, "CollectGoodsFragment");
			transaction.commit();

		

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
