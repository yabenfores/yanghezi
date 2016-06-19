package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.SendOut;
import com.raoleqing.yangmatou.common.YangHeZiApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class FhuoMsgAdapter extends BaseAdapter {

    Context context;
    List<SendOut> goodsList;
    private LayoutInflater mInflater;

    FhuoMsgAdapter() {

    }

    public FhuoMsgAdapter(Context context, List<SendOut> goodsList) {
        this.goodsList = goodsList;
        this.context=context;
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
            convertView = mInflater.inflate(R.layout.fahuo_msg_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            SendOut mGoods = goodsList.get(position);
            holder.tv_msg_orderid.setText(mGoods.getOrder_sn());
            holder.tv_ship_num.setText(mGoods.getShipping_code());
//			holder.tv_ship.setText(mGoods.get);
            holder.tv_send_time.setText(mGoods.getMsg_createtime());
            JSONArray goods=mGoods.getGoods_array();
            for (int i=0;i<goods.length();i++){
                JSONObject object=goods.getJSONObject(i);
                holder.tv_goods_comment.setText(object.optString("goods_name"));
                ImageLoader.getInstance().displayImage(object.optString("goods_image"),holder.iv_goods);
                holder.lyo_goods_list.addView(holder.lyo_goods);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    class ViewHolder {

        ImageView iv_goods;
        LinearLayout lyo_goods_list, lyo_goods;
        TextView tv_msg_orderid, tv_goods_comment, tv_ship, tv_ship_num, tv_send_time;

        public ViewHolder(View convertView) {
            lyo_goods_list = (LinearLayout) convertView.findViewById(R.id.lyo_goods_list);
            lyo_goods = (LinearLayout) convertView.findViewById(R.id.lyo_goods);
            tv_msg_orderid = (TextView) convertView.findViewById(R.id.tv_msg_orderid);
            tv_goods_comment = (TextView) convertView.findViewById(R.id.tv_goods_comment);
            tv_ship = (TextView) convertView.findViewById(R.id.tv_ship);
            tv_ship_num = (TextView) convertView.findViewById(R.id.tv_ship_num);
            tv_send_time = (TextView) convertView.findViewById(R.id.tv_send_time);
            iv_goods= (ImageView) convertView.findViewById(R.id.iv_goods);
        }

    }


}
