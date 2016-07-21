package com.raoleqing.yangmatou.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.uitls.WindowManagerUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *专题
 * **/
public class SpecialTopicFragment extends Fragment {
	
	

	private int width;
	private int height;

	private WebView webView;
	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		
		}

	};
	

//	SpecialTopicFragment() {
//	}

	public static Fragment newInstance() {
		Fragment fragment = new SpecialTopicFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WindowManagerUtils manage = new WindowManagerUtils(getActivity());
		width = manage.getWidth();
		height = manage.getHeight();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.lyo_special_fgm, null);
		viewInfo(view);
		getWeb();
		return view;
	}

	private void getWeb() {
		try {

			NetHelper.topics(new NetConnectionInterface.iConnectListener3() {
				@Override
				public void onStart() {
					if (getActivity()==null) return;
					((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);
				}

				@Override
				public void onFinish() {
					if (getActivity()==null) return;
					((MainActivity) getActivity()).setProgressVisibility(View.GONE);
				}

				@Override
				public void onSuccess(JSONObject result) {
					JSONObject jsonObject = result.optJSONObject(Constant.DATA);
					String url = jsonObject.optString("url");
					webView.loadUrl(url);
				}

				@Override
				public void onFail(JSONObject result) {
					((MainActivity) getActivity()).makeShortToast(result.optString(Constant.INFO));
				}
			});

		} catch (Exception e) {
			throw e;
		}
	}

	private void viewInfo(View view) {

		webView = (WebView) view.findViewById(R.id.wv_special);
		WebSettings webSettings=webView.getSettings();
		webSettings.setSupportZoom(true);//支持缩放
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);//支持JS
		webSettings.setBuiltInZoomControls(false); //隐藏放大缩小按键
		webSettings.setUseWideViewPort(false);  //不允许用户双击放大
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				String[] strings = url.split("[?]");
				String pr = strings[1];
				String parm=pr.replace("&",",");
				parm=parm.replace("=",",");
				String[] str=parm.split(",");
				JSONObject jsonObject=new JSONObject();
				for (int i=0;i<str.length;i=i+2){
					try {
						jsonObject.put(str[i],str[i+1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				String app=jsonObject.optString("app");
				if (!app.equals("true")) {
					view.loadUrl(url);
					((MainActivity)getActivity()).setCodeVisible(View.INVISIBLE);
					((MainActivity)getActivity()).setWebBack(View.VISIBLE);
				}else {
					int i=jsonObject.optInt("goods_id");
					Intent intent = new Intent(getActivity(), GoodsDetail.class);
					intent.putExtra("goods_id", i);
					startActivity(intent);
				}
				return true;
			}
		});
		webView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键时的操作
						webView.goBack();   //后退
						if (!webView.canGoBack()){
							((MainActivity)getActivity()).setCodeVisible(View.VISIBLE);
							((MainActivity)getActivity()).setWebBack(View.GONE);
						}
						return true;    //已处理
					}
				}
				return false;
			}
		});


	}



}
