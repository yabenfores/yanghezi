package com.raoleqing.yangmatou.ui.shop;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShopAdapter;
import com.raoleqing.yangmatou.adapter.ShowShatAdapter;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.webserver.HttpUtil;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 商店
 **/
public class ShopActivity extends BaseActivity implements OnClickListener {

	private ImageView activity_return;
	private LinearLayout shop_introduction;
	private LinearLayout shop_filter;
	private LinearLayout shop_layout01;
	private LinearLayout shop_layout02;
	private ListView shop_list;

	private int introductionShow = 0;
	private int filterShow = 0;
	
	private List<Shop> sowShatList = new ArrayList<Shop>();
	private  ShopAdapter adapter;

	private int store_id;

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
		setContentView(R.layout.shop_activity);

		Intent intent = this.getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		setTitleText("店铺");
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		shop_introduction = (LinearLayout) findViewById(R.id.shop_introduction);
		shop_filter = (LinearLayout) findViewById(R.id.shop_filter);
		shop_layout01 = (LinearLayout) findViewById(R.id.shop_layout01);
		shop_layout02 = (LinearLayout) findViewById(R.id.shop_layout02);
		shop_list = (ListView) findViewById(R.id.shop_list);
		
		for(int i = 0;i < 10; i++){
			sowShatList.add(new Shop());
		}
		
		adapter = new ShopAdapter(this,sowShatList);
		shop_list.setAdapter(adapter);

		shop_introduction.setOnClickListener(this);
		shop_filter.setOnClickListener(this);
		activity_return.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			ShopActivity.this.onBackPressed();
			break;
		case R.id.shop_introduction:

			if (introductionShow == 0) {
				introductionShow = 1;
				shop_layout01.setVisibility(View.VISIBLE);
			} else {
				introductionShow = 0;
				shop_layout01.setVisibility(View.GONE);
			}

			break;
		case R.id.shop_filter:

			if (filterShow == 0) {
				filterShow = 1;
				shop_layout02.setVisibility(View.VISIBLE);
			} else {
				filterShow = 0;
				shop_layout02.setVisibility(View.GONE);
			}

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
