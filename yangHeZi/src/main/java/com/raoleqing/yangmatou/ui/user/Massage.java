package com.raoleqing.yangmatou.ui.user;

import org.json.JSONObject;

public class Massage{

    private int msg_groupid;
    private String msg_grouptitle;

    public String getMessage_array() {
        return message_array;
    }

    public Massage(JSONObject object) {
        this.message_array = object.optString("message_array");
        this.msg_createtime = object.optString("msg_createtime");
        this.msg_groupid = object.optInt("msg_groupid");
        this.msg_groupimg = object.optString("msg_groupimg");
        this.msg_groupsort = object.optInt("msg_groupsort");
        this.msg_grouptitle = object.optString("msg_grouptitle");
        this.msg_grouptype = object.optInt("msg_grouptype");
        this.msg_id = object.optInt("msg_id");
        this.msg_is_read = object.optInt("msg_is_read");
        this.msg_serviceid = object.optInt("msg_serviceid");
        this.msg_title = object.optString("msg_title");
        this.msg_userid = object.optInt("msg_userid");
    }


    public void setMessage_array(String message_array) {
        this.message_array = message_array;

    }

    public String getMsg_createtime() {
        return msg_createtime;
    }

    public void setMsg_createtime(String msg_createtime) {
        this.msg_createtime = msg_createtime;
    }

    public int getMsg_groupid() {
        return msg_groupid;
    }

    public void setMsg_groupid(int msg_groupid) {
        this.msg_groupid = msg_groupid;
    }

    public String getMsg_groupimg() {
        return msg_groupimg;
    }

    public void setMsg_groupimg(String msg_groupimg) {
        this.msg_groupimg = msg_groupimg;
    }

    public int getMsg_groupsort() {
        return msg_groupsort;
    }

    public void setMsg_groupsort(int msg_groupsort) {
        this.msg_groupsort = msg_groupsort;
    }

    public String getMsg_grouptitle() {
        return msg_grouptitle;
    }

    public void setMsg_grouptitle(String msg_grouptitle) {
        this.msg_grouptitle = msg_grouptitle;
    }

    public int getMsg_grouptype() {
        return msg_grouptype;
    }

    public void setMsg_grouptype(int msg_grouptype) {
        this.msg_grouptype = msg_grouptype;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getMsg_is_read() {
        return msg_is_read;
    }

    public void setMsg_is_read(int msg_is_read) {
        this.msg_is_read = msg_is_read;
    }

    public int getMsg_serviceid() {
        return msg_serviceid;
    }

    public void setMsg_serviceid(int msg_serviceid) {
        this.msg_serviceid = msg_serviceid;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public int getMsg_userid() {
        return msg_userid;
    }

    public void setMsg_userid(int msg_userid) {
        this.msg_userid = msg_userid;
    }

    private String msg_groupimg;
    private int msg_grouptype;//1-洋盒子通告，2-发货提醒，3-客服信息
    private int msg_groupsort;
    private String message_array;
    private int msg_id;
    private String msg_title;
    private String msg_createtime;
    private int msg_is_read;//0-否，1-是
    private int msg_userid;
    private int msg_serviceid;
}