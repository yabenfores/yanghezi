package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.R.color;
import com.raoleqing.yangmatou.ben.OneCat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首页标题栏
 **/
public class OneCatAdapter extends BaseAdapter {

	private Context context;
	List<OneCat> oneCatList;
	private LayoutInflater mInflater;
	private int index = 0;

	OneCatAdapter() {

	}

	public OneCatAdapter(Context context, List<OneCat> oneCatList) {
		this.context = context;
		this.oneCatList = oneCatList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return oneCatList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return oneCatList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.one_cat, null);

			LinearLayout one_cat_layout = (LinearLayout) convertView.findViewById(R.id.one_cat_layout);
			TextView one_cat_text = (TextView) convertView.findViewById(R.id.one_cat_text);
			View one_cat_view = (View) convertView.findViewById(R.id.one_cat_view);

			OneCat MOneCat = oneCatList.get(position);
			one_cat_text.setText(MOneCat.getGc_name());

			if (position == this.index) {

				one_cat_view.setBackgroundResource(R.color.line02);
				one_cat_layout.setBackgroundResource(R.color.bg04);
			} else {
				one_cat_view.setBackgroundResource(R.color.line01);
				one_cat_layout.setBackgroundResource(R.color.text03);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
