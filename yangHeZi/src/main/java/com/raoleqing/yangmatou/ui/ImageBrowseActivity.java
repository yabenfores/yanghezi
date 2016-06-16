package com.raoleqing.yangmatou.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.AdvManage;
import com.raoleqing.yangmatou.common.CheckNet;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.MyPagerAdapter;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.webserver.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageBrowseActivity extends BaseActivity implements View.OnClickListener {

    private ChildViewPager main_viewPager;
    private LinearLayout main_viewPager_point;

    private ArrayList<String> imageList = new ArrayList<>();

    private ImageView activity_return;
    private int pointsCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);
        setTitleVisibility(View.GONE);
        imageList=getIntent().getStringArrayListExtra("imageList");
        setProgressVisibility(View.GONE);
        viewInfo();
    }

    private void viewInfo() {
        activity_return= (ImageView) findViewById(R.id.image_return);
        activity_return.setOnClickListener(this);
        main_viewPager = (ChildViewPager) findViewById(R.id.main_viewPager);// 广告栏
        main_viewPager_point = (LinearLayout) findViewById(R.id.main_viewPager_point);// 页数提示点
        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                changePositionImage(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
        loadImage();
    }

    private void loadImage() {

        try {

            ArrayList<View> viewList = new ArrayList<View>();
            for (int i = 0; i < imageList.size(); i++) {
                final String imageUrl = imageList.get(i);
                ImageView img = new ImageView(this);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ImageLoader.getInstance().displayImage(imageUrl, img);
                viewList.add(img);
            }
            MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);
            main_viewPager.setAdapter(pagerAdapter);
            pointsCount = imageList.size();
            loadPositionImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 获取网络数据之后 加载 圆点图标*/
    protected void loadPositionImage() {
        ImageView aImageView = null;
        main_viewPager_point.removeAllViews();
        for (int i = 0; i < pointsCount; i++) {
            aImageView = new ImageView(this);
            aImageView.setPadding(10, 0, 10, 0);
            if (i == 0) {
                aImageView.setImageResource(R.drawable.point2);
            } else {
                aImageView.setImageResource(R.drawable.point1);
            }
            main_viewPager_point.addView(aImageView);
        }
    }

    /**
     * 更改提示h
     */
    public void changePositionImage(int aPos) {

        ImageView aImageView = (ImageView) main_viewPager_point.getChildAt(aPos);
        if (aImageView != null) {
            aImageView.setImageResource(R.drawable.point2);
        }
        for (int i = 0; i < pointsCount; i++) {
            if (i != aPos) {
                aImageView = (ImageView) main_viewPager_point.getChildAt(i);
                aImageView.setImageResource(R.drawable.point1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()){
                case R.id.image_return:
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
