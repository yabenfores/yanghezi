package com.raoleqing.yangmatou.ui.user;

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
import android.widget.TextView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

public class ForgetPasswordActivity extends BaseActivity implements OnClickListener {

    private ImageView activity_return;
    private EditText mEtNewPwd, mEtRePwd;

    private TimeCount time;
    private Button send_massage, btn_forget_set;
    private EditText mEtPhone, mEtCode;

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
        setContentView(R.layout.foerget_password);
        setTitleText("重置密码");
        myHandler.sendEmptyMessageDelayed(0, 50);
        time = new TimeCount(60000, 1000);
        setProgressVisibility(View.GONE);
    }

    protected void viewInfo() {
        activity_return = (ImageView) findViewById(R.id.activity_return);
        activity_return.setOnClickListener(this);
        mEtNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        mEtRePwd = (EditText) findViewById(R.id.et_re_pwd);

        send_massage = (Button) findViewById(R.id.btn_send_massage);
        send_massage.setOnClickListener(this);
        btn_forget_set = (Button) findViewById(R.id.btn_forget_set);
        btn_forget_set.setOnClickListener(this);
        mEtCode = (EditText) findViewById(R.id.et_forget_code);
        mEtPhone = (EditText) findViewById(R.id.et_gorget_phone);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.activity_return:
                ForgetPasswordActivity.this.onBackPressed();
                break;

            case R.id.btn_send_massage:
                if (!inputCorrectPhone()) {
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
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
                break;
            case R.id.btn_forget_set:
                if (!inputCorrect()) {
                    break;
                }

                NetHelper.ResetPwd(mEtPhone.getText().toString().trim(), mEtCode.getText().toString().trim(), getMD5(mEtNewPwd.getText().toString().trim()), new NetConnectionInterface.iConnectListener3() {
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
                        SharedPreferencesUtil.putBoolean(ForgetPasswordActivity.this, "remember_password", false);
                        finish();
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
                break;
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

    private boolean inputCorrect() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            makeShortToast("请输入手机号码");
            return false;
        }
        if (!mEtPhone.getText().toString().trim().matches(Constant.REGULAR_PHONE)) {
            makeShortToast("请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(mEtCode.getText().toString().trim())) {
            makeShortToast("请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(mEtNewPwd.getText().toString().trim())) {
            makeShortToast("请输入新的登录密码");
            return false;
        }
        if (TextUtils.isEmpty(mEtRePwd.getText().toString().trim())) {
            makeShortToast("请确认登录密码");
            return false;
        }
        String newPwd, rePwd;
        newPwd = mEtNewPwd.getText().toString().trim();
        rePwd = mEtRePwd.getText().toString().trim();
        if (!newPwd.equals(rePwd)) {
            makeShortToast("两次输入密码不一致，请重新输入");
            return false;
        }
        return true;
    }

    public static String getMD5(String old) {
        byte[] source = old.getBytes();
        String s = null;
        char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0;                                // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i];                 // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
            }
            s = new String(str);                                 // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
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
