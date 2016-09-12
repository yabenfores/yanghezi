package com.raoleqing.yangmatou.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RefuCheckActivity extends BaseActivity implements View.OnClickListener{

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    private String refund_id;
    private ImageView iv_app_run,imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private List<ImageView> list = new ArrayList<>();
    private TextView tv_re_tui,tv_re_huan,sn_reason,et_re_massage,tv_shouli,tv_chengong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refu_check);
        try {
            refund_id=getIntent().getStringExtra("refund_id");
        } catch (Exception e) {
            throwEx(e);
        }
        viewInfo();
    }
    private void viewInfo() {
        setTitleText("售后进度");
        iv_app_run= (ImageView) findViewById(R.id.iv_app_run);
        tv_re_tui= (TextView) findViewById(R.id.tv_re_tui);
        tv_re_huan= (TextView) findViewById(R.id.tv_re_huan);
        sn_reason= (TextView) findViewById(R.id.sn_reason);
        tv_shouli= (TextView) findViewById(R.id.tv_shouli);
        tv_chengong= (TextView) findViewById(R.id.tv_chengong);
        et_re_massage= (TextView) findViewById(R.id.et_re_massage);
        activity_return= (ImageView) findViewById(R.id.activity_return);
        activity_return.setOnClickListener(this);
        imageView1= (ImageView) findViewById(R.id.iv_eval_image1);
        imageView2= (ImageView) findViewById(R.id.iv_eval_image2);
        imageView3= (ImageView) findViewById(R.id.iv_eval_image3);
        imageView4= (ImageView) findViewById(R.id.iv_eval_image4);
        imageView5= (ImageView) findViewById(R.id.iv_eval_image5);
        imageView6= (ImageView) findViewById(R.id.iv_eval_image6);
        imageView7= (ImageView) findViewById(R.id.iv_eval_image7);
        imageView8= (ImageView) findViewById(R.id.iv_eval_image8);
        imageView9= (ImageView) findViewById(R.id.iv_eval_image9);
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        list.add(imageView4);
        list.add(imageView5);
        list.add(imageView6);
        list.add(imageView7);
        list.add(imageView8);
        list.add(imageView9);
        setProgressVisibility(View.GONE);
        getInfo();
    }

    private void getInfo() {
        NetHelper.REFUNDDETAIL(refund_id, new NetConnectionInterface.iConnectListener3() {
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
                setView(result);
            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private String[] imgList;
    private void setView(JSONObject result) {
        JSONObject object=result.optJSONObject(Constant.DATA);
        if (object.optString("refund_type").equals("3")){
            tv_re_huan.setTextColor(0xFFE81258);
        }
        sn_reason.setText(object.optString("reason_info"));
        et_re_massage.setText(object.optString("buyer_message"));
        switch (object.optString("refund_state")) {
            case "1":
            case "2":
                iv_app_run.setImageResource(R.drawable.bg_refu_2);
                tv_shouli.setTextColor(0xFFE81258);
                break;
            case "3":
                iv_app_run.setImageResource(R.drawable.bg_refu_3);
                tv_shouli.setTextColor(0xFFE81258);
                tv_chengong.setTextColor(0xFFE81258);
                break;
            default:
                break;
        }
        imgList=object.optString("pic_info").split(";");
        setImage();
    }

    private void setImage() {
        try {
            for (int i=0;i<imgList.length;i++){
                String url=imgList[i];
                list.get(i).setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(url,list.get(i));
            }
        } catch (Exception e) {
            throwEx(e);
        }
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.activity_return:
                    this.onBackPressed();
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }

    }

}
