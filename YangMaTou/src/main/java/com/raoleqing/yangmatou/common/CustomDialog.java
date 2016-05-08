package com.raoleqing.yangmatou.common;


import com.raoleqing.yangmatou.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomDialog extends Dialog {

	private Button first;
	private Button second;
	private Button cancel;
	private MyDialogInterface myDialogInterface;
	private String buttonFrist;
	private String buttonSecond;
	private boolean firstVisible;
	private boolean secondVisible;

	public CustomDialog(Context context, String first, boolean firstVisible, String second, boolean secondVisible,
			MyDialogInterface myDialogInterface) {
		super(context, R.style.customDialog);
		this.myDialogInterface = myDialogInterface;
		buttonFrist = first;
		buttonSecond = second;
		this.firstVisible = firstVisible;
		this.secondVisible = secondVisible;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		first = (Button) this.findViewById(R.id.button_first);
		first.setText(buttonFrist);
		first.setVisibility(firstVisible ? View.VISIBLE : View.GONE);
		first.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialogInterface.firstButton(CustomDialog.this);
			}
		});
		second = (Button) this.findViewById(R.id.button_second);
		second.setText(buttonSecond);
		second.setVisibility(secondVisible ? View.VISIBLE : View.GONE);
		second.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialogInterface.secondButton(CustomDialog.this);
			}
		});
		cancel = (Button) this.findViewById(R.id.button_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialogInterface.cancelButton(CustomDialog.this);
			}
		});

		this.setCanceledOnTouchOutside(true);
		getWindow().setGravity(Gravity.BOTTOM);
		getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().getAttributes().width = ViewGroup.LayoutParams.FILL_PARENT;
		getWindow().setWindowAnimations(R.style.PopupAnimation);
	}

	protected void setCancelButton(CharSequence text, android.view.View.OnClickListener mOnClickListener) {
		cancel.setText(text);
		cancel.setOnClickListener(mOnClickListener);
	};

	public interface MyDialogInterface {

		void firstButton(Dialog mDialog);

		void secondButton(Dialog mDialog);

		void cancelButton(Dialog mDialog);
	}

}
