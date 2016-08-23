package com.raoleqing.yangmatou.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.allinpay.appayassistex.APPayAssistEx;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.PayAdapter;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ben.PayResult;
import com.raoleqing.yangmatou.ui.address.AddressActivity;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.ui.user.AboutActivity;
import com.raoleqing.yangmatou.uitls.PaaCreator;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import net.sourceforge.simcpux.wxapi.WXPayEntryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import entity.NotifyUpdateEntity;

/**
 * 购买界面
 **/
public class GoodsPayActivity extends BaseActivity implements OnClickListener ,IWXAPIEventHandler{

    private ListView lv_pay_paylist;
    private ImageView activity_return;
    private LinearLayout goods_pay_address_layout;
    private TextView goods_pay_explanation;
    private TextView pay_name;
    private TextView pay_phone;
    private TextView pay_address;
    private EditText user_card, et_app_massage;
    private String transaction_type;
    private ImageView goods_image;
    private String pay_type;
    private String url;
    private Address mMaddress;
    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_discount;
    private TextView goods_price01;
    private TextView goods_add;
    private TextView goods_number;
    private TextView goods_pay_price;
    private TextView goods_del;
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
    private PayAdapter adapte;
    double price;
    private List<PayList> payList = new ArrayList<>();
    private List<Address> addressList = new ArrayList<Address>();
    private int addressIndex = 0;
//    private static IWXAPI wxApi;


    private static final int SDK_PAY_FLAG = 1;
    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    viewInfo();
                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(GoodsPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        startOrder();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(GoodsPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(GoodsPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                break;

                default:
                    break;
            }
        }

    };

    private void startOrder() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("index", 2);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_pay);
        Intent intent = this.getIntent();
//        wxApi = WXAPIFactory.createWXAPI(getAppContext(), null);
//        wxApi.registerApp(Constant.WXAPPID);
//        wxApi.handleIntent(getIntent(), this);
        goods_id = intent.getIntExtra("goods_id", 0);
        goodsNumber = intent.getIntExtra("goodsNumber", 1);
        wh_id = intent.getIntExtra("wh_id", 0);
        goods_promotion_price = intent.getDoubleExtra("goods_promotion_price", 0.00);
        goods_marketprice = intent.getDoubleExtra("goods_marketprice", 0.00);
        goodsName = intent.getStringExtra("goodsName");
        transaction_type = intent.getStringExtra("transaction_type");
        tax = intent.getDoubleExtra("goods_lineposttax", 0);
        url = intent.getStringExtra("goods_image");
        setTitleVisibility(View.GONE);
        pay_name = (TextView) findViewById(R.id.pay_name);
        pay_phone = (TextView) findViewById(R.id.pay_phone);
        pay_address = (TextView) findViewById(R.id.pay_address);
        tv_goods_tax = (TextView) findViewById(R.id.tv_goods_tax);

        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    protected void viewInfo() {
        lv_pay_paylist = (ListView) findViewById(R.id.lv_pay_paylist);
        adapte = new PayAdapter(this, payList);
        lv_pay_paylist.setAdapter(adapte);
        activity_return = (ImageView) findViewById(R.id.goods_pay_return);
        goods_pay_address_layout = (LinearLayout) findViewById(R.id.goods_pay_address_layout);
        user_card = (EditText) findViewById(R.id.user_card);
        et_app_massage = (EditText) findViewById(R.id.et_app_massage);
        goods_pay_explanation = (TextView) findViewById(R.id.goods_pay_explanation);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_discount = (TextView) findViewById(R.id.goods_discount);
        goods_price01 = (TextView) findViewById(R.id.goods_price01);
        goods_add = (TextView) findViewById(R.id.goods_add);
        goods_number = (TextView) findViewById(R.id.goods_number);
        goods_pay_price = (TextView) findViewById(R.id.goods_pay_price);
        goods_del = (TextView) findViewById(R.id.goods_del);
        goodsd_detail_buy = (Button) findViewById(R.id.goodsd_detail_buy);

        goods_image = (ImageView) findViewById(R.id.goods_image);
        ImageLoader.getInstance().displayImage(url, goods_image);

        String member_card = SharedPreferencesUtil.getString(GoodsPayActivity.this, "member_card");
        user_card.setText(member_card);

        goods_name.setText(goodsName);
        goods_price.setText("￥" + goods_promotion_price);
        goods_price01.setText("原价: ￥" + goods_marketprice);
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

        getAddress();
        getPayList();
        setProgressVisibility(View.GONE);
    }

    private void setPrice() {
        // TODO Auto-generated method stub
        DecimalFormat df = new DecimalFormat("###.0");
        price = (goods_promotion_price + tax) * goodsNumber;
        orderPrice = df.format(price);
        goods_pay_price.setText(orderPrice);
        tv_goods_tax.setText("￥" + (tax * goodsNumber));
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        getAddress();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.goods_pay_return:
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
                if (mMaddress == null) {
                    makeShortToast("请输入地址");
                    return;
                }
                if (pay_type.isEmpty()) {
                    makeShortToast("请选择支付方式");
                    return;
                }
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
            }
        });
    }

    protected void addressResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        try {
            String message = response.optString("message");

            if (response == null) {
                setProgressVisibility(View.GONE);
                return;
            }

            if (addressList.size() > 0) {
                addressList.removeAll(addressList);
            }


            JSONArray data = response.optJSONArray("data");
            if (data.length() == 0) {
                return;
            }
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
                    addressIndex = i;
                }
                mAddress.setIs_default(is_default);
                addressList.add(mAddress);
            }

            if (addressList.size() > 0) {
                mMaddress = addressList.get(addressIndex);
                pay_phone.setText(mMaddress.getMob_phone());
                pay_name.setText(mMaddress.getTrue_name());
                pay_address.setText(mMaddress.getArea_info());
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        setProgressVisibility(View.GONE);

    }

    /*wh_id	y	int	场区id
    quantity	y	int	产品数量
    uid	y	int	用户id
    pid	y	int	产品id
    order_message	N	string	订单留言
    sfzno	Y	int	身份证
    pay_type	Y	string	支付方式	*/
    private void submitOrder(String userCard) {
        // TODO Auto-generated method stub
//        [{"goods_id":2029,"quantity":1}]
        String goods_array = "[{\"goods_id\":" + goods_id + ",\"quantity\":" + goodsNumber + "}]";

        NetHelper.submitOrder(wh_id + "", transaction_type, goods_array, et_app_massage.getText().toString().trim(), userCard, pay_type, mMaddress.getAddress_id() + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject result) {
                orderResolveJson(result);

            }

            @Override
            public void onFail(JSONObject result) {
                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    protected void orderResolveJson(JSONObject response) {
        switch (pay_type) {
            case "2"://支付宝
                final String payInfo = response.optString(Constant.DATA);

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(GoodsPayActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        myHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
            case "8"://通联
                JSONObject object = response.optJSONObject("data");
                JSONObject payData = PaaCreator.randomPaa(object);
                APPayAssistEx.startPay(this, payData.toString(), APPayAssistEx.MODE_DEBUG);
                break;
            case "3"://微信
                try {
                    JSONObject json = response.optJSONObject("data");
//                    sendNotifyUpdate(WXPayEntryActivity.class,WXPAY_REQ,json);
                    Intent intent=new Intent(this,WXPayEntryActivity.class);
                    intent.putExtra("req", json.toString());
                    startActivity(intent);
//                    WXPayEntity wxPayEntity = new WXPayEntity();
//                    wxPayEntity.setAppId(Constant.WXAPPID);
//                    wxPayEntity.setPartnerId(json.getString("partnerid"));
//                    wxPayEntity.setPrepayId(json.getString("prepayid"));
//                    wxPayEntity.setPackageName(json.getString("package"));
//                    wxPayEntity.setNonceStr(json.getString("noncestr"));
//                    wxPayEntity.setTimeStamp(json.getString("timestamp"));
//                    wxPayEntity.setSign(json.getString("sign"));
//                    if ( !WXPayHelper.init(getBaseContext())) {
//                        makeShortToast("网络异常，请稍后重试！");
//                        return;
//                    }
//                    if (!WXPayHelper.pay(wxPayEntity)) {
//                        makeShortToast("微信支付失败，请联系客服！！");
//                    }
                } catch (Exception e) {
                    Log.e("PAY_GET", "异常：" + e.getMessage());
                }

                break;

            default:
                break;
        }


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
                if (null != payRes && payRes.equals(APPayAssistEx.RES_SUCCESS)) {
                    makeShortToast("支付成功！");
                    System.out.println("支付成功！");
                    startOrder();
                } else {
                    System.out.println("支付失败！");
                    makeShortToast("支付失败！");
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }

    public void getPayList() {
        NetHelper.payList(new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                JSONArray array = result.optJSONArray(Constant.DATA);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        PayList pay = new PayList();
                        pay.setPayment_ico(obj.optString("payment_ico"));
                        pay.setPayment_id(obj.optInt("payment_id"));
                        pay.setPayment_name(obj.optString("payment_name"));
                        if (i == 0) {
                            pay.setSelect(true);
                            pay_type = pay.getPayment_id() + "";
                        }
                        payList.add(pay);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                int hight = UnitConverterUtils.dip2px(getAppContext(), 45) * payList.size();
                lv_pay_paylist.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, hight));
                adapte.notifyDataSetChanged();
            }

            @Override
            public void onFail(JSONObject result) {

            }
        });
    }


    //---------------
    public final static String SELECTPAY = "selectpay";
    public final static String WXPAY_REQ = "wxpay_req";
    //---------------
    public final static String ADDRESSSELECT = "addressSelect";

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
                case ADDRESSSELECT:
                    Address address = (Address) notifyUpdateEntity.getObj();
                    mMaddress = address;
                    pay_phone.setText(address.getMob_phone());
                    pay_name.setText(address.getTrue_name());
                    pay_address.setText(address.getArea_info());
                    break;
                case SELECTPAY:
                    pay_type = (String) notifyUpdateEntity.getObj();
                    for (int i = 0; i < payList.size(); i++) {
                        PayList p = payList.get(i);
                        String id = p.getPayment_id() + "";
                        if (id.equals(pay_type)) {
                            p.setSelect(true);
                        } else {
                            p.setSelect(false);
                        }
                    }
                    adapte.notifyDataSetChanged();
                    break;
            }
        } catch (Exception ex) {
            throwEx(ex);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
//        makeShortToast(baseResp.errStr);
        Log.e("ssss",baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case Constant.WX_PAY_FAIL:
                    makeShortToast("支付失败");
                    break;
                case Constant.WX_PAY_SUCCESS:
                    startOrder();
                    break;
                case Constant.WX_PAY_CANCEL:
                    makeShortToast("已取消");
                    break;
            }
        }
    }
}
