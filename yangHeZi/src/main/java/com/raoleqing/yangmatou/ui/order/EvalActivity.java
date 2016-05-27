package com.raoleqing.yangmatou.ui.order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.CustomDialog;
import com.raoleqing.yangmatou.common.ImageUtils;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.AsyncFileUpload;
import com.raoleqing.yangmatou.webserver.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvalActivity extends BaseActivity implements View.OnClickListener, CustomDialog.MyDialogInterface {

    private ImageView activity_return, uploadImage, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private List<ImageView> list=new ArrayList<>();
    private int anInt=0;
    private Button button;
    private Dialog mDialog; // 拍照Dialog
    private ProgressDialog mProgressDialog; // 图片上传进度对话框
    private String mCurrentPhotoPath;// 当前要上传的图片地址
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_TAKE_ALBUM = 2;
    private static final int FROM_CAMERA_ACTIVITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressVisibility(View.GONE);
        setContentView(R.layout.activity_eval);
        setTitleText(" 我的订单");
        viewInfo();
    }

    protected void viewInfo() {
        activity_return = (ImageView) findViewById(R.id.activity_return);
        uploadImage = (ImageView) findViewById(R.id.iv_app_up_image);
        imageView1 = (ImageView) findViewById(R.id.iv_eval_image1);
        imageView2 = (ImageView) findViewById(R.id.iv_eval_image2);
        imageView3 = (ImageView) findViewById(R.id.iv_eval_image3);
        imageView4 = (ImageView) findViewById(R.id.iv_eval_image4);
        imageView5 = (ImageView) findViewById(R.id.iv_eval_image5);
        imageView6 = (ImageView) findViewById(R.id.iv_eval_image6);
        imageView7 = (ImageView) findViewById(R.id.iv_eval_image7);
        imageView8 = (ImageView) findViewById(R.id.iv_eval_image8);
        imageView9 = (ImageView) findViewById(R.id.iv_eval_image9);
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        list.add(imageView4);
        list.add(imageView5);
        list.add(imageView6);
        list.add(imageView7);
        list.add(imageView8);
        list.add(imageView9);
        button = (Button) findViewById(R.id.btn_eval);
        uploadImage.setOnClickListener(this);
        mDialog = new CustomDialog(EvalActivity.this, "照相", true, "相册", true, EvalActivity.this);
        initProgressDlg02();

    }

    /**
     * 初始化进度对话框
     */
    private void initProgressDlg02() {
        mProgressDialog = new ProgressDialog(this);
        // mProgressDialog.setIcon(R.drawable.ic_launcher);
        mProgressDialog.setTitle("图片上传");
        mProgressDialog.setMessage("正在上传图片，请稍后……");
        // mProgressDialog.setMessage("请稍后……");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条对话框//样式（水平，旋转）
    }

    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.activity_return:
                    this.onBackPressed();
                    break;
                case R.id.iv_app_up_image:
                    mDialog.show();
                    break;
                case R.id.btn_eval:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }
    }

    /**
     * 相机
     **/
    @Override
    public void firstButton(Dialog mDialog) {
        // TODO Auto-generated method stub

        mDialog.dismiss();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = ImageUtils.createImageFile(this);
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);

    }

    /**
     * 相册
     **/
    @Override
    public void secondButton(Dialog mDialog) {
        // TODO Auto-generated method stub
        mDialog.dismiss();

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), ACTION_TAKE_ALBUM);

    }

    @Override
    public void cancelButton(Dialog mDialog) {
        // TODO Auto-generated method stub
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 图片上传2
     */
    private void upLoadOicture2(String path) {

        setProgressVisibility(View.VISIBLE);

        try {

            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            File file = new File(path);

            if (file.exists() && file.length() > 0) {
                RequestParams req = new RequestParams();
                //req.put("upfile", file);
                req.put("image", file);
                System.out.println("headImage: " + file.getPath());
                //Home/Users/member_avatar  Home/Users/reviewImg
//				NetHelper.reviewImg(file);
                new AsyncFileUpload(Constant.API_BASE + Constant.MEMBER_AVATAR, path, "image", new AsyncFileUpload.ResultCallback() {
                    @Override
                    public void onNetError() {
                        mProgressDialog.dismiss();

                    }

                    @Override
                    public void onServerError(String error) {
                        try {
                            JSONObject jsonObject = new JSONObject(error);
                            if (!jsonObject.optString(Constant.INFO).isEmpty()) {
                                makeShortToast(jsonObject.optString(Constant.INFO));
                                mProgressDialog.dismiss();
                            }
                            setProgressVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onSuccess(String result, String filePath) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            imageResolveJson(jsonObject);
                            mProgressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onProgress(float percent) {

                    }
                }).execute();


            }

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * 图片上传
     */
    protected void imageResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("图片上传: " + response);

        try {

            if (response == null) {
                setProgressVisibility(View.GONE);
                Toast.makeText(EvalActivity.this, "修改失败", 1).show();
                return;
            }
            JSONObject json = response.optJSONObject(Constant.DATA);
            String filename = json.optString("filename");//图象地址
            ImageView imageView= list.get(anInt);
            imageView.setVisibility(View.VISIBLE);
            anInt++;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        setProgressVisibility(View.GONE);

    }


    /**
     * 图片上传提示框
     **/
    private void showTipDialog(final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)// .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String path = new File(mCurrentPhotoPath).getPath();
                        if (TextUtils.isEmpty(path)) {
                            //showShortToast("获取图片失败！");
                        } else {
                            upLoadOicture2(path);
                        }

                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri file = data.getData();
                    mCurrentPhotoPath = ImageUtils.getPath(EvalActivity.this, file);
                    if (mCurrentPhotoPath != null) {
                        ImageUtils.showPic(uploadImage, mCurrentPhotoPath);

                        showTipDialog("是否上传图片？");
                    }
                }
                break;

            case ACTION_TAKE_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri file = data.getData();
                    mCurrentPhotoPath = ImageUtils.getPath(EvalActivity.this, file);
                    if (mCurrentPhotoPath != null) {
                        ImageUtils.showPic(list.get(anInt), mCurrentPhotoPath);
                        showTipDialog("是否上传图片？");
                    }
                }
                break;

            case FROM_CAMERA_ACTIVITY:

                break;

            default:
                break;
        }

    }
}
