package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 商品收藏 
 **/
public class CollectGoodsFragment extends Fragment implements OnClickListener {

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
		View view = inflater.inflate(R.layout.payment_fragment, null);

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
		null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
		payment_listview = (ListView) view.findViewById(R.id.payment_listview);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
