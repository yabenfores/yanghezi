package com.raoleqing.yangmatou.webserver;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;

import org.json.JSONException;
import org.json.JSONObject;

public class WebActivity extends BaseActivity implements View.OnClickListener {


    private ImageView iv_return;
    private WebView webView;
    private String title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        viewInfo();
    }

    private void viewInfo() {
        iv_return = (ImageView) findViewById(R.id.activity_return);
        iv_return.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.ww_web);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitleText(title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
                }else {
                    int i=jsonObject.optInt("goods_id");
                    Intent intent = new Intent(getAppContext(), GoodsDetail.class);
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
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        setTitleText(webView.getTitle());
        setProgressVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {
                case R.id.activity_return:
                    this.onBackPressed();
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            throwEx(e);
        }

    }
}
