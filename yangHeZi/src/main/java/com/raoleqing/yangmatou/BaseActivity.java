package com.raoleqing.yangmatou;

import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.HashMap;

import entity.NotifyUpdateEntity;

/**
 * 所有窗口的父级activity 主要放 标题， 没有网络提示
 **/
public class BaseActivity extends FragmentActivity {

	private LinearLayout parentLinearLayout;// 把父类activity和子类activity的view都add到这里
	private RelativeLayout base_title_layout;// 标题布局
	private TextView activity_title;// 标题布局
	private ImageView activity_return;// 返回
	private EditText activity_search;
	protected View progress;
	private int progressIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initContentView(R.layout.base_activity);
		infoView();
		addNotifyUpdate();
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


	//---------------------------------------------------------------------------------------
	public final static String NOTIFY_CREATE = "notify_create";
	public final static String NOTIFY_RESUME = "notify_resume";
	public final static String NOTIFY_FINISH = "notify_finish";
	private Handler notifyUpdateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			notifyUpdate((NotifyUpdateEntity) msg.obj);
		}
	};

	protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
		switch (notifyUpdateEntity.getNotifyTag()) {
			case NOTIFY_FINISH:
				finish();
				break;
		}
	}

	private static HashMap<String, HashMap<Integer, Handler>> notifyUpdateMap = new HashMap<>(20, 10);

	public static <Aty extends BaseActivity> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, Object entity) {
		sendNotifyUpdate(fgmClass, notifyTag, entity, 0);
	}

	public static <Aty extends BaseActivity> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag) {
		sendNotifyUpdate(fgmClass, notifyTag, null, 0);
	}

	public static <Aty extends BaseActivity> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, long delay) {
		sendNotifyUpdate(fgmClass, notifyTag, null, delay);
	}

	public static <Aty extends BaseActivity> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, Object entity, long delay) {
		String tag = fgmClass.getName();
		if (!notifyUpdateMap.containsKey(tag)) return;
		NotifyUpdateEntity notifyUpdateEntity = new NotifyUpdateEntity(notifyTag, entity);
		HashMap<Integer, Handler> handlerList = notifyUpdateMap.get(tag);
		Handler handler;
		Message message;
		for (int i : handlerList.keySet()) {
			handler = handlerList.get(i);
			message = handler.obtainMessage();
			message.what = handler.hashCode();
			message.obj = notifyUpdateEntity;
			handler.sendMessageDelayed(message, delay);
		}
	}

	private void addNotifyUpdate() {
		String tag = this.getClass().getName();
		HashMap<Integer, Handler> handlerList;
		if (notifyUpdateMap.containsKey(tag)) {
			handlerList = notifyUpdateMap.get(tag);
			if (!handlerList.containsKey(notifyUpdateHandler.hashCode()))
				handlerList.put(notifyUpdateHandler.hashCode(), notifyUpdateHandler);
		} else {
			handlerList = new HashMap<>(2, 2);
			handlerList.put(notifyUpdateHandler.hashCode(), notifyUpdateHandler);
			notifyUpdateMap.put(tag, handlerList);
		}
	}

	private void removeNotifyUpdate() {
		String tag = this.getClass().getName();
		if (!notifyUpdateMap.containsKey(tag)) return;
		HashMap<Integer, Handler> handlerList = notifyUpdateMap.get(tag);
		notifyUpdateHandler.removeMessages(notifyUpdateHandler.hashCode());
		handlerList.remove(notifyUpdateHandler.hashCode());
		if (handlerList.size() == 0)
			notifyUpdateMap.remove(tag);
	}

	@Override
	protected void onDestroy() {
		removeNotifyUpdate();
		super.onDestroy();
	}

}