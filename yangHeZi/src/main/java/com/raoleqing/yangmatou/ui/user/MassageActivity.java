package com.raoleqing.yangmatou.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.MassageAdapter;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MassageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView activity_return;
    private MassageAdapter adapter;
    private List<Massage> massageList=new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        setTitleText("消息中心");
        setProgressVisibility(View.GONE);
        viewInfo();
        getMassage();
    }

    private void viewInfo() {
        activity_return= (ImageView) findViewById(R.id.activity_return);
        listView= (ListView) findViewById(R.id.lv_massage);
        adapter=new MassageAdapter(this,massageList);
        listView.setAdapter(adapter);
        activity_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.activity_return:
                    this.onBackPressed();
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }
    }

    public void getMassage() {
        NetHelper.MessageInfo(new NetConnectionInterface.iConnectListener3() {
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
                JSONArray obj=result.optJSONArray(Constant.DATA);
                if (obj==null){
                    makeShortToast("暂无消息");
                }
                setMsg(obj);
            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });

    }

    private void setMsg(JSONArray obj) {
        try {
            for (int i=0;i<obj.length();i++){
                JSONObject object=obj.getJSONObject(i);
                Massage massage=new Massage(object);
                massageList.add(massage);
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            throwEx(e);
        }
    }
}
