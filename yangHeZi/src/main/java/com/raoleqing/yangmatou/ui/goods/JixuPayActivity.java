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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.allinpay.appayassistex.APPayAssistEx;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.PayAdapter;
import com.raoleqing.yangmatou.ben.PayResult;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.PaaCreator;
import com.raoleqing.yangmatou.uitls.UnitConverterUtils;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import net.sourceforge.simcpux.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import entity.NotifyUpdateEntity;

/**
 * 购买界面
 **/
public class JixuPayActivity extends BaseActivity implements OnClickListener ,IWXAPIEventHandler{


    private ListView lv_pay_paylist;
    private ImageView activity_return;
    private String pay_type;
    private TextView goods_pay_price;
    private Button goodsd_detail_buy;
    private PayAdapter adapte;
    private double price;
    private String order_id;
    private List<PayList> payList = new ArrayList<>();
    private static IWXAPI wxApi;


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
                        Toast.makeText(JixuPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        startOrder();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(JixuPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(JixuPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.jixu_pay);
        Intent intent = this.getIntent();
        price=intent.getDoubleExtra("order_amount",0);
        order_id=intent.getStringExtra("order_id");
        wxApi = WXAPIFactory.createWXAPI(getAppContext(), null);
        wxApi.registerApp(Constant.WXAPPID);
        wxApi.handleIntent(getIntent(), this);
        setTitleVisibility(View.GONE);
        myHandler.sendEmptyMessageDelayed(0, 50);

    }

    protected void viewInfo() {
        lv_pay_paylist = (ListView) findViewById(R.id.lv_pay_paylist);
        adapte = new PayAdapter(this, payList);
        lv_pay_paylist.setAdapter(adapte);
        activity_return = (ImageView) findViewById(R.id.goods_pay_return);
        goodsd_detail_buy = (Button) findViewById(R.id.goodsd_detail_buy);

        goods_pay_price= (TextView) findViewById(R.id.goods_pay_price);

        setPrice();

//        getData();

        activity_return.setOnClickListener(this);
        goodsd_detail_buy.setOnClickListener(this);

        getPayList();
        setProgressVisibility(View.GONE);
    }

    private void setPrice() {
        goods_pay_price.setText(price+"");
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
                JixuPayActivity.this.onBackPressed();
                break;
            case R.id.goodsd_detail_buy:
                if (pay_type.isEmpty()) {
                    makeShortToast("请选择支付方式");
                    return;
                }
                submitOrder();
                break;

            default:
                break;
        }

    }

    private void submitOrder() {

        NetHelper.order_conpay(order_id, pay_type, new NetConnectionInterface.iConnectListener3() {
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
                        PayTask alipay = new PayTask(JixuPayActivity.this);
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
                    Intent intent=new Intent(this,WXPayEntryActivity.class);
                    intent.putExtra("req", json.toString());
                    startActivity(intent);
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
    //---------------
    public final static String ADDRESSSELECT = "addressSelect";

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        super.notifyUpdate(notifyUpdateEntity);
        try {
            switch (notifyUpdateEntity.getNotifyTag()) {
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
