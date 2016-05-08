package com.raoleqing.yangmatou.ui.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.CollectShopAdapter;
import com.raoleqing.yangmatou.ben.CollectShop;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.HttpUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 商加收藏
 **/
public class CollectShopFragment extends Fragment implements OnClickListener {

	private LinearLayout null_data_layout;
	private ListView payment_listview;

	private List<CollectShop> collectShopList = new ArrayList<CollectShop>();
	private CollectShopAdapter adapter;

//	CollectShopFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new CollectShopFragment();
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
		View view = inflater.inflate(R.layout.payment_fragment, null);

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
		null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
		payment_listview = (ListView) view.findViewById(R.id.payment_listview);

		for (int i = 0; i < 10; i++) {
			collectShopList.add(new CollectShop());
		}
		adapter = new CollectShopAdapter(getActivity(), collectShopList);
		payment_listview.setAdapter(adapter);
		null_data_layout.setVisibility(View.GONE);

		getData();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void getData() {

		//((OrderActivity) getActivity()).setMainProgress(View.VISIBLE);

		int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");
		RequestParams params = new RequestParams();
		params.put("uid", uid);

		HttpUtil.post(getActivity(), "Home/Users/member_fglist", params, new JsonHttpResponseHandler() {

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
				//((OrderActivity) getActivity()).setMainProgress(View.GONE);
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

		System.out.println("Home/Users/member_fglist: " + response);

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(getActivity(), message, 1).show();
				((OrderActivity) getActivity()).setMainProgress(View.GONE);
				return;
			}

			if (code == 200 || code == 1) {
				JSONArray data = response.optJSONArray("data");

				// adapter.notifyDataSetChanged();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getActivity(), "数据加载失败", 1).show();
		}

		//((OrderActivity) getActivity()).setMainProgress(View.GONE);

	}

}
