package com.raoleqing.yangmatou.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.CircleImageView;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;


/**
 * 个人中心
 **/
public class UserFragment extends Fragment implements OnClickListener {

	private CircleImageView main_user_icon;

	private FragmentManager manager;
	private FragmentTransaction transaction;
	private TextView user_order;
	private Button navBtn01;// 待支付
	private Button navBtn02;// 待发货
	private Button navBtn03;// 待收货
	private Button navBtn04;// 待评介
	private Button navBtn05;// 退换货
	private View user_fragment_view01;
	private View user_fragment_view02;
	private View user_fragment_view03;
	private View user_fragment_view04;
	private View user_fragment_view05;

	private TextView user_fragemnt_tiem01;
	private TextView user_fragemnt_tiem02;
	private TextView user_fragemnt_tiem03;
	private TextView user_fragemnt_tiem04;
	private TextView user_fragemnt_tiem05;
	private TextView user_fragemnt_tiem06;
	private TextView user_name;

//	UserFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new UserFragment();
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
		View view = inflater.inflate(R.layout.user_fragment, null);
		manager = getFragmentManager();

		viewInfo(view);
		return view;
	}

	private void viewInfo(View view) {
		// TODO Auto-generated method stub
		user_name= (TextView) view.findViewById(R.id.main_user_name);
		main_user_icon = (CircleImageView) view.findViewById(R.id.main_user_icon);
		user_order = (TextView) view.findViewById(R.id.user_order);
		navBtn01 = (Button) view.findViewById(R.id.navBtn01);
		navBtn02 = (Button) view.findViewById(R.id.navBtn02);
		navBtn03 = (Button) view.findViewById(R.id.navBtn03);
		navBtn04 = (Button) view.findViewById(R.id.navBtn04);
		navBtn05 = (Button) view.findViewById(R.id.navBtn05);
		user_fragment_view01 = (View) view.findViewById(R.id.user_fragment_view01);
		user_fragment_view02 = (View) view.findViewById(R.id.user_fragment_view02);
		user_fragment_view03 = (View) view.findViewById(R.id.user_fragment_view03);
		user_fragment_view04 = (View) view.findViewById(R.id.user_fragment_view04);
		user_fragment_view05 = (View) view.findViewById(R.id.user_fragment_view05);

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

		user_order.setOnClickListener(this);
		navBtn01.setOnClickListener(this);
		navBtn02.setOnClickListener(this);
		navBtn03.setOnClickListener(this);
		navBtn04.setOnClickListener(this);
		navBtn05.setOnClickListener(this);

		String member_avatar=SharedPreferencesUtil.getString(getActivity().getBaseContext(),"member_avatar");
//		String member_avatar="http://rescdn.qqmail.com/dyimg/20140409/72B8663B7F23.jpg";
		ImageLoader.getInstance().displayImage(member_avatar, main_user_icon,
				YangMaTouApplication.imageOption(R.drawable.user_icon));
		String name=SharedPreferencesUtil.getString(BaseActivity.getAppContext(),"member_name");
		user_name.setText(name);
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
			
			Intent intent02 = new Intent(getActivity(), CollectActivity.class);
			startActivity(intent02);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


			break;
		case R.id.user_fragemnt_tiem03:
			// 等级说明

			break;
		case R.id.user_fragemnt_tiem04:
			// 在线客服
			String user_msg_helper=SharedPreferencesUtil.getString(getActivity().getBaseContext(),"user_msg_helper");
			Intent i=new Intent(getActivity(),ChatActivity.class);
			i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
			i.putExtra("key","single");
			startActivity(i);
			break;
		case R.id.user_fragemnt_tiem05:
			// 关于我们
			
			
			Intent intent5 = new Intent(getActivity(), AboutActivity.class);
			startActivity(intent5);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.user_fragemnt_tiem06:
			// 设置

			Intent intent6 = new Intent(getActivity(), SetActivity.class);
			startActivity(intent6);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.user_order:
			setView(0);
			break;

		case R.id.navBtn01:

			setView(0);

			break;
		case R.id.navBtn02:

			setView(1);

			break;
		case R.id.navBtn03:

			setView(2);

			break;
		case R.id.navBtn04:

			setView(3);

			break;
		case R.id.navBtn05:

			setView(4);

			break;

		default:
			break;
		}

	}

	private void setView(int i) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getActivity(), OrderActivity.class);
		intent.putExtra("index", i);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

	}
//	/**
//	 * 获取网落图片资源
//	 * @param url
//	 * @return
//	 */
//	public static Bitmap getHttpBitmap(String url){
//		Bitmap bitmap=null;
//		try {
//			URL myFileUrl = new URL(url);
//			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//			conn.setDoInput(true);
//			conn.connect();
//			InputStream is = conn.getInputStream();
//			bitmap = BitmapFactory.decodeStream(is);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return bitmap;
//	}



}
