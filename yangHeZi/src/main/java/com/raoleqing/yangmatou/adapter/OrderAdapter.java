package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShowShatAdapter.ViewHolder;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.ben.ShowShat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {


    private List<Order> orderList;
    private LayoutInflater mInflater;
    private Context context;

    private int ItemHeight = 0;

    OrderAdapter() {

    }

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return orderList.size();

    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return orderList.get(arg0);
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
            convertView = mInflater.inflate(R.layout.order_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order mOrder = orderList.get(position);
        holder.store_name.setText(mOrder.getStore_name());
        holder.store_orderId.setText("订单号: " + mOrder.getOrder_id());
        holder.order_time.setText("下单时间 : " + mOrder.getCreatetime());
        holder.goods_price.setText("￥" + mOrder.getGoods_amount());
        holder.goods_price01.setText("原价： ");
        holder.order_number.setText("数量：1件        共计： ￥" + mOrder.getOrder_amount());
        holder.order_shipping.setText("（含运费" + mOrder.getShipping_fee() + "元）");
        /*
		 * 10代付款 20待发货
		30待收货 40已完成
		0已取消 50待评价*/

        switch (mOrder.getOrder_state()) {
            case 10:
                //待支付
                holder.order_state.setText("待支付");
                holder.btn_order_back.setVisibility(View.GONE);
                holder.btn_order_cancel.setVisibility(View.VISIBLE);
                holder.btn_order_pay.setVisibility(View.VISIBLE);
                holder.btn_order_tip.setVisibility(View.GONE);
                holder.btn_order_confirm.setVisibility(View.GONE);
                holder.btn_order_eval.setVisibility(View.GONE);
                break;
            case 20:
                //待发货
                holder.order_state.setText("待发货");
                holder.btn_order_back.setVisibility(View.GONE);
                holder.btn_order_cancel.setVisibility(View.GONE);
                holder.btn_order_pay.setVisibility(View.GONE);
                holder.btn_order_tip.setVisibility(View.VISIBLE);
                holder.btn_order_confirm.setVisibility(View.GONE);
                holder.btn_order_eval.setVisibility(View.GONE);
                break;
            case 30:
                //待收货
                holder.order_state.setText("待收货");
                holder.btn_order_back.setVisibility(View.GONE);
                holder.btn_order_cancel.setVisibility(View.GONE);
                holder.btn_order_pay.setVisibility(View.GONE);
                holder.btn_order_tip.setVisibility(View.GONE);
                holder.btn_order_confirm.setVisibility(View.VISIBLE);
                holder.btn_order_eval.setVisibility(View.GONE);
                break;
            case 40:
                //待评价
                holder.order_state.setText("待评价");
                holder.btn_order_back.setVisibility(View.VISIBLE);
                holder.btn_order_cancel.setVisibility(View.GONE);
                holder.btn_order_pay.setVisibility(View.GONE);
                holder.btn_order_tip.setVisibility(View.GONE);
                holder.btn_order_confirm.setVisibility(View.GONE);
                holder.btn_order_eval.setVisibility(View.VISIBLE);
                //AftermarketActivity
                break;

            default:
                break;
        }

        holder.btn_order_pay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }
        });

			
	/*	ImageLoader.getInstance().displayImage(mThreeData.getGc_thumb(), holder.cat_goodes_image,
				YangMaTouApplication.imageOption(R.drawable.image_icon01));
		holder.cat_goodes_text.setText(mThreeData.getGc_name());
		*/

        return convertView;
    }

    class ViewHolder {

        ImageView store_icon;
        TextView store_name;
        TextView order_state;
        TextView store_orderId;
        TextView order_time;
        ImageView goods_image;
        TextView goods_name;
        TextView goods_price;
        TextView goods_price01;
        TextView order_number;
        TextView order_shipping;
        TextView btn_order_cancel, btn_order_pay, btn_order_tip, btn_order_confirm, btn_order_eval, btn_order_back;

        public ViewHolder(View convertView) {

            this.store_icon = (ImageView) convertView.findViewById(R.id.store_icon);
            this.store_name = (TextView) convertView.findViewById(R.id.store_name);
            this.order_state = (TextView) convertView.findViewById(R.id.order_state);
            this.store_orderId = (TextView) convertView.findViewById(R.id.store_orderId);
            this.order_time = (TextView) convertView.findViewById(R.id.order_time);
            this.goods_image = (ImageView) convertView.findViewById(R.id.goods_image);
            this.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
            this.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            this.goods_price01 = (TextView) convertView.findViewById(R.id.goods_price01);
            this.order_number = (TextView) convertView.findViewById(R.id.order_number);
            this.order_number = (TextView) convertView.findViewById(R.id.order_number);
            this.order_shipping = (TextView) convertView.findViewById(R.id.order_shipping);
            this.btn_order_back = (TextView) convertView.findViewById(R.id.btn_order_back);
            this.btn_order_cancel = (TextView) convertView.findViewById(R.id.btn_order_cancel);
            this.btn_order_pay = (TextView) convertView.findViewById(R.id.btn_order_pay);
            this.btn_order_tip = (TextView) convertView.findViewById(R.id.btn_order_tip);
            this.btn_order_confirm = (TextView) convertView.findViewById(R.id.btn_order_confirm);
            this.btn_order_eval = (TextView) convertView.findViewById(R.id.btn_order_eval);


        }

    }


}
