package com.raoleqing.yangmatou.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.CircularAdapter;
import com.raoleqing.yangmatou.ben.Circular;
import com.raoleqing.yangmatou.ben.SendOut;
import com.raoleqing.yangmatou.ui.login.loginActivity;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import entity.NotifyUpdateEntity;

public class CircularActivity extends BaseActivity implements XListView.IXListViewListener {

    private XListView msgList;
    private int page = 1, maxPage = 1;
    private int msg_groupid, msg_grouptype;
    private List<Circular> criteriaList = new ArrayList<>();
    private CircularAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("洋盒子通告");
        setContentView(R.layout.activity_circular);
        Intent i = getIntent();
        msg_groupid = i.getIntExtra("msg_groupid", 0);
        msg_grouptype = i.getIntExtra("msg_grouptype", 0);
        initView();
    }

    private void initView() {
        msgList = (XListView) findViewById(R.id.list_cir);
        msgList.setXListViewListener(this);
        msgList.setPullLoadEnable(true);
        msgList.setPullRefreshEnable(true);
        msgList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        adapter = new CircularAdapter(this, criteriaList);
        msgList.setAdapter(adapter);
        getInfo();
        setProgressVisibility(View.GONE);
    }

    private void getInfo() {
        NetHelper.Msglist(msg_groupid + "", msg_grouptype + "", page + "", new NetConnectionInterface.iConnectListener3() {
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
                } else {
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
                Circular circular=new Circular(massage.getJSONObject(i));
                criteriaList.add(circular);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (criteriaList.size() != 0) {
            criteriaList.removeAll(criteriaList);
        }
        page = 1;
        getInfo();
        msgList.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        if (page < maxPage) {
            page++;
            getInfo();
        } else {
            makeShortToast("没有更多了");
        }
        msgList.stopLoadMore();
    }
    //--------------
    private final static String MSGCHANGE="msgchange";

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case MSGCHANGE:
                    int id= (int) notifyUpdateEntity.getObj();
                    criteriaList.remove(id);
                    adapter.notifyDataSetChanged();
                    break;
            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }
}
