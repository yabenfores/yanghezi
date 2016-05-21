package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.CatGoods;
import com.raoleqing.yangmatou.ben.ThreeData;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CatGoodesAdapter extends BaseAdapter {

    List<CatGoods> goodsList;
    private LayoutInflater mInflater;
    private Context context;

    CatGoodesAdapter() {

    }

    public CatGoodesAdapter(Context context, List<CatGoods> goodsList) {
        this.context = context;
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
            convertView = mInflater.inflate(R.layout.cat_goodes_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatGoods mCatGoods = goodsList.get(position);
        holder.cat_goodes_title.setText(mCatGoods.getGc_name());

        final List<ThreeData> three_data = mCatGoods.getThree_data();
        CatGoodesGridViewAdapter adapter = new CatGoodesGridViewAdapter(context, three_data);
        holder.cat_goodes_gridview.setAdapter(adapter);
        holder.cat_goodes_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                ThreeData mThreeData = three_data.get(arg2);
                int cid = mThreeData.getGc_id();
                Intent intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("cid", cid);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
        int size = three_data.size();
        if (size > 0) {
            int heightsize = size / 3;
            if (heightsize * 3 < size) {
                heightsize++;
            }
            int height = UnitConverterUtils.dip2px(context, 100);
            LayoutParams params = holder.cat_goodes_gridview.getLayoutParams();
            params.height = heightsize * (UnitConverterUtils.dip2px(context, 10) + height);
            holder.cat_goodes_gridview.setLayoutParams(params);

        }

        return convertView;
    }

    class ViewHolder {

        TextView cat_goodes_title;
        GridView cat_goodes_gridview;

        public ViewHolder(View convertView) {

            this.cat_goodes_title = (TextView) convertView.findViewById(R.id.cat_goodes_title);
            this.cat_goodes_gridview = (GridView) convertView.findViewById(R.id.cat_goodes_gridview);

        }

    }

}
