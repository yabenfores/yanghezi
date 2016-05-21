package com.raoleqing.yangmatou.ui.user;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import com.android.internal.http.multipart.MultipartEntity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.CircleImageView;
import com.raoleqing.yangmatou.common.CustomDialog;
import com.raoleqing.yangmatou.common.CustomDialog.MyDialogInterface;
import com.raoleqing.yangmatou.common.ImageUtils;
import com.raoleqing.yangmatou.common.YangMaTouApplication;
import com.raoleqing.yangmatou.ui.login.loginActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.HttpUtil;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import internal.org.apache.http.entity.mime.HttpMultipartMode;
import internal.org.apache.http.entity.mime.content.FileBody;

/**
 * 个人设置
 **/
public class SetActivity extends BaseActivity implements OnClickListener, MyDialogInterface {

	private ImageView activity_return;
	private CircleImageView main_user_icon;
	private TextView user_name;
	private TextView exit_account;

	private Dialog mDialog; // 拍照Dialog
	private ProgressDialog mProgressDialog; // 图片上传进度对话框
	private String mCurrentPhotoPath;// 当前要上传的图片地址
	private static final int ACTION_TAKE_PHOTO = 1;
	private static final int ACTION_TAKE_ALBUM = 2;
	private static final int FROM_CAMERA_ACTIVITY = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_activity);
		setTitleText("个人设置");
		viewInfo();
	}

	protected void viewInfo() {

		activity_return = (ImageView) findViewById(R.id.activity_return);
		main_user_icon = (CircleImageView) findViewById(R.id.main_user_icon);
		user_name = (TextView) findViewById(R.id.user_name);
		exit_account = (TextView) findViewById(R.id.exit_account);

		activity_return.setOnClickListener(this);
		main_user_icon.setOnClickListener(this);
		exit_account.setOnClickListener(this);
		user_name.setOnClickListener(this);

		mDialog = new CustomDialog(SetActivity.this, "照相", true, "相册", true, SetActivity.this);
		initProgressDlg02();

		getData();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		String member_name = SharedPreferencesUtil.getString(SetActivity.this, "member_name");
		String member_avatar = SharedPreferencesUtil.getString(SetActivity.this, "member_avatar");
		user_name.setText(member_name);
		ImageLoader.getInstance().displayImage(member_avatar, main_user_icon,
				YangMaTouApplication.imageOption(R.drawable.user_icon));

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
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.activity_return:
			SetActivity.this.onBackPressed();
			break;
		case R.id.user_name:

			Intent intent02 = new Intent(SetActivity.this, SetTextActivity.class);
			startActivity(intent02);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.main_user_icon:
			mDialog.show();
			break;

		default:
			break;
		}

	}

	private void getData() {
		// TODO Auto-generated method stub

		setProgressVisibility(View.GONE);

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

			mProgressDialog.show();
			File file = new File(path);

			if (file.exists() && file.length() > 0) {
				RequestParams req = new RequestParams();
				//req.put("upfile", file);
				req.put("image",file);
				System.out.println("headImage: " + file.getPath());
				//Home/Users/member_avatar  Home/Users/reviewImg
//				NetHelper.reviewImg(file);

				HttpUtil.post1(this, "Home/Users/member_avatar", req, new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						imageResolveJson(response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable throwable,
							JSONObject errorResponse) {
						super.onFailure(statusCode, headers, throwable, errorResponse);
						//showShortToast("连接网络失败！");
					}

					@Override
					public void onFinish() {
						super.onFinish();
						mProgressDialog.dismiss();
					}
				});

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
				Toast.makeText(SetActivity.this, "修改失败", 1).show();
				return;
			}
			JSONObject json = response.optJSONObject(Constant.DATA);
			String filename = json.optString("filename");//图象地址
			SharedPreferencesUtil.putString(getAppContext(),"member_avatar",filename);


			
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
				if (mCurrentPhotoPath != null) {
					ImageUtils.showPic(main_user_icon, mCurrentPhotoPath);

					showTipDialog("是否上传头像？");
				}
			}
			break;

		case ACTION_TAKE_ALBUM:
			if (resultCode == RESULT_OK) {
				Uri file = data.getData();
				mCurrentPhotoPath = ImageUtils.getPath(SetActivity.this, file);
				if (mCurrentPhotoPath != null) {
					ImageUtils.showPic(main_user_icon, mCurrentPhotoPath);

					showTipDialog("是否上传头像？");
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
	}

}
