package com.raoleqing.yangmatou.mi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    private ImageView activity_return;
    EaseUI easeUI = EaseUI.getInstance();
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    private EaseConversationListFragment easeConversationListfragment;

    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        activityInstance = this;
        setContentView(R.layout.activity_chat);
                setView(0);
        setProgressVisibility(View.GONE);
        activity_return = (ImageView) findViewById(R.id.activity_return);
        activity_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {
                case R.id.activity_return:
                    finish();
                default:
                    break;
            }


        } catch (Exception e) {
            throwEx(e);
        }

    }


    private void setView(int i) {
        try {
            switch (i) {
                case 0:
                    setTitleText("客服");
                    //new出EaseChatFragment或其子类的实例
                    String user = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
                    chatFragment = new EaseChatFragment();
                    //传入参数
                    easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
                        @Override
                        public EaseUser getUser(String username) {
                            EaseUser easeUser = new EaseUser(username);
                            String member_avatar = SharedPreferencesUtil.getString(ChatActivity.this, "member_avatar");
                            String member_name = SharedPreferencesUtil.getString(ChatActivity.this, "member_name");
                            easeUser.setAvatar(member_avatar);
                            easeUser.setNick(member_name);
                            return easeUser;
                        }
                    });
                    Bundle args = new Bundle();
                    args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    args.putString(EaseConstant.EXTRA_USER_ID, user);
                    chatFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
                    break;
                case 1:
                    setTitleText("消息");
                    easeConversationListfragment = new EaseConversationListFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, easeConversationListfragment).commit();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

//    @Override
//    public void onBackPressed() {
//        chatFragment.onBackPressed();
//    }

    public String getToChatUsername() {
        return toChatUsername;
    }
}
