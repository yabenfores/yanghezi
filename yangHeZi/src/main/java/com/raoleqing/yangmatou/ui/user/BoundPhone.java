package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;


/**
 * 绑定
 **/
public class BoundPhone extends BaseActivity implements OnClickListener {

    private TimeCount time;
    private Button send_massage,bind;
    private EditText mEtPhone,mEtCode;


    private ImageView activity_return;

    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    viewInfo();
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bound_phone);
        setTitleText("绑定手机");
        myHandler.sendEmptyMessageDelayed(0, 50);
        time = new TimeCount(60000, 1000);

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            send_massage.setText("获取验证码");
            send_massage.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            send_massage.setClickable(false);//防止重复点击
            send_massage.setText(millisUntilFinished / 1000 + "s");
        }
    }

    protected void viewInfo() {
        send_massage = (Button) findViewById(R.id.btn_send_massage);
        send_massage.setOnClickListener(this);
        mEtPhone= (EditText) findViewById(R.id.et_app_mobile);
        bind= (Button) findViewById(R.id.btn_app_bind);
        bind.setOnClickListener(this);
        activity_return = (ImageView) findViewById(R.id.activity_return);
        activity_return.setOnClickListener(this);
        mEtCode= (EditText) findViewById(R.id.et_app_code);
        getData();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.activity_return:
                BoundPhone.this.onBackPressed();
                break;
            case R.id.btn_send_massage:
                if (!inputCorrectPhone()){
                    break;
                }
                NetHelper.MsgInfo(mEtPhone.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                        time.start();
                        makeShortToast(result.optString(Constant.INFO));
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
                break;
            case R.id.btn_app_bind:
                if (mEtCode.getText().toString().trim().isEmpty()){
                    makeShortToast("请输入验证码");
                    break;
                }
                NetHelper.bindUserMobile(mEtPhone.getText().toString().trim(), mEtCode.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                        makeShortToast(result.optString(Constant.INFO));
                        finish();

                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.DATA));

                    }
                });

            default:
                break;
        }

    }
    private boolean inputCorrectPhone() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            makeShortToast("请输入手机号码");
            return false;
        }
        if (!mEtPhone.getText().toString().trim().matches(Constant.REGULAR_PHONE)) {
            makeShortToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    private void getData() {
        // TODO Auto-generated method stub
        setProgressVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }

}
