package com.raoleqing.yangmatou;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatActivity;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleVisibility(View.GONE);
        setProgressVisibility(View.GONE);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String type = uri.getQueryParameter("share_type");
                String id = uri.getQueryParameter("share_id");
                if (type.equals("1")) {
                    Intent i = new Intent(getAppContext(), GoodsDetail.class);
                    i.putExtra("goods_id", Integer.parseInt(id));
                    startActivity(i);
                    finish();
                }
                if (type.equals("2")){
                    Intent i = new Intent(getAppContext(), ShowShatActivity.class);
                    i.putExtra("goods_id", Integer.parseInt(id));
                }
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        }
    }
}
