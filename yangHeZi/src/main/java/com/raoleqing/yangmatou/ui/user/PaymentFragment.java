package com.raoleqing.yangmatou.ui.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.OrderAdapter;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

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
 * 待支付
 **/
public class PaymentFragment extends Fragment implements OnClickListener {

	private LinearLayout null_data_layout;
	private ListView payment_listview;
	
	private List<Order> orderList = new ArrayList<Order>();
	private OrderAdapter adapter;

//	PaymentFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new PaymentFragment();
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
	String state_type= "10";

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

}

protected void resolveJson(JSONObject response) {
	// TODO Auto-generated method stub
	
	System.out.println(response);

	try {

		if (response == null) {
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
				mOrder.setOrder_sn(obj.optString("order_sn"));
				mOrder.setGoods_amount(obj.optDouble("goods_amount"));
				mOrder.setOrder_amount(obj.optDouble("order_amount"));
				mOrder.setStore_name(obj.optString("store_name"));
				mOrder.setStore_label(obj.optString("store_label"));
				mOrder.setGoods_num(obj.optInt("goods_num"));
				mOrder.setShipping_fee(obj.optDouble("shipping_fee"));
				JSONArray array=obj.getJSONArray("extend_order_goods");
				JSONObject jsonObject=array.optJSONObject(0);
				mOrder.setExtend_order_goods(jsonObject);
				mOrder.setOrder_state(obj.optInt("order_state"));
				mOrder.setPay_sn(obj.optString("pay_sn"));
				mOrder.setAdd_time(obj.optLong("add_time"));
				orderList.add(mOrder);
				
			}
			
			
			adapter.notifyDataSetChanged();

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

	((OrderActivity) getActivity()).setMainProgress(View.GONE);

}

}
