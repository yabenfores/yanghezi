package com.raoleqing.yangmatou.webserver;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;

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
        setTitleText(title);
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
