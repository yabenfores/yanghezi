package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.ui.shop.ShopSai;
import com.raoleqing.yangmatou.ui.showwhat.Brand;

import java.util.List;

/**
 * Created by ybin on 2016/5/29.
 */
public class ShopSaiAdapter extends BaseAdapter {
    private List<ShopSai> shopSaiList;
    private LayoutInflater mInflater;
    private Context context;

    ShopSaiAdapter() {

    }

    public ShopSaiAdapter(Context context, List<ShopSai> shopSaiList) {
        this.context = context;
        this.shopSaiList = shopSaiList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return shopSaiList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopSaiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brand_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            final ShopSai shopSai = shopSaiList.get(position);
            holder.tv_brand.setText(shopSai.getGc_name().trim());
            holder.tv_brand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    BaseActivity.sendNotifyUpdate(ShopActivity.class,SAISELECT,shopSai.getGc_id(),0);
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return convertView;
    }


    class ViewHolder {

        TextView tv_brand;

        public ViewHolder(View convertView) {

            this.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);

        }
    }

    //---------------
    public final static String SAISELECT = "saiSelect";
}
