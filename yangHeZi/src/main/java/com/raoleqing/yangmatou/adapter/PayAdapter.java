package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.goods.GoodsPayActivity;
import com.raoleqing.yangmatou.ui.goods.JixuPayActivity;
import com.raoleqing.yangmatou.ui.goods.PayList;
import java.util.List;

/**
 * Created by ybin on 2016/6/6.
 */
public class PayAdapter extends BrandAdapter {
    private List<PayList>payList;
    private LayoutInflater mInflater;
    private Context context;

    PayAdapter() {

    }

    public PayAdapter(Context context, List<PayList> payList) {
        this.context = context;
        this.payList = payList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return payList.size();
    }

    @Override
    public Object getItem(int position) {
        return payList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pay_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            final PayList pay = payList.get(position);
            if (pay.isSelect()){
                holder.iv_pay_select.setImageResource(R.drawable.ic_pay_select);
            }else {
                holder.iv_pay_select.setImageResource(R.drawable.ic_pay);
            }
            ImageLoader.getInstance().displayImage(pay.getPayment_ico(),holder.iv_pay_icon);
            holder.tv_pay.setText(pay.getPayment_name());
            holder.lyo_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.sendNotifyUpdate(GoodsPayActivity.class,SELECTPAY,pay.getPayment_id()+"");
                    BaseActivity.sendNotifyUpdate(JixuPayActivity.class,SELECTPAY,pay.getPayment_id()+"");
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {

        LinearLayout lyo_pay;
        ImageView iv_pay_select,iv_pay_icon;
        TextView tv_pay;

        public ViewHolder(View convertView) {
            this.iv_pay_select = (ImageView) convertView.findViewById(R.id.iv_pay_select);
            this.iv_pay_icon = (ImageView) convertView.findViewById(R.id.iv_pay_icon);
            this.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
            this.lyo_pay = (LinearLayout) convertView.findViewById(R.id.lyo_pay);

        }
    }

    //---------------
    public final static String SELECTPAY = "selectpay";
}
