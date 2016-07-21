package com.raoleqing.yangmatou.ui.order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.ReasonAdapter;
import com.raoleqing.yangmatou.common.CustomDialog;
import com.raoleqing.yangmatou.common.ImageUtils;
import com.raoleqing.yangmatou.ui.showwhat.Brand;
import com.raoleqing.yangmatou.webserver.AsyncFileUpload;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RefuActivity extends BaseActivity implements View.OnClickListener, CustomDialog.MyDialogInterface {

    private Button btn_eval;
    private ImageView activity_return, uploadImage, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private List<ImageView> list = new ArrayList<>();
    private StringBuilder builder=new StringBuilder();
    private int anInt = 0,goods_num,quantity=1,reason_id,refund_type=2;
    private Dialog mDialog; // 拍照Dialog
    private List<Brand> reasonlist=new ArrayList<>();
    private ReasonAdapter adapter;
    private ProgressDialog mProgressDialog; // 图片上传进度对话框
    private String mCurrentPhotoPath;// 当前要上传的图片地址
    private ImageView goods_image;
    private TextView goods_name,goods_price,goods_price01,tv_refu_num,tv_re_del,tv_re_add,tv_re_tui,tv_re_huan;
    private String image,name,price,maketprice,order_id,pic_info;
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_TAKE_ALBUM = 2;
    private static final int FROM_CAMERA_ACTIVITY = 3;
    private Spinner sn_reason;
    private EditText et_re_massage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refu);
        try {
            image=getIntent().getStringExtra("goods_image");
            name=getIntent().getStringExtra("goods_name");
            price=getIntent().getStringExtra("goods_price");
            maketprice=getIntent().getStringExtra("goods_marketprice");
            order_id=getIntent().getStringExtra("order_id");
            goods_num=getIntent().getIntExtra("goods_num",0);
        } catch (Exception e) {
            throwEx(e);
        }
        viewInfo();
        getReason();
    }
    private void viewInfo() {
        setTitleText("申请售后");
        btn_eval= (Button) findViewById(R.id.btn_eval);
        sn_reason= (Spinner) findViewById(R.id.sn_reason);
        et_re_massage= (EditText) findViewById(R.id.et_re_massage);
        tv_refu_num= (TextView) findViewById(R.id.tv_refu_num);
        tv_re_tui= (TextView) findViewById(R.id.tv_re_tui);
        tv_re_huan= (TextView) findViewById(R.id.tv_re_huan);
        tv_re_del= (TextView) findViewById(R.id.tv_re_del);
        tv_re_add= (TextView) findViewById(R.id.tv_re_add);
        activity_return= (ImageView) findViewById(R.id.activity_return);
        uploadImage= (ImageView) findViewById(R.id.iv_app_up_image);
        uploadImage.setOnClickListener(this);
        imageView1= (ImageView) findViewById(R.id.iv_eval_image1);
        imageView2= (ImageView) findViewById(R.id.iv_eval_image2);
        imageView3= (ImageView) findViewById(R.id.iv_eval_image3);
        imageView4= (ImageView) findViewById(R.id.iv_eval_image4);
        imageView5= (ImageView) findViewById(R.id.iv_eval_image5);
        imageView6= (ImageView) findViewById(R.id.iv_eval_image6);
        imageView7= (ImageView) findViewById(R.id.iv_eval_image7);
        imageView8= (ImageView) findViewById(R.id.iv_eval_image8);
        imageView9= (ImageView) findViewById(R.id.iv_eval_image9);
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        list.add(imageView4);
        list.add(imageView5);
        list.add(imageView6);
        list.add(imageView7);
        list.add(imageView8);
        list.add(imageView9);
        adapter=new ReasonAdapter(this,reasonlist);

        activity_return.setOnClickListener(this);
        goods_image= (ImageView) findViewById(R.id.goods_image);
        goods_name= (TextView) findViewById(R.id.goods_name);
        goods_price= (TextView) findViewById(R.id.goods_price);
        goods_price01= (TextView) findViewById(R.id.goods_price01);
//        goods_price01.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//加
        ImageLoader.getInstance().displayImage(image,goods_image);
        goods_name.setText(name);
        tv_refu_num.setText(quantity+"");
//        goods_price.setText("￥"+price);

        goods_price01.setText("数量：共"+goods_num+"件");
        mDialog = new CustomDialog(RefuActivity.this, "照相", true, "相册", true, RefuActivity.this);
        initProgressDlg02();
        tv_re_add.setOnClickListener(this);
        tv_re_del.setOnClickListener(this);
        btn_eval.setOnClickListener(this);
        tv_re_huan.setOnClickListener(this);
        tv_re_tui.setOnClickListener(this);
        sn_reason.setAdapter(adapter);
        sn_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Brand selectReason= (Brand) parent.getItemAtPosition(position);
                reason_id=selectReason.getBrand_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setProgressVisibility(View.GONE);
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
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK ) {  //表示按返回键时的操作
                        mProgressDialog.dismiss();
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
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

        try {
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            File file = new File(path);
            if (file.exists() && file.length() > 0) {
//                new AsyncFileUpload(Constant.API_BASE + Constant.Upload, path, "image","?dotype=2", new AsyncFileUpload.ResultCallback() {
                new AsyncFileUpload(Constant.API_BASE + "Home/Upload/Index", path, "image","2", new AsyncFileUpload.ResultCallback() {
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
                return;
            }
            JSONObject json = response.optJSONObject(Constant.DATA);
            String filename = json.optString("filename");//图象地址
            builder.append(filename+";");
            ImageView imageView = list.get(anInt);
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
                            makeShortToast("获取图片失败！");
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
//                    Uri file = data.getData();
//                    mCurrentPhotoPath = ImageUtils.getPath(EvalActivity.this, file);
                    if (mCurrentPhotoPath != null) {
                        ImageUtils.showPic(list.get(anInt), mCurrentPhotoPath);
                        showTipDialog("是否上传图片？");
                    }
                }
                break;

            case ACTION_TAKE_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri file = data.getData();
                    mCurrentPhotoPath = ImageUtils.getPath(RefuActivity.this, file);
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

    @Override
    public void onClick(View v) {

        try {

            switch (v.getId()){
                case R.id.activity_return:
                    this.onBackPressed();
                    break;
                case R.id.iv_app_up_image:
                    if (anInt==9){
                        makeShortToast("只能上传9张图片");
                        return;
                    }
                    mDialog.show();
                    break;
                case R.id.tv_re_add:
                    if (quantity<goods_num){
                        quantity++;
                        tv_refu_num.setText(quantity+"");
                    }
                    break;
                case R.id.tv_re_del:
                    if (quantity>1){
                        quantity--;
                        tv_refu_num.setText(quantity+"");
                    }
                    break;
                case R.id.btn_eval:
                    pic_info=builder.toString();
                    NetHelper.AfterSales(order_id, refund_type + "", quantity + "", reason_id + "", et_re_massage.getText().toString(), pic_info, new NetConnectionInterface.iConnectListener3() {
                        @Override
                        public void onStart() {
                            setProgressVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            setProgressVisibility(View.GONE);

                        }

                        @Override
                        public void onSuccess(JSONObject result) {
                            makeShortToast(result.optString(Constant.INFO));
                            finish();
                        }

                        @Override
                        public void onFail(JSONObject result) {
                            makeShortToast(result.optString(Constant.INFO));
                        }
                    });
                    break;
                case R.id.tv_re_huan:
                    refund_type=2;
                    tv_re_huan.setTextColor(0xFFE81258);
                    tv_re_tui.setTextColor(Color.BLACK);
                    break;
                case R.id.tv_re_tui:
                    refund_type=3;
                    tv_re_huan.setTextColor(Color.BLACK);
                    tv_re_tui.setTextColor(0xFFE81258);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }

    }

    public void getReason() {

        NetHelper.AfterReason(new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {
                JSONArray array=result.optJSONArray(Constant.DATA);
                if (array==null){
                    return;
                }
                for (int i=0;i<array.length();i++){
                    try {
                        JSONObject obj=array.getJSONObject(i);
                        Brand reason=new Brand();
                        reason.setBrand_id(obj.optInt("reason_id"));
                        reason.setBrand_name(obj.optString("reason_info"));
                        reasonlist.add(reason);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFail(JSONObject result) {

            }
        });
    }
}
