package com.raoleqing.yangmatou.ui.address;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.LocNearAddressAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MapActivity extends BaseActivity implements OnClickListener {

	private ImageView activity_return;
	private ListView lvLocNear;
	// 定位相关
	LocationClient mLocClient;
	BitmapDescriptor mCurrentMarker;
	private PoiSearch poiSearch;// 搜索

	public MyLocationListenner myListener = new MyLocationListenner();
	private List<com.baidu.mapapi.search.core.PoiInfo> nearList = new ArrayList<>();
	private LocNearAddressAdapter adapter;

	MapView mMapView;
	BaiduMap mBaiduMap;

	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true; // 是否首次定位

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 地图初始化
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map_activity);
		setTitleText("添加地址");
		viewInfo();

	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		lvLocNear = (ListView) findViewById(R.id.lv_location_nearby);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 初始化搜索模块
		poiSearch = PoiSearch.newInstance();
		// 注册搜索事件监听
		poiSearch.setOnGetPoiSearchResultListener(new PoiSearchResultListener() {
			@Override
			public void onGetPoiResult(PoiResult poiResult) {

			}
		});
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		adapter = new LocNearAddressAdapter(MapActivity.this, nearList);
		lvLocNear.setAdapter(adapter);
		activity_return.setOnClickListener(this);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocClient.setLocOption(option);
		mLocClient.start();

		lvLocNear.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent inent = MapActivity.this.getIntent();
				com.baidu.mapapi.search.core.PoiInfo mPoiInfo = nearList.get(arg2);
				inent.putExtra("city", mPoiInfo.city);
				inent.putExtra("address", mPoiInfo.address);
				MapActivity.this.setResult(1, inent);
				MapActivity.this.onBackPressed();
			}
		});

		getData();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			MapActivity.this.onBackPressed();
			break;

		default:
			break;
		}

	}

	double mCurrentLantitude;
	double mCurrentLongitude;

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
				mCurrentLantitude = location.getLatitude();
				mCurrentLongitude = location.getLongitude();

				new Thread(new Runnable() {
					@Override
					public void run() {
						searchNeayBy();
					}
				}).start();

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/**
	 * 搜索周边地理位置
	 */
	private void searchNeayBy() {
		System.out.println(mCurrentLantitude + "," + mCurrentLongitude);

		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.keyword("");
		option.sortType(PoiSortType.distance_from_near_to_far);
		option.location(new LatLng(mCurrentLantitude, mCurrentLongitude));
		option.pageCapacity(20);
		option.pageNum(10);
		option.radius(1000);
		poiSearch.searchNearby(option);

	}

	private abstract class PoiSearchResultListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {

			System.out.println("PoiDetailResult()");

			if (result.error != SearchResult.ERRORNO.NO_ERROR) {

			} else {
				System.out.println(result.getName() + ": " + result.getAddress());

			}
		}

		@Override
		public void onGetPoiResult(PoiResult result) {

			System.out.println("onGetPoiResult()");

			nearList.removeAll(nearList);

			if (result != null) {
				if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
					nearList=result.getAllPoi();
					adapter.notifyDataSetChanged();
					if (nearList != null && nearList.size() > 0) {
						for (int i = 0; i < nearList.size(); i++) {
							System.out.println(nearList.get(i).address);
							System.out.println(nearList.get(i).name);
						}

					}

				}
			}

		}

	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	private void getData() {
		// TODO Auto-generated method stub

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
