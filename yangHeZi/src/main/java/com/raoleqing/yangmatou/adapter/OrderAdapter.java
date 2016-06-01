package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.allinpay.appayassistex.APPayAssistEx;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.order.EvalActivity;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.PaaCreator;
import com.raoleqing.yangmatou.uitls.TimeUitls;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderAdapter extends BaseAdapter {


    private List<Order> orderList;
    private LayoutInflater mInflater;
    private Context context;

    private int ItemHeight = 0;

    OrderAdapter() {

    }

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
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

        final Order mOrder = orderList.get(position);
        holder.store_name.setText(mOrder.getStore_name());
        holder.store_orderId.setText("订单号: " + mOrder.getOrder_sn());
        holder.order_time.setText("下单时间 : " + TimeUitls.getDate(mOrder.getAdd_time()));

        holder.order_number.setText("数量：" + mOrder.getGoods_num() + "件        共计： ￥" + mOrder.getOrder_amount());
        holder.order_shipping.setText("（含运费" + mOrder.getShipping_fee() + "元）");
        ImageLoader.getInstance().displayImage(mOrder.getStore_label(), holder.store_icon,
                YangHeZiApplication.imageOption(R.drawable.image_icon01));
        final JSONObject object = mOrder.getExtend_order_goods();
        if (object != null) {
            ImageLoader.getInstance().displayImage(object.optString("goods_image"), holder.goods_image,
                    YangHeZiApplication.imageOption(R.drawable.image_icon01));
            holder.goods_name.setText(object.optString("goods_name"));

            holder.goods_price.setText("￥" + object.optString("goods_price"));
            holder.goods_price01.setText("原价： " + object.optString("goods_marketprice"));
        }
        /*
         * 10代付款 20待发货
		30待收货 40已完成
		0已取消 50待评价*/

        switch (mOrder.getOrder_state()) {
            case 0:
                holder.order_state.setText("已取消");
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
                NetHelper.order_conpay(mOrder.getOrder_id() + "", new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                        ((OrderActivity) context).setProgressVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onFinish() {

                        ((OrderActivity) context).setProgressVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(JSONObject result) {

                        JSONObject object=result.optJSONObject("data");
                        try {
                            object.put("productName",mOrder.getExtend_order_goods().optString("goods_name").trim());
                            object.put("orderAmount",((int) mOrder.getOrder_amount()*100)+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject payData = PaaCreator.randomPaa(object);

                        System.out.println("aaaaaaaaa:" + payData.toString());
                        APPayAssistEx.startPay((OrderActivity) context, payData.toString(), APPayAssistEx.MODE_DEBUG);

                    }

                    @Override
                    public void onFail(JSONObject result) {
                        ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));
                    }
                });


            }
        });
        holder.btn_order_eval.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, EvalActivity.class);
                intent.putExtra("goods_name",object.optString("goods_name"));
                intent.putExtra("goods_image",object.optString("goods_image"));
                intent.putExtra("order_sn",mOrder.getOrder_id());
                context.startActivity(intent);

            }
        });
        holder.btn_order_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                NetHelper.ordercancel(mOrder.getOrder_id() + "", new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onSuccess(JSONObject result) {
                        BaseActivity.sendNotifyUpdate(OrderActivity.class, ORDERCHAGE);
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        ToastUtil.MakeShortToast(context, result.optString(Constant.INFO));
                    }
                });

            }
        });


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

    //----------------
    public final static String ORDERCHAGE = "orderchage";


}
