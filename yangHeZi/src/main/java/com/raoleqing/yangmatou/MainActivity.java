package com.raoleqing.yangmatou;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.ben.OneCat;
import com.raoleqing.yangmatou.main.DealsFragment;
import com.raoleqing.yangmatou.main.GouWuGuangChangFragment;
import com.raoleqing.yangmatou.main.SpecialTopicFragment;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.classification.ClassificationActivity;
import com.raoleqing.yangmatou.ui.classification.MipcaActivityCapture;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.ui.login.loginActivity;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatFragment;
import com.raoleqing.yangmatou.ui.user.UserFragment;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.BaseNetConnection;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.webserver.NetParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import entity.NotifyUpdateEntity;

public class MainActivity extends BaseActivity implements OnClickListener {

    private RelativeLayout main_title_layout;
    private LinearLayout gou_wu_message;// 二维码
    private LinearLayout gou_wu_advisory;// 在线
    private EditText activity_search;// 搜索
    private LinearLayout gor_wu_title_layout;
    private ImageView webBack;
    private LinearLayout main_host01;
    private LinearLayout main_host02;
    private LinearLayout main_host03;
    private LinearLayout main_host04;
    private LinearLayout main_host05;
    private ImageView main_host_image01;
    private ImageView main_host_image02;
    private ImageView main_host_image03;
    private ImageView main_host_image04;
    private ImageView main_host_image05;
    private TextView main_host_text01;
    private TextView main_host_text02;
    private TextView main_host_text03;
    private TextView main_host_text04;
    private TextView main_host_text05;
    private RelativeLayout to_top;

    private int contentIndex = 0;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private List<OneCat> oneCatList = new ArrayList<OneCat>();// 标准内容
    private List<OneCat> towCatList = new ArrayList<OneCat>();// 标准内容
    private String oneCatContent;
    private String towCatContent;

    private final static int SCANNIN_GREQUEST_CODE = 1;

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
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();

        setTitleVisibility(View.GONE);
        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (activity_search!=null){
        activity_search.setFocusableInTouchMode(true);}
        if (UserUitls.exitBut == 2) {
            UserUitls.exitBut = 1;
            setView(0);
        }
    }

    protected void viewInfo() {
        // TODO Auto-generated method stub
        String loginName, loginPwd;
        loginName = SharedPreferencesUtil.getString(getAppContext(), "user_name");
        loginPwd = SharedPreferencesUtil.getString(getAppContext(), "user_pwd");
        try {

            if (!TextUtils.isEmpty(loginName) && TextUtils.isEmpty(loginPwd)) {
                userLongin(loginName, loginPwd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        main_title_layout = (RelativeLayout) findViewById(R.id.main_title_layout);
        gou_wu_message = (LinearLayout) findViewById(R.id.gou_wu_message);
        webBack= (ImageView) findViewById(R.id.iv_web_back);
        webBack.setOnClickListener(this);
        activity_search = (EditText) findViewById(R.id.main_search);
        gor_wu_title_layout = (LinearLayout) findViewById(R.id.gor_wu_title_layout);
        gou_wu_advisory = (LinearLayout) findViewById(R.id.gou_wu_advisory);
        main_host01 = (LinearLayout) findViewById(R.id.main_host01);
        main_host02 = (LinearLayout) findViewById(R.id.main_host02);
        main_host03 = (LinearLayout) findViewById(R.id.main_host03);
        main_host04 = (LinearLayout) findViewById(R.id.main_host04);
        main_host05 = (LinearLayout) findViewById(R.id.main_host05);
        main_host_image01 = (ImageView) findViewById(R.id.main_host_image01);
        main_host_image02 = (ImageView) findViewById(R.id.main_host_image02);
        main_host_image03 = (ImageView) findViewById(R.id.main_host_image03);
        main_host_image04 = (ImageView) findViewById(R.id.main_host_image04);
        main_host_image05 = (ImageView) findViewById(R.id.main_host_image05);

        main_host_text01 = (TextView) findViewById(R.id.main_host_text01);
        main_host_text02 = (TextView) findViewById(R.id.main_host_text02);
        main_host_text03 = (TextView) findViewById(R.id.main_host_text03);
        main_host_text04 = (TextView) findViewById(R.id.main_host_text04);
        main_host_text05 = (TextView) findViewById(R.id.main_host_text05);

        to_top = (RelativeLayout) findViewById(R.id.iv_app_to_top);

        gou_wu_message.setOnClickListener(this);
        main_host01.setOnClickListener(this);
        main_host02.setOnClickListener(this);
        main_host03.setOnClickListener(this);
        main_host04.setOnClickListener(this);
        main_host05.setOnClickListener(this);
        gou_wu_advisory.setOnClickListener(this);
        to_top.setOnClickListener(this);

        activity_search.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String search_str = activity_search.getText().toString();
                        if (search_str != null && !search_str.trim().equals("")) {
                            Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                            intent.putExtra("search_str", search_str);
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }

                        break;

                    default:
                        break;
                }
                return false;
            }
        });

        setView(0);
        IMLogin();

    }

    private void IMLogin() {
        String user_name, user_pwd;
        user_name = SharedPreferencesUtil.getString(getAppContext(), "user_name");
        user_pwd = SharedPreferencesUtil.getString(getAppContext(), "user_pwd");
        // TODO Auto-generated method stub
        try {
            EMClient.getInstance().login(user_name, user_pwd, new EMCallBack() {// 回调
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            System.out.println("登陆聊天服务器成功！");

                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    System.out.println("登陆聊天服务器失败！");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        try {
            if (progress.getVisibility() == View.VISIBLE) {
                v.setClickable(false);
            }
            switch (v.getId()) {

                case R.id.gou_wu_advisory:
                    if (UserUitls.isLongin(MainActivity.this)) {
                        String user_msg_helper = SharedPreferencesUtil.getString(getBaseContext(), "user_msg_helper");
                        Intent i = new Intent(this, ChatActivity.class);
                        i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
                        startActivity(i);
                    }else {
                        UserUitls.longInDialog(this);
                    }
                    break;
                case R.id.main_host01:
                    if (contentIndex != 0)
                        setView(0);
                    break;
                case R.id.main_host02:
                    if (contentIndex != 1)
                        setView(1);
                    break;
                case R.id.main_host03:
                    if (contentIndex != 2)
                        setView(2);
                    break;
                case R.id.main_host04:
                    if (contentIndex != 3)
                        setView(3);
                    break;
                case R.id.main_host05:
                    if (contentIndex != 4) {
                        if (UserUitls.isLongin(MainActivity.this)) {
                            setView(4);
                        } else {
                            Intent intent = new Intent(MainActivity.this, loginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }

                    }

                    break;
                case R.id.gou_wu_message:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                    break;
                case R.id.iv_app_to_top:
                    gouWuGuangChangFragment.getListView().setSelection(0);
                    break;

                case R.id.iv_web_back:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        }
                    }).start();

//                    onKeyDown(KeyEvent.KEYCODE_BACK,KeyEvent.);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throwEx(e);
        }

    }


    GouWuGuangChangFragment gouWuGuangChangFragment;

    public void setView(int i) {
        // TODO Auto-generated method stub
        int text01 = 0xFFE81258;
        int text02 = 0xFF333333;

        transaction = manager.beginTransaction();

        switch (i) {
            case 0:
                contentIndex = 0;
                main_host_image01.setImageResource(R.drawable.main_host01_host);
                main_host_image02.setImageResource(R.drawable.main_host02);
                main_host_image03.setImageResource(R.drawable.main_host03);
                main_host_image04.setImageResource(R.drawable.main_host04);
                main_host_image05.setImageResource(R.drawable.main_host05);
                gor_wu_title_layout.setVisibility(View.VISIBLE);
                main_title_layout.setVisibility(View.VISIBLE);
                to_top.setVisibility(View.GONE);

                main_host_text01.setTextColor(text01);
                main_host_text02.setTextColor(text02);
                main_host_text03.setTextColor(text02);
                main_host_text04.setTextColor(text02);
                main_host_text05.setTextColor(text02);
                gouWuGuangChangFragment = GouWuGuangChangFragment.newInstance();
                transaction.replace(R.id.main_content, gouWuGuangChangFragment, "MainFragment");
                transaction.commitAllowingStateLoss();

                if (oneCatList.size() > 0) {
                    setTitleContent(oneCatList);
                } else {
                    getDataTitle(0);
                }
                break;
            case 1:
                contentIndex = 1;
                main_host_image01.setImageResource(R.drawable.main_host01);
                main_host_image02.setImageResource(R.drawable.main_host02_host);
                main_host_image03.setImageResource(R.drawable.main_host03);
                main_host_image04.setImageResource(R.drawable.main_host04);
                main_host_image05.setImageResource(R.drawable.main_host05);
                gor_wu_title_layout.setVisibility(View.GONE);
                main_title_layout.setVisibility(View.VISIBLE);
                to_top.setVisibility(View.GONE);

                main_host_text01.setTextColor(text02);
                main_host_text02.setTextColor(text01);
                main_host_text03.setTextColor(text02);
                main_host_text04.setTextColor(text02);
                main_host_text05.setTextColor(text02);

                Fragment fragment02 = DealsFragment.newInstance();
                transaction.replace(R.id.main_content, fragment02, "MainFragment");
                transaction.commitAllowingStateLoss();
                if (towCatList.size() > 0) {
                    setTitleContent(towCatList);
                } else {
                    getDataTitle(1);
                }

                break;
            case 2:
                contentIndex = 2;
                main_host_image01.setImageResource(R.drawable.main_host01);
                main_host_image02.setImageResource(R.drawable.main_host02);
                main_host_image03.setImageResource(R.drawable.main_host03_host);
                main_host_image04.setImageResource(R.drawable.main_host04);
                main_host_image05.setImageResource(R.drawable.main_host05);
                gor_wu_title_layout.setVisibility(View.GONE);
                main_title_layout.setVisibility(View.VISIBLE);
                to_top.setVisibility(View.GONE);

                main_host_text01.setTextColor(text02);
                main_host_text02.setTextColor(text02);
                main_host_text03.setTextColor(text01);
                main_host_text04.setTextColor(text02);
                main_host_text05.setTextColor(text02);

                Fragment fragment03 = SpecialTopicFragment.newInstance();
                transaction.replace(R.id.main_content, fragment03, "MainFragment");
                transaction.commitAllowingStateLoss();

                if (towCatList.size() > 0) {
                    setTitleContent(towCatList);
                } else {
                    getDataTitle(1);

                }

                break;
            case 3:
                contentIndex = 3;
                main_host_image01.setImageResource(R.drawable.main_host01);
                main_host_image02.setImageResource(R.drawable.main_host02);
                main_host_image03.setImageResource(R.drawable.main_host03);
                main_host_image04.setImageResource(R.drawable.main_host04_host);
                main_host_image05.setImageResource(R.drawable.main_host05);
                main_title_layout.setVisibility(View.GONE);
                gor_wu_title_layout.setVisibility(View.GONE);
                to_top.setVisibility(View.GONE);

                main_host_text01.setTextColor(text02);
                main_host_text02.setTextColor(text02);
                main_host_text03.setTextColor(text02);
                main_host_text04.setTextColor(text01);
                main_host_text05.setTextColor(text02);

               Fragment fragment04 = ShowShatFragment.newInstance();
                transaction.replace(R.id.main_content, fragment04, "MainFragment");
                transaction.commitAllowingStateLoss();

                break;
            case 4:
                contentIndex = 4;
                main_host_image01.setImageResource(R.drawable.main_host01);
                main_host_image02.setImageResource(R.drawable.main_host02);
                main_host_image03.setImageResource(R.drawable.main_host03);
                main_host_image04.setImageResource(R.drawable.main_host04);
                main_host_image05.setImageResource(R.drawable.main_host05_host);
                gor_wu_title_layout.setVisibility(View.GONE);
                main_title_layout.setVisibility(View.GONE);
                to_top.setVisibility(View.GONE);
                main_host_text01.setTextColor(text02);
                main_host_text02.setTextColor(text02);
                main_host_text03.setTextColor(text02);
                main_host_text04.setTextColor(text02);
                main_host_text05.setTextColor(text01);
                Fragment fragment05 = UserFragment.newInstance();
                transaction.replace(R.id.main_content, fragment05, "MainFragment");
                transaction.commitAllowingStateLoss();
                break;

            default:
                break;
        }

    }

    private void getDataTitle(final int type) {
        // TODO Auto-generated method stub

        setMainProgress(View.VISIBLE);
        NetHelper.getOneCat(new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                resolveJson(result, type);

            }

            @Override
            public void onFail(JSONObject result) {
                setProgressVisibility(View.GONE);
                ToastUtil.MakeShortToast(getAppContext(), result.optString(Constant.INFO));
            }
        });
    }

    protected void resolveJson(JSONObject response, int type) {
        // TODO Auto-generated method stub

        try {
            String message = response.optString("message");

            if (response == null) {
                setMainProgress(View.GONE);
                return;
            }

            JSONArray data = response.optJSONArray("data");

            if (type == 0) {
                oneCatContent = response.toString();
                if (oneCatList.size() > 0) {
                    oneCatList.retainAll(oneCatList);
                }
            } else {
                if (towCatList.size() > 0) {
                    towCatList.retainAll(towCatList);
                }
                towCatContent = response.toString();
            }

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                OneCat mOneCat = new OneCat();
                mOneCat.setGc_id(obj.optInt("gc_id"));
                mOneCat.setGc_name(obj.optString("gc_name").trim());
                if (type == 0) {
                    oneCatList.add(mOneCat);
                } else {
                    towCatList.add(mOneCat);
                }

            }

            if (type == 0) {
                setTitleContent(oneCatList);
            } else {
                setTitleContent(towCatList);
            }



        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setMainProgress(View.GONE);

    }

    /**
     * 标题内容
     **/
    private void setTitleContent(List<OneCat> list) {
        // TODO Auto-generated method stub

        gor_wu_title_layout.removeAllViews();

        RadioGroup myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        gor_wu_title_layout.addView(myRadioGroup);
        for (int i = 0; i < list.size(); i++) {
            final OneCat mOneCat = list.get(i);
            TextView radio = new TextView(this);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    UnitConverterUtils.dip2px(this, 40), Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER_VERTICAL);
            radio.setPadding(20, 0, 20, 0);
            radio.setText(mOneCat.getGc_name());
            radio.setTextColor(Color.WHITE);
            radio.setTextSize(16);
            final int gcid = i;
            radio.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, ClassificationActivity.class);
                    intent.putExtra("gc_id", gcid);
                    if (contentIndex == 0) {
                        intent.putExtra("cat_content", oneCatContent);
                    } else {
                        intent.putExtra("cat_content", towCatContent);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
            });
            myRadioGroup.addView(radio);
        }

    }

    /*
     * 物理返回按扭
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showDialogExit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialogExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定要退出吗？").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.onBackPressed();
            }
        }).setNegativeButton("取消", null).show();
    }

    /*
     * 加载条显示隐藏设置
     */
    public void setMainProgress(int visibility) {
        setProgressVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }



    public String getCity_id() {
        return city_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public int getShowType() {
        return showType;
    }

    private String city_id,brand_id;
    private int showType=0;
    //-------------------------
    public final static String USER_LOGIN = "user_login";

    public final static String JPUSH_SETTAG = "jpush_settag";
    public final static String COUNTRY = "country";
    public final static String BRAND = "brand";
    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case USER_LOGIN:
                    setView(4);
                    break;
                case COUNTRY:
                    city_id= (String) notifyUpdateEntity.getObj();
                    showType=2;
                    setView(3);
                    break;
                case BRAND:
                    brand_id=(String) notifyUpdateEntity.getObj();
                    showType=3;
                    setView(3);
                    break;
                case JPUSH_SETTAG:
                    setTag((String) notifyUpdateEntity.getObj());
                    break;
            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }

    private static String TAG = "mainActivity";
    private void setTag(final String name) {
        JPushInterface.setAlias(getAppContext(), getMD5(name), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                String logs;
                switch (i) {
                    case 0:
                        logs = "Set tag and alias success";
                        Log.i(TAG, logs);
                        SharedPreferencesUtil.putBoolean(getAppContext(),name, true);
                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        break;
                    case 6002:
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        Log.i(TAG, logs);
                        // 延迟 60 秒来调用 Handler 设置别名
                        sendNotifyUpdate(MainActivity.class, JPUSH_SETTAG,name, 60 * 1000);
                        break;
                    default:
                        logs = "Failed with errorCode = " + i;
                        Log.e(TAG, logs);
                }
            }
        });
    }
    public void setCodeVisible(int visible){
        if (gou_wu_message!=null){
        gou_wu_message.setVisibility(visible);}
    }
    public void setWebBack(int visible){
        if (webBack!=null){
        webBack.setVisibility(visible);}
    }


    /**
     * 登陆
     **/
    private void userLongin(String loginName, String loginPwd) {
        //Home/Users/checkLogin
        String authorization, md5, auth;
        md5 = getMD5(loginPwd);
        Log.e("pwd", md5);
        auth = loginName + "|" + md5;
        authorization = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
        RequestParams params = new RequestParams();
        params.put("Authorization", authorization);
        SharedPreferencesUtil.putString(getAppContext(), "Authorization", authorization);
        new BaseNetConnection(Constant.LOGIN, NetParams.HttpMethod.Post, true, new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONObject json = result.optJSONObject(Constant.DATA);
                    ToastUtil.MakeShortToast(BaseActivity.getAppContext(), "登录成功");
                    String member_auth = json.optString("Authorization");//认证
                    SharedPreferencesUtil.putString(getAppContext(), "Authorization", member_auth);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(getAppContext(), result.optString(Constant.INFO));
            }
        });

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


}
