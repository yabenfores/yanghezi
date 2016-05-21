package com.raoleqing.yangmatou.ui.showwhat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShowShatAdapter;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.ui.order.OrderActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * 秀一下
 * **/
public class ShowShatFragment extends Fragment implements OnClickListener {

	private LinearLayout show_write_show;
	private LinearLayout show_message;
	private LinearLayout show_item01;
	private LinearLayout show_item02;
	private LinearLayout show_item03;
	private LinearLayout show_item04;
	private LinearLayout show_item05;
	private ListView show_lsit;

	private FragmentManager manager;
	private FragmentTransaction transaction;

	private List<ShowShat> sowShatList = new ArrayList<ShowShat>();
	private ShowShatAdapter adapter;
	private View advManageView;

//	ShowShatFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new ShowShatFragment();
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
		View view = inflater.inflate(R.layout.show_shat_fragment, null);
		manager = getFragmentManager();

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub

		show_write_show = (LinearLayout) view.findViewById(R.id.show_write_show);
		show_message = (LinearLayout) view.findViewById(R.id.show_message);

	
		show_lsit = (ListView) view.findViewById(R.id.show_lsit);

		for (int i = 0; i < 10; i++) {

			sowShatList.add(new ShowShat());
		}
		
		adapter = new ShowShatAdapter(getActivity(), sowShatList);
		show_lsit.setAdapter(adapter);

		if (show_lsit.getHeaderViewsCount() < 1) {

			if (advManageView == null) {
				advManageView = getActivity().getLayoutInflater().from(getActivity())
						.inflate(R.layout.show_shat_list_title, null);
			}
			
			show_item01 = (LinearLayout) advManageView.findViewById(R.id.show_item01);
			show_item02 = (LinearLayout) advManageView.findViewById(R.id.show_item02);
			show_item03 = (LinearLayout) advManageView.findViewById(R.id.show_item03);
			show_item04 = (LinearLayout) advManageView.findViewById(R.id.show_item04);
			show_item05 = (LinearLayout) advManageView.findViewById(R.id.show_item05);
			show_item01.setOnClickListener(this);
			show_item02.setOnClickListener(this);
			show_item03.setOnClickListener(this);
			show_item04.setOnClickListener(this);
			show_item05.setOnClickListener(this);


			show_lsit.addHeaderView(advManageView);
		}

	

		show_write_show.setOnClickListener(this);
		show_message.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.show_write_show:

			Intent intent = new Intent(getActivity(), OrderActivity.class);
			intent.putExtra("index", 3);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.show_message:

//			startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "bbbb"));
			break;

		case R.id.show_item01:

			setView(0);

			break;
		case R.id.show_item02:

			setView(1);

			break;
		case R.id.show_item03:
			Intent intent01 = new Intent(getActivity(), CountryActivity.class);
			startActivity(intent01);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.show_item04:

			Intent intent02 = new Intent(getActivity(), BrandsActivity.class);
			startActivity(intent02);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.show_item05:

			setView(4);

			break;

		default:
			break;
		}

	}

	private void setView(int i) {
		// TODO Auto-generated method stub

	}

}
