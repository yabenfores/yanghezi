package com.raoleqing.yangmatou.ben;

import org.json.JSONObject;

/**
 * Created by ybin on 2016/8/17.
 */
public class Share {
//    title	string	分享数据主题
//    body	string	分享数据内容
//    url	string	分享数据url
//    imgurl	string	分享数据图片

    public Share(JSONObject object){
      this.title=object.optString("title");
      this.body=object.optString("body");
      this.url=object.optString("url");
      this.imgurl=object.optString("imgurl");
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String title;
    public String body;
    public String url;
    public String imgurl;
}
