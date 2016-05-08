package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShopAdapter.ViewHolder;
import com.raoleqing.yangmatou.ben.CollectShop;
import com.raoleqing.yangmatou.ben.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectShopAdapter extends BaseAdapter{
	
	private List<CollectShop> sowShatList;
	private LayoutInflater mInflater;
	private Context context;
	
	private int ItemHeight = 0;

	CollectShopAdapter() {

	}

	public CollectShopAdapter(Context context, List<CollectShop> sowShatList ) {
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
			convertView = mInflater.inflate(R.layout.collect_shop_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CollectShop mCollectShop = sowShatList.get(position);
			
	/*	ImageLoader.getInstance().displayImage(mThreeData.getGc_thumb(), holder.cat_goodes_image,
				YangMaTouApplication.imageOption(R.drawable.image_icon01));
		holder.cat_goodes_text.setText(mThreeData.getGc_name());
		*/
	
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
