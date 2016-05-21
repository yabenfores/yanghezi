package com.raoleqing.yangmatou.ui.order;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.user.PaymentFragment;
import com.raoleqing.yangmatou.ui.user.ReceiptFragment;
import com.raoleqing.yangmatou.ui.user.ReturnFragment;
import com.raoleqing.yangmatou.ui.user.ReviewFragment;
import com.raoleqing.yangmatou.ui.user.ShipFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 我的订单
 **/
public class OrderActivity extends BaseActivity implements OnClickListener {
	private ImageView activity_return;

	private LinearLayout user_fragment_item01;//
	private LinearLayout user_fragment_item02;// 待支付
	private LinearLayout user_fragment_item03;// 待发货
	private LinearLayout user_fragment_item04;// 待收货
	private LinearLayout user_fragment_item05;// 待评介
	private LinearLayout user_fragment_item06;// 退换货
	private View user_fragment_view01;
	private View user_fragment_view02;
	private View user_fragment_view03;
	private View user_fragment_view04;
	private View user_fragment_view05;
	private View user_fragment_view06;

	private int contentIndex = 0;
	private FragmentManager manager;
	private FragmentTransaction transaction;

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
		setContentView(R.layout.order_activity);
		setTitleText(" 我的订单");
		contentIndex = this.getIntent().getIntExtra("index", 0);
		manager = getSupportFragmentManager();
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);

		user_fragment_item01 = (LinearLayout) findViewById(R.id.user_fragment_item01);
		user_fragment_item02 = (LinearLayout) findViewById(R.id.user_fragment_item02);
		user_fragment_item03 = (LinearLayout) findViewById(R.id.user_fragment_item03);
		user_fragment_item04 = (LinearLayout) findViewById(R.id.user_fragment_item04);
		user_fragment_item05 = (LinearLayout) findViewById(R.id.user_fragment_item05);
		user_fragment_item06 = (LinearLayout) findViewById(R.id.user_fragment_item06);

		user_fragment_view01 = (View) findViewById(R.id.user_fragment_view01);
		user_fragment_view02 = (View) findViewById(R.id.user_fragment_view02);
		user_fragment_view03 = (View) findViewById(R.id.user_fragment_view03);
		user_fragment_view04 = (View) findViewById(R.id.user_fragment_view04);
		user_fragment_view05 = (View) findViewById(R.id.user_fragment_view05);
		user_fragment_view06 = (View) findViewById(R.id.user_fragment_view06);

		activity_return.setOnClickListener(this);
		user_fragment_item01.setOnClickListener(this);
		user_fragment_item02.setOnClickListener(this);
		user_fragment_item03.setOnClickListener(this);
		user_fragment_item04.setOnClickListener(this);
		user_fragment_item05.setOnClickListener(this);
		user_fragment_item06.setOnClickListener(this);

		getData();

		setView(0);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			OrderActivity.this.onBackPressed();
			break;
		case R.id.user_fragment_item01:
				setView(0);
			break;
		case R.id.user_fragment_item02:
				setView(1);
			break;
		case R.id.user_fragment_item03:
				setView(2);
			break;
		case R.id.user_fragment_item04:
				setView(3);
			break;
		case R.id.user_fragment_item05:
				setView(4);
			break;
		case R.id.user_fragment_item06:
				setView(5);
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
			user_fragment_view01.setVisibility(View.VISIBLE);
			user_fragment_view02.setVisibility(View.GONE);
			user_fragment_view03.setVisibility(View.GONE);
			user_fragment_view04.setVisibility(View.GONE);
			user_fragment_view05.setVisibility(View.GONE);
			user_fragment_view06.setVisibility(View.GONE);

			Fragment fragment01 = AllOrderFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment01, "MainFragment");
			transaction.commit();

			break;
		case 1:
			contentIndex = 1;

			user_fragment_view01.setVisibility(View.GONE);
			user_fragment_view02.setVisibility(View.VISIBLE);
			user_fragment_view03.setVisibility(View.GONE);
			user_fragment_view04.setVisibility(View.GONE);
			user_fragment_view05.setVisibility(View.GONE);
			user_fragment_view06.setVisibility(View.GONE);

			Fragment fragment02 = PaymentFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment02, "MainFragment");
			transaction.commit();

			break;
		case 2:
			contentIndex = 2;
			user_fragment_view01.setVisibility(View.GONE);
			user_fragment_view02.setVisibility(View.GONE);
			user_fragment_view03.setVisibility(View.VISIBLE);
			user_fragment_view04.setVisibility(View.GONE);
			user_fragment_view05.setVisibility(View.GONE);
			user_fragment_view06.setVisibility(View.GONE);

			Fragment fragment03 = ShipFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment03, "MainFragment");
			transaction.commit();

			break;
		case 3:
			contentIndex = 3;
			user_fragment_view01.setVisibility(View.GONE);
			user_fragment_view02.setVisibility(View.GONE);
			user_fragment_view03.setVisibility(View.GONE);
			user_fragment_view04.setVisibility(View.VISIBLE);
			user_fragment_view05.setVisibility(View.GONE);
			user_fragment_view06.setVisibility(View.GONE);

			Fragment fragment04 = ReceiptFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment04, "MainFragment");
			transaction.commit();

			break;
		case 4:
			contentIndex = 4;

			user_fragment_view01.setVisibility(View.GONE);
			user_fragment_view02.setVisibility(View.GONE);
			user_fragment_view03.setVisibility(View.GONE);
			user_fragment_view04.setVisibility(View.GONE);
			user_fragment_view05.setVisibility(View.VISIBLE);
			user_fragment_view06.setVisibility(View.GONE);

			Fragment fragment05 = ReviewFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment05, "MainFragment");
			transaction.commit();

			break;

		case 5:
			contentIndex = 4;

			user_fragment_view01.setVisibility(View.GONE);
			user_fragment_view02.setVisibility(View.GONE);
			user_fragment_view03.setVisibility(View.GONE);
			user_fragment_view04.setVisibility(View.GONE);
			user_fragment_view05.setVisibility(View.GONE);
			user_fragment_view06.setVisibility(View.VISIBLE);

			Fragment fragment06 = ReturnFragment.newInstance();
			transaction.replace(R.id.order_contnet, fragment06, "MainFragment");
			transaction.commit();

			break;

		default:
			break;
		}

	}

	private void getData() {
		// TODO Auto-generated method stub

		setProgressVisibility(View.GONE);

	}

	/*
	 * 加载条显示隐藏设置
	 */
	public void setMainProgress(int visibility) {
		setProgressVisibility(visibility);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}

}
