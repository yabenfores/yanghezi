package com.raoleqing.yangmatou.ui.goods;

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
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.GoodsGridAdapter;
import com.raoleqing.yangmatou.adapter.GoodsListAdapter;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.xlist.XListView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 产品列表
 **/
public class GoodsListActivity extends BaseActivity implements OnClickListener, XListView.IXListViewListener {

	private ImageView activity_return;
	private EditText activity_search;
	private ImageView switching;
	private TextView goods_list_key01;
	private LinearLayout goods_list_key02;
	private LinearLayout goods_list_key03;
	private ImageView goods_list_keyview02;// 人气
	private ImageView goods_list_keyview03;// 价格

	private GridView goods_list_gridView;
	private XListView goods_list_listView;

	private List<Goods> goodsList = new ArrayList<Goods>();
	private GoodsListAdapter listAdapter;

	private GoodsGridAdapter gridAdapter;

	private int cid;
	private String keyword;
	private int key = 0;// int 属性 0->默认 1->销售 2->人气 3->价格;
	private int order = 1; // N int 排序 0->默认 1->asc 2->desc
	private int page = 1;

	int lastItem;// 产品下标
	int itemsCount;// 现在产品总个数

	private boolean listIsData = true;
	private int contentIndex = 0;
	private int load = 0;

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
		setContentView(R.layout.goods_list_activity);

		setTitleVisibility(View.GONE);
		Intent intent = this.getIntent();
		cid = intent.getIntExtra("cid", 0);
		keyword = intent.getStringExtra("search_str");

		setSearchVisibility();
		myHandler.sendEmptyMessageDelayed(0, 50);
	}

	protected void viewInfo() {
		// TODO Auto-generated method stub
		activity_return = (ImageView) findViewById(R.id.goods_list_return);
		activity_search = (EditText) findViewById(R.id.goods_list_search);
		switching = (ImageView) findViewById(R.id.goods_list_filter);
		goods_list_gridView = (GridView) findViewById(R.id.goods_list_gridView);
		goods_list_key01 = (TextView) findViewById(R.id.goods_list_key01);
		goods_list_key02 = (LinearLayout) findViewById(R.id.goods_list_key02);
		goods_list_key03 = (LinearLayout) findViewById(R.id.goods_list_key03);
		goods_list_keyview02 = (ImageView) findViewById(R.id.goods_list_keyview02);
		goods_list_keyview03 = (ImageView) findViewById(R.id.goods_list_keyview03);
		goods_list_listView = (XListView) findViewById(R.id.goods_list_listView);

		gridAdapter = new GoodsGridAdapter(GoodsListActivity.this, goodsList);
		goods_list_gridView.setAdapter(gridAdapter);

		goods_list_listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
		listAdapter = new GoodsListAdapter(GoodsListActivity.this, goodsList);
		goods_list_listView.setAdapter(listAdapter);
		goods_list_listView.setXListViewListener(this);
		goods_list_listView.setPullLoadEnable(true);// 上拉刷新
		goods_list_listView.setPullRefreshEnable(true);// 下拉刷新

		switching.setVisibility(View.VISIBLE);
		activity_return.setOnClickListener(this);
		switching.setOnClickListener(this);
		goods_list_key01.setOnClickListener(this);
		goods_list_key02.setOnClickListener(this);
		goods_list_key03.setOnClickListener(this);

		goods_list_gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Goods mGoods = goodsList.get(arg2);
				Intent intent = new Intent(GoodsListActivity.this, GoodsDetail.class);
				intent.putExtra("goods_id", mGoods.getGoods_id());
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			}
		});

		activity_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				switch (actionId) {
				case EditorInfo.IME_ACTION_SEARCH:
					String search_str = activity_search.getText().toString();
					if (search_str != null && !search_str.trim().equals("")) {
						keyword = search_str;
						getData();
					}

					break;

				default:
					break;
				}
				return false;
			}
		});

		// 没动更新
		goods_list_gridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (lastItem == itemsCount && scrollState == this.SCROLL_STATE_IDLE && listIsData) {
					getData();
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount; // 减1是因为上面加了个addFooterView
			}

		});

		getData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.goods_list_return:
			GoodsListActivity.this.onBackPressed();
			break;

		case R.id.goods_list_filter:

			if (contentIndex == 0) {
				contentIndex = 1;
				goods_list_gridView.setVisibility(View.GONE);
				goods_list_listView.setVisibility(View.VISIBLE);
				listAdapter.notifyDataSetChanged();

			} else {
				goods_list_gridView.setVisibility(View.VISIBLE);
				goods_list_listView.setVisibility(View.GONE);
				gridAdapter.notifyDataSetChanged();
				contentIndex = 0;

			}

			break;
		case R.id.goods_list_key01:
			// 综合
			if (key != 1) {
				key = 1;
				order = 1;
				page = 1;
				getData();
			}

			break;
		case R.id.goods_list_key02:
			// 人气
			if (key != 2) {
				key = 2;
				order = 1;
				goods_list_keyview02.setBackgroundResource(R.drawable.price_icon01);
			} else {
				order = 2;
				goods_list_keyview02.setBackgroundResource(R.drawable.price_icon02);
			}

			page = 1;
			getData();

			break;
		case R.id.goods_list_key03:
			// 价格
			key = 3;
			if (key != 3) {
				key = 3;
				order = 1;
				goods_list_keyview03.setBackgroundResource(R.drawable.price_icon01);
			} else {
				order = 2;
				goods_list_keyview03.setBackgroundResource(R.drawable.price_icon02);
			}
			page = 1;
			getData();
			break;
		default:
			break;
		}

	}

	/**
	 * 产品列表
	 **/
	private void getData() {
		// TODO Auto-generated method stub
		// Home/Cate/goodsList

		RequestParams params = new RequestParams();
		params.put("cid", cid);
		if (keyword != null && !keyword.equals("")) {
			params.put("keyword", keyword);
		}

		params.put("key", key);
		params.put("order", order);
		params.put("p", page);

		HttpUtil.post(GoodsListActivity.this, HttpUtil.GOODS_LIST, params, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				resolveJson(response);
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
				System.out.println("onFinish");

			}

		});

	}

	protected void resolveJson(JSONObject response) {
		// TODO Auto-generated method stub

		System.out.println("商品： " + response);

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(GoodsListActivity.this, message, 1).show();
				setProgressVisibility(View.GONE);
				return;
			}

			if (code == 200) {
				JSONArray data = response.optJSONArray("data");

				if (page == 1 && goodsList.size() > 0) {
					goodsList.retainAll(goodsList);
				}

				if (data.length() < 10) {
					listIsData = false;
				}

				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					Goods mGoods = new Goods();
					mGoods.setGoods_id(obj.optInt("goods_id"));
					mGoods.setGoods_commonid(obj.optInt("goods_commonid"));
					mGoods.setGoods_name(obj.optString("goods_name"));
					mGoods.setGc_id(obj.optInt("gc_id"));
					mGoods.setStore_id(obj.optInt("store_id"));
					mGoods.setStore_name(obj.optString("store_name"));
					mGoods.setGoods_price(obj.optDouble("goods_price"));
					mGoods.setGoods_promotion_price(obj.optDouble("goods_promotion_price"));
					mGoods.setGoods_promotion_type(obj.optInt("goods_promotion_type"));
					mGoods.setGoods_marketprice(obj.optDouble("goods_marketprice"));
					mGoods.setGoods_storage(obj.optInt("goods_storage"));
					mGoods.setGoods_image(obj.optString("goods_image"));
					mGoods.setGoods_salenum(obj.optInt("goods_salenum"));
					mGoods.setColor_id(obj.optInt("color_id"));

					goodsList.add(mGoods);
				}

				page++;
				itemsCount = goodsList.size();
				if (contentIndex == 0) {
					gridAdapter.notifyDataSetChanged();
				} else {
					listAdapter.notifyDataSetChanged();
					onLoad();
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(GoodsListActivity.this, "数据加载失败", 1).show();
		}

		setProgressVisibility(View.GONE);

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		if (listIsData) {
			getData();
		} else {
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page = 1;
		listIsData = true;
		getData();

	}

	private void onLoad() {
		goods_list_listView.stopRefresh();
		goods_list_listView.stopLoadMore();
		SimpleDateFormat refleshSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		goods_list_listView.setRefreshTime(refleshSimpleDateFormat.format(new Date()));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}

}
