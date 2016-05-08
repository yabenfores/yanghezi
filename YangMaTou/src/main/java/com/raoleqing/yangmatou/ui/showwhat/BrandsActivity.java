package com.raoleqing.yangmatou.ui.showwhat;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class BrandsActivity extends BaseActivity implements OnClickListener{
	
	private ImageView brands_return;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brands_activity);
		setTitleVisibility(View.GONE);
		viewInfo();
	}

	protected void viewInfo() {
		
		brands_return = (ImageView) findViewById(R.id.brands_return);
		
		
			
		
		
		setProgressVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.brands_return:			
			BrandsActivity.this.onBackPressed();			
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
