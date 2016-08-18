package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Share;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.ui.goods.GoodsDetail;
import com.raoleqing.yangmatou.ui.goods.GoodsPayActivity;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends BaseAdapter {


    private List<Shop> sowShatList;
    private LayoutInflater mInflater;
    private ShopActivity context;

    private int ItemHeight = 0;

    ShopAdapter() {

    }

    public ShopAdapter(ShopActivity context, List<Shop> sowShatList) {
        this.sowShatList = sowShatList;
        this.context = context;
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
            convertView = mInflater.inflate(R.layout.shop_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Shop mShop = sowShatList.get(position);
        String str = mShop.getGoods_images();
        List<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(holder.tv_shop_adapter_image1);
        imageViewList.add(holder.tv_shop_adapter_image2);
        imageViewList.add(holder.tv_shop_adapter_image3);
        imageViewList.add(holder.tv_shop_adapter_image4);
        imageViewList.add(holder.tv_shop_adapter_image5);
        imageViewList.add(holder.tv_shop_adapter_image6);
        for (int i=0;i<imageViewList.size();i++){
            imageViewList.get(i).setImageDrawable(null);
            imageViewList.get(i).setVisibility(View.GONE);
        }
        final String[] image = str.split(";");
        try {
            for (int i = 0; i < image.length; i++) {
                imageViewList.get(i).setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(image[i], imageViewList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageLoader.getInstance().displayImage(mShop.getFlag_imgSrc(), holder.iv_flg);

        if (mShop.getIs_favorite()==1){

        }

        holder.tv_shop_adapter_time.setText("发布于"+mShop.getGoods_publictime());
        holder.tv_shop_adapter_com.setText(mShop.getGoods_name());
        holder.tv_flg.setText(mShop.getFlag_name());
        holder.tv_shop_adapter_sale.setText("销量：" + mShop.getGoods_salenum());
        holder.tv_shop_adapter_sto.setText("库存：" + mShop.getGoods_storage());
        holder.tv_shop_adapter_sprice.setText("￥" + mShop.getGoods_price());
        holder.tv_shop_adapter_mprice.setText("￥" + mShop.getGoods_marketprice());
        holder.tv_shop_adapter_mprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_shop_adapter_eval.setText(mShop.getComment_total());



        holder.shop_pay_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUitls.isLongin(context)) {
                    Intent intent = new Intent(context, GoodsPayActivity.class);
                    intent.putExtra("goodsNumber", 1);
                    intent.putExtra("goods_promotion_price", mShop.getGoods_price());
                    intent.putExtra("goods_marketprice", mShop.getGoods_marketprice());
                    intent.putExtra("goods_id", mShop.getGoods_id());
                    intent.putExtra("goodsName", mShop.getGoods_name());
                    intent.putExtra("goods_image",image[0]);
                    context.startActivity(intent);
                } else {
                    UserUitls.longInDialog(context);
                }
            }
        });

        holder.lyo_eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetail.class);
                intent.putExtra("goods_id", mShop.getGoods_id());
                context.startActivity(intent);
            }
        });
        holder.lyo_shop_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetail.class);
                intent.putExtra("goods_id", mShop.getGoods_id());
                context.startActivity(intent);
            }
        });

        holder.lyo_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserUitls.isLongin(context)) {
                    UserUitls.longInDialog(context);
                }
            }
        });

        holder.lyo_shop_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetHelper.Share("1", mShop.getGoods_id()+"", new NetConnectionInterface.iConnectListener3() {
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
                        Share share=new Share(result.optJSONObject(Constant.DATA));
                        context.showShare(share);
                    }
                    @Override
                    public void onFail(JSONObject result) {
                        if (!UserUitls.isLongin(context)) UserUitls.longInDialog(context);
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {

        LinearLayout lyo_shop_adapter,lyo_eval,lyo_like,lyo_shop_share;
        ImageView tv_shop_adapter_image1, tv_shop_adapter_image2, tv_shop_adapter_image3, tv_shop_adapter_image4, tv_shop_adapter_image5, tv_shop_adapter_image6, iv_flg;
        TextView tv_shop_adapter_time, tv_shop_adapter_com, tv_flg, tv_shop_adapter_sale, tv_shop_adapter_sto, tv_shop_adapter_sprice, tv_shop_adapter_mprice, tv_shop_adapter_eval, tv_shop_adapter_share, tv_shop_adapter_fov;

        Button shop_pay_but;
        public ViewHolder(View convertView) {
            this.tv_shop_adapter_image1 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image1);
            this.tv_shop_adapter_image2 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image2);
            this.tv_shop_adapter_image3 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image3);
            this.tv_shop_adapter_image4 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image4);
            this.tv_shop_adapter_image5 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image5);
            this.tv_shop_adapter_image6 = (ImageView) convertView.findViewById(R.id.tv_shop_adapter_image6);
            this.iv_flg = (ImageView) convertView.findViewById(R.id.iv_flg);
            this.tv_shop_adapter_time = (TextView) convertView.findViewById(R.id.tv_shop_adapter_time);
            this.tv_shop_adapter_com = (TextView) convertView.findViewById(R.id.tv_shop_adapter_com);
            this.tv_flg = (TextView) convertView.findViewById(R.id.tv_flg);
            this.tv_shop_adapter_sale = (TextView) convertView.findViewById(R.id.tv_shop_adapter_sale);
            this.tv_shop_adapter_sto = (TextView) convertView.findViewById(R.id.tv_shop_adapter_sto);
            this.tv_shop_adapter_sprice = (TextView) convertView.findViewById(R.id.tv_shop_adapter_sprice);
            this.tv_shop_adapter_mprice = (TextView) convertView.findViewById(R.id.tv_shop_adapter_mprice);
            this.tv_shop_adapter_eval = (TextView) convertView.findViewById(R.id.tv_shop_adapter_eval);
            this.tv_shop_adapter_share = (TextView) convertView.findViewById(R.id.tv_shop_adapter_share);
            this.tv_shop_adapter_fov = (TextView) convertView.findViewById(R.id.tv_shop_adapter_fov);

            this.lyo_shop_adapter = (LinearLayout) convertView.findViewById(R.id.lyo_shop_adapter);
            this.lyo_shop_share = (LinearLayout) convertView.findViewById(R.id.lyo_shop_share);
            this.lyo_eval = (LinearLayout) convertView.findViewById(R.id.lyo_eval);
            this.lyo_like = (LinearLayout) convertView.findViewById(R.id.lyo_like);
            this.shop_pay_but= (Button) convertView.findViewById(R.id.shop_pay_but);
        }

    }


}
