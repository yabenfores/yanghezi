package net.sourceforge.simcpux.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.webserver.Constant;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ybin on 2016/7/19.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    private JSONObject json;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, null);
//        api = WXPayHelper.getIWXAPI();
        api.handleIntent(getIntent(), this);
        String string = getIntent().getStringExtra("req");
        try {
            json = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        send();
    }

    private void send() {
        try {
            if (null != json && !json.has("retcode")) {
                PayReq req = new PayReq();
                req.appId = Constant.WXAPPID;
                req.partnerId = json.getString("partnerid");
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
                req.packageValue = json.getString("package");
                req.sign = json.getString("sign");
                api.sendReq(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    private int ti = 1;

    @Override
    protected void onResume() {
        super.onResume();
        if (ti == 0) {
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra("index", 2);
            startActivity(intent);
            finish();
        }
        ti--;
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            try {
                switch (resp.errCode) {
                    case Constant.WX_PAY_FAIL:
                        Toast.makeText(WXPayEntryActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.WX_PAY_CANCEL:
                        Toast.makeText(WXPayEntryActivity.this,"已取消",Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.WX_PAY_SUCCESS:
                        Intent intent = new Intent(this, OrderActivity.class);
                        intent.putExtra("index", 2);
                        startActivity(intent);
                        break;
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
