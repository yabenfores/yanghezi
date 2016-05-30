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
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.Pavilion;
import com.raoleqing.yangmatou.ben.Store;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.UserUitls;

import java.util.List;

/**
 * Created by ybin on 2016/5/27.
 */
public class CountryAdapter extends BaseAdapter {
    private List<Pavilion> pavilionList;
    private LayoutInflater mInflater;
    private Context context;

    CountryAdapter() {

    }

    public CountryAdapter(Context context, List<Pavilion> pavilionList) {
        this.context = context;
        this.pavilionList = pavilionList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return pavilionList.size();
    }

    @Override
    public Object getItem(int position) {
        return pavilionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.country_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            final Pavilion pavilion = pavilionList.get(position);
            ImageLoader.getInstance().displayImage(pavilion.getAdv_url(), holder.iv_country,YangHeZiApplication.imageOption(R.drawable.pavilion_icon));
            holder.tv_country.setText(pavilion.getFlag_name());
            // 进入店铺
            holder.lyo_country.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {

        LinearLayout lyo_country;
        ImageView iv_country;
        TextView tv_country;

        public ViewHolder(View convertView) {

            this.iv_country = (ImageView) convertView.findViewById(R.id.iv_country);
            this.tv_country = (TextView) convertView.findViewById(R.id.tv_country);
            this.lyo_country = (LinearLayout) convertView.findViewById(R.id.lyo_country);

        }
    }
}
