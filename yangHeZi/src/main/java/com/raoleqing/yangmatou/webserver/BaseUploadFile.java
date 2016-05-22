//package com.raoleqing.yangmatou.webserver;
//
//
//import com.raoleqing.yangmatou.BaseActivity;
//import com.raoleqing.yangmatou.R;
//import com.raoleqing.yangmatou.uitls.LogUtil;
//import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
//import com.raoleqing.yangmatou.uitls.ToastUtil;
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.util.Map;
//
//
///**
// * Created by Administrator on 2016/5/21.
// */
//public class BaseUploadFile extends AsyncFileUpload {
//
//    private String apiUrl;
//    private boolean addToken;
//
//    public BaseUploadFile(String apiUrl, Map<String, String> params, Map<String, File> files, INetConnection.iFileUploadListener fileUploadListener) {
//        super(Constant.API_BASE + apiUrl, params, files, fileUploadListener);
//        this.apiUrl = apiUrl;
//        this.addToken = addToken;
//    }
//
//    @Override
//    protected Result getResult(String response) {
//        Result result = new Result();
//        try {
//            if (response.indexOf("<br>") > 0) {
//                String[] s = response.split("<br>");
//                response = s[s.length - 1];
//            }
//            JSONObject jsonObject = new JSONObject(response);
//            switch (jsonObject.optString(Constant.NET_STATUS)) {
//                case Constant.NET_STATUS_SUCCESS:
//                case Constant.NET_STATUS_SUCCESS1:
//                    result.responseStatus = NetParams.OPERATE_SUCCESS;
//                    break;
//                default:
//                    result.responseStatus = NetParams.OPERATE_FAIL;
//                    break;
//            }
//            result.resultData = jsonObject;
//        } catch (Exception ex) {
//            LogUtil.printStackTrace(BaseNetConnection.class, ex);
//            result.responseStatus = NetParams.RESPONSE_ERROR;
//        } finally {
//            return result;
//        }
//    }
//
//    @Override
//    protected void setDefaultParams(Map<String, String> params) {
//
//    }
//
//    @Override
//    protected void onResult(Result result) {
//
//    }
//
//    @Override
//    protected void setHead(HttpURLConnection connection) {
//        String auth = SharedPreferencesUtil.getString(BaseActivity.getAppContext(),"Authorization");
//        connection.addRequestProperty("Authorization",auth);
//    }
//}
