package com.raoleqing.yangmatou;

import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 所有窗口的父级activity 主要放 标题， 没有网络提示
 **/
public class BaseActivity extends FragmentActivity {

	private LinearLayout parentLinearLayout;// 把父类activity和子类activity的view都add到这里
	private RelativeLayout base_title_layout;// 标题布局
	private TextView activity_title;// 标题布局
	private ImageView activity_return;// 返回
	private EditText activity_search;
	private View progress;
	private int progressIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initContentView(R.layout.base_activity);
		infoView();
		Log.e("Activity:",getClass().getName());
	}

	/**
	 * 初始化contentview
	 */
	private void initContentView(int layoutResID) {
		// TODO Auto-generated method stub
		ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
		viewGroup.removeAllViews();
		parentLinearLayout = new LinearLayout(this);
		parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
		viewGroup.addView(parentLinearLayout);
		LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

		progress = LayoutInflater.from(this).inflate(R.layout.progress, null);
		viewGroup.addView(progress);

		// 不作任何处理
		progress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void setContentView(int layoutResID) {

		LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

	}

	@Override
	public void setContentView(View view) {

		parentLinearLayout.addView(view);
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {

		parentLinearLayout.addView(view, params);

	}

	/**
	 * 控件初始化
	 **/
	private void infoView() {
		// TODO Auto-generated method stub
		base_title_layout = (RelativeLayout) findViewById(R.id.base_title_layout);
		activity_title = (TextView) findViewById(R.id.activity_title);
		activity_return = (ImageView) findViewById(R.id.activity_return);
		activity_search = (EditText) findViewById(R.id.activity_search);
		
		activity_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				
				return false;
			}
		});
		

	}

	/**
	 * 设置标题的可视化
	 **/
	public void setTitleVisibility(int visibility) {

		if (base_title_layout != null)
			base_title_layout.setVisibility(visibility);

	}

	/**
	 * 搜索可见
	 **/
	public void setSearchVisibility() {

		if (activity_search != null)
			activity_search.setVisibility(View.VISIBLE);

		activity_title.setVisibility(View.GONE);

	}

	/**
	 * 加载条的显示与隐藏
	 **/
	public void setProgressVisibility(int visibility) {

		progress.setVisibility(visibility);

	}

	/**
	 * 加载条的显示与隐藏
	 **/
	public void showProgress() {

		if (progress != null) {
			progressIndex++;
			progress.setVisibility(View.VISIBLE);
		}

	}

	public void hideProgress() {

		if (progress != null) {
			progressIndex--;
			if (progressIndex <= 0) {
				progressIndex = 0;
				progress.setVisibility(View.GONE);
			}

		}

	}

	/**
	 * 设置标题
	 **/
	public void setTitleText(String text) {
		if (activity_title != null) {
			activity_title.setText(text);
		}

	}

	// 返回
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		System.gc();
	}

	public static Context getAppContext(){
		return YangMaTouApplication.getAppContext();
	}


	public void makeLongToast(String msg) {
		ToastUtil.MakeLongToast(getAppContext(), msg);
	}

	public void makeShortToast(String msg) {
		ToastUtil.MakeShortToast(getAppContext(), msg);
	}

	public void makeShortToast(int resId) {
		ToastUtil.MakeShortToast(getAppContext(), resId);
	}

	public void makeLongToast(int resId) {
		ToastUtil.MakeLongToast(getAppContext(), resId);
	}

	public void throwEx(Exception e) {
		e.printStackTrace();
//		switch (BaseApplication.getDebugMode()) {
//			case Debug:
//				makeToast("crash");
//				break;
//			case Test:
//				throw new CException(e.getMessage());
//			case Release:
//				MobclickHelper.reportError(e);
//				break;
//		}
	}
}
