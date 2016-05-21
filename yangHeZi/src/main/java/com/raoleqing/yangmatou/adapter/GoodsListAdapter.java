package com.raoleqing.yangmatou.adapter;

import java.text.DecimalFormat;
import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListAdapter extends BaseAdapter {

	List<Goods> goodsList;
	private LayoutInflater mInflater;

	GoodsListAdapter() {

	}

	public GoodsListAdapter(Context context, List<Goods> goodsList ) {
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
			convertView = mInflater.inflate(R.layout.goods_list_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		try {
			
			Goods mGoods = goodsList.get(position);
			
			ImageLoader.getInstance().displayImage(mGoods.getGoods_image(), holder.goods_image,
					YangMaTouApplication.imageOption(R.drawable.image_icon01));
			// 0无促销，1团购，2限时折扣
			// goods_promotion_price 原价是：goods_marketprice
			// 折扣：goods_promotion_price/goods_marketprice*10
			double goods_promotion_price = mGoods.getGoods_promotion_price();
			double goods_marketprice = mGoods.getGoods_marketprice();
			holder.goods_price.setText("￥" + goods_promotion_price);
			holder.goods_price01.setText("￥" + goods_marketprice);

			double discount = goods_promotion_price / goods_marketprice * 10;
			DecimalFormat df = new DecimalFormat("###.0");
			if (mGoods.getGoods_promotion_type() == 0) {
				holder.goods_discount.setText(df.format(discount) + "");
				holder.goods_discount.setVisibility(View.GONE);
			} else if (mGoods.getGoods_promotion_type() == 1) {
				holder.goods_discount.setText("团购");
				holder.goods_discount.setVisibility(View.VISIBLE);
			} else {
				holder.goods_discount.setText(df.format(discount) + "");
				holder.goods_discount.setVisibility(View.VISIBLE);
			}

			holder.goods_name.setText(mGoods.getGoods_name());
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		
		
		
		
		return convertView;
	}

	class ViewHolder {

		ImageView goods_image;
		TextView goods_price;
		TextView goods_price01;
		TextView goods_discount;
		TextView goods_name;

		public ViewHolder(View convertView) {

			this.goods_image = (ImageView) convertView.findViewById(R.id.goods_image);
			this.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
			this.goods_price01 = (TextView) convertView.findViewById(R.id.goods_price01);
			this.goods_discount = (TextView) convertView.findViewById(R.id.goods_discount);
			this.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
			
			this.goods_price01.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  

		}

	}


}
