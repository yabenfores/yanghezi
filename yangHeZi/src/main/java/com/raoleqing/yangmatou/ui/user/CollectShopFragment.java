package com.raoleqing.yangmatou.ui.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.CollectShopAdapter;
import com.raoleqing.yangmatou.ben.CollectShop;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 商加收藏
 **/
public class CollectShopFragment extends Fragment implements OnClickListener {

    private LinearLayout null_data_layout;
    private ListView payment_listview;

    private List<CollectShop> collectShopList = new ArrayList<CollectShop>();
    private CollectShopAdapter adapter;

//	CollectShopFragment() {
//	}

    public static Fragment newInstance() {
        Fragment fragment = new CollectShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.payment_fragment, null);

        viewInfo(view);
        return view;
    }

    private void viewInfo(View view) {
        // TODO Auto-generated method stub
        null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
        payment_listview = (ListView) view.findViewById(R.id.payment_listview);

//        for (int i = 0; i < 10; i++) {
//            collectShopList.add(new CollectShop());
//        }
//        adapter = new CollectShopAdapter(getActivity(), collectShopList);
//        payment_listview.setAdapter(adapter);
//        null_data_layout.setVisibility(View.GONE);
        getData();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private void getData() {

        //((OrderActivity) getActivity()).setMainProgress(View.VISIBLE);

//        int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);

        NetHelper.member_fslist(new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                resolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {

            }
        });

    }

    protected void resolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("Home/Users/member_fslist: " + response);

        try {
            int code = response.optInt("code");
            String message = response.optString("message");

            if (response == null) {
                Toast.makeText(getActivity(), message, 1).show();
                ((OrderActivity) getActivity()).setMainProgress(View.GONE);
                return;
            }
            if (response.optJSONArray("data").equals(null)) {
                return;
            }
            JSONArray data = response.optJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                CollectShop mCollectShop = new CollectShop();
                mCollectShop.setStore_id(obj.optInt("store_id"));
                mCollectShop.setStore_id(obj.optInt("store_id"));
                mCollectShop.setStore_name(obj.optString("store_name"));
                mCollectShop.setTitle(obj.optString("title"));
                mCollectShop.setContent(obj.optString("content"));
                mCollectShop.setCreate_time(obj.optLong("create_time"));
                mCollectShop.setState(obj.optInt("state"));
                mCollectShop.setAddress(obj.optString("address"));
                mCollectShop.setFans(obj.optString("fans"));
                mCollectShop.setImg(obj.optString("img"));
                mCollectShop.setAttention(obj.optInt("attention"));
                JSONArray goods_list = obj.optJSONArray("goods_list");
                List<Goods> goodsList = new ArrayList<Goods>();
                for (int j = 0; j < goods_list.length(); j++) {

                    JSONObject goodsContent = goods_list.optJSONObject(j);
                    Goods mGoods = new Goods();
                    mGoods.setGoods_id(goodsContent.optInt("goods_id"));
                    mGoods.setStore_name(goodsContent.optString("store_name"));
                    mGoods.setGoods_image(goodsContent.optString("goods_image"));
                    mGoods.setGoods_promotion_price(goodsContent.optDouble("goods_promotion_price"));
                    goodsList.add(mGoods);

                }
                mCollectShop.setGoods_list(goodsList);
                collectShopList.add(mCollectShop);
            }
            adapter = new CollectShopAdapter(getActivity(), collectShopList);
            payment_listview.setAdapter(adapter);
            null_data_layout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        //((OrderActivity) getActivity()).setMainProgress(View.GONE);

    }

}
