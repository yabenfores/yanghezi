package com.raoleqing.yangmatou.ui.order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.OrderAdapter;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.address.AddressActivity;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.graphics.Paint;
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
 * 全部订单
 **/
public class AllOrderFragment extends Fragment implements OnClickListener {

	private LinearLayout null_data_layout;
	private ListView payment_listview;

	private List<Order> orderList = new ArrayList<Order>();
	private OrderAdapter adapter;
	
	//private int state_type;

//	AllOrderFragment() {
//
//	}

	public static Fragment newInstance() {
		Fragment fragment = new AllOrderFragment();
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
		getData();
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
		null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
		payment_listview = (ListView) view.findViewById(R.id.payment_listview);

		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());

		adapter = new OrderAdapter(getActivity(), orderList);

		payment_listview.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 10代付款 20待发货
30待收货 40已完成
0已取消 50待评价

*/
	private void getData(){
		
		((OrderActivity) getActivity()).setMainProgress(View.VISIBLE);

//	int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");
	RequestParams params = new RequestParams();
		String state_type="";
		NetHelper.member_order(state_type, new NetConnectionInterface.iConnectListener3() {
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
				((OrderActivity) getActivity()).setMainProgress(View.GONE);

			}
		});
//
}

protected void resolveJson(JSONObject response) {
	// TODO Auto-generated method stub
	
	System.out.println(response);

	try {
		int code = response.optInt("code");
		String message = response.optString("message");

		if (response == null) {
			Toast.makeText(getActivity(), message, 1).show();
			((OrderActivity) getActivity()).setMainProgress(View.GONE);
			return;
		}
		
			JSONArray data = response.optJSONArray("data");
			
			
			if (orderList.size() > 0) {
				orderList.removeAll(orderList);
			}
			
			for(int i = 0; i< data.length(); i++){
				JSONObject obj = data.optJSONObject(i);
				Order mOrder = new Order();
				mOrder.setOrder_id(obj.optString("order_id"));
				mOrder.setGoods_amount(obj.optDouble("goods_amount"));
				mOrder.setOrder_amount(obj.optDouble("order_amount"));
				mOrder.setStore_name(obj.optString("store_name"));
				mOrder.setOrder_state(obj.optInt("order_state"));
				mOrder.setPay_sn(obj.optString("pay_sn"));
				mOrder.setCreatetime(obj.optString("createtime"));
				orderList.add(mOrder);
				
			}
			
			
			adapter.notifyDataSetChanged();

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		Toast.makeText(getActivity(), "数据加载失败", 1).show();
	}

	((OrderActivity) getActivity()).setMainProgress(View.GONE);

}

}
