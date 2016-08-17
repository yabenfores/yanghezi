package com.raoleqing.yangmatou.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ShopAdapter;
import com.raoleqing.yangmatou.adapter.ShopSaiAdapter;
import com.raoleqing.yangmatou.ben.Shop;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import entity.NotifyUpdateEntity;

/**
 * 商店
 **/
public class ShopActivity extends BaseActivity implements OnClickListener, XListView.IXListViewListener {

    private ImageView activity_return;
    private ImageView goods_shop_iocn;
    private LinearLayout shop_introduction;
    private LinearLayout shop_filter;
    private LinearLayout shop_layout01;
    private LinearLayout shop_layout02;
    private GridView listView;

    private XListView shop_list;
    private int isFov = 0, gcId = 0, page = 1, maxPage = 1;
    private Button but_attention;
    private TextView goods_shop_name, goods_shop_Fans, tv_shop_address, tv_shop_com;
    private int introductionShow = 0;
    private int filterShow = 0;

    private List<Shop> sowShatList = new ArrayList<>();
    private List<ShopSai> shopSaiList = new ArrayList<>();
    private ShopAdapter adapter;
    private ShopSaiAdapter shopSaiAdapter;
    private int store_id;

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
        setContentView(R.layout.shop_activity);
        Intent intent = this.getIntent();
        store_id = intent.getIntExtra("store_id", 0);
        setTitleText("店铺");
        myHandler.sendEmptyMessageDelayed(0, 50);
    }

    protected void viewInfo() {

        but_attention = (Button) findViewById(R.id.but_attention);
        activity_return = (ImageView) findViewById(R.id.activity_return);
        goods_shop_iocn = (ImageView) findViewById(R.id.goods_shop_iocn);
        shop_introduction = (LinearLayout) findViewById(R.id.shop_introduction);
        shop_filter = (LinearLayout) findViewById(R.id.shop_filter);
        shop_layout01 = (LinearLayout) findViewById(R.id.shop_layout01);
        shop_layout02 = (LinearLayout) findViewById(R.id.shop_layout02);
        shop_list = (XListView) findViewById(R.id.shop_list);
        listView = (GridView) findViewById(R.id.shop_listview);
        goods_shop_name = (TextView) findViewById(R.id.goods_shop_name);
        goods_shop_Fans = (TextView) findViewById(R.id.goods_shop_Fans);
        tv_shop_address = (TextView) findViewById(R.id.tv_shop_address);
        tv_shop_com = (TextView) findViewById(R.id.tv_shop_com);
        adapter = new ShopAdapter(this, sowShatList);
        shopSaiAdapter = new ShopSaiAdapter(this, shopSaiList);
        shop_list.setAdapter(adapter);
        listView.setAdapter(shopSaiAdapter);
        shop_introduction.setOnClickListener(this);
        but_attention.setOnClickListener(this);
        shop_filter.setOnClickListener(this);
        activity_return.setOnClickListener(this);
        shop_list.setXListViewListener(this);
        shop_list.setPullLoadEnable(true);
        shop_list.setPullRefreshEnable(true);
        getInfo();
        getGoodsList();
    }

    private void getGoodsList() {

        NetHelper.Storegoods(store_id + "", gcId + "", page + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {
                setProgressVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                shop_list.stopRefresh();
                shop_list.stopLoadMore();
                setProgressVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject result) {
                resolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));

            }
        });

    }

    private void resolveJson(JSONObject result) {
        JSONObject json = result.optJSONObject(Constant.DATA);
        if (json == null) return;
        maxPage = json.optInt("pagetotal");
        JSONArray array = json.optJSONArray("goods_array");
        if (array == null) return;
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.optJSONObject(i);
                Shop shop = new Shop();
                shop.setGoods_id(object.optInt("goods_id"));
                shop.setGoods_name(object.optString("goods_name"));
                shop.setGoods_jingle(object.optString("goods_jingle"));
                shop.setGoods_price(object.optDouble("goods_price"));
                shop.setGoods_marketprice(object.optDouble("goods_marketprice"));
                shop.setGoods_salenum(object.optInt("goods_salenum"));
                shop.setGoods_storage(object.optInt("goods_storage"));
                shop.setTransaction_type(object.optInt("transaction_type"));
                shop.setFlag_imgSrc(object.optString("flag_imgSrc"));
                shop.setFlag_name(object.optString("flag_name"));
                shop.setGoods_images(object.optString("goods_images"));
                shop.setIs_favorite(object.optInt("is_favorite"));
                shop.setComment_total(object.optString("comment_total"));
                shop.setGoods_publictime(object.optString("goods_publictime"));
                sowShatList.add(shop);
            } catch (Exception e) {
                throwEx(e);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void getInfo() {
        NetHelper.Store(store_id + "", new NetConnectionInterface.iConnectListener3() {
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
                JSONObject object = result.optJSONObject(Constant.DATA);
                if (object == null) {
                    return;
                }
                try {
                    setTitleText(object.optString("store_name"));
                    goods_shop_name.setText(object.optString("store_name"));
                    tv_shop_address.setText(" " + object.optString("store_address"));
                    tv_shop_com.setText(object.optString("store_description"));
                    ImageLoader.getInstance().displayImage(object.optString("store_label"), goods_shop_iocn);
                    isFov = object.optInt("is_favorite");
                    if (isFov == 0) {
                        but_attention.setText("+ 关注");
                    } else {
                        but_attention.setText("已关注");
                    }
                    goods_shop_Fans.setText("粉丝：" + object.optString("fav_total"));
                    JSONArray jsonArray = object.optJSONArray("gclass_array");
                    if (jsonArray == null) return;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        ShopSai shopSai = new ShopSai();
                        shopSai.setGc_id(obj.optInt("gc_id"));
                        shopSai.setGc_name(obj.optString("gc_name").trim());
                        shopSaiList.add(shopSai);
                    }
                    shopSaiAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    throwEx(e);
                }
            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.activity_return:
                ShopActivity.this.onBackPressed();
                break;
            case R.id.shop_introduction:
                if (introductionShow == 0) {
                    introductionShow = 1;
                    shop_layout02.setVisibility(View.GONE);
                    shop_layout01.setVisibility(View.VISIBLE);
                } else {
                    introductionShow = 0;
                    shop_layout01.setVisibility(View.GONE);
                }

                break;
            case R.id.shop_filter:
                if (filterShow == 0) {
                    filterShow = 1;
                    shop_layout01.setVisibility(View.GONE);
                    shop_layout02.setVisibility(View.VISIBLE);
                } else {
                    filterShow = 0;
                    shop_layout02.setVisibility(View.GONE);
                }
                break;

            case R.id.but_attention:
                if (UserUitls.isLongin(this)) {
                    if (isFov == 0) {
                        NetHelper.favoritesstore(store_id + "", new NetConnectionInterface.iConnectListener3() {
                            @Override
                            public void onStart() {
                                setProgressVisibility(View.GONE);
                            }

                            @Override
                            public void onFinish() {
                                setProgressVisibility(View.GONE);

                            }

                            @Override
                            public void onSuccess(JSONObject result) {
                                but_attention.setText("已关注");
                            }

                            @Override
                            public void onFail(JSONObject result) {
                                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

                            }
                        });
                    } else {
                        NetHelper.cancelStore(store_id + "", new NetConnectionInterface.iConnectListener3() {
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
                                but_attention.setText("+ 关注");
                            }

                            @Override
                            public void onFail(JSONObject result) {
                                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

                            }
                        });

                    }
                } else {
                    UserUitls.longInDialog(this);
                }
                break;

            default:
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

    public void showShare() {
        ShareSDK.initSDK(getBaseContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://baidu.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(getBaseContext());
    }

    //---------------
    public final static String SAISELECT = "saiSelect";

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case SAISELECT:
                    shop_layout02.setVisibility(View.GONE);
                    gcId = (int) notifyUpdateEntity.getObj();
                    sowShatList.removeAll(sowShatList);
                    getGoodsList();
                    break;
            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }

    @Override
    public void onRefresh() {
        sowShatList.removeAll(sowShatList);
        page = 1;
        gcId = 0;
        getGoodsList();
    }

    @Override
    public void onLoadMore() {
        if (maxPage > page) {
            page++;
            getGoodsList();
        }else {
            shop_list.stopLoadMore();
            makeShortToast("没有更多了");
        }
    }
}
