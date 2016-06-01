package com.raoleqing.yangmatou.ui.showwhat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.BaseFragment;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShowShatAdapter;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ben.AdvManage;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.common.CheckNet;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.MyPagerAdapter;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.goods.GoodsListActivity;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.ui.user.AboutActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.WindowManagerUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.webserver.WebActivity;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.NotifyUpdateEntity;

/*
 * 秀一下
 * **/
public class ShowShatFragment extends Fragment implements OnClickListener, XListView.IXListViewListener {

    private LinearLayout show_write_show;
    private LinearLayout show_message;
    private LinearLayout show_item01;
    private LinearLayout show_item02;
    private LinearLayout show_item03;
    private LinearLayout show_item04;
    private LinearLayout show_item05;
    private XListView show_lsit;

    private ChildViewPager main_viewPager;


    private int dotype = 1, page = 1, maxPage = 1;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private List<ShowShat> sowShatList = new ArrayList<ShowShat>();
    private ShowShatAdapter adapter;
    private View advManageView;
    private List<AdvManage> advManageList = new ArrayList<AdvManage>();

    private int pagerIndex = 0;// 广告页面
    private int pointsCount = 0;
    private LinearLayout main_viewPager_point;
    private int width;
    private int height;
    private int showType = 0;
    private String brand_id = "", city_id = "";
//	ShowShatFragment() {
//	}

    public static Fragment newInstance() {
        Fragment fragment = new ShowShatFragment();
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
        View view = inflater.inflate(R.layout.show_shat_fragment, null);
        manager = getFragmentManager();
        WindowManagerUtils manage = new WindowManagerUtils(getActivity());
        width = manage.getWidth();
        height = manage.getHeight();
        brand_id = ((MainActivity) getActivity()).getBrand_id();
        city_id = ((MainActivity) getActivity()).getCity_id();
        showType = ((MainActivity) getActivity()).getShowType();
        viewInfo(view);
        getAdvertising();
        return view;
    }

    private void viewInfo(View view) {
        // TODO Auto-generated method stub

        adapter = new ShowShatAdapter((MainActivity) getActivity(), sowShatList);
        show_write_show = (LinearLayout) view.findViewById(R.id.show_write_show);
        show_message = (LinearLayout) view.findViewById(R.id.show_message);

        show_lsit = (XListView) view.findViewById(R.id.show_lsit);
        show_lsit.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        show_lsit.setXListViewListener(this);

        show_lsit.setAdapter(adapter);


        if (advManageView == null) {
            advManageView = getActivity().getLayoutInflater().from(getActivity())
                    .inflate(R.layout.show_shat_list_title, null);
        }
        main_viewPager = (ChildViewPager) advManageView.findViewById(R.id.main_viewPager);
        main_viewPager_point = (LinearLayout) advManageView.findViewById(R.id.main_viewPager_point);// 页数提示点

        float viewPagerHeight = (float) height * 0.25f;

        ViewGroup.LayoutParams params = main_viewPager.getLayoutParams();
        params.height = (int) viewPagerHeight;
        main_viewPager.setLayoutParams(params);

        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        show_item01 = (LinearLayout) advManageView.findViewById(R.id.show_item01);
        show_item02 = (LinearLayout) advManageView.findViewById(R.id.show_item02);
        show_item03 = (LinearLayout) advManageView.findViewById(R.id.show_item03);
        show_item04 = (LinearLayout) advManageView.findViewById(R.id.show_item04);
        show_item05 = (LinearLayout) advManageView.findViewById(R.id.show_item05);
        show_item01.setOnClickListener(this);
        show_item02.setOnClickListener(this);
        show_item03.setOnClickListener(this);
        show_item04.setOnClickListener(this);
        show_item05.setOnClickListener(this);
        show_lsit.addHeaderView(advManageView);
        show_lsit.setPullLoadEnable(true);// 上拉刷新
        show_lsit.setPullRefreshEnable(true);// 下拉刷新


        show_write_show.setOnClickListener(this);
        show_message.setOnClickListener(this);
        setView(showType);
    }

    private void getInfo() {
        NetHelper.Evaluate(dotype + "", page + "", city_id, brand_id, new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {
                ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {

                ((MainActivity) getActivity()).setProgressVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject result) {
                show_lsit.stopRefresh();
                show_lsit.stopLoadMore();
                ResolveJson(result);
            }

            @Override
            public void onFail(JSONObject result) {


                ((MainActivity) getActivity()).makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private void ResolveJson(JSONObject result) {

        try {

            JSONObject data = result.optJSONObject("data");
            if (data == null) {
                sowShatList.removeAll(sowShatList);
                adapter.notifyDataSetChanged();
                return;
            }

//            if (sowShatList.size() > 0) {
//                sowShatList.retainAll(sowShatList);
//            }

            maxPage = data.optInt("pagetotal");
            JSONArray obj = data.optJSONArray("evaluates");
            for (int i = 0; i < obj.length(); i++) {
                JSONObject object = obj.optJSONObject(i);
                ShowShat showShat = new ShowShat();
                showShat.setGeval_addtime(object.optLong("geval_addtime"));
                showShat.setGeval_id(object.optInt("geval_id"));
                showShat.setGeval_goodsname(object.optString("geval_goodsname"));
                showShat.setGeval_scores(object.optInt("geval_scores"));
                showShat.setGeval_content(object.optString("geval_content"));
                showShat.setGeval_isanonymous(object.optInt("geval_isanonymous"));
                showShat.setGeval_storename(object.optString("geval_storename"));
                showShat.setGeval_image(object.optString("geval_image"));
                showShat.setGeval_comment_num(object.optInt("geval_comment_num"));
                showShat.setGeval_like_num(object.optInt("geval_like_num"));
                showShat.setIs_like(object.optInt("is_like"));
                showShat.setStore_label(object.optString("store_label"));
                showShat.setMember_avatar(object.optString("member_avatar"));
                showShat.setMember_name(object.optString("member_name"));

                sowShatList.add(showShat);
            }
            adapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.show_write_show:

                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("index", 4);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.show_message:
                String user_msg_helper = SharedPreferencesUtil.getString(BaseActivity.getAppContext(), "user_msg_helper");
                Intent i = new Intent(BaseActivity.getAppContext(), ChatActivity.class);
                i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
                startActivity(i);
                break;

            case R.id.show_item01:
                setView(0);
                break;
            case R.id.show_item02:
                setView(1);
                break;
            case R.id.show_item03:
                Intent intent01 = new Intent(getActivity(), CountryActivity.class);
                startActivity(intent01);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.show_item04:
                Intent intent02 = new Intent(getActivity(), BrandsActivity.class);
                startActivity(intent02);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
//		case R.id.show_item05:
//
//			setView(4);
//
//			break;

            default:
                break;
        }

    }

    private void setView(int i) {
        // TODO Auto-generated method stub
        try {

            switch (i) {
                case 0:
                    dotype = 1;
                    page = 1;
                    brand_id = "";
                    city_id = "";
                    sowShatList.removeAll(sowShatList);
                    getInfo();
                    break;
                case 1:
                    brand_id = "";
                    city_id = "";
                    dotype = 2;
                    page = 1;
                    sowShatList.removeAll(sowShatList);
                    getInfo();
                    break;
                case 2:
                    sowShatList.removeAll(sowShatList);
                    dotype = 3;
                    page = 1;
                    brand_id = "";
                    getInfo();
                    break;
                case 3:
                    sowShatList.removeAll(sowShatList);
                    page = 1;
                    city_id = "";
                    dotype = 4;
                    getInfo();
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 广告数据加载
     **/
    private void getAdvertising() {
        // TODO Auto-generated method stub

        ((MainActivity) getActivity()).setProgressVisibility(View.VISIBLE);

        NetHelper.adImginfo(new NetConnectionInterface.iConnectListener3() {
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
    }

    /**
     * 广告数据解析
     **/
    protected void advertisingResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("广告:" + response);

        try {

            JSONArray data = response.optJSONArray("data");
            if (data == null) {
                return;

            }
            if (advManageList.size() > 0) {
                advManageList.retainAll(advManageList);
            }
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                AdvManage mAdvManage = new AdvManage();
                mAdvManage.setAdv_id(obj.optInt("advimg_id"));
                mAdvManage.setAdv_image(obj.optString("advimg_path"));
                mAdvManage.setAdv_title(obj.optString("adv_title"));
                mAdvManage.setAdv_url(obj.optString("adv_url"));
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

        ArrayList<View> viewList = new ArrayList<View>();
        for (int i = 0; i < advManageList.size(); i++) {

            final AdvManage nAdvManage = advManageList.get(i);
            ImageView img = new ImageView(getActivity());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageLoader.getInstance().displayImage(nAdvManage.getAdv_image(), img,
                    YangHeZiApplication.imageOption(R.drawable.adv_manage_image));
            viewList.add(img);
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);

        main_viewPager.setAdapter(pagerAdapter);
        pointsCount = advManageList.size();

        loadPositionImage();

		main_viewPager.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {

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
        getInfo();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        if (maxPage > page) {
            page++;
            getInfo();
        } else {
            show_lsit.stopLoadMore();
            ToastUtil.MakeShortToast(getActivity().getBaseContext(), "没有更多了");
        }

    }

    //---------------
    public final static String COUNTRY = "country";
    public final static String BRAND = "brand";

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        this.notifyUpdate(notifyUpdateEntity);
        try {

            switch (notifyUpdateEntity.getNotifyTag()) {
                case COUNTRY:
                    city_id = (String) notifyUpdateEntity.getObj();
                    setView(2);
                    break;
                case BRAND:
                    brand_id = (String) notifyUpdateEntity.getObj();
                    setView(3);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
