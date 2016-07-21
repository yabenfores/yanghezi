package com.raoleqing.yangmatou.ui.goods;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.EvaluationAdapter;
import com.raoleqing.yangmatou.ben.Evaluation;
import com.raoleqing.yangmatou.ben.WhRrea;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.MyPagerAdapter;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.ImageBrowseActivity;
import com.raoleqing.yangmatou.ui.shop.ShopActivity;
import com.raoleqing.yangmatou.uitls.LogUtil;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.view.DefaultValueEntity;
import com.raoleqing.yangmatou.view.ValueEntity;
import com.raoleqing.yangmatou.view.ValuePickerView;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetail extends BaseActivity implements OnClickListener {


    private String transaction_type;
    private ImageView activity_return;
    private ImageView share;
    private TextView activity_title01;
    private TextView activity_title02;
    private TextView goods_evaluation;
    private Button goods_detaile_advisory;
    private Button goods_detaile_collect;
    private Button goods_detaile_shop;
    private Button goodsd_detail_buy;
    private PullToRefreshScrollView myscrollview;
    private ChildViewPager goods_viewPager;
    private LinearLayout goods_viewPager_point,lyo_goods_shop;
    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_price01;
    private TextView goods_discount;
    private TextView goods_shipment;
    private TextView goods_evaluation_empty;
    private TextView goods_sold;
    private TextView goods_add;
    private TextView goods_number;
    private TextView goods_del;
    private TextView goods_stock;
    private TextView goods_shop_address;//
    private ImageView goods_shop_iocn;
    private TextView goods_shop_name;//
    private TextView goods_shop_Fans;//
    private TextView tv_goods_detail;//
    private TextView tv_goods_comment;//
    private TextView tv_product_favorite;//
    private Button but_attention;//
    private View divider_goods_detail;
    private View divider_goods_comment;
    private WebView goods_detaile_webview;
    private LinearLayout goodsSvc;
    private ListView goods_evaluation_list;//

    private ArrayList<String> goodsImageList = new ArrayList<String>();
    private List<DefaultValueEntity> whRreaList;
    private List<Evaluation> evaluationList = new ArrayList<Evaluation>();
    private EvaluationAdapter adapter;
    private int pointsCount = 0;
    private int goods_id, store_id;

    private int goodsNumber = 1;
    private double goods_promotion_price;
    private double goods_lineposttax;
    private double goods_marketprice;
    private String geval_goodsid;
    private String goodsName;
    private WhRrea whRrea;
    private ValuePickerView mUvp;

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
        setContentView(R.layout.goods_detail);

        Intent intent = this.getIntent();
        goods_id = intent.getIntExtra("goods_id", 0);

        setTitleText("商品详情");
        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    protected void viewInfo() {

//        goods_evaluation= (TextView) findViewById(R.id.goods_evaluation);
        share= (ImageView) findViewById(R.id.activity_qrode);
        share.setImageResource(R.drawable.share_icon01);
        share.setVisibility(View.VISIBLE);
        share.setOnClickListener(this);
        activity_return = (ImageView) findViewById(R.id.activity_return);
        //goods_detaile_advisory = (Button) findViewById(R.id.goods_detaile_advisory);
        //goods_detaile_collect = (Button) findViewById(R.id.goods_detaile_collect);
        //goods_detaile_shop = (Button) findViewById(R.id.goods_detaile_shop);
        goodsd_detail_buy = (Button) findViewById(R.id.goodsd_detail_buy);
        myscrollview = (PullToRefreshScrollView) findViewById(R.id.goods_myscrollview);
        goods_viewPager = (ChildViewPager) findViewById(R.id.goods_viewPager);
        goods_viewPager_point = (LinearLayout) findViewById(R.id.goods_viewPager_point);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_price01 = (TextView) findViewById(R.id.goods_price01);
        goods_discount = (TextView) findViewById(R.id.goods_discount);
        goods_shipment = (TextView) findViewById(R.id.goods_shipment);
        tv_product_favorite = (TextView) findViewById(R.id.tv_product_favorite);
        tv_product_favorite.setSelected(false);
        goods_sold = (TextView) findViewById(R.id.goods_sold);
        goods_add = (TextView) findViewById(R.id.goods_add);
        goods_number = (TextView) findViewById(R.id.goods_number);
        goods_del = (TextView) findViewById(R.id.goods_del);
        goods_stock = (TextView) findViewById(R.id.goods_stock);
        goods_shop_address = (TextView) findViewById(R.id.goods_shop_address);
        findViewById(R.id.lyo_goods_shop_address).setOnClickListener(this);
        goods_shop_iocn = (ImageView) findViewById(R.id.goods_shop_iocn);
        goods_shop_name = (TextView) findViewById(R.id.goods_shop_name);
        tv_goods_detail = (TextView) findViewById(R.id.tv_goods_detail);
        tv_goods_detail.setOnClickListener(this);
        tv_goods_comment = (TextView) findViewById(R.id.tv_goods_comment);
        tv_goods_comment.setOnClickListener(this);
        divider_goods_detail = findViewById(R.id.divider_goods_detail);
        divider_goods_comment = findViewById(R.id.divider_goods_comment);
        goods_shop_Fans = (TextView) findViewById(R.id.goods_shop_Fans);
        but_attention = (Button) findViewById(R.id.but_attention);
        but_attention.setOnClickListener(this);
        findViewById(R.id.lyo_goods_favorite).setOnClickListener(this);
        goods_detaile_webview = (WebView) findViewById(R.id.goods_detaile_webview);
        goods_evaluation_list = (ListView) findViewById(R.id.goods_evaluation_list);
        goods_evaluation_empty = (TextView) findViewById(R.id.goods_evaluation_empty);
        goods_evaluation_empty.setVisibility(View.GONE);
        adapter = new EvaluationAdapter(GoodsDetail.this, evaluationList);
        goods_evaluation_list.setAdapter(adapter);

        lyo_goods_shop= (LinearLayout) findViewById(R.id.lyo_goods_shop);
        lyo_goods_shop.setOnClickListener(this);
        goodsSvc= (LinearLayout) findViewById(R.id.lyo_goods_svc);
        goodsSvc.setOnClickListener(this);
        activity_return.setOnClickListener(this);
        goodsd_detail_buy.setOnClickListener(this);
        goods_add.setOnClickListener(this);
        goods_del.setOnClickListener(this);


        goods_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                changePositionImage(arg0);
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

        mUvp = (ValuePickerView) findViewById(R.id.uvp_um_personal);
        mUvp.setOnClickSelectListener(new ValuePickerView.OnClickSelectListener() {
            @Override
            public void onConfirm(int[] selectPosition, ValueEntity[] selectValue) {
                try {
                    whRrea = (WhRrea) selectValue[0].getObject();
                    goods_shop_address.setText(whRrea.getWh_name() + "  ");
                } catch (Exception ex) {
//					throwEx(ex);
                }
            }

            @Override
            public void onCancel() {

            }
        });

        getData();
        getGoodsReview();
    }

    // 选择控件
    private <T extends ValueEntity> void showValuePicker(List<T> list, int levelNum) {
        if (list == null || list.size() == 0) {
//			makeToast(R.string.common_network_error);
            return;
        }
        mUvp.show(list, null, levelNum);
    }

    @Override
    public void onClick(View v) {
        try {

            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.activity_return:
                    GoodsDetail.this.onBackPressed();
                    break;
                case R.id.goodsd_detail_buy:

                    if (UserUitls.isLongin(GoodsDetail.this)) {
                        if (whRrea == null) {
                            makeShortToast("请选择订单受理");
                            return;
                        }
                        Intent intent = new Intent(GoodsDetail.this, GoodsPayActivity.class);
                        intent.putExtra("goodsNumber", goodsNumber);
                        intent.putExtra("goods_promotion_price", goods_promotion_price);
                        intent.putExtra("goods_marketprice", goods_marketprice);
                        intent.putExtra("goods_lineposttax", goods_lineposttax);
                        intent.putExtra("transaction_type", transaction_type);
                        intent.putExtra("wh_id", whRrea.getWh_id());
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("goodsName", goodsName);
                        intent.putExtra("goods_image",goodsImageList.get(0));
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        UserUitls.longInDialog(GoodsDetail.this);
                    }

                    break;
                case R.id.goods_add:
                    goodsNumber++;
                    goods_number.setText(goodsNumber + "");


                    break;
                case R.id.goods_del:
                    if (goodsNumber > 1) {
                        goodsNumber--;
                        goods_number.setText(goodsNumber + "");
                    }
                    break;
                case R.id.lyo_goods_shop_address:
                    showValuePicker(whRreaList, 1);
                    break;
                case R.id.tv_goods_detail:
                    tv_goods_comment.setTextColor(0xFF666666);
                    divider_goods_comment.setVisibility(View.INVISIBLE);
                    goods_evaluation_list.setVisibility(View.VISIBLE);
                    goods_evaluation_empty.setVisibility(View.GONE);
                    tv_goods_detail.setTextColor(0xFFE81258);
                    divider_goods_detail.setVisibility(View.VISIBLE);
                    goods_detaile_webview.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_goods_comment:
                    tv_goods_comment.setTextColor(0xFFE81258);
                    divider_goods_comment.setVisibility(View.VISIBLE);
                    if (evaluationList.size()==0) {
                        goods_evaluation_empty.setVisibility(View.VISIBLE);
                    } else {
                        goods_evaluation_list.setVisibility(View.VISIBLE);
                    }
                    tv_goods_detail.setTextColor(0xFF666666);
                    divider_goods_detail.setVisibility(View.INVISIBLE);
                    goods_detaile_webview.setVisibility(View.GONE);
                    break;
                case R.id.but_attention:
                    if (!UserUitls.isLongin(GoodsDetail.this)) {
                        UserUitls.longInDialog(GoodsDetail.this);
                        return;
                    }
                    if (but_attention.isSelected()) {
                        cancelShopStore(store_id);
                    } else {
                        favoritesShopStore(store_id);
                    }
                    break;
                case R.id.lyo_goods_favorite:
                    if (!UserUitls.isLongin(GoodsDetail.this)) {
                        UserUitls.longInDialog(GoodsDetail.this);
                        return;
                    }
                    if (tv_product_favorite.isSelected()) {
                        cancelPorductStore(goods_id);
                    } else {
                        favoritesPorductStore(goods_id);
                    }
                    break;
                case R.id.lyo_goods_svc:
                    if (!UserUitls.isLongin(GoodsDetail.this)) {
                        UserUitls.longInDialog(GoodsDetail.this);
                        return;
                    }
                    String user_msg_helper= SharedPreferencesUtil.getString(getBaseContext(),"user_msg_helper");
                    Intent i=new Intent(getBaseContext(),ChatActivity.class);
                    i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
                    startActivity(i);
                    break;
                case R.id.lyo_goods_shop:
                    Intent intent =new Intent(getBaseContext(), ShopActivity.class);
                    intent.putExtra("store_id",store_id);
                    startActivity(intent);
                    break;
                case R.id.activity_qrode:
                    showShare();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throwEx(e);
        }

    }

    private void getData() {
        // TODO Auto-generated method stub

        RequestParams params = new RequestParams();
        params.put("goods_id", goods_id);

        NetHelper.goodsDetails(goods_id + "", new NetConnectionInterface.iConnectListener3() {
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
                resolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));
            }
        });

    }


    protected void resolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        //System.out.println(response);

        try {

            if (response == null) {
                setProgressVisibility(View.GONE);
                return;
            }

            JSONObject data = response.optJSONObject("data");
            JSONArray more_image = data.optJSONArray("more_image");
            if (goodsImageList.size() > 0) {
                goodsImageList.removeAll(goodsImageList);
            }
            for (int i = 0; i < more_image.length(); i++) {
                String goods_image = more_image.optString(i);
                goodsImageList.add(goods_image);
            }
            setGoodsImage();
            geval_goodsid = data.optString("geval_goodsid");
            transaction_type = data.optString("transaction_type");
            store_id = data.optInt("store_id");
            goodsName = data.optString("goods_name");
            goods_name.setText(goodsName);
            goods_price01.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // 0无促销，1团购，2限时折扣
            int goods_promotion_type = data.optInt("goods_promotion_type");
            goods_promotion_price = data.optDouble("goods_promotion_price");
            goods_lineposttax = data.optDouble("goods_lineposttax");
            goods_marketprice = data.optDouble("goods_marketprice");
//                but_attention.setSelected(data.);
            double discount = goods_promotion_price / goods_marketprice * 10;
            DecimalFormat df = new DecimalFormat("###.0");
            goods_price.setText("￥" + goods_promotion_price);
            goods_price01.setText("￥" + goods_marketprice);
            goods_discount.setText(df.format(discount) + "");

            if (goods_promotion_type == 0) {
                goods_discount.setVisibility(View.GONE);
            } else if (goods_promotion_type == 1) {
                goods_discount.setText("团购");
                goods_discount.setVisibility(View.VISIBLE);
            } else {
                goods_discount.setVisibility(View.VISIBLE);
            }

            if (data.optDouble("goods_freight")==0.00){
                goods_shipment.setText("免运费");
            }else {
                goods_shipment.setText("运费：" + data.optString("goods_freight"));
            }
            goods_sold.setText("已售：" + data.optInt("goods_salenum"));
            goods_stock.setText("(库存： " + data.optInt("goods_storage") + ")");
            goods_shop_address.setText("");
            goods_shop_name.setText(data.optString("store_name"));

            ImageLoader.getInstance().displayImage("", goods_shop_iocn,
                    YangHeZiApplication.imageOption(R.drawable.store_icon));
            // html代码
            String goods_body = data.optString("goods_body");

            whRreaList = new ArrayList<DefaultValueEntity>();

				/*"wh_area_info":"上海,江苏,浙江,安徽,福建,江西,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,台湾,香港,澳门,海外",
                "wh_id":"1","r_predeposit":"0.00","wh_name":"海南省海口市万国大都会"*/
            JSONArray rgoodswh_list = data.optJSONArray("rgoodswh_list");
            DefaultValueEntity defaultValueEntity;
            for (int i = 0; i < rgoodswh_list.length(); i++) {
                JSONObject obj = rgoodswh_list.optJSONObject(i);
                WhRrea mWhRrea = new WhRrea();
                mWhRrea.setWh_id(obj.optInt("wh_id"));
                mWhRrea.setWh_name(obj.optString("wh_name"));
                mWhRrea.setWh_area_info(obj.optString("wh_area_info"));

                defaultValueEntity = new DefaultValueEntity(mWhRrea.getWh_id() + "", mWhRrea.getWh_name());
                defaultValueEntity.setObject(mWhRrea);
                whRreaList.add(defaultValueEntity);

            }
            setGoodsDesc(goods_body);
            goods_shop_address.setText(whRreaList.get(0).getValue() + "   ");
            whRrea = (WhRrea) whRreaList.get(0).getObject();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setProgressVisibility(View.GONE);

    }

    /**
     * 图文详情
     **/
    private void setGoodsDesc(final String goods_desc) {
        // TODO Auto-generated method stub

        if (goods_desc != null && !goods_desc.equals("")) {

            goods_detaile_webview.loadUrl(goods_desc);
            // 能够的调用JavaScript代码
//             product_picture_html = new WebView(ProductDetails.this);
//            goods_detaile_webview.getSettings().setDefaultTextEncodingName("utf-8");
//            // 加载HTML字符串进行显示
//
//            goods_detaile_webview.getSettings().setJavaScriptEnabled(true);
//            goods_detaile_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//            goods_detaile_webview.getSettings().setUseWideViewPort(true);
//            goods_detaile_webview.getSettings().setLoadWithOverviewMode(true);
//            goods_detaile_webview.setSaveEnabled(true);
//            goods_detaile_webview.getSettings().setRenderPriority(RenderPriority.HIGH);
//            goods_detaile_webview.getSettings().setSupportZoom(true);// 支持缩放
//            goods_detaile_webview.loadData(goods_desc, "text/html", "UTF-8");// 这种写法可以正确解码
        }
    }

    private void getGoodsReview() {
        // TODO Auto-generated method stub
        ////

        NetHelper.goodsReview(goods_id + "", new NetConnectionInterface.iConnectListener3() {
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
                goodsReviewResolveJson(result);
            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

            }
        });


    }

    protected void goodsReviewResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("商品评价： " + response);

        try {
            // 04-11 15:57:15.069: I/System.out(12426): 评价：
            String dataStr = response.optString("data");
            if (!dataStr.isEmpty()) {
                JSONArray data = response.optJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    Evaluation mEvaluation = new Evaluation();
                    mEvaluation.setMember_img(obj.optString("member_img"));
                    mEvaluation.setGeval_addtime(obj.optString("geval_addtime"));
                    mEvaluation.setGeval_goodsname(obj.optString("geval_goodsname"));
                    mEvaluation.setGeval_frommembername(obj.optString("geval_frommembername"));
                    mEvaluation.setGeval_content(obj.optString("geval_content"));
                    evaluationList.add(mEvaluation);
                }
                setListViewHeight(evaluationList.size());
                adapter.notifyDataSetChanged();

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setProgressVisibility(View.GONE);

    }

    /**
     * 评价的高度
     */
    private void setListViewHeight(int size) {
        // TODO Auto-generated method stub
        if (size == 0) {
            size=1;
        }
        int height = UnitConverterUtils.dip2px(GoodsDetail.this, 130) * size;
        LayoutParams params = goods_evaluation_list.getLayoutParams();
        params.height = (int) height;
        goods_evaluation_list.setLayoutParams(params);
    }

    private void setGoodsImage() {
        // TODO Auto-generated method stub

        ArrayList<View> viewList = new ArrayList<View>();
        for (int i = 0; i < goodsImageList.size(); i++) {
            ImageView img = new ImageView(this);
            img.setScaleType(ScaleType.CENTER_CROP);
            img.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            ImageLoader.getInstance().displayImage(goodsImageList.get(i), img);
            viewList.add(img);
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);

        goods_viewPager.setAdapter(pagerAdapter);
        pointsCount = goodsImageList.size();

        loadPositionImage();
        goods_viewPager.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch() {
                Intent intent = new Intent(getAppContext(), ImageBrowseActivity.class);
                intent.putStringArrayListExtra("imageList",goodsImageList);
                startActivity(intent);
            }
        });

    }

    // 获取网络数据之后 加载 圆点图标*/
    protected void loadPositionImage() {
        ImageView aImageView = null;
        goods_viewPager_point.removeAllViews();
        for (int i = 0; i < pointsCount; i++) {
            aImageView = new ImageView(this);
            aImageView.setPadding(10, 0, 10, 0);
            if (i == 0) {
                aImageView.setImageResource(R.drawable.point2);
            } else {
                aImageView.setImageResource(R.drawable.point1);
            }
            goods_viewPager_point.addView(aImageView);
        }
    }

    /**
     * 更改提示h
     */
    public void changePositionImage(int aPos) {

        ImageView aImageView = (ImageView) goods_viewPager_point.getChildAt(aPos);
        if (aImageView != null) {
            aImageView.setImageResource(R.drawable.point2);
        }
        for (int i = 0; i < pointsCount; i++) {
            if (i != aPos) {
                aImageView = (ImageView) goods_viewPager_point.getChildAt(i);
                aImageView.setImageResource(R.drawable.point1);
            }
        }
    }

    /**
     * 关注店铺
     **/
    protected void favoritesShopStore(int store_id) {
        // TODO Auto-generated method stub
        // Home/Index/favoritesstore
        setProgressVisibility(View.VISIBLE);


        NetHelper.favoritesstore(store_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int code = result.optInt("code");
                    String message = result.optString("message");

                    if (result == null) {
                        setProgressVisibility(View.GONE);
                        return;
                    }
                    but_attention.setSelected(true);
                    but_attention.setText("取消关注");
                    Toast.makeText(GoodsDetail.this, message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(GoodsDetail.this, "关注失败", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

            }
        });

    }

    /**
     * 关注店铺
     **/
    protected void cancelShopStore(int store_id) {
        // TODO Auto-generated method stub
        // Home/Index/cancelStore

        setProgressVisibility(View.VISIBLE);

        NetHelper.cancelStore(store_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);


            }

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String message = result.optString("message");

                    if (result == null) {
                        Toast.makeText(getAppContext(), message, Toast.LENGTH_LONG).show();
                        setProgressVisibility(View.GONE);
                        return;
                    }

                    but_attention.setSelected(false);
                    but_attention.setText("+ 关注");

                    Toast.makeText(getAppContext(), message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getAppContext(), "取消失败", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFail(JSONObject result) {
                ToastUtil.MakeShortToast(BaseActivity.getAppContext(), result.optString(Constant.INFO));

            }
        });


    }

    /**
     * 关注商品
     **/
    protected void favoritesPorductStore(int goods_id) {
        // TODO Auto-generated method stub
        // Home/Index/favoritesstore
        setProgressVisibility(View.VISIBLE);


        NetHelper.favoritespro(goods_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    LogUtil.loge(getClass(), result.toString());

                    if (result == null) {
                        setProgressVisibility(View.GONE);
                        return;
                    }
                    tv_product_favorite.setSelected(true);
                    tv_product_favorite.setText("取消收藏");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(GoodsDetail.this, "关注失败", Toast.LENGTH_LONG).show();
                }

                setProgressVisibility(View.GONE);

            }

            @Override
            public void onFail(JSONObject result) {
                setProgressVisibility(View.GONE);
                makeShortToast(result.optString(Constant.INFO));

            }
        });


    }

    /**
     * 关注商品
     **/
    protected void cancelPorductStore(int goods_id) {
        // TODO Auto-generated method stub
        // Home/Index/cancelStore

        setProgressVisibility(View.VISIBLE);


        RequestParams params = new RequestParams();
        params.put("fid", goods_id);
//		params.put("uid", 1090);
        NetHelper.cancelPro(goods_id + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);

            }

            @Override
            public void onSuccess(JSONObject result) {
                if (result == null) {
                    setProgressVisibility(View.GONE);
                    return;
                }

                tv_product_favorite.setSelected(false);
                tv_product_favorite.setText("收藏");

            }

            @Override
            public void onFail(JSONObject result) {

                makeShortToast(result.optString(Constant.INFO));
            }
        });

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }


}
