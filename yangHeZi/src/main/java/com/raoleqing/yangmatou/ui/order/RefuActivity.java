package com.raoleqing.yangmatou.ui.order;

import android.os.Bundle;
import android.view.View;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;

public class RefuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refu);
        setProgressVisibility(View.GONE);
    }
}
