package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.order.AllOrderFragment;
import com.raoleqing.yangmatou.ui.order.OrderActivity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import entity.NotifyUpdateEntity;

public class CollectActivity extends BaseActivity implements OnClickListener {


    private ImageView country_return;
    private LinearLayout collect_itemt01;
    private LinearLayout collect_itemt02;
    private TextView mTv00, mTv01;

    private int contentIndex = 0;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_activity);

        setTitleText("我的收藏");
        manager = getSupportFragmentManager();
        viewInfo();
    }

    protected void viewInfo() {

        country_return = (ImageView) findViewById(R.id.activity_return);
        collect_itemt01 = (LinearLayout) findViewById(R.id.collect_itemt01);
        collect_itemt02 = (LinearLayout) findViewById(R.id.collect_itemt02);
        mTv00 = (TextView) findViewById(R.id.user_fragment_view00_text);
        mTv01 = (TextView) findViewById(R.id.user_fragment_view01_text);
        country_return.setOnClickListener(this);
        collect_itemt01.setOnClickListener(this);
        collect_itemt02.setOnClickListener(this);


        setView(0);


        setProgressVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.activity_return:
                finish();
                break;

            case R.id.collect_itemt01:

                setView(0);
                break;
            case R.id.collect_itemt02:

                setView(1);
                break;

            default:
                break;
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setView(int i) {
        // TODO Auto-generated method stub

        transaction = manager.beginTransaction();

        switch (i) {

            case 0:
                findViewById(R.id.user_fragment_view00).setVisibility(View.VISIBLE);
                findViewById(R.id.user_fragment_view01).setVisibility(View.INVISIBLE);
                mTv00.setTextColor(0xFFE81258);
                mTv01.setTextColor(Color.BLACK);
                Fragment fragment01 = CollectShopFragment.newInstance();
                transaction.replace(R.id.collect_content, fragment01, "CollectShopFragment");
                transaction.commitAllowingStateLoss();

                break;
            case 1:
                findViewById(R.id.user_fragment_view00).setVisibility(View.INVISIBLE);
                findViewById(R.id.user_fragment_view01).setVisibility(View.VISIBLE);
                mTv01.setTextColor(0xFFE81258);
                mTv00.setTextColor(Color.BLACK);
                Fragment fragment02 = CollectGoodsFragment.newInstance();
                transaction.replace(R.id.collect_content, fragment02, "CollectGoodsFragment");
                transaction.commitAllowingStateLoss();


                break;

            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }

    //----------------
    public final static String LISTCHAGE = "listchage";
    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case LISTCHAGE:
                    setView(0);
                    break;

            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }

}
