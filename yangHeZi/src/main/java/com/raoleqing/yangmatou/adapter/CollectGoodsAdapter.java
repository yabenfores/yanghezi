package com.raoleqing.yangmatou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.CollectShop;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsPayActivity;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.ui.user.CollectActivity;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

import java.util.List;

public class CollectGoodsAdapter extends BaseAdapter{

	private List<CollectShop> sowShatList;
	private LayoutInflater mInflater;
	private CollectActivity context;
	private int height;

	private int ItemHeight = 0;

	CollectGoodsAdapter() {

	}

	public CollectGoodsAdapter(CollectActivity context, List<CollectShop> sowShatList ) {
		this.context = context;
		this.sowShatList = sowShatList;
		height = UnitConverterUtils.dip2px(context, 80);
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
			convertView = mInflater.inflate(R.layout.collect_goods_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			final CollectShop mStore = sowShatList.get(position);
			ImageLoader.getInstance().displayImage(mStore.getStore_img(), holder.store_icon,
					YangHeZiApplication.imageOption(R.drawable.store_icon));
			ImageLoader.getInstance().displayImage(mStore.getGoods_image(), holder.goods_image);
			holder.store_name.setText(mStore.getStore_name());
			holder.store_content.setText(mStore.getContent());

			holder.goods_name.setText(mStore.getGoods_name());
			holder.goods_price.setText(mStore.getGoods_price()+"");

			holder.btn_buy.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, GoodsPayActivity.class);
					intent.putExtra("goodsNumber", 1);
					intent.putExtra("goods_promotion_price", mStore.getGoods_price());
					intent.putExtra("goods_marketprice", mStore.getGoods_marketprice());
					intent.putExtra("goods_id", mStore.getGoods_id());
					intent.putExtra("goodsName", mStore.getGoods_name());
					intent.putExtra("goods_image", mStore.getGoods_image());
					context.startActivity(intent);
				}
			});
			holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					NetHelper.cancelPro(mStore.getGoods_id()+"", new NetConnectionInterface.iConnectListener3() {
						@Override
						public void onStart() {
							context.setProgressVisibility(View.VISIBLE);
						}

						@Override
						public void onFinish() {

							context.setProgressVisibility(View.GONE);
						}

						@Override
						public void onSuccess(JSONObject result) {
							BaseActivity.sendNotifyUpdate(CollectActivity.class,LISTCHAGE);
						}

						@Override
						public void onFail(JSONObject result) {
							context.makeShortToast(result.optString(Constant.INFO));
						}
					});

				}
			});

			// 进入店铺
			holder.store_layout.setOnClickListener(new View.OnClickListener() {

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

		CollectShop mCollectShop = sowShatList.get(position);
			

		return convertView;
	}

	class ViewHolder {

		Button btn_cancel;
		Button btn_buy;
		ImageView cat_goodes_image;
		TextView cat_goodes_text;
		ImageView store_icon;
		ImageView goods_image;
//		Button but_attention;
		TextView store_name;
		TextView goods_name;
		TextView goods_price;
		TextView store_fans;
		TextView store_content;
		LinearLayout store_image_layout;
//		TextView store_address;
		private RelativeLayout store_layout;

		public ViewHolder(View convertView) {
			this.cat_goodes_image = (ImageView) convertView.findViewById(R.id.cat_goodes_image);
			this.cat_goodes_text = (TextView) convertView.findViewById(R.id.cat_goodes_text);
			this.store_icon = (ImageView) convertView.findViewById(R.id.store_icon);
			this.goods_image = (ImageView) convertView.findViewById(R.id.goods_image);
//			this.but_attention = (Button) convertView.findViewById(R.id.but_attention);
			this.store_name = (TextView) convertView.findViewById(R.id.store_name);
			this.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
			this.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
			this.store_fans = (TextView) convertView.findViewById(R.id.store_fans);
			this.store_content = (TextView) convertView.findViewById(R.id.store_content);
			this.store_image_layout = (LinearLayout) convertView.findViewById(R.id.store_image_layout);
			this.store_layout = (RelativeLayout) convertView.findViewById(R.id.store_layout);
//			this.store_address = (TextView) convertView.findViewById(R.id.store_address);
			this.btn_cancel= (Button) convertView.findViewById(R.id.btn_cancel);
			this.btn_buy= (Button) convertView.findViewById(R.id.btn_buy);
		}

	}
	//----------------
	public final static String LISTCHAGE = "listchage";

}
