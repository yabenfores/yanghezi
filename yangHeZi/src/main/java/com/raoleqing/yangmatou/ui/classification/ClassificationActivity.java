package com.raoleqing.yangmatou.ui.classification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.CatGoodesAdapter;
import com.raoleqing.yangmatou.adapter.OneCatAdapter;
import com.raoleqing.yangmatou.ben.CatGoods;
import com.raoleqing.yangmatou.ben.OneCat;
import com.raoleqing.yangmatou.ben.ThreeData;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 **/
public class ClassificationActivity extends BaseActivity implements OnClickListener {

    private ImageView activity_return;
    private ImageView qrode;//二维码

    private ListView classification_listview;
    private ListView classification_gridview;

    private int contentIndex = 0;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private EditText editText;
    private int gc_id = 0;
    private String cat_content;

    private List<OneCat> oneCatList = new ArrayList<OneCat>();// 标准内容
    private List<CatGoods> goodsList = new ArrayList<CatGoods>();// 标准内容
    private OneCatAdapter catAdapter;
    private CatGoodesAdapter goodsAdapter;

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
        setContentView(R.layout.classification_activity);
        manager = getSupportFragmentManager();
        setTitleText("分类查看");
        Intent intent = this.getIntent();
        gc_id = intent.getIntExtra("gc_id", 0);
        cat_content = intent.getStringExtra("cat_content");

        myHandler.sendEmptyMessageDelayed(0, 50);
    }

    protected void viewInfo() {
        // TODO Auto-generated method stub
        activity_return = (ImageView) findViewById(R.id.classification_return);
        qrode = (ImageView) findViewById(R.id.classification_code);
        classification_listview = (ListView) findViewById(R.id.classification_listview);
        classification_gridview = (ListView) findViewById(R.id.classification_gridview);
        setTitleVisibility(View.GONE);
        editText= (EditText) findViewById(R.id.goods_list_search);
        catAdapter = new OneCatAdapter(ClassificationActivity.this, oneCatList);
        catAdapter.setIndex(gc_id);
        classification_listview.setDivider(null);
        classification_listview.setAdapter(catAdapter);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String search_str = editText.getText().toString();
                        if (search_str != null && !search_str.trim().equals("")) {
                            Intent intent = new Intent(ClassificationActivity.this, GoodsListActivity.class);
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


        classification_listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                catAdapter.setIndex(arg2);
                catAdapter.notifyDataSetChanged();

                setProgressVisibility(View.VISIBLE);
                getData(oneCatList.get(arg2).getGc_id());
            }


        });

        goodsAdapter = new CatGoodesAdapter(this, goodsList);
        classification_gridview.setAdapter(goodsAdapter);
        setTitle();
        activity_return.setOnClickListener(this);
        qrode.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.classification_return:
                ClassificationActivity.this.onBackPressed();
                break;
            case R.id.classification_code:
                Intent intent = new Intent();
                intent.setClass(ClassificationActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

                break;

            default:
                break;
        }

    }


    private void setTitle() {
        // TODO Auto-generated method stub
        try {

            JSONObject content = new JSONObject(cat_content);
            JSONArray data = content.optJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                OneCat mOneCat = new OneCat();
                mOneCat.setGc_id(obj.optInt("gc_id"));
                mOneCat.setGc_name(obj.optString("gc_name").trim());
                oneCatList.add(mOneCat);
            }

            catAdapter.setIndex(gc_id);
            catAdapter.notifyDataSetInvalidated();
            getData(oneCatList.get(gc_id).getGc_id());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    private void getData(int cid) {
        // TODO Auto-generated method stub


        RequestParams params = new RequestParams();
        params.put("cid", cid);
        NetHelper.getCatList(cid + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                resolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {

                ToastUtil.MakeShortToast(getAppContext(), result.optString(Constant.INFO));

            }
        });


    }

    protected void resolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        try {

            JSONArray data = response.optJSONArray("data");
            if (data == null) {
                setProgressVisibility(View.GONE);
                return;
            }
            if (goodsList.size() > 0) {
                goodsList.retainAll(goodsList);
                goodsList.clear();
            }

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                CatGoods mCatGoods = new CatGoods();
                mCatGoods.setGc_name(obj.optString("gc_name"));
                mCatGoods.setGc_id(obj.optInt("gc_id"));
                List<ThreeData> threeList = new ArrayList<ThreeData>();
                JSONArray three_data = obj.optJSONArray("three_data");
                if (three_data==null) return;
                for (int j = 0; j < three_data.length(); j++) {
                    JSONObject threrObj = three_data.getJSONObject(j);
                    ThreeData mThreeData = new ThreeData();
                    mThreeData.setGc_id(threrObj.optInt("gc_id"));
                    mThreeData.setGc_name(threrObj.optString("gc_name"));
                    mThreeData.setGc_thumb(threrObj.optString("gc_thumb"));
                    threeList.add(mThreeData);
                }
                mCatGoods.setThree_data(threeList);

                goodsList.add(mCatGoods);
            }
            goodsAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setProgressVisibility(View.GONE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    //mTextView.setText(bundle.getString("result"));
                    //显示
                    //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }


}
