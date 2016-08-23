package com.raoleqing.yangmatou.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.CollectGoodsAdapter;
import com.raoleqing.yangmatou.ben.CollectShop;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品收藏 
 **/
public class CollectGoodsFragment extends Fragment implements OnClickListener {

	private List<CollectShop> collectShopList = new ArrayList<CollectShop>();
	private CollectGoodsAdapter adapter;
	private LinearLayout null_data_layout;
	private ListView payment_listview;

//	CollectGoodsFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new CollectGoodsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.coll_fragment, null);

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
		null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
		payment_listview = (ListView) view.findViewById(R.id.payment_listview);
		getData();
	}

	private void getData() {
		NetHelper.member_fglist(new NetConnectionInterface.iConnectListener3() {
			@Override
			public void onStart() {
				
			}

			@Override
			public void onFinish() {

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
			if (result == null) {
				((OrderActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}
			if (result.optJSONArray("data")==null) {
				return;
			}
			JSONArray data = result.optJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.optJSONObject(i);
				CollectShop mCollectShop = new CollectShop();
				mCollectShop.setStore_id(obj.optInt("store_id"));
				mCollectShop.setStore_id(obj.optInt("store_id"));
				mCollectShop.setStore_name(obj.optString("store_name"));
				mCollectShop.setTitle(obj.optString("title"));
				mCollectShop.setContent(obj.optString("content"));
				mCollectShop.setCreate_time(obj.optLong("create_time"));
				mCollectShop.setState(obj.optInt("state"));
				mCollectShop.setAddress(obj.optString("address"));
				mCollectShop.setFans(obj.optString("fans"));
				mCollectShop.setImg(obj.optString("img"));
				mCollectShop.setAttention(obj.optInt("attention"));
				mCollectShop.setGoods_image(obj.optString("goods_image"));
				mCollectShop.setGoods_name(obj.optString("goods_name"));
				mCollectShop.setStore_img(obj.optString("store_img"));
				mCollectShop.setGoods_price(obj.optDouble("goods_price"));
				mCollectShop.setGoods_marketprice(obj.optDouble("goods_marketprice"));
				collectShopList.add(mCollectShop);
			}
			adapter = new CollectGoodsAdapter((CollectActivity) getActivity(), collectShopList);
			payment_listview.setAdapter(adapter);
			null_data_layout.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
