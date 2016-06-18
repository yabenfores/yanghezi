package com.raoleqing.yangmatou.ui.user;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

public class IdCardActivity extends BaseActivity implements View.OnClickListener {


    private EditText et_idcard_phone, et_idcard_num, et_idcard_code;
    private Button btn_send_massage, btn_idcard_set;
    private String code;

    private TimeCount time;
    private boolean phone=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        viewInfo();
    }

    private void viewInfo() {
        et_idcard_phone = (EditText) findViewById(R.id.et_idcard_phone);
        et_idcard_num = (EditText) findViewById(R.id.et_idcard_num);
        et_idcard_code = (EditText) findViewById(R.id.et_idcard_code);
        btn_send_massage = (Button) findViewById(R.id.btn_send_massage);
        btn_idcard_set = (Button) findViewById(R.id.btn_idcard_set);
        btn_send_massage.setOnClickListener(this);
        btn_idcard_set.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
        setProgressVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.btn_send_massage:
                    checkPhone();
                    break;
                case R.id.btn_idcard_set:
                    if (inputCorrect()) {
                        NetHelper.modfiycard(et_idcard_num.getText().toString().trim(),et_idcard_phone.getText().toString().trim(),et_idcard_code.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                                SharedPreferencesUtil.putString(getAppContext(), "member_card", et_idcard_num.getText().toString().trim());
                                finish();
                            }

                            @Override
                            public void onFail(JSONObject result) {
                                makeShortToast(result.optString(Constant.INFO));
                            }
                        });
                    }
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }

    }

    private boolean inputCorrect() {
        if (TextUtils.isEmpty(et_idcard_phone.getText().toString().trim())) {
            makeShortToast("请输入手机号码");
            return false;
        }
        if (TextUtils.isEmpty(et_idcard_num.getText().toString().trim())) {
            makeShortToast("请输入身份证号码");
            return false;
        }
        if (TextUtils.isEmpty(et_idcard_code.getText().toString().trim())) {
            makeShortToast("请输入验证码");
            return false;
        }
        if (!code.equals(et_idcard_code.getText().toString().trim())) {
            makeShortToast("验证码不正确，请重新输入");
            return false;
        }

        return true;
    }

    private void checkPhone() {
        NetHelper.CheckMember(et_idcard_phone.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                NetHelper.MsgInfo(et_idcard_phone.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                        JSONObject object = result.optJSONObject(Constant.DATA);
                        code = object.optString("verification_code");
                        time.start();
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private boolean checkId() {
        String idCard = et_idcard_num.getText().toString().trim();
        final boolean[] id = {false};
        NetHelper.CheckMemberId(idCard, new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                id[0] = true;

            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
        return id[0];

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn_send_massage.setText("获取验证码");
            btn_send_massage.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn_send_massage.setClickable(false);//防止重复点击
            btn_send_massage.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
