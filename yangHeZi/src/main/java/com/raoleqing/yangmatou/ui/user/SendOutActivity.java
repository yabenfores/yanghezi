package com.raoleqing.yangmatou.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.FhuoMsgAdapter;
import com.raoleqing.yangmatou.ben.SendOut;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendOutActivity extends BaseActivity implements XListView.IXListViewListener,View.OnClickListener {

    private XListView msgList;
    private FhuoMsgAdapter adapter;
    private List<SendOut> msg=new ArrayList<>();
    private int page = 1,maxPage=1;
    private int msg_groupid,msg_grouptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("发货通知");
        setContentView(R.layout.activity_fahuo);
        Intent i = getIntent();
        msg_groupid = i.getIntExtra("msg_groupid", 0);
        msg_grouptype = i.getIntExtra("msg_grouptype",0);
        initView();
    }

    private void initView() {
        activity_return.setOnClickListener(this);
        msgList = (XListView) findViewById(R.id.list_fahuo);
        msgList.setXListViewListener(this);
        msgList.setPullLoadEnable(true);
        msgList.setPullRefreshEnable(true);
        msgList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        adapter = new FhuoMsgAdapter(this, msg);
        msgList.setAdapter(adapter);
        getInfo();
        setProgressVisibility(View.GONE);
    }

    private void getInfo() {
        NetHelper.Msglist(msg_groupid + "", msg_grouptype+"", page + "", new NetConnectionInterface.iConnectListener3() {
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
                JSONObject obj = result.optJSONObject(Constant.DATA);
                if (obj != null) {
                    setMsg(obj);
                }else {
                    makeShortToast("暂无消息");
                }
            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private void setMsg(JSONObject result) {
        maxPage=result.optInt("pagetotal");
        try {
            JSONArray massage = result.optJSONArray("message_array");
            for (int i=0;i<massage.length();i++){
                SendOut send=new SendOut(massage.getJSONObject(i));
                msg.add(send);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (msg.size()!=0){
        msg.removeAll(msg);}
        page=1;
        getInfo();
        msgList.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        if (page<maxPage){
            page++;
            getInfo();

        }else {
            makeShortToast("没有更多了");
        }
        msgList.stopLoadMore();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.activity_return:
                    onBackPressed();
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }
    }
}
