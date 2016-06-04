package com.raoleqing.yangmatou.ui.login;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.ToastUtil;
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
import android.widget.TextView;

import org.json.JSONObject;

/**
 * 登录
 */
public class RegisteredActivity extends BaseActivity implements OnClickListener {

    private String code;
    private TimeCount time;
    private ImageView activity_return;
    private TextView registered_codes;
    private EditText mEtPhone, et_reg_card, et_reg_name, et_reg_code, et_reg_pwd, et_reg_repwd;
    private Button btn_reg, btn_reg_login;
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
        setContentView(R.layout.registered_activity);

        setTitleText("注册");
        time = new TimeCount(60000, 1000);
        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    protected void viewInfo() {

        activity_return = (ImageView) findViewById(R.id.activity_return);

        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(this);
        btn_reg_login = (Button) findViewById(R.id.btn_reg_login);
        btn_reg_login.setOnClickListener(this);
        et_reg_card = (EditText) findViewById(R.id.et_reg_card);
        et_reg_name = (EditText) findViewById(R.id.et_reg_name);
        et_reg_code = (EditText) findViewById(R.id.et_reg_code);
        et_reg_pwd = (EditText) findViewById(R.id.et_reg_pwd);
        mEtPhone = (EditText) findViewById(R.id.et_reg_phone);
        et_reg_repwd = (EditText) findViewById(R.id.et_reg_repwd);
        registered_codes = (TextView) findViewById(R.id.registered_codes);
        registered_codes.setOnClickListener(this);

        activity_return.setOnClickListener(this);

        setProgressVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_reg_login:
                this.onBackPressed();
                break;
            case R.id.activity_return:
                RegisteredActivity.this.onBackPressed();
                break;
            case R.id.registered_codes:
                if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
                    makeShortToast("请输入手机号码");
                    return;
                }
                if (!mEtPhone.getText().toString().trim().matches(Constant.REGULAR_PHONE)) {
                    makeShortToast("请输入正确的手机号码");
                    return;
                }
                if (!checkPhone()){
                    return;
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
                        JSONObject obj=result.optJSONObject(Constant.DATA);
                        code=obj.optString("verification_code");
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                    }
                });
                break;
            case R.id.btn_reg:
                if (!inputCorrectPhone()) {
                    break;
                }
                final String newPwd, rePwd;
                newPwd = et_reg_pwd.getText().toString().trim();
                rePwd = et_reg_repwd.getText().toString().trim();
                if (newPwd.equals(rePwd)) {
                    NetHelper.Register(mEtPhone.getText().toString().trim(), et_reg_card.getText().toString().trim(), et_reg_name.getText().toString().trim(), getMD5(newPwd), et_reg_code.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                            makeShortToast(result.optString(Constant.INFO));
                        }
                    });
                } else {
                    ToastUtil.MakeShortToast(BaseActivity.getAppContext(), "两次输入密码不一致，请重新输入");
                    break;
                }
            default:
                break;
        }

    }

    private boolean checkPhone() {
        final boolean[] b = {false};
        NetHelper.CheckMember( mEtPhone.getText().toString().trim(), new NetConnectionInterface.iConnectListener3() {
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
                b[0] =true;
            }
            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
        return b[0];
    }

    private boolean checkId(){
        String idCard=et_reg_card.getText().toString().trim();
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
                id[0] =true;

            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
        return id[0];

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
        if (TextUtils.isEmpty(et_reg_card.getText().toString().trim())) {
            makeShortToast("请输入身份证");
            return false;
        }
        if (!checkId()){
            return false;
        }
        if (TextUtils.isEmpty(et_reg_name.getText().toString().trim())) {
            makeShortToast("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(et_reg_code.getText().toString().trim())) {
            makeShortToast("请输入验证码");
            return false;
        }
        if (!code.equals(et_reg_code.getText().toString().trim())){
            makeShortToast("验证码不正确，请重新输入");
            return false;
        }
        if (TextUtils.isEmpty(et_reg_repwd.getText().toString().trim())) {
            makeShortToast("请输入密码");
            return false;
        }

        return true;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            registered_codes.setText("获取验证码");
            registered_codes.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            registered_codes.setClickable(false);//防止重复点击
            registered_codes.setText(millisUntilFinished / 1000 + "s");
        }
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }


}
