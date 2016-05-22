package com.raoleqing.yangmatou.ui.address;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.AddressAdapter;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ui.user.SafetyActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddressActivity extends BaseActivity implements OnClickListener {

	private ImageView activity_return;
	private TextView address_add;
	private ListView address_lsit;
	private LinearLayout create_address_layout;
	private Button create_address_but;
	
	private final static int SCANNIN_GREQUEST_CODE = 1;

	private List<Address> addressList = new ArrayList<Address>();
	private AddressAdapter adapter;

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				viewInfo();
				break;
			case 1:
				// 删除
				int position = msg.arg1;

				showDelAddressDialog(position);
				break;
			case 2:
				// 编辑
				Address mAddress = addressList.get(msg.arg1);
				Intent intent03 = new Intent(AddressActivity.this, EditAddressActivity.class);
				intent03.putExtra("Address", mAddress);
				startActivity(intent03);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_activity);
		setTitleText("绑定手机");
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		address_add = (TextView) findViewById(R.id.address_add);
		address_lsit = (ListView) findViewById(R.id.address_lsit);
		create_address_layout = (LinearLayout) findViewById(R.id.create_address_layout);
		create_address_but = (Button) findViewById(R.id.create_address_but);

		activity_return.setOnClickListener(this);
		address_add.setOnClickListener(this);
		create_address_but.setOnClickListener(this);

		adapter = new AddressAdapter(this, addressList, myHandler);
		address_lsit.setAdapter(adapter);

		getData();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			AddressActivity.this.onBackPressed();
			break;
		case R.id.address_add:
			
			Intent intent02 = new Intent(AddressActivity.this, EditAddressActivity.class);
			startActivity(intent02);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			
			break;
		case R.id.create_address_but:

			Intent intent03 = new Intent(AddressActivity.this, EditAddressActivity.class);
			startActivity(intent03);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;

		default:
			break;
		}

	}

	private void getData() {
		// TODO Auto-generated method stub
		// Home/Orders/getDefaultAddress

		NetHelper.getDefaultAddress(new NetConnectionInterface.iConnectListener3() {
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
				ResolveJson(result);

			}

			@Override
			public void onFail(JSONObject result) {
				makeLongToast(result.optString(Constant.INFO));
			}
		});

	}

	protected void ResolveJson(JSONObject response) {
		// TODO Auto-generated method stub

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(AddressActivity.this, message, 1).show();
				setProgressVisibility(View.GONE);
				return;
			}

			if (addressList.size() > 0) {
				addressList.removeAll(addressList);
			}


				JSONArray data = response.optJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					Address mAddress = new Address();
					mAddress.setAddress_id(obj.optInt("address_id"));
					mAddress.setMember_id(obj.optInt("member_id"));
					mAddress.setTrue_name(obj.optString("true_name"));
					mAddress.setArea_id(obj.optInt("area_id"));
					mAddress.setCity_id(obj.optInt("city_id"));
					mAddress.setArea_info(obj.optString("area_info"));
					mAddress.setAddress(obj.optString("address"));
					mAddress.setMob_phone(obj.optString("mob_phone"));
					mAddress.setIs_default(obj.optInt("is_default"));

					addressList.add(mAddress);
				}

				if (addressList.size() > 0) {
					adapter.notifyDataSetChanged();
					create_address_layout.setVisibility(View.GONE);
				} else {
					create_address_layout.setVisibility(View.VISIBLE);
				}


			Toast.makeText(AddressActivity.this, message, 1).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(AddressActivity.this, "数据加载失败", 1).show();
		}

		setProgressVisibility(View.GONE);

	}

	/**
	 * 关注店铺
	 **/
	protected void showDelAddressDialog(final int position) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
		builder.setMessage("是否删除这条地址").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				delAddress(position);
				dialog.dismiss();
			}
		}).setNegativeButton("取消", null).show();

	}

	/**
	 * 删除地址
	 **/
	protected void delAddress(final int position) {
		// TODO Auto-generated method stub
		Address mAddress = addressList.get(position);
		int addressId = mAddress.getAddress_id();
		
		setProgressVisibility(View.VISIBLE);

		RequestParams params = new RequestParams();
		params.put("address_id", addressId);

		HttpUtil.post1(AddressActivity.this, HttpUtil.DEL_ADDRESS, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				delResolveJson(response, position);
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

	protected void delResolveJson(JSONObject response, int position) {
		// TODO Auto-generated method stub
		
		System.out.println("删除地址： " + response);
		

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(AddressActivity.this, message, 1).show();
				setProgressVisibility(View.GONE);
				return;
			}

			if (addressList.size() > 0) {
				addressList.removeAll(addressList);
			}

			if (code == 1 || code == 200) {

				addressList.remove(position);
				adapter.notifyDataSetChanged();
			}

			Toast.makeText(AddressActivity.this, message, 1).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(AddressActivity.this, "数据加载失败", 1).show();
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
