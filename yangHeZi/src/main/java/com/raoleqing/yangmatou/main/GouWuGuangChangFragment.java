package com.raoleqing.yangmatou.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.StoreAdapter;
import com.raoleqing.yangmatou.ben.AdvManage;
import com.raoleqing.yangmatou.ben.Goods;
import com.raoleqing.yangmatou.ben.Pavilion;
import com.raoleqing.yangmatou.ben.Store;
import com.raoleqing.yangmatou.common.CheckNet;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.ChildViewPager.OnSingleTouchListener;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.common.MyPagerAdapter;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.WindowManagerUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.webserver.WebActivity;
import com.raoleqing.yangmatou.xlist.XListView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/*
 *购物广场
 * **/
public class GouWuGuangChangFragment extends Fragment implements XListView.IXListViewListener {
    private XListView listView;
    private View advManageView;
    private ChildViewPager main_viewPager;
    private LinearLayout main_viewPager_point;
    private LinearLayout main_pavilion_layout;// 国家布局
    private List<AdvManage> advManageList = new ArrayList<AdvManage>();
    private List<Store> storeList = new ArrayList<Store>();
    private StoreAdapter storeAdapter;
    private List<Pavilion> pavilionList = new ArrayList<Pavilion>();
    private int page = 1,likeNum=0;
    private boolean isData = true;
    private int pagerIndex = 0;// 广告页面
    private int pointsCount = 0;


    private int width;
    private int height;

    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    String name = (String) msg.obj;
                    // 收藏
                    showFavoritesStoreDialog(msg.arg1, msg.arg2, name);

                    break;
                case 1:
                    String name01 = (String) msg.obj;
                    // 取消
                    showCancelStore(msg.arg1, msg.arg2, name01);

                    break;

                default:
                    break;
            }
        }

    };

    public static GouWuGuangChangFragment newInstance() {
        return new GouWuGuangChangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        WindowManagerUtils manage = new WindowManagerUtils(getActivity());
        width = manage.getWidth();
        height = manage.getHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.gou_wu_guang_chang, null);

        viewInfo(view);
        getAdvertising();
        getStoreList();
        getPavilion();
        return view;
    }

    private void viewInfo(View view) {
        // TODO Auto-generated method stub
        listView = (XListView) view.findViewById(R.id.gou_wu_listview);
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        storeAdapter = new StoreAdapter(getActivity(), storeList, myHandler);
        // InfoOnItemClick infoItem = new InfoOnItemClick();
        // listview.setOnItemClickListener(infoItem);
        listView.setAdapter(storeAdapter);
        listView.setXListViewListener(this);
        listView.setPullLoadEnable(true);// 上拉刷新
        listView.setPullRefreshEnable(true);// 下拉刷新

        if (listView.getHeaderViewsCount() < 2) {

            if (advManageView == null) {
                advManageView = getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.adv_manage,
                        null);
            }

            main_viewPager = (ChildViewPager) advManageView.findViewById(R.id.main_viewPager);// 广告栏
            main_viewPager_point = (LinearLayout) advManageView.findViewById(R.id.main_viewPager_point);// 页数提示点
            main_pavilion_layout = (LinearLayout) advManageView.findViewById(R.id.main_pavilion_layout);// 页数提示点
            float viewPagerHeight = (float) height * 0.25f;
            LayoutParams params = main_viewPager.getLayoutParams();
            params.height = (int) viewPagerHeight;
            main_viewPager.setLayoutParams(params);
            listView.addHeaderView(advManageView);

            main_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                    changePositionImage(arg0);
                    pagerIndex = arg0;
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }

    }

    /**
     * 广告数据加载
     **/
    private void getAdvertising() {
        // TODO Auto-generated method stub

        try {


            ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);

            NetHelper.advManage(new NetConnectionInterface.iConnectListener3() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {
                    ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

                }

                @Override
                public void onSuccess(JSONObject result) {

                    advertisingResolveJson(result);

                }

                @Override
                public void onFail(JSONObject result) {

                    ((MainActivity) getActivity()).makeShortToast(result.optString(Constant.INFO));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广告数据解析
     **/
    protected void advertisingResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("广告:" + response);

        try {

            JSONArray data = response.optJSONArray("data");
            if (advManageList.size() > 0) {
                advManageList.retainAll(advManageList);
            }
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                AdvManage mAdvManage = new AdvManage();
                mAdvManage.setAdv_id(obj.optInt("advimg_id"));
                mAdvManage.setAdv_type(obj.optString("adv_type"));
                mAdvManage.setAdv_name(obj.optString("adv_name"));
                mAdvManage.setAdv_image(obj.optString("advimg_path"));
                mAdvManage.setAdv_url(obj.optString("adv_url"));
                mAdvManage.setAdv_title(obj.optString("adv_title"));
                advManageList.add(mAdvManage);
            }

            setAdvManage();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

    }

    /**
     * 广告内容
     **/
    private void setAdvManage() {
        // TODO Auto-generated method stub

        try {


            ArrayList<View> viewList = new ArrayList<View>();
            for (int i = 0; i < advManageList.size(); i++) {

                final AdvManage nAdvManage = advManageList.get(i);
                ImageView img = new ImageView(getActivity());
                img.setScaleType(ScaleType.CENTER_CROP);
                img.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                ImageLoader.getInstance().displayImage(nAdvManage.getAdv_image(), img,
                        YangHeZiApplication.imageOption(R.drawable.adv_manage_image));
                viewList.add(img);
            }

            MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);

            main_viewPager.setAdapter(pagerAdapter);
            pointsCount = advManageList.size();

            loadPositionImage();

            main_viewPager.setOnSingleTouchListener(new OnSingleTouchListener() {

                @Override
                public void onSingleTouch() {
                    // TODO Auto-generated method stub
                    // System.out.println("ChildViewPager --> onclick()");
                    if (!CheckNet.isNetworkConnected(getActivity())) {
                        Toast.makeText(getActivity(), "没有可用的网络连接，请检查网络设置", 1).show();
                        return;
                    }
                    AdvManage nAdvManage = advManageList.get(pagerIndex);
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url", nAdvManage.getAdv_url());
                    intent.putExtra("title", nAdvManage.getAdv_title());
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public XListView getListView() {
        return listView;
    }

    // 店铺列表
    private void getStoreList() {
        // TODO Auto-generated method stub

        ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);


        NetHelper.getStoreList(page + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                listView.stopRefresh();
                StoreListResolveJson(result);
            }

            @Override
            public void onFail(JSONObject result) {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));
            }
        });

    }

    protected void StoreListResolveJson(JSONObject response) {

        try {

            if (response == null) {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
                onLoad();
                return;
            }

            JSONArray data = response.optJSONArray("data");
            if (storeList.size() > 0 && page == 1) {
                storeList.retainAll(storeList);
            }

            if (data.length() < 10) {
                isData = false;
            }

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                Store mStore = new Store();
                mStore.setId(obj.optInt("id"));
                mStore.setStore_id(obj.optInt("store_id"));
                mStore.setStore_name(obj.optString("store_name"));
                mStore.setTitle(obj.optString("title"));
                mStore.setContent(obj.optString("content"));
                mStore.setCreate_time(obj.optLong("create_time"));
                mStore.setState(obj.optInt("state"));
                mStore.setAddress(obj.optString("store_address"));
                mStore.setFans(obj.optString("fav_total"));
                mStore.setImg(obj.optString("store_label"));
                mStore.setAttention(obj.optInt("is_favorite"));
                JSONArray goods_list = obj.optJSONArray("goods_array");
                List<Goods> goodsList = new ArrayList<Goods>();

                if (goods_list != null) {
                    for (int j = 0; j < goods_list.length(); j++) {
                        JSONObject goodsContent = goods_list.optJSONObject(j);
                        Goods mGoods = new Goods();
                        mGoods.setGoods_id(goodsContent.optInt("goods_id"));
                        mGoods.setStore_name(goodsContent.optString("store_name"));
                        mGoods.setGoods_image(goodsContent.optString("goods_image"));
                        mGoods.setGoods_promotion_price(goodsContent.optDouble("goods_price"));
                        goodsList.add(mGoods);

                    }
                }

                mStore.setGoods_list(goodsList);

                storeList.add(mStore);
            }


            storeAdapter.notifyDataSetChanged();
            onLoad();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

    }

    /**
     * 国家馆:
     **/
    private void getPavilion() {
        // TODO Auto-generated method stub
        ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);

        HttpUtil.post(getActivity(), HttpUtil.GET_PAVILION, new JsonHttpResponseHandler() {

            // 获取数据成功会调用这里
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, headers, response);
                pavilionResolveJson(response);
            }

            // 失败
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
            }

            // 结束
            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
                System.out.println("onFinish");

            }

        });

    }

    /**
     * 国家馆:
     **/
    protected void pavilionResolveJson(JSONObject response) {
        System.out.println("aaaaaaaaa" + response);
        // TODO Auto-generated method stub

        try {
            int code = response.optInt("code");

            if (response == null) {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
                return;
            }

            if (code == 1 || code == 200) {
                JSONArray data = response.optJSONArray("data");
                if (pavilionList.size() > 0) {
                    pavilionList.retainAll(pavilionList);
                }
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.optJSONObject(i);
                    Pavilion mPavilion = new Pavilion();
                    mPavilion.setFlag_id(obj.optInt("advimg_id"));
                    mPavilion.setFlag_imgSrc(obj.optString("advimg_path"));
                    mPavilion.setAdv_url(obj.optString("adv_url"));
                    mPavilion.setAdv_title(obj.optString("adv_title"));
                    pavilionList.add(mPavilion);
                }
                setPavilion();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

    }

    /**
     * 关注店铺
     **/
    protected void showFavoritesStoreDialog(final int position, final int store_id, String store_name) {
        // TODO Auto-generated method stub

        favoritesStore(position, store_id);


    }

    /**
     * 取消店铺
     **/
    protected void showCancelStore(final int position, final int store_id, String store_name) {
        // TODO Auto-generated method stub

        cancelStore(position, store_id);

    }

    /**
     * 关注店铺
     **/
    protected void favoritesStore(final int position, int store_id) {
        // TODO Auto-generated method stub
        // Home/Index/favoritesstore

        ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);

//		int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");

        RequestParams params = new RequestParams();
        params.put("fid", store_id);
//		params.put("uid", 1090);

        NetHelper.favoritesstore(store_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                favoritesResolveJson(result, position);

            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

            }
        });

    }

    protected void favoritesResolveJson(JSONObject response, int position) {
        // TODO Auto-generated method stub

        try {
            int code = response.optInt("code");
            String message = response.optString("message");

            if (response == null) {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
                return;
            }

            storeList.get(position).setAttention(1);
            likeNum= Integer.parseInt(storeList.get(position).getFans());
            likeNum++;
            storeList.get(position).setFans(likeNum+"");
            storeAdapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), message, 1).show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

    }

    /**
     * 关注店铺
     **/
    protected void cancelStore(final int position, int store_id) {
        // TODO Auto-generated method stub
        // Home/Index/cancelStore

        ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);

//		int uid = SharedPreferencesUtil.getInt(getActivity(), "member_id");

        RequestParams params = new RequestParams();
        params.put("fid", store_id);
//		params.put("uid", 1090);

        NetHelper.cancelStore(store_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                cancelResolveJson(result, position);

            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

            }
        });

    }

    protected void cancelResolveJson(JSONObject response, int position) {

        try {
            int code = response.optInt("code");
            String message = response.optString("message");

            if (response == null) {
                Toast.makeText(getActivity(), message, 1).show();
                ((MainActivity) getActivity()).setMainProgress(View.GONE);
                return;
            }

            storeList.get(position).setAttention(0);
            likeNum= Integer.parseInt(storeList.get(position).getFans());
            likeNum--;
            storeList.get(position).setFans(likeNum+"");

            storeAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), message, 1).show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getActivity(), "取消失败", 1).show();
        }

        ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
        // TODO Auto-generated method stub

    }

    private void setPavilion() {
        // TODO Auto-generated method stub

        main_pavilion_layout.removeAllViews();
        RadioGroup myRadioGroup = new RadioGroup(getActivity());
        myRadioGroup.setPadding(0, 1, 0, 0);
        myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        main_pavilion_layout.addView(myRadioGroup);
        for (int i = 0; i < pavilionList.size(); i++) {
            final Pavilion mPavilion = pavilionList.get(i);
            ImageView radio = new ImageView(getActivity());
            ImageLoader.getInstance().displayImage(mPavilion.getFlag_imgSrc(), radio,
                    YangHeZiApplication.imageOption(R.drawable.pavilion_icon));
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.25),
                    UnitConverterUtils.dip2px(getActivity(), 80));
            radio.setLayoutParams(l);
//            int x = UnitConverterUtils.dip2px(getActivity(), 10);
            radio.setBackgroundColor(Color.WHITE);
//            radio.setPadding(x, x, x, x);
            radio.setScaleType(ScaleType.FIT_CENTER);

            radio.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("cid", mPavilion.getFlag_id());
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
            });
            myRadioGroup.addView(radio);
        }

    }

    // 获取网络数据之后 加载 圆点图标*/
    protected void loadPositionImage() {
        ImageView aImageView = null;
        main_viewPager_point.removeAllViews();
        for (int i = 0; i < pointsCount; i++) {
            aImageView = new ImageView(getActivity());
            aImageView.setPadding(10, 0, 10, 0);
            if (i == 0) {
                aImageView.setImageResource(R.drawable.point2);
            } else {
                aImageView.setImageResource(R.drawable.point1);
            }
            main_viewPager_point.addView(aImageView);
        }
    }

    /**
     * 更改提示h
     */
    public void changePositionImage(int aPos) {

        ImageView aImageView = (ImageView) main_viewPager_point.getChildAt(aPos);
        if (aImageView != null) {
            aImageView.setImageResource(R.drawable.point2);
        }
        for (int i = 0; i < pointsCount; i++) {
            if (i != aPos) {
                aImageView = (ImageView) main_viewPager_point.getChildAt(i);
                aImageView.setImageResource(R.drawable.point1);
            }
        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

        getStoreList();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        page++;
        getStoreList();

    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        SimpleDateFormat refleshSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        listView.setRefreshTime(refleshSimpleDateFormat.format(new Date()));
    }

}
