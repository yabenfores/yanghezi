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
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ben.City;
import com.raoleqing.yangmatou.ben.Province;
import com.raoleqing.yangmatou.ben.Zone;
import com.raoleqing.yangmatou.db.CtiyDBManage;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditAddressActivity extends BaseActivity implements OnClickListener {

	private ImageView address_edit_return;
	private TextView address_edit_save;
	private EditText address_name;
	private EditText address_phone;
	private TextView address_text01;
	private EditText address_text02;
	private CheckBox address_default;
	private TextView address_locate;
	
	private RelativeLayout address_list_layout;

	private Address mAddress = null;
	private int address_id = 0;
	private String addressName;
	private String addressPhone;
	private String addressText01;
	private String addressText02;

	private int city_id = 0;
	private int area_id = 0;
	
	private String[] provinceArray = new String[34];
	private String[] cityArray = new String[60];
	private String[] zoneArray = new String[60];

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
				setDayText();
				break;

			case 2:
				showDatePickerDialog();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_address);
		setTitleVisibility(View.GONE);
		mAddress = (Address) this.getIntent().getSerializableExtra("Address");

		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {

		address_edit_return = (ImageView) findViewById(R.id.address_edit_return);
		address_edit_save = (TextView) findViewById(R.id.address_edit_save);
		address_name = (EditText) findViewById(R.id.address_name);
		address_phone = (EditText) findViewById(R.id.address_phone);
		address_text01 = (TextView) findViewById(R.id.address_text01);
		address_text02 = (EditText) findViewById(R.id.address_text02);
		address_default = (CheckBox) findViewById(R.id.address_default);
		address_locate = (TextView) findViewById(R.id.address_locate);
		address_list_layout = (RelativeLayout) findViewById(R.id.address_list_layout);
		provinceNP = (NumberPicker) findViewById(R.id.province_pick);
		provinceNP.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
		cityNP = (NumberPicker) findViewById(R.id.city_pick);
		cityNP.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
		zoneNP = (NumberPicker) findViewById(R.id.zone_pick);
		zoneNP.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);


		address_edit_return.setOnClickListener(this);
		address_edit_save.setOnClickListener(this);
		address_locate.setOnClickListener(this);
		address_text01.setOnClickListener(this);
		address_list_layout.setOnClickListener(this);

		if (mAddress != null) {

			address_id = mAddress.getAddress_id();
			addressName = mAddress.getTrue_name();
			addressPhone = mAddress.getMob_phone();
			addressText01 = mAddress.getArea_info();
			addressText02 = mAddress.getAddress();

			address_name.setText(addressName);
			address_phone.setText(addressPhone);
			address_text02.setText(addressText02);
			address_text01.setText(addressText01);

		}
		
		for(int i = 0; i< cityArray.length;i++){
			cityArray[i] = "";
		}
		for(int i = 0; i< zoneArray.length;i++){
			zoneArray[i] = "";
		}

		setProgressVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.address_edit_return:
			EditAddressActivity.this.onBackPressed();
			break;
		case R.id.address_edit_save:

			getSaveData();

			break;

		case R.id.address_locate:

			Intent intent03 = new Intent(EditAddressActivity.this, MapActivity.class);
			startActivityForResult(intent03, 0);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.address_text01:

			setProgressVisibility(View.VISIBLE);
			myHandler.sendEmptyMessage(2);

			break;
		case R.id.address_list_layout:
			address_list_layout.setVisibility(View.GONE);
			break;

		default:
			break;
		}

	}

	/**
	 * 获取保存数据
	 **/
	private void getSaveData() {
		// TODO Auto-generated method stub
		String name = address_name.getText().toString();

		if (name == null || name.trim().equals("")) {
			Toast.makeText(EditAddressActivity.this, "请输入收件人名字", 1).show();
			return;

		}

		String phone = address_phone.getText().toString();

		if (phone == null || phone.trim().equals("")) {
			Toast.makeText(EditAddressActivity.this, "请输入收件人电话", 1).show();
			return;

		}
		String text01 = address_text01.getText().toString();

		if (text01 == null || text01.trim().equals("")) {
			Toast.makeText(EditAddressActivity.this, "请输入收件人地址", 1).show();
			return;

		}
		String text02 = address_text02.getText().toString();

		if (text02 == null || text02.trim().equals("")) {
			Toast.makeText(EditAddressActivity.this, "请输入收件人详细地址", 1).show();
			return;

		}

		if (address_id != 0) {
			editaddress(address_id, name, area_id, city_id, text02, text01, phone, phone);
		} else {
			addAddress(name, 0, 0, text02, text01, phone, phone);
		}

	}

	/*
	 * 添加地址
	 ***/
	private void addAddress(String true_name, int area_id, int city_id, String area_info, String address,
			String tel_phone, String mob_phone) {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams();
		params.put("true_name", true_name);
		params.put("area_id", area_id);// 地区id
		params.put("city_id", city_id);// 城市id
		params.put("area_info", area_info);// 地区内容
		params.put("address", address);// 地址
		params.put("tel_phone", tel_phone);// 座机电话
		params.put("mob_phone", mob_phone);// 座机电话

		HttpUtil.post1(EditAddressActivity.this, HttpUtil.ADD_ADDRESS, params, new JsonHttpResponseHandler() {

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

		System.out.println("地址： " + response);

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(EditAddressActivity.this, message, 1).show();
				setProgressVisibility(View.GONE);
				return;
			}

			if (code == 1 || code == 200) {

				EditAddressActivity.this.onBackPressed();
			}

			Toast.makeText(EditAddressActivity.this, message, 1).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		setProgressVisibility(View.GONE);

	}

	/*
	 * 编辑地址
	 ***/
	private void editaddress(int address_id, String true_name, int area_id, int city_id, String area_info,
			String address, String tel_phone, String mob_phone) {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams();
		params.put("address_id", address_id);// 用户名id

		params.put("true_name", true_name);
		params.put("area_id", area_id);// 地区id
		params.put("city_id", city_id);// 城市id
		params.put("area_info", area_info);// 地区内容
		params.put("address", address);// 地址
		params.put("tel_phone", tel_phone);// 座机电话
		params.put("mob_phone", mob_phone);// 座机电话

		HttpUtil.post(EditAddressActivity.this, HttpUtil.EDIT_ADDRESS, params, new JsonHttpResponseHandler() {

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

	private NumberPicker provinceNP;
	private NumberPicker cityNP;
	private NumberPicker zoneNP;
	private int provinceIndex = 0;
	private int cityIndex = 0;
	private int zoneIndex = 0;

	List<Province> provinceList;
	List<City> cityList;
	List<Zone> zoneList;

	

	/**
	 * 选择器
	 **/
	private void showDatePickerDialog() {
		// TODO Auto-generated method stub

		int nowYear, showMonth, showDay;// 当年,月，日
		int showYear;// 八字上显示的年份

		address_list_layout.setVisibility(View.VISIBLE);
		
		getProvince();

		provinceNP.setDisplayedValues(provinceArray);
		provinceNP.setMinValue(0);
		provinceNP.setMaxValue(provinceArray.length - 1);
		provinceNP.setValue(provinceIndex);

		provinceNP.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					provinceIndex = provinceNP.getValue();

					cityIndex = 0;
					Province mProvince = provinceList.get(provinceIndex);
					getCity(mProvince.getProSort());
					if(cityList.size() > 0){
						cityNP.setDisplayedValues(cityArray);
						cityNP.setMinValue(0);
						cityNP.setMaxValue(cityList.size() - 1);
					}
					

					zoneIndex = 0;
					City mCity = cityList.get(cityIndex);
					getZone(mCity.getCitySort());
					if(zoneList.size() > 0){
						zoneNP.setDisplayedValues(zoneArray);
						zoneNP.setMinValue(0);
						zoneNP.setMaxValue(zoneList.size() - 1);
						zoneNP.setValue(0);

					}
					
					Message msg = myHandler.obtainMessage();
					msg.what = 1;
					myHandler.sendMessageDelayed(msg, 500);

					break;
				}

			}
		});

		cityIndex = 0;
		Province mProvince = provinceList.get(provinceIndex);
		getCity(mProvince.getProSort());
		cityNP.setDisplayedValues(cityArray);
		cityNP.setMinValue(0);
		cityNP.setMaxValue(cityList.size() - 1);
		cityNP.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					cityIndex = cityNP.getValue();

					zoneIndex = 0;
					City mCity = cityList.get(cityIndex);
					getZone(mCity.getCitySort());
					if(zoneList.size() > 0){
						zoneNP.setDisplayedValues(zoneArray);
						zoneNP.setMinValue(0);
						zoneNP.setMaxValue(zoneList.size() - 1);
						zoneNP.setValue(0);
					}
					

					Message msg = myHandler.obtainMessage();
					msg.what = 1;
					myHandler.sendMessageDelayed(msg, 500);
					break;
				}

			}
		});

		zoneIndex = 0;
		City mCity = cityList.get(cityIndex);
		getZone(mCity.getCitySort());
		zoneNP.setDisplayedValues(zoneArray);
		zoneNP.setMinValue(0);
		zoneNP.setMaxValue(zoneList.size() - 1);
		zoneNP.setValue(0);

		zoneNP.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					zoneIndex = zoneNP.getValue();

					Message msg = myHandler.obtainMessage();
					msg.what = 1;
					myHandler.sendMessageDelayed(msg, 500);
					break;

				default:
					break;
				}

			}
		});

		setDayText();
		

		setProgressVisibility(View.GONE);

	}

	private void setDayText() {
		// TODO Auto-generated method stub

		String proName = provinceList.get(provinceNP.getValue()).getProName();
		String cityName = cityList.get(cityNP.getValue()).getCityName();
		String zoneName = zoneList.get(zoneNP.getValue()).getZoneName();
		addressText01 = proName + "" + cityName + " " + zoneName;

		city_id = cityList.get(cityNP.getValue()).getArea_id();
		area_id = zoneList.get(zoneNP.getValue()).getCityID();

		address_text01.setText(addressText01);

	}

	private void getProvince() {

		CtiyDBManage db = new CtiyDBManage(EditAddressActivity.this);
		provinceList = db.queryProvince();
		for (int i = 0; i < provinceArray.length; i++) {
			Province mProvince = provinceList.get(i);
			provinceArray[i] = mProvince.getProName();
		}
		db.closeDb();
	}

	private void getCity(int proSort) {

		CtiyDBManage db = new CtiyDBManage(EditAddressActivity.this);
		cityList = db.queryCity(proSort + "");
		for (int i = 0; i < cityList.size(); i++) {
			City mCity = cityList.get(i);
			cityArray[i] = mCity.getCityName();
		}
		db.closeDb();
	}

	private void getZone(int CityID) {

		CtiyDBManage db = new CtiyDBManage(EditAddressActivity.this);
		zoneList = db.queryZone(CityID + "");
		for (int i = 0; i < zoneList.size(); i++) {
			Zone mZone = zoneList.get(i);
			zoneArray[i] = mZone.getZoneName();
		}
		db.closeDb();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		switch (arg1) {
		case 1:
//			PoiInfo mPoiInfo = (PoiInfo) arg2.getSerializableExtra("PoiInfo");
			//inent.putExtra("city", mPoiInfo.city);
			//inent.putExtra("address", mPoiInfo.address);
			addressText01 = arg2.getStringExtra("city");
			addressText02 = arg2.getStringExtra("address");
			address_text01.setText(addressText01);
			address_text02.setText(addressText02);

			// CtiyDBManage db = new CtiyDBManage(EditAddressActivity.this);

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
