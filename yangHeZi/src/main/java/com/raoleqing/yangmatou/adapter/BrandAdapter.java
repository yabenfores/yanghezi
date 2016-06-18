package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.BaseFragment;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.showwhat.Brand;
import com.raoleqing.yangmatou.ui.showwhat.BrandsActivity;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatFragment;

import java.util.List;

/**
 * Created by ybin on 2016/5/29.
 */
public class BrandAdapter extends BaseAdapter {
    private List<Brand> brandsList;
    private LayoutInflater mInflater;
    private BrandsActivity context;

    BrandAdapter() {

    }

    public BrandAdapter(BrandsActivity context, List<Brand> brandsList) {
        this.context = context;
        this.brandsList = brandsList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return brandsList.size();
    }

    @Override
    public Object getItem(int position) {
        return brandsList.get(position);
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
            final Brand brand = brandsList.get(position);
            holder.tv_brand.setText(brand.getBrand_name());
            holder.tv_brand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String brand_id=brand.getBrand_id()+"";
                    BaseActivity.sendNotifyUpdate(MainActivity.class,BRAND,brand_id);
                    context.onBackPressed();
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
    public final static String BRAND = "brand";
}
