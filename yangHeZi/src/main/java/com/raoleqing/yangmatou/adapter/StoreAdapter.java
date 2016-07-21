package com.raoleqing.yangmatou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.Store;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.UserUitls;

import java.util.List;

/**
 * 首页
 **/
public class StoreAdapter extends BaseAdapter {

	private List<Store> storeList;
	private LayoutInflater mInflater;
	private Context context;
	private int height;
	private Handler myHandler;

	StoreAdapter() {

	}

	public StoreAdapter(Context context, List<Store> storeList, Handler myHandler) {
		this.context = context;
		this.storeList = storeList;
		this.myHandler = myHandler;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		height = UnitConverterUtils.dip2px(context, 80);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return storeList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return storeList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.store_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.store_icon.setImageResource(R.color.transparent);
			final Store mStore = storeList.get(position);
			ImageLoader.getInstance().displayImage(mStore.getImg(), holder.store_icon);
			holder.store_name.setText(mStore.getStore_name());
			holder.store_fans.setText("|  粉丝：" + mStore.getFans());
			holder.store_content.setText(mStore.getContent());
			holder.store_address.setText(mStore.getAddress());
			final int attention = mStore.getAttention();
			if (attention == 0) {
				holder.but_attention.setText("+ 关注");
			} else {
				holder.but_attention.setText("已关注");
			}

			holder.store_image_layout.removeAllViews();
			List<Goods> goodsList = mStore.getGoods_list();
			for (int i = 0; i < goodsList.size(); i++) {
				final Goods mGoods = goodsList.get(i);
				RelativeLayout lyo=new RelativeLayout(context);
				lyo.setLayoutParams(new RelativeLayout.LayoutParams(height,height));
				ImageView image = new ImageView(context);
				image.setScaleType(ScaleType.CENTER_CROP);
				image.setLayoutParams(new LinearLayout.LayoutParams(height, height));
				ImageLoader.getInstance().displayImage(mGoods.getGoods_image(), image,
						YangHeZiApplication.imageOption(R.drawable.image_icon01));
				lyo.addView(image);
				TextView price=new TextView(context);
				RelativeLayout.LayoutParams tv_params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				tv_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				price.setLayoutParams(tv_params);
				price.setText(mGoods.getGoods_promotion_price()+"");
				price.setGravity(Gravity.CENTER_HORIZONTAL);
				price.setTextColor(Color.WHITE);
				price.setBackgroundColor(0xB0000000);
				lyo.addView(price);
				holder.store_image_layout.addView(lyo);
				View view = new View(context);
				view.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
				holder.store_image_layout.addView(view);

				// 每个图片的点击
				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent intent = new Intent(context, GoodsDetail.class);
						intent.putExtra("goods_id", mGoods.getGoods_id());
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

					}
				});
			}

			// 关注
			holder.but_attention.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stubser
					if (UserUitls.isLongin(context)) {
						Message message = myHandler.obtainMessage();
						message.arg1 = position;
						message.arg2 = mStore.getStore_id();
						message.obj=mStore.getStore_name();
						message.what = attention;
						myHandler.sendMessage(message);
					} else {
						UserUitls.longInDialog(context);
					}

				}
			});

			// 进入店铺
			holder.store_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopActivity.class);
					intent.putExtra("store_id", mStore.getStore_id());
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}

	class ViewHolder {

		ImageView store_icon;
		Button but_attention;
		TextView store_name;
		TextView store_fans;
		TextView store_content;
		LinearLayout store_image_layout;
		TextView store_address;
		private LinearLayout store_layout;

		public ViewHolder(View convertView) {

			this.store_icon = (ImageView) convertView.findViewById(R.id.store_icon);
			this.but_attention = (Button) convertView.findViewById(R.id.but_attention);
			this.store_name = (TextView) convertView.findViewById(R.id.store_name);
			this.store_fans = (TextView) convertView.findViewById(R.id.store_fans);
			this.store_content = (TextView) convertView.findViewById(R.id.store_content);
			this.store_image_layout = (LinearLayout) convertView.findViewById(R.id.store_image_layout);
			this.store_layout = (LinearLayout) convertView.findViewById(R.id.store_layout);
			this.store_address = (TextView) convertView.findViewById(R.id.store_address);

		}

	}

}
