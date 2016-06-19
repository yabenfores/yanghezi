package com.raoleqing.yangmatou.ben;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ybin on 2016/6/18.
 */
public class SendOut {
    public JSONArray getGoods_array() {
        return goods_array;
    }

    public SendOut(JSONObject object) {
        this.goods_array = object.optJSONArray("goods_array");
        this.msg_createtime = object.optString("msg_createtime");
        this.msg_id = object.optInt("msg_id");
        this.msg_title = object.optString("msg_title");
        this.order_sn = object.optString("order_sn");
        this.shipping_code = object.optString("shipping_code");
    }


    public void setGoods_array(JSONArray goods_array) {
        this.goods_array = goods_array;
    }

    public String getMsg_createtime() {
        return msg_createtime;
    }

    public void setMsg_createtime(String msg_createtime) {
        this.msg_createtime = msg_createtime;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    private int msg_id;
    private String msg_title;
    private String order_sn;
    private String shipping_code;
    private String msg_createtime;

    private JSONArray goods_array;
}
