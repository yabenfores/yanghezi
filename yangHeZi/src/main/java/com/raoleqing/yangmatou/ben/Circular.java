package com.raoleqing.yangmatou.ben;

import org.json.JSONObject;

/**
 * Created by ybin on 2016/6/19.
 */
public class Circular {
    public String getMsg_context() {
        return msg_context;
    }

    public Circular(JSONObject object) {
        this.msg_context = object.optString("msg_context");
        this.msg_createtime = object.optString("msg_createtime");
        this.msg_id = object.optInt("msg_id");
        this.msg_title = object.optString("msg_title");
        this.msg_url = object.optString("msg_url");
    }

    public void setMsg_context(String msg_context) {
        this.msg_context = msg_context;
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

    public String getMsg_url() {
        return msg_url;
    }

    public void setMsg_url(String msg_url) {
        this.msg_url = msg_url;
    }

    private int msg_id;
    private String msg_title;
    private String msg_context;
    private String msg_url;
    private String msg_createtime;
}
