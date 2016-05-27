package com.raoleqing.yangmatou.ui.showwhat;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

public class CountryActivity extends BaseActivity implements OnClickListener {

	private ImageView country_return;
	private GridView country_gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_activity);
		setTitleVisibility(View.GONE);
		viewInfo();
	}

	protected void viewInfo() {
		country_return = (ImageView) findViewById(R.id.country_return);
		country_gridview= (GridView) findViewById(R.id.country_gridview);
		country_return.setOnClickListener(this);
		getInfo();
	}

	private void getInfo() {

		NetHelper.Flags(new NetConnectionInterface.iConnectListener3() {
			@Override
			public void onStart() {
				setProgressVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish() {

				setProgressVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject result) {

				resolveJson(result);
			}

			@Override
			public void onFail(JSONObject result) {

			}
		});
	}

	private void resolveJson(JSONObject result) {
		try {

			JSONArray jsonArray=result.optJSONArray(Constant.DATA);
			if (jsonArray==null){
				return;
			}
			for (int i=0;i<jsonArray.length();i++){

			}

		} catch (Exception e) {
			throwEx(e);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.country_return:
			CountryActivity.this.onBackPressed();
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
