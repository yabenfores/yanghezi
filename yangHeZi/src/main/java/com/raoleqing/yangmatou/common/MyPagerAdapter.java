package com.raoleqing.yangmatou.common;

import java.util.ArrayList;

import com.raoleqing.yangmatou.R;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 * 滑动适配器
 * **/
public class MyPagerAdapter extends PagerAdapter {
	private ArrayList<View> views;

	public MyPagerAdapter(ArrayList<View> views) {
		if (views != null)
			this.views = views;
		else
			this.views = new ArrayList<View>();
	}

	@Override
	public void destroyItem(View pager, int position, Object arg2) {
		// TODO Auto-generated method stub
		View child = views.get(position);
		((ViewPager) pager).removeView(child);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public Object instantiateItem(View pager, int position) {
		// TODO Auto-generated method stub
		View child = views.get(position);
		child.setBackgroundResource(R.drawable.adv_manage_image);
		((ViewPager) pager).addView(child);
		return child;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}
