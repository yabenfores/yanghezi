package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class SetTextActivity extends BaseActivity implements OnClickListener {

	private ImageView activity_return;
	private TextView activity_save;
	private EditText set_text_edit;
	private String title,mName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_text_activity);

		setProgressVisibility(View.GONE);
		setTitleVisibility(View.GONE);
		activity_return = (ImageView) findViewById(R.id.activity_return);
		activity_save = (TextView) findViewById(R.id.activity_save);
		set_text_edit = (EditText) findViewById(R.id.set_text_edit);

		activity_return.setOnClickListener(this);
		activity_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_return:
			SetTextActivity.this.onBackPressed();
			break;
		case R.id.activity_save:

			mName = set_text_edit.getText().toString();
			if (mName != null && !mName.trim().equals("")) {

				if (mName.length() > 2) {
					NetHelper.editUserName(mName, new NetConnectionInterface.iConnectListener3() {
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
							SharedPreferencesUtil.putString(SetTextActivity.this, "member_name", mName);
							SetTextActivity.this.onBackPressed();

						}

						@Override
						public void onFail(JSONObject result) {

							makeShortToast(result.optString(Constant.INFO));
						}
					});


				} else {
					Toast.makeText(SetTextActivity.this, "昵称长度不对", 1).show();
				}

			} else {
				Toast.makeText(SetTextActivity.this, "请输入昵称", 1).show();
			}

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
