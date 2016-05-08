package com.raoleqing.yangmatou.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.StoreAdapter;
import com.raoleqing.yangmatou.ben.AdvManage;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.Pavilion;
import com.raoleqing.yangmatou.ben.Store;
import com.raoleqing.yangmatou.common.CheckNet;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.ChildViewPager.OnSingleTouchListener;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.common.MyPagerAdapter;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.WindowManagerUtils;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.xlist.XListView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/*
 *购物广场
 * **/
public class GouWuGuangChangFragment extends Fragment implements XListView.IXListViewListener {

	private XListView listView;
	private View advManageView;
	private ChildViewPager main_viewPager;
	private LinearLayout main_viewPager_point;
	private LinearLayout main_pavilion_layout;// 国家布局
	private List<AdvManage> advManageList = new ArrayList<AdvManage>();
	private List<Store> storeList = new ArrayList<Store>();
	private StoreAdapter storeAdapter;
	private List<Pavilion> pavilionList = new ArrayList<Pavilion>();

	private int page = 1;
	private boolean isData = true;
	private int pagerIndex = 0;// 广告页面
	private int pointsCount = 0;

	private int width;
	private int height;

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:

				String name = (String) msg.obj;
				// 收藏
				showFavoritesStoreDialog(msg.arg1, msg.arg2, name);

				break;
			case 1:
				String name01 = (String) msg.obj;
				// 取消
				showCancelStore(msg.arg1, msg.arg2, name01);

				break;

			default:
				break;
			}
		}

	};

	public static Fragment newInstance() {
		Fragment fragment = new GouWuGuangChangFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		WindowManagerUtils manage = new WindowManagerUtils(getActivity());
		width = manage.getWidth();
		height = manage.getHeight();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.gou_wu_guang_chang, null);

		viewInfo(view);
		getAdvertising();
		getStoreList();
		getPavilion();
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub

		listView = (XListView) view.findViewById(R.id.gou_wu_listview);
		listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
		storeAdapter = new StoreAdapter(getActivity(), storeList, myHandler);
		// InfoOnItemClick infoItem = new InfoOnItemClick();
		// listview.setOnItemClickListener(infoItem);
		listView.setAdapter(storeAdapter);
		listView.setXListViewListener(this);
		listView.setPullLoadEnable(true);// 上拉刷新
		listView.setPullRefreshEnable(true);// 下拉刷新

		if (listView.getHeaderViewsCount() < 2) {

			if (advManageView == null) {
				advManageView = getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.adv_manage,
						null);
			}

			main_viewPager = (ChildViewPager) advManageView.findViewById(R.id.main_viewPager);// 广告栏
			main_viewPager_point = (LinearLayout) advManageView.findViewById(R.id.main_viewPager_point);// 页数提示点
			main_pavilion_layout = (LinearLayout) advManageView.findViewById(R.id.main_pavilion_layout);// 页数提示点

			float viewPagerHeight = (float) height * 0.23f;

			LayoutParams params = main_viewPager.getLayoutParams();
			params.height = (int) viewPagerHeight;
			main_viewPager.setLayoutParams(params);
			listView.addHeaderView(advManageView);

			main_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					changePositionImage(arg0);
					pagerIndex = arg0;
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
				}
			});

		}

	}

	/**
	 * 广告数据加载
	 **/
	private void getAdvertising() {
		// TODO Auto-generated method stub

		((MainActivity) getActivity()).setMainProgress(View.VISIBLE);

		HttpUtil.get(getActivity(), HttpUtil.ADV_MANAGE, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				advertisingResolveJson(response);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				((MainActivity) getActivity()).setMainProgress(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.out.println("onFinish");

			}

		});
	}

	/**
	 * 广告数据解析
	 **/
	protected void advertisingResolveJson(JSONObject response) {
		// TODO Auto-generated method stub

		System.out.println("广告:" + response);

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((MainActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}

			if (code == 200) {
				JSONArray data = response.optJSONArray("data");
				if (advManageList.size() > 0) {
					advManageList.retainAll(advManageList);
				}
				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					AdvManage mAdvManage = new AdvManage();
					mAdvManage.setC_id(obj.optInt("c_id"));
					mAdvManage.setAdv_id(obj.optInt("adv_id"));
					mAdvManage.setAdv_type(obj.optString("adv_type"));
					mAdvManage.setAdv_name(obj.optString("adv_name"));
					mAdvManage.setAdv_image(obj.optString("adv_image"));
					mAdvManage.setAdv_url(obj.optString("adv_url"));
					advManageList.add(mAdvManage);
				}

				setAdvManage();

			}

			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		((MainActivity) getActivity()).setMainProgress(View.GONE);

	}

	/**
	 * 广告内容
	 **/
	private void setAdvManage() {
		// TODO Auto-generated method stub

		ArrayList<View> viewList = new ArrayList<View>();
		for (int i = 0; i < advManageList.size(); i++) {

			final AdvManage nAdvManage = advManageList.get(i);
			ImageView img = new ImageView(getActivity());
			img.setScaleType(ScaleType.CENTER_CROP);
			img.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			ImageLoader.getInstance().displayImage(nAdvManage.getAdv_image(), img,
					YangMaTouApplication.imageOption(R.drawable.adv_manage_image));
			viewList.add(img);
		}

		MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);

		main_viewPager.setAdapter(pagerAdapter);
		pointsCount = advManageList.size();

		loadPositionImage();

		main_viewPager.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch() {
				// TODO Auto-generated method stub
				// System.out.println("ChildViewPager --> onclick()");
				if (!CheckNet.isNetworkConnected(getActivity())) {
					Toast.makeText(getActivity(), "没有可用的网络连接，请检查网络设置", 1).show();
					return;
				}
				AdvManage nAdvManage = advManageList.get(pagerIndex);

				Intent intent = new Intent(getActivity(), GoodsListActivity.class);
				intent.putExtra("cid", nAdvManage.getC_id());
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			}
		});

	}

	// 店铺列表
	private void getStoreList() {
		// TODO Auto-generated method stub

		((MainActivity) getActivity()).setMainProgress(View.VISIBLE);

		RequestParams params = new RequestParams();
		params.put("p", page);

		HttpUtil.post(getActivity(), HttpUtil.GET_STORE_LIST, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				StoreListResolveJson(response);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				((MainActivity) getActivity()).setMainProgress(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.out.println("onFinish");

			}

		});

	}

	protected void StoreListResolveJson(JSONObject response) {

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((MainActivity) getActivity()).setMainProgress(View.GONE);
				onLoad();
				return;
			}

			if (code == 200) {
				JSONArray data = response.optJSONArray("data");
				if (storeList.size() > 0 && page == 1) {
					storeList.retainAll(storeList);
				}

				if (data.length() < 10) {
					isData = false;
				}

				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					System.out.println("aaaaaaaaaa:"+response.toString());
					Store mStore = new Store();
					mStore.setId(obj.optInt("id"));
					mStore.setStore_id(obj.optInt("store_id"));
					mStore.setStore_name(obj.optString("store_name"));
					mStore.setTitle(obj.optString("title"));
					mStore.setContent(obj.optString("content"));
					mStore.setCreate_time(obj.optLong("create_time"));
					mStore.setState(obj.optInt("state"));
					mStore.setAddress(obj.optString("address"));
					mStore.setFans(obj.optString("fans"));
					mStore.setImg(obj.optString("img"));
					mStore.setAttention(obj.optInt("attention"));
					JSONArray goods_list = obj.optJSONArray("goods_list");
					List<Goods> goodsList = new ArrayList<Goods>();

					for (int j = 0; j < goods_list.length(); j++) {

						JSONObject goodsContent = goods_list.optJSONObject(j);
						Goods mGoods = new Goods();
						mGoods.setGoods_id(goodsContent.optInt("goods_id"));
						mGoods.setStore_name(goodsContent.optString("store_name"));
						mGoods.setGoods_image(goodsContent.optString("goods_image"));
						mGoods.setGoods_promotion_price(goodsContent.optDouble("goods_promotion_price"));
						goodsList.add(mGoods);

					}

					mStore.setGoods_list(goodsList);

					storeList.add(mStore);
				}

				page++;
				storeAdapter.notifyDataSetChanged();
				onLoad();

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getActivity(), "数据加载失败", 1).show();
		}

		((MainActivity) getActivity()).setMainProgress(View.GONE);

	}

	/**
	 * 国家馆:
	 **/
	private void getPavilion() {
		// TODO Auto-generated method stub
		((MainActivity) getActivity()).setMainProgress(View.VISIBLE);

		HttpUtil.get(getActivity(), HttpUtil.GET_PAVILION, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				pavilionResolveJson(response);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				((MainActivity) getActivity()).setMainProgress(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.out.println("onFinish");

			}

		});

	}

	/**
	 * 国家馆:
	 **/
	protected void pavilionResolveJson(JSONObject response) {
		// TODO Auto-generated method stub

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((MainActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}

			if (code == 1) {
				JSONArray data = response.optJSONArray("data");
				if (pavilionList.size() > 0) {
					pavilionList.retainAll(pavilionList);
				}
				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					Pavilion mPavilion = new Pavilion();
					mPavilion.setFlag_id(obj.optInt("flag_id"));
					mPavilion.setFlag_name(obj.optString("flag_name"));
					mPavilion.setFlag_imgSrc(obj.optString("flag_imgSrc"));
					pavilionList.add(mPavilion);
				}

				setPavilion();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		((MainActivity) getActivity()).setMainProgress(View.GONE);

	}

	/**
	 * 关注店铺
	 **/
	protected void showFavoritesStoreDialog(final int position, final int store_id, String store_name) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("您确定要关注：" + store_name).setTitle("提示")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						favoritesStore(position, store_id);
						dialog.dismiss();
					}
				}).setNegativeButton("取消", null).show();

	}

	/**
	 * 取消店铺
	 **/
	protected void showCancelStore(final int position, final int store_id, String store_name) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("您确定要取消关注：" + store_name).setTitle("提示")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						cancelStore(position, store_id);
						dialog.dismiss();
					}
				}).setNegativeButton("取消", null).show();

	}

	/**
	 * 关注店铺
	 **/
	protected void favoritesStore(final int position, int store_id) {
		// TODO Auto-generated method stub
		// Home/Index/favoritesstore

		((MainActivity) getActivity()).setMainProgress(View.VISIBLE);

		int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");

		RequestParams params = new RequestParams();
		params.put("fid", store_id);
		params.put("uid", uid);
//		params.put("uid", 1090);

		HttpUtil.post(getActivity(), HttpUtil.FAVORITES_SHOP_STORE, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				favoritesResolveJson(response, position);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				((MainActivity) getActivity()).setMainProgress(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.out.println("onFinish");

			}

		});

	}

	protected void favoritesResolveJson(JSONObject response, int position) {
		// TODO Auto-generated method stub

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((MainActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}

			if (code == 1 || code == 200) {

				storeList.get(position).setAttention(1);
				storeAdapter.notifyDataSetChanged();
			}

			Toast.makeText(getActivity(), message, 1).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getActivity(), "关注失败", 1).show();
		}

		((MainActivity) getActivity()).setMainProgress(View.GONE);

	}

	/**
	 * 关注店铺
	 **/
	protected void cancelStore(final int position, int store_id) {
		// TODO Auto-generated method stub
		// Home/Index/cancelStore

		((MainActivity) getActivity()).setMainProgress(View.VISIBLE);

		int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");

		RequestParams params = new RequestParams();
		params.put("fid", store_id);
		params.put("uid", uid);
//		params.put("uid", 1090);

		HttpUtil.post(getActivity(), HttpUtil.CANCEL_SHOP_STORE, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				cancelResolveJson(response, position);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				((MainActivity) getActivity()).setMainProgress(View.GONE);
			}

			// 结束
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.out.println("onFinish");

			}

		});

	}

	protected void cancelResolveJson(JSONObject response, int position) {

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((MainActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}

			if (code == 1 || code == 200) {

				storeList.get(position).setAttention(0);
				storeAdapter.notifyDataSetChanged();
			}

			Toast.makeText(getActivity(), message, 1).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getActivity(), "取消失败", 1).show();
		}

		((MainActivity) getActivity()).setMainProgress(View.GONE);
		// TODO Auto-generated method stub

	}

	private void setPavilion() {
		// TODO Auto-generated method stub

		main_pavilion_layout.removeAllViews();

		RadioGroup myRadioGroup = new RadioGroup(getActivity());
		myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
		main_pavilion_layout.addView(myRadioGroup);
		for (int i = 0; i < pavilionList.size(); i++) {
			final Pavilion mPavilion = pavilionList.get(i);
			ImageView radio = new ImageView(getActivity());
			ImageLoader.getInstance().displayImage(mPavilion.getFlag_imgSrc(), radio,
					YangMaTouApplication.imageOption(R.drawable.pavilion_icon));

			LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(UnitConverterUtils.dip2px(getActivity(), 100),
					UnitConverterUtils.dip2px(getActivity(), 80));
			radio.setLayoutParams(l);
			radio.setScaleType(ScaleType.CENTER_CROP);

			radio.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), GoodsListActivity.class);
					intent.putExtra("cid", mPavilion.getFlag_id());
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				}
			});
			myRadioGroup.addView(radio);
		}

	}

	// 获取网络数据之后 加载 圆点图标*/
	protected void loadPositionImage() {
		ImageView aImageView = null;
		main_viewPager_point.removeAllViews();
		for (int i = 0; i < pointsCount; i++) {
			aImageView = new ImageView(getActivity());
			aImageView.setPadding(10, 0, 10, 0);
			if (i == 0) {
				aImageView.setImageResource(R.drawable.point2);
			} else {
				aImageView.setImageResource(R.drawable.point1);
			}
			main_viewPager_point.addView(aImageView);
		}
	}

	/**
	 * 更改提示h
	 */
	public void changePositionImage(int aPos) {

		ImageView aImageView = (ImageView) main_viewPager_point.getChildAt(aPos);
		if (aImageView != null) {
			aImageView.setImageResource(R.drawable.point2);
		}
		for (int i = 0; i < pointsCount; i++) {
			if (i != aPos) {
				aImageView = (ImageView) main_viewPager_point.getChildAt(i);
				aImageView.setImageResource(R.drawable.point1);
			}
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		if (isData) {
			getStoreList();
		} else {
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page = 1;
		isData = true;
		getStoreList();

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		SimpleDateFormat refleshSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		listView.setRefreshTime(refleshSimpleDateFormat.format(new Date()));
	}

}
