package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShowShatAdapter.ViewHolder;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.ben.ShowShat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdapter  extends BaseAdapter{
	

	private List<Shop> sowShatList;
	private LayoutInflater mInflater;
	private Context context;
	
	private int ItemHeight = 0;

	ShopAdapter() {

	}

	public ShopAdapter(Context context, List<Shop> sowShatList ) {
		this.sowShatList = sowShatList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sowShatList.size();
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return sowShatList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.shop_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Shop mShop = sowShatList.get(position);
			
		ItemHeight = convertView.getHeight();
		System.out.println("拿到的高度: " + ItemHeight);
		return convertView;
	}

	class ViewHolder {

		ImageView cat_goodes_image;
		TextView cat_goodes_text;

		public ViewHolder(View convertView) {

			this.cat_goodes_image = (ImageView) convertView.findViewById(R.id.cat_goodes_image);
			this.cat_goodes_text = (TextView) convertView.findViewById(R.id.cat_goodes_text);

		}

	}



}
