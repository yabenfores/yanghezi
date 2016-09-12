package com.raoleqing.yangmatou.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.OrderAdapter;
import com.raoleqing.yangmatou.ben.Order;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 待评价
 **/
public class ReviewFragment extends Fragment implements OnClickListener ,XListView.IXListViewListener{

    private int page = 1;
    private boolean noMore = false;

    private LinearLayout null_data_layout;
    private XListView payment_listview;

    private List<Order> orderList = new ArrayList<Order>();
    private OrderAdapter adapter;


//	ReviewFragment() {
//	}

    public static Fragment newInstance() {
        Fragment fragment = new ReviewFragment();
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
        getData();
        return view;
    }

    private void viewInfo(View view) {
        // TODO Auto-generated method stub
        null_data_layout = (LinearLayout) view.findViewById(R.id.null_data_layout);
        payment_listview = (XListView) view.findViewById(R.id.payment_listview);
        orderList.removeAll(orderList);
        adapter = new OrderAdapter(getActivity(), orderList);
        payment_listview.setAdapter(adapter);
        payment_listview.setPullLoadEnable(true);
        payment_listview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        payment_listview.setXListViewListener(this);
        payment_listview.setPullRefreshEnable(true);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


    /*
     * 10代付款 20待发货
30待收货 40已完成
0已取消 50待评价

*/
    private void getData() {

        ((OrderActivity) getActivity()).setMainProgress(View.VISIBLE);


//        int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");
        RequestParams params = new RequestParams();
        params.put("state_type", 50);

        String state_type = "4";

        NetHelper.member_order(state_type,page+"", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                payment_listview.stopLoadMore();
                payment_listview.stopRefresh();
                ((OrderActivity) getActivity()).setMainProgress(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                resolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {
                ((OrderActivity) getActivity()).makeShortToast(result.optString(Constant.INFO));

            }
        });

    }

    protected void resolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println(response);

        try {
            JSONArray data = response.optJSONArray("data");
            if (data.length() < 10) {
                noMore = true;
            }
            if (data.length()==0){
                if (page==1){
                    null_data_layout.setVisibility(View.VISIBLE);
                }
                return;
            }
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                Order mOrder = new Order();
                mOrder.setOrder_id(obj.optString("order_id"));
                mOrder.setOrder_sn(obj.optString("order_sn"));
                mOrder.setGoods_amount(obj.optDouble("goods_amount"));
                mOrder.setOrder_amount(obj.optDouble("order_amount"));
                mOrder.setStore_name(obj.optString("store_name"));
                mOrder.setStore_label(obj.optString("store_label"));
                mOrder.setGoods_num(obj.optInt("goods_num"));
                mOrder.setShipping_fee(obj.optDouble("shipping_fee"));
                JSONArray array = obj.getJSONArray("extend_order_goods");
                JSONObject jsonObject = array.optJSONObject(0);
                mOrder.setExtend_order_goods(jsonObject);
                mOrder.setOrder_state(obj.optInt("order_state"));
                mOrder.setPay_sn(obj.optString("pay_sn"));
                mOrder.setAdd_time(obj.optLong("add_time"));
                mOrder.setRefund_state(obj.optInt("refund_state"));
                mOrder.setRefund_id(obj.optString("refund_id"));
                orderList.add(mOrder);

            }
            if (orderList.size()==0){
                null_data_layout.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        ((OrderActivity) getActivity()).setMainProgress(View.GONE);

    }

    @Override
    public void onRefresh() {
        orderList.removeAll(orderList);
        noMore=false;
        getData();
    }

    @Override
    public void onLoadMore() {
        if (noMore){
            payment_listview.stopLoadMore();
            ToastUtil.MakeShortToast(getContext(),"没有更多了");
        }else {
            page++;
            getData();
        }
    }
}
