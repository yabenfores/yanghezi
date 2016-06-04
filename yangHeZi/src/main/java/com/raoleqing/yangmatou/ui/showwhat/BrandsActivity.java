package com.raoleqing.yangmatou.ui.showwhat;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.BrandAdapter;
import com.raoleqing.yangmatou.ben.OneCat;
import com.raoleqing.yangmatou.ui.classification.ClassificationActivity;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BrandsActivity extends BaseActivity implements OnClickListener {
    private String code[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z"};

    private ImageView brands_return;
    private ListView listView;
    private BrandAdapter mAdapter;
    private LinearLayout lyo_code;
    private List<Brand> brandList=new ArrayList<>();
    private List<TextView> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brands_activity);
        setTitleVisibility(View.GONE);
        viewInfo();
    }

    protected void viewInfo() {

        brands_return = (ImageView) findViewById(R.id.brands_return);
        brands_return.setOnClickListener(this);
        listView= (ListView) findViewById(R.id.brands_list);
        lyo_code = (LinearLayout) findViewById(R.id.lyo_code);
        mAdapter=new BrandAdapter(this,brandList);
        listView.setAdapter(mAdapter);
        setTitleCode();
        getInfo(list.get(0),0);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.brands_return:
                BrandsActivity.this.onBackPressed();
                break;

            default:
                break;
        }

    }


    private void setTitleCode() {
        // TODO Auto-generated method stub

        lyo_code.removeAllViews();
        RadioGroup myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        lyo_code.addView(myRadioGroup);
        for (int i = 0; i < code.length; i++) {
            final String mOneCat = code[i];
            final TextView radio = new TextView(this);
            View view=new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(0xFFCCCCCC);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(UnitConverterUtils.dip2px(this, 30),
                    UnitConverterUtils.dip2px(this, 30), Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setBackgroundColor(0xFFFFFFFF);
            radio.setGravity(Gravity.CENTER);
            radio.setText(mOneCat);
            radio.setTextSize(16);
            final int gcid = i;
            radio.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    reSetColor();
                    getInfo(radio,gcid );
                }
            });
            list.add(radio);
            myRadioGroup.addView(radio);
            myRadioGroup.addView(view);
        }

    }

    private void getInfo(TextView radio, int gcid) {
        radio.setBackgroundColor(0xFFE81258);
        NetHelper.Brands(code[gcid], new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

                setProgressVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {

                setProgressVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject result) {
                JSONArray array=result.optJSONArray(Constant.DATA);
                if (array==null){
                    return;
                }
                brandList.removeAll(brandList);
                for (int i=0;i<array.length();i++){
                    try {
                        JSONObject obj=array.getJSONObject(i);
                        Brand mBrand=new Brand();
                        mBrand.setBrand_id(obj.optInt("brand_id"));
                        mBrand.setBrand_name(obj.optString("brand_name").trim());
                        brandList.add(mBrand);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    mAdapter.notifyDataSetChanged();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(JSONObject result) {

                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private void reSetColor() {
        for (int i=0;i<list.size();i++){
            TextView text=list.get(i);
            text.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }


}
