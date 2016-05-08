package com.raoleqing.yangmatou.mi;

import android.content.Intent;
import android.os.Bundle;

import com.raoleqing.yangmatou.BaseActivity;

public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
//    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        setContentView(R.layout.activity_chat);
//        setTitleVisibility(View.GONE);
//        activityInstance = this;
//        //聊天人或群id
//        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
//        chatFragment = new EaseChatFragment();
//        //传入参数
//        chatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
//
//        setProgressVisibility(View.GONE);
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
    @Override
    public void onBackPressed() {
//        chatFragment.onBackPressed();
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
