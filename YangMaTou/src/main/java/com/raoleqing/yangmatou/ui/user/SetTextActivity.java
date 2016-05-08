package com.raoleqing.yangmatou.ui.user;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetTextActivity extends Activity implements OnClickListener {

	private ImageView activity_return;
	private TextView activity_save;
	private EditText set_text_edit;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_text_activity);

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

			String name = set_text_edit.getText().toString();
			if (name != null && !name.trim().equals("")) {

				if (name.length() > 2) {
					SharedPreferencesUtil.putString(SetTextActivity.this, "member_name", name);
					SetTextActivity.this.onBackPressed();

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
