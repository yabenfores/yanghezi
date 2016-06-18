package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.showwhat.CountryActivity;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import org.json.JSONObject;

public class AboutActivity extends BaseActivity implements OnClickListener {


    private ImageView country_return;
    private int type;

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        type = this.getIntent().getIntExtra("type", 0);
        viewInfo();
    }

    protected void viewInfo() {
        country_return = (ImageView) findViewById(R.id.activity_return);
        country_return.setOnClickListener(this);
        webView= (WebView) findViewById(R.id.ww_app_about);
        if (type != 0) {
            setTitleText("关于进口税说明");
            setView(0);
        } else {
            setTitleText("关于洋盒子");
            setView(1);
        }
        setProgressVisibility(View.GONE);
    }

    private void setView(int i) {
        switch (i){
            case 0:
                NetHelper.tariff(new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                        setProgressVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        setProgressVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(JSONObject result) {

                        String json=result.optString(Constant.DATA);
                        if (json != null && !json.equals("")) {

//                             能够的调用JavaScript代码
                            webView.getSettings().setDefaultTextEncodingName("utf-8");
            // 加载HTML字符串进行显示
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            webView.getSettings().setUseWideViewPort(true);
                            webView.getSettings().setLoadWithOverviewMode(true);
                            webView.setSaveEnabled(true);
                            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                            webView.getSettings().setSupportZoom(true);// 支持缩放
                            webView.loadDataWithBaseURL("",json, "text/html", "UTF-8","");// 这种写法可以正确解码
                        }
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
                break;
            case 1:
                NetHelper.aboutYhz(new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                        setProgressVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onFinish() {
                        setProgressVisibility(View.GONE);

                    }

                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONObject json=result.optJSONObject(Constant.DATA);
                        String url=json.optString("url");
                        webView.loadUrl(url);
                    }

                    @Override
                    public void onFail(JSONObject result) {

                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        try {


            switch (v.getId()) {
                case R.id.activity_return:
                    AboutActivity.this.onBackPressed();
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            throwEx(e);
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }


}
