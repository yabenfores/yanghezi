package com.raoleqing.yangmatou.webserver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.common.ImageUtils;
import com.raoleqing.yangmatou.uitls.LogUtil;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncFileUpload extends AsyncTask<Void, Void, String> {

    ResultCallback resultCallback;
    String url, filePath,type,dotype;
    Result resultType = Result.None;


    public enum Result {
        FileError, NetError, ServerError, Success, None
    }

    public AsyncFileUpload() {
    }

    public AsyncFileUpload(String url, String filePath, String type ,ResultCallback resultCallback) {
        this.url = url;
        this.filePath = filePath;
        this.type=type;
        this.resultCallback = resultCallback;
    }
    public AsyncFileUpload(String url, String filePath, String type ,String params,ResultCallback resultCallback) {
        this.url = url;
        this.dotype=params;
        this.filePath = filePath;
        this.type=type;
        this.resultCallback = resultCallback;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        try {
            return upload();
        } catch (IOException e) {
            resultType = Result.NetError;
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (resultType == Result.NetError) {
            if (resultCallback != null)
                resultCallback.onNetError();
        } else if (resultType == Result.Success) {
            if (resultCallback != null)
                resultCallback.onSuccess(result, filePath);
        } else if (resultType == Result.ServerError)
            if (resultCallback != null)
                resultCallback.onServerError(result);
        this.cancel(true);
    }

    private String upload() throws IOException {
        try {
            File f;
            f = ImageUtils.createImageFile(BaseActivity.getAppContext());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            getimage(filePath).compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            File file = f;
            if (!file.exists() || !file.isFile()) {
                resultType = Result.FileError;
                return "";
            }
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod(NetParams.POST);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Connection", NetParams.KEEPALIVE);
            con.setRequestProperty("Charset", NetParams.CHARSET);
            String auth = SharedPreferencesUtil.getString(BaseActivity.getAppContext(), "Authorization");
            con.addRequestProperty("Authorization", auth);
            con.addRequestProperty("Dotype", dotype);
            con.setRequestProperty("Content-Type", type);
            OutputStream out = con.getOutputStream();
            FileInputStream fInStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(
                    fInStream);
            byte[] b = new byte[(int) file.length()];
            bufferedInputStream.read(b, 0, b.length);
            out.write(b, 0, b.length);
            out.flush();
            InputStream is = con.getInputStream();
            InputStreamReader bReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(bReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
//                if (resultCallback != null)
//                    resultCallback.onProgress(line.length() * 1.0f / b.length);
            }
            bufferedInputStream.close();
            out.close();
            br.close();
            is.close();
            resultType = Result.Success;
            Log.e("busd", builder.toString());
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return NetParams.NET_ERROR;
        }
    }


    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }
    public interface ResultCallback {
        void onNetError();

        void onServerError(String error);

        void onSuccess(String result, String filePath);

        void onProgress(float percent);
    }
}
