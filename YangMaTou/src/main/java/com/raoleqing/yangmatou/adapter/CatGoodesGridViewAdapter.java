package com.raoleqing.yangmatou.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.ThreeData;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CatGoodesGridViewAdapter extends BaseAdapter {

	List<ThreeData> goodsList;
	private LayoutInflater mInflater;

	CatGoodesGridViewAdapter() {

	}

	public CatGoodesGridViewAdapter(Context context, List<ThreeData> goodsList ) {
		this.goodsList = goodsList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goodsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return goodsList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.cat_goodes, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ThreeData mThreeData = goodsList.get(position);
			
		ImageLoader.getInstance().displayImage(mThreeData.getGc_thumb(), holder.cat_goodes_image,
				YangMaTouApplication.imageOption(R.drawable.image_icon01));
		holder.cat_goodes_text.setText(mThreeData.getGc_name());
		
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
