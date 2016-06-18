package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.CircleImageView;
import com.raoleqing.yangmatou.ui.login.loginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;


/**
 * 我的合字
 * **/
public class UserContentFragment extends Fragment implements OnClickListener{
	

	private TextView user_fragemnt_tiem01;
	private TextView user_fragemnt_tiem02;
	private TextView user_fragemnt_tiem03;
	private TextView user_fragemnt_tiem04;
	private TextView user_fragemnt_tiem05;
	private TextView user_fragemnt_tiem06;

//	UserContentFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new UserContentFragment();
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
		View view = inflater.inflate(R.layout.user_content_fragment, null);

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
	
		user_fragemnt_tiem01 = (TextView) view.findViewById(R.id.user_fragemnt_tiem01);
		user_fragemnt_tiem02 = (TextView) view.findViewById(R.id.user_fragemnt_tiem02);
		user_fragemnt_tiem03 = (TextView) view.findViewById(R.id.user_fragemnt_tiem03);
		user_fragemnt_tiem04 = (TextView) view.findViewById(R.id.user_fragemnt_tiem04);
		user_fragemnt_tiem05 = (TextView) view.findViewById(R.id.user_fragemnt_tiem05);
		user_fragemnt_tiem06 = (TextView) view.findViewById(R.id.user_fragemnt_tiem06);

		user_fragemnt_tiem01.setOnClickListener(this);
		user_fragemnt_tiem02.setOnClickListener(this);
		user_fragemnt_tiem03.setOnClickListener(this);
		user_fragemnt_tiem04.setOnClickListener(this);
		user_fragemnt_tiem05.setOnClickListener(this);
		user_fragemnt_tiem06.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		
		case R.id.user_fragemnt_tiem01:
			// 安全
			
			
			Intent intent01 = new Intent(getActivity(), SafetyActivity.class);
			startActivity(intent01);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.user_fragemnt_tiem02:
			// 我的收藏

			break;
		case R.id.user_fragemnt_tiem03:
			// 等级说明

			break;
		case R.id.user_fragemnt_tiem04:
			// 在线客服

			break;
		case R.id.user_fragemnt_tiem05:
			// 关于我们

			break;
		case R.id.user_fragemnt_tiem06:
			// 设置
			
			Intent intent6 = new Intent(getActivity(), SetActivity.class);
			startActivity(intent6);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


			break;

		default:
			break;
		}

	}

}
