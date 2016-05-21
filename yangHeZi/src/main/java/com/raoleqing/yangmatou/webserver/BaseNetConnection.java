package com.raoleqing.yangmatou.webserver;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.login.loginActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLConnection;

/**
 * Created by ybin on 2016/5/10.
 */

    public class BaseNetConnection {
        private NetConnectionInterface.iConnectListener3 connectListener;

        public BaseNetConnection(String action, NetParams.HttpMethod method, boolean addAuth, NetConnectionInterface.iConnectListener3 connectListener, String... kvs) {
            this(false, Constant.CHARSET, action, method, addAuth, connectListener, 10000, kvs);
        }

        public BaseNetConnection(boolean doEncode, String charset, String action, NetParams.HttpMethod method, final boolean addAuth, NetConnectionInterface.iConnectListener3 connectListener, int timeOut, final String... kvs) {
            this.connectListener = connectListener;

            new NetConnection(doEncode, charset, Constant.API_BASE + action, method, new NetConnectionInterface.iSetHeader() {
                @Override
                public void setHeader(URLConnection urlConnection) {
                    try {
                        StringBuilder paramsBuffer = new StringBuilder();
                        for (int i = 0; i < kvs.length - 1; i += 2) {
                            if (kvs[i + 1] == null) {
                                continue;
                            }
                            paramsBuffer.append(kvs[i] + "=" + kvs[i + 1]);
                        }
                        String auth = null;
                        if (addAuth) {
                            auth = SharedPreferencesUtil.getString(BaseActivity.getAppContext(),"Authorization");
                        }
                        urlConnection.addRequestProperty("Authorization", auth);
                        Log.e(String.valueOf(this.getClass()), "auth:" + auth);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, netConnectListener, timeOut, kvs);


        }

        private NetConnectionInterface.iConnectListener2 netConnectListener = new NetConnectionInterface.iConnectListener2() {
            @Override
            public void onStart() {
                if (connectListener == null) return;
                connectListener.onStart();
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(String result) {
                if (connectListener == null) return;
                callback(result);

            }

            @Override
            public void onFail(String result) {
                if (connectListener != null) {
                    connectListener.onFail(getExcepttionResultData(result).getData());
                }
            }
        };

        private enum Status {
            Success, Fail
        }

        private void callback(final String result) {
            new AsyncTask<Void, Void, ResultData>() {
                @Override
                protected ResultData doInBackground(Void... params) {
                    try {
                        JSONObject json = new JSONObject(result);
                        String status = json.optString(Constant.NET_STATUS);
                        if (status.equals(Constant.NET_STATUS_SUCCESS)||status.equals(Constant.NET_STATUS_SUCCESS1)) {

                                return new ResultData(BaseNetConnection.Status.Success, json);

                        } else {
                            return new ResultData(BaseNetConnection.Status.Fail, json);
                        }
                    } catch (Exception e) {
                        return getExcepttionResultData(e.getMessage());
                    }
                }

                @Override
                protected void onPostExecute(ResultData resultData) {
                    try {
                        if (resultData.getStatus() == BaseNetConnection.Status.Success) {
                            connectListener.onSuccess(resultData.getData());
                        } else {
                            connectListener.onFail(resultData.getData());
                        }
                    } catch (Exception e) {
                        connectListener.onFail(getExcepttionResultData(e.getMessage()).getData());
                    } finally {
                        connectListener.onFinish();
                    }
                }
            }.execute();
        }

        private ResultData getExcepttionResultData(String ex) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.NET_STATUS, "0");
                jsonObject.put(Constant.INFO, ex);
                return new ResultData(Status.Fail, jsonObject);
            } catch (JSONException e1) {
                return null;
            }

        }

        private class ResultData {
            Status status;
            JSONObject data;

            public ResultData(Status status, JSONObject data) {
                this.status = status;
                this.data = data;
            }

            public Status getStatus() {
                return status;
            }

            public JSONObject getData() {
                return data;
            }
        }
    }

