package com.raoleqing.yangmatou.ui.goods;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.allinpay.appayassistex.APPayAssistEx;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ui.address.AddressActivity;
import com.raoleqing.yangmatou.ui.user.AboutActivity;
import com.raoleqing.yangmatou.uitls.PaaCreator;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import entity.NotifyUpdateEntity;

/**
 * 购买界面
 **/
public class GoodsPayActivity extends BaseActivity implements OnClickListener {

    private ImageView activity_return;
    private LinearLayout goods_pay_address_layout;
    private TextView goods_pay_explanation;
    private TextView pay_name;
    private TextView pay_phone;
    private TextView pay_address;
    private EditText user_card;

    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_discount;
    private TextView goods_price01;
    private Button goods_add;
    private TextView goods_number;
    private TextView goods_pay_price;
    private Button goods_del;
    private Button goodsd_detail_buy;
    private TextView tv_goods_tax;
    private int goods_id;
    private int goodsNumber;
    private int wh_id;
    private double goods_promotion_price;
    private double goods_marketprice;
    private String goodsName;
    private String orderPrice;
    private double tax;
    private List<Address> addressList = new ArrayList<Address>();
    private int addressIndex = 0;

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
        setContentView(R.layout.goods_pay);

        Intent intent = this.getIntent();
        goods_id = intent.getIntExtra("goods_id", 0);
        goodsNumber = intent.getIntExtra("goodsNumber", 1);
        wh_id = intent.getIntExtra("wh_id", 0);
        goods_promotion_price = intent.getDoubleExtra("goods_promotion_price", 0.00);
        goods_marketprice = intent.getDoubleExtra("goods_marketprice", 0.00);
        goodsName = intent.getStringExtra("goodsName");
        tax = intent.getDoubleExtra("goods_lineposttax", 0);

        setTitleVisibility(View.GONE);
        pay_name = (TextView) findViewById(R.id.pay_name);
        pay_phone = (TextView) findViewById(R.id.pay_phone);
        pay_address = (TextView) findViewById(R.id.pay_address);
        tv_goods_tax = (TextView) findViewById(R.id.tv_goods_tax);

        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    protected void viewInfo() {

        activity_return = (ImageView) findViewById(R.id.goods_pay_return);
        goods_pay_address_layout = (LinearLayout) findViewById(R.id.goods_pay_address_layout);
        user_card = (EditText) findViewById(R.id.user_card);
        goods_pay_explanation = (TextView) findViewById(R.id.goods_pay_explanation);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_discount = (TextView) findViewById(R.id.goods_discount);
        goods_price01 = (TextView) findViewById(R.id.goods_price01);
        goods_add = (Button) findViewById(R.id.goods_add);
        goods_number = (TextView) findViewById(R.id.goods_number);
        goods_pay_price = (TextView) findViewById(R.id.goods_pay_price);
        goods_del = (Button) findViewById(R.id.goods_del);
        goodsd_detail_buy = (Button) findViewById(R.id.goodsd_detail_buy);

        String member_card = SharedPreferencesUtil.getString(GoodsPayActivity.this, "member_card");
        user_card.setText(member_card);

        goods_name.setText(goodsName);
        goods_price.setText("￥" + goods_promotion_price);
        goods_price01.setText("原价: ￥" + goods_promotion_price);
        goods_number.setText(goodsNumber + "");

        double discount = goods_promotion_price / goods_marketprice * 10;
        DecimalFormat df = new DecimalFormat("###.0");
        goods_discount.setText(df.format(discount) + "折");

        setPrice();

//        getData();

        activity_return.setOnClickListener(this);
        goods_add.setOnClickListener(this);
        goods_del.setOnClickListener(this);
        goods_pay_address_layout.setOnClickListener(this);
        goodsd_detail_buy.setOnClickListener(this);
        goods_pay_explanation.setOnClickListener(this);

        setProgressVisibility(View.GONE);
    }

    private void setPrice() {
        // TODO Auto-generated method stub
        DecimalFormat df = new DecimalFormat("###.0");
        double price = (goods_promotion_price+tax) * goodsNumber;
        orderPrice = df.format(price);
        goods_pay_price.setText(orderPrice);
        tv_goods_tax.setText("￥" + (tax * goodsNumber));
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        getAddress();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_return:
                GoodsPayActivity.this.onBackPressed();
                break;
            case R.id.goods_pay_explanation:

                Intent intent5 = new Intent(GoodsPayActivity.this, AboutActivity.class);
                intent5.putExtra("type", 1);
                startActivity(intent5);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.goods_pay_address_layout:
                Intent intent = new Intent(GoodsPayActivity.this, AddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                GoodsPayActivity.this.onBackPressed();
                break;

            case R.id.goods_add:

                goodsNumber++;
                goods_number.setText(goodsNumber + "");
                setPrice();
                break;
            case R.id.goods_del:
                if (goodsNumber > 1) {
                    goodsNumber--;
                    goods_number.setText(goodsNumber + "");
                    setPrice();
                }
                break;
            case R.id.goodsd_detail_buy:

                String userCard = user_card.getText().toString();
                if (userCard != null && !userCard.trim().equals("")) {
                    submitOrder(userCard);
                } else {
                    makeLongToast("请输入身份证号码");
                }


                break;

            default:
                break;
        }

    }

    private void getAddress() {
        // TODO Auto-generated method stub
        // Home/Orders/getDefaultAddress

        NetHelper.getDefaultAddress(new NetConnectionInterface.iConnectListener3() {
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
                addressResolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {
                makeLongToast(result.optString(Constant.INFO));
            }
        });

//        HttpUtil.post(GoodsPayActivity.this, HttpUtil.GET_DEFAULT_ADDRESS, params, new JsonHttpResponseHandler() {
//
//            // 获取数据成功会调用这里
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // TODO Auto-generated method stub
//                super.onSuccess(statusCode, headers, response);
//                addressResolveJson(response);
//            }
//
//            // 失败
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                // TODO Auto-generated method stub
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                setProgressVisibility(View.GONE);
//            }
//
//            // 结束
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                super.onFinish();
//            }
//
//        });

    }

    private void getTax() {

//        HttpUtil.post(GoodsPayActivity.this, HttpUtil.GET_TAX, params, new JsonHttpResponseHandler() {
//
//            // 获取数据成功会调用这里
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // TODO Auto-generated method stub
//                super.onSuccess(statusCode, headers, response);
//                tax = response.optInt("data", 0);
//                setPrice();
//            }
//
//            // 失败
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                // TODO Auto-generated method stub
//                super.onFailure(statusCode, headers, throwable, errorResponse);
////				setProgressVisibility(View.GONE);
//            }
//
//            // 结束
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                super.onFinish();
//            }
//
//        });

    }

    protected void addressResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        try {
            int code = response.optInt("code");
            String message = response.optString("message");

            if (response == null) {
                Toast.makeText(GoodsPayActivity.this, message, 1).show();
                setProgressVisibility(View.GONE);
                return;
            }

            if (addressList.size() > 0) {
                addressList.removeAll(addressList);
            }


            JSONArray data = response.optJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.optJSONObject(i);
                Address mAddress = new Address();
                mAddress.setAddress_id(obj.optInt("address_id"));
                mAddress.setMember_id(obj.optInt("member_id"));
                mAddress.setTrue_name(obj.optString("true_name"));
                mAddress.setArea_id(obj.optInt("area_id"));
                mAddress.setCity_id(obj.optInt("city_id"));
                mAddress.setArea_info(obj.optString("area_info"));
                mAddress.setAddress(obj.optString("address"));
                mAddress.setMob_phone(obj.optString("mob_phone"));
                int is_default = obj.optInt("is_default");
                if (is_default == 1) {
                    addressIndex = is_default;
                }
                mAddress.setIs_default(is_default);

                addressList.add(mAddress);
            }

            if (addressList.size() > 0) {

                Address mAddress = addressList.get(addressIndex);
                pay_phone.setText(mAddress.getMob_phone());
                pay_name.setText(mAddress.getTrue_name());
                pay_address.setText(mAddress.getArea_info());
            }

            Toast.makeText(GoodsPayActivity.this, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setProgressVisibility(View.GONE);

    }

//    private void getData() {
//        // TODO Auto-generated method stub
//        // Home/Orders/getGoodsList
//
//        RequestParams params = new RequestParams();
//        params.put("pid", goods_id);
//
//        HttpUtil.post(GoodsPayActivity.this, "Home/Orders/getTax", params, new JsonHttpResponseHandler() {
//
//            // 获取数据成功会调用这里
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // TODO Auto-generated method stub
//                super.onSuccess(statusCode, headers, response);
//                resolveJson(response);
//            }
//
//            // 失败
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                // TODO Auto-generated method stub
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                setProgressVisibility(View.GONE);
//            }
//
//            // 结束
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                super.onFinish();
//                System.out.println("onFinish");
//
//            }
//
//        });
//
//    }
//
//
//    protected void resolveJson(JSONObject response) {
//        // TODO Auto-generated method stub
//
//        System.out.println(response);
//
//    }


    /*wh_id	y	int	场区id
    quantity	y	int	产品数量
    uid	y	int	用户id
    pid	y	int	产品id
    order_message	N	string	订单留言
    sfzno	Y	int	身份证
    pay_type	Y	string	支付方式	*/
    private void submitOrder(String userCard) {
        // TODO Auto-generated method stub

        RequestParams params = new RequestParams();
        params.put("wh_id", wh_id);
        params.put("quantity", goodsNumber);
        params.put("pid", goods_id);
        params.put("order_message", "");
        params.put("sfzno", userCard);
        params.put("pay_type", "1");

        HttpUtil.post(GoodsPayActivity.this, "Home/Orders/submitOrder", params, new JsonHttpResponseHandler() {

            // 获取数据成功会调用这里
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, headers, response);
                orderResolveJson(response);
            }

            // 失败
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, headers, throwable, errorResponse);
                setProgressVisibility(View.GONE);
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

    protected void orderResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("aaaaaaaaa:" + response);

        JSONObject payData = PaaCreator.randomPaa();
        System.out.println("aaaaaaaaa:" + payData.toString());
        APPayAssistEx.startPay(this, payData.toString(), APPayAssistEx.MODE_DEBUG);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (APPayAssistEx.REQUESTCODE == requestCode) {
            if (null != data) {
                String payRes = null;
                String payAmount = null;
                String payTime = null;
                String payOrderId = null;
                try {
                    JSONObject resultJson = new
                            JSONObject(data.getExtras().getString("result"));
                    payRes = resultJson.getString(APPayAssistEx.KEY_PAY_RES);
                    payAmount = resultJson.getString("payAmount");
                    payTime = resultJson.getString("payTime");
                    payOrderId = resultJson.getString("payOrderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (null != payRes &&
                        payRes.equals(APPayAssistEx.RES_SUCCESS)) {
                    System.out.println("支付成功！");
                } else {
                    System.out.println("支付失败！");
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //---------------
    public final static String ADDRESSSELECT = "addressSelect";
    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case ADDRESSSELECT:
                    Address address= (Address) notifyUpdateEntity.getObj();
                    pay_phone.setText(address.getMob_phone());
                    pay_name.setText(address.getTrue_name());
                    pay_address.setText(address.getArea_info());
                    break;
            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }
}
