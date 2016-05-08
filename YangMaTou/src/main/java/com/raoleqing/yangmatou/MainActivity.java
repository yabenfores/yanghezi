package com.raoleqing.yangmatou;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raoleqing.yangmatou.ben.OneCat;
import com.raoleqing.yangmatou.main.DealsFragment;
import com.raoleqing.yangmatou.main.GouWuGuangChangFragment;
import com.raoleqing.yangmatou.main.SpecialTopicFragment;
import com.raoleqing.yangmatou.ui.classification.ClassificationActivity;
import com.raoleqing.yangmatou.ui.classification.MipcaActivityCapture;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.ui.login.loginActivity;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatFragment;
import com.raoleqing.yangmatou.ui.user.UserFragment;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout main_title_layout;
	private LinearLayout gou_wu_message;// 消息
	private LinearLayout gou_wu_advisory;// 在线
	private EditText activity_search;// 搜索
	private LinearLayout gor_wu_title_layout;

	private LinearLayout main_host01;
	private LinearLayout main_host02;
	private LinearLayout main_host03;
	private LinearLayout main_host04;
	private LinearLayout main_host05;
	private ImageView main_host_image01;
	private ImageView main_host_image02;
	private ImageView main_host_image03;
	private ImageView main_host_image04;
	private ImageView main_host_image05;
	private TextView main_host_text01;
	private TextView main_host_text02;
	private TextView main_host_text03;
	private TextView main_host_text04;
	private TextView main_host_text05;

	private int contentIndex = 0;
	private FragmentManager manager;
	private FragmentTransaction transaction;

	private List<OneCat> oneCatList = new ArrayList<OneCat>();// 标准内容
	private List<OneCat> towCatList = new ArrayList<OneCat>();// 标准内容
	private String oneCatContent;
	private String towCatContent;
	
	private final static int SCANNIN_GREQUEST_CODE = 1;

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
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();

		setTitleVisibility(View.GONE);
		myHandler.sendEmptyMessageDelayed(0, 50);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserUitls.exitBut == 2) {
			UserUitls.exitBut = 1;
			setView(0);
		}
	}

	protected void viewInfo() {
		// TODO Auto-generated method stub
		main_title_layout = (RelativeLayout) findViewById(R.id.main_title_layout);
		gou_wu_message = (LinearLayout) findViewById(R.id.gou_wu_message);
		activity_search = (EditText) findViewById(R.id.main_search);
		gor_wu_title_layout = (LinearLayout) findViewById(R.id.gor_wu_title_layout);
		gou_wu_advisory = (LinearLayout) findViewById(R.id.gou_wu_advisory);
		main_host01 = (LinearLayout) findViewById(R.id.main_host01);
		main_host02 = (LinearLayout) findViewById(R.id.main_host02);
		main_host03 = (LinearLayout) findViewById(R.id.main_host03);
		main_host04 = (LinearLayout) findViewById(R.id.main_host04);
		main_host05 = (LinearLayout) findViewById(R.id.main_host05);
		main_host_image01 = (ImageView) findViewById(R.id.main_host_image01);
		main_host_image02 = (ImageView) findViewById(R.id.main_host_image02);
		main_host_image03 = (ImageView) findViewById(R.id.main_host_image03);
		main_host_image04 = (ImageView) findViewById(R.id.main_host_image04);
		main_host_image05 = (ImageView) findViewById(R.id.main_host_image05);

		main_host_text01 = (TextView) findViewById(R.id.main_host_text01);
		main_host_text02 = (TextView) findViewById(R.id.main_host_text02);
		main_host_text03 = (TextView) findViewById(R.id.main_host_text03);
		main_host_text04 = (TextView) findViewById(R.id.main_host_text04);
		main_host_text05 = (TextView) findViewById(R.id.main_host_text05);

		gou_wu_message.setOnClickListener(this);
		main_host01.setOnClickListener(this);
		main_host02.setOnClickListener(this);
		main_host03.setOnClickListener(this);
		main_host04.setOnClickListener(this);
		main_host05.setOnClickListener(this);
		gou_wu_advisory.setOnClickListener(this);

		activity_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				switch (actionId) {
				case EditorInfo.IME_ACTION_SEARCH:
					String search_str = activity_search.getText().toString();
					if (search_str != null && !search_str.trim().equals("")) {
						Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
						intent.putExtra("search_str", search_str);
						startActivity(intent);
						overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					}

					break;

				default:
					break;
				}
				return false;
			}
		});

		setView(0);
		IMLogin();

	}

	private void IMLogin() {
		// TODO Auto-generated method stub
		EMClient.getInstance().login("aaaa", "123456", new EMCallBack() {// 回调
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					public void run() {
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager().loadAllConversations();
						System.out.println("登陆聊天服务器成功！");

					}
				});
			}

			@Override
			public void onProgress(int progress, String status) {

			}

			@Override
			public void onError(int code, String message) {
				System.out.println("登陆聊天服务器失败！");
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.gou_wu_advisory:
//			startActivity(
//					new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "bbbb"));
			break;
		case R.id.main_host01:
			if (contentIndex != 0)
				setView(0);
			break;
		case R.id.main_host02:
			if (contentIndex != 1)
				setView(1);
			break;
		case R.id.main_host03:
			if (contentIndex != 2)
				setView(2);
			break;
		case R.id.main_host04:
			if (contentIndex != 3)
				setView(3);
			break;
		case R.id.main_host05:
			if (contentIndex != 4) {
				if (UserUitls.isLongin(MainActivity.this)) {
					setView(4);
				} else {
					Intent intent = new Intent(MainActivity.this, loginActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				}

			}

			break;
		case R.id.gou_wu_message:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;

		default:
			break;
		}

	}

	private void setView(int i) {
		// TODO Auto-generated method stub
		int text01 = getResources().getColor(R.color.text01);
		int text02 = getResources().getColor(R.color.text02);

		transaction = manager.beginTransaction();

		switch (i) {
		case 0:
			contentIndex = 0;
			main_host_image01.setBackgroundResource(R.drawable.main_host01_host);
			main_host_image02.setBackgroundResource(R.drawable.main_host02);
			main_host_image03.setBackgroundResource(R.drawable.main_host03);
			main_host_image04.setBackgroundResource(R.drawable.main_host04);
			main_host_image05.setBackgroundResource(R.drawable.main_host05);
			gor_wu_title_layout.setVisibility(View.VISIBLE);
			main_title_layout.setVisibility(View.VISIBLE);

			main_host_text01.setTextColor(text01);
			main_host_text02.setTextColor(text02);
			main_host_text03.setTextColor(text02);
			main_host_text04.setTextColor(text02);
			main_host_text05.setTextColor(text02);
			Fragment fragment01 = GouWuGuangChangFragment.newInstance();
			transaction.replace(R.id.main_content, fragment01, "MainFragment");
			transaction.commit();

			if (oneCatList.size() > 0) {
				setTitleContent(oneCatList);
			} else {
				getDataTitle(0);
			}
			break;
		case 1:
			contentIndex = 1;
			main_host_image01.setBackgroundResource(R.drawable.main_host01);
			main_host_image02.setBackgroundResource(R.drawable.main_host02_host);
			main_host_image03.setBackgroundResource(R.drawable.main_host03);
			main_host_image04.setBackgroundResource(R.drawable.main_host04);
			main_host_image05.setBackgroundResource(R.drawable.main_host05);
			gor_wu_title_layout.setVisibility(View.VISIBLE);
			main_title_layout.setVisibility(View.VISIBLE);

			main_host_text01.setTextColor(text02);
			main_host_text02.setTextColor(text01);
			main_host_text03.setTextColor(text02);
			main_host_text04.setTextColor(text02);
			main_host_text05.setTextColor(text02);

			Fragment fragment02 = DealsFragment.newInstance();
			transaction.replace(R.id.main_content, fragment02, "MainFragment");
			transaction.commit();

			if (towCatList.size() > 0) {
				setTitleContent(towCatList);
			} else {
				getDataTitle(1);
			}

			break;
		case 2:
			contentIndex = 2;
			main_host_image01.setBackgroundResource(R.drawable.main_host01);
			main_host_image02.setBackgroundResource(R.drawable.main_host02);
			main_host_image03.setBackgroundResource(R.drawable.main_host03_host);
			main_host_image04.setBackgroundResource(R.drawable.main_host04);
			main_host_image05.setBackgroundResource(R.drawable.main_host05);
			gor_wu_title_layout.setVisibility(View.VISIBLE);
			main_title_layout.setVisibility(View.VISIBLE);

			main_host_text01.setTextColor(text02);
			main_host_text02.setTextColor(text02);
			main_host_text03.setTextColor(text01);
			main_host_text04.setTextColor(text02);
			main_host_text05.setTextColor(text02);

			Fragment fragment03 = SpecialTopicFragment.newInstance();
			transaction.replace(R.id.main_content, fragment03, "MainFragment");
			transaction.commit();

			if (towCatList.size() > 0) {
				setTitleContent(towCatList);
			} else {
				getDataTitle(1);

			}

			break;
		case 3:
			contentIndex = 3;
			main_host_image01.setBackgroundResource(R.drawable.main_host01);
			main_host_image02.setBackgroundResource(R.drawable.main_host02);
			main_host_image03.setBackgroundResource(R.drawable.main_host03);
			main_host_image04.setBackgroundResource(R.drawable.main_host04_host);
			main_host_image05.setBackgroundResource(R.drawable.main_host05);
			main_title_layout.setVisibility(View.GONE);
			gor_wu_title_layout.setVisibility(View.GONE);

			main_host_text01.setTextColor(text02);
			main_host_text02.setTextColor(text02);
			main_host_text03.setTextColor(text02);
			main_host_text04.setTextColor(text01);
			main_host_text05.setTextColor(text02);

			Fragment fragment04 = ShowShatFragment.newInstance();
			transaction.replace(R.id.main_content, fragment04, "MainFragment");
			transaction.commit();

			break;
		case 4:
			contentIndex = 4;
			main_host_image01.setBackgroundResource(R.drawable.main_host01);
			main_host_image02.setBackgroundResource(R.drawable.main_host02);
			main_host_image03.setBackgroundResource(R.drawable.main_host03);
			main_host_image04.setBackgroundResource(R.drawable.main_host04);
			main_host_image05.setBackgroundResource(R.drawable.main_host05_host);
			gor_wu_title_layout.setVisibility(View.GONE);
			main_title_layout.setVisibility(View.VISIBLE);

			main_host_text01.setTextColor(text02);
			main_host_text02.setTextColor(text02);
			main_host_text03.setTextColor(text02);
			main_host_text04.setTextColor(text02);
			main_host_text05.setTextColor(text01);

			Fragment fragment05 = UserFragment.newInstance();
			transaction.replace(R.id.main_content, fragment05, "MainFragment");
			transaction.commit();

			break;

		default:
			break;
		}

	}

	private void getDataTitle(final int type) {
		// TODO Auto-generated method stub

		setMainProgress(View.VISIBLE);
		String method;
		if (type == 0) {
			method = HttpUtil.GET_ONE_CAT;
		} else {
			method = HttpUtil.CATE_GET_ONE_CAT;
		}

		HttpUtil.get(this, method, new JsonHttpResponseHandler() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				resolveJson(response, type);
			}

			// 失败
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				setMainProgress(View.GONE);
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

	protected void resolveJson(JSONObject response, int type) {
		// TODO Auto-generated method stub

		try {
			int code = response.optInt("code");
			String message = response.optString("message");

			if (response == null) {
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
				setMainProgress(View.GONE);
				return;
			}

			if (code == 200) {
				JSONArray data = response.optJSONArray("data");

				if (type == 0) {
					oneCatContent = response.toString();
					if (oneCatList.size() > 0) {
						oneCatList.retainAll(oneCatList);
					}
				} else {
					if (towCatList.size() > 0) {
						towCatList.retainAll(towCatList);
					}
					towCatContent = response.toString();
				}

				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.optJSONObject(i);
					OneCat mOneCat = new OneCat();
					mOneCat.setGc_id(obj.optInt("gc_id"));
					mOneCat.setGc_name(obj.optString("gc_name"));
					if (type == 0) {
						oneCatList.add(mOneCat);
					} else {
						towCatList.add(mOneCat);
					}

				}

				if (type == 0) {
					setTitleContent(oneCatList);
				} else {
					setTitleContent(towCatList);
				}

			}

			Toast.makeText(this, message,  Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		setMainProgress(View.GONE);

	}

	/**
	 * 标题内容
	 **/
	private void setTitleContent(List<OneCat> list) {
		// TODO Auto-generated method stub

		gor_wu_title_layout.removeAllViews();

		RadioGroup myRadioGroup = new RadioGroup(this);
		myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
		gor_wu_title_layout.addView(myRadioGroup);
		for (int i = 0; i < list.size(); i++) {
			final OneCat mOneCat = list.get(i);
			TextView radio = new TextView(this);
			LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					UnitConverterUtils.dip2px(this, 40), Gravity.CENTER);
			radio.setLayoutParams(l);
			radio.setGravity(Gravity.CENTER_VERTICAL);
			radio.setPadding(20, 0, 20, 0);
			radio.setText(mOneCat.getGc_name());
			radio.setTextColor(Color.WHITE);
			final int gcid = i;
			radio.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this, ClassificationActivity.class);
					intent.putExtra("gc_id", gcid);
					if (contentIndex == 0) {
						intent.putExtra("cat_content", oneCatContent);
					} else {
						intent.putExtra("cat_content", towCatContent);
					}
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				}
			});
			myRadioGroup.addView(radio);
		}

	}

	/*
	 * 物理返回按扭
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			showDialogExit();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDialogExit() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("您确定要退出吗？").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.onBackPressed();
			}
		}).setNegativeButton("取消", null).show();
	}

	/*
	 * 加载条显示隐藏设置
	 */
	public void setMainProgress(int visibility) {
		setProgressVisibility(visibility);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
