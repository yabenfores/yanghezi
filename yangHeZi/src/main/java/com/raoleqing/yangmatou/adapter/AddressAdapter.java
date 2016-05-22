package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ui.goods.GoodsPayActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {

	private List<Address> addressList;
	private LayoutInflater mInflater;
	private Context context;
	private Handler myHandler;

	AddressAdapter() {

	}

	public AddressAdapter(Context context, List<Address> addressList, Handler myHandler) {
		this.addressList = addressList;
		this.context = context;
		this.myHandler = myHandler;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addressList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return addressList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.address_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Address mAddress = addressList.get(position);
		holder.address_item_name.setText(mAddress.getTrue_name() + "        " + mAddress.getMob_phone());
		holder.address_item_address.setText(mAddress.getAddress() + "    " + mAddress.getArea_info());

		int isdefault = mAddress.getIs_default();
		if (isdefault == 1) {
			holder.address_item_default.setClickable(true);
		} else {
			holder.address_item_default.setClickable(false);
		}

		holder.address_item_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message mesg = myHandler.obtainMessage();
				mesg.what = 2;
				mesg.arg1 = position;
				myHandler.sendMessage(mesg);

			}
		});

		holder.address_item_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message mesg = myHandler.obtainMessage();
				mesg.what = 1;
				mesg.arg1 = position;
				myHandler.sendMessage(mesg);

			}
		});
		
		
		holder.address_item_default.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
			}
		});

		holder.lyo_app_select_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Address address =addressList.get(position);
				BaseActivity.sendNotifyUpdate(GoodsPayActivity.class,"",address);
			}
		});

		return convertView;
	}

	class ViewHolder {

		TextView address_item_name;
		TextView address_item_address;
		CheckBox address_item_default;
		TextView address_item_edit;
		TextView address_item_del;
		LinearLayout lyo_app_select_address;

		public ViewHolder(View convertView) {

			this.address_item_name = (TextView) convertView.findViewById(R.id.address_item_name);
			this.address_item_address = (TextView) convertView.findViewById(R.id.address_item_address);
			this.address_item_default = (CheckBox) convertView.findViewById(R.id.address_item_default);
			this.address_item_edit = (TextView) convertView.findViewById(R.id.address_item_edit);
			this.address_item_del = (TextView) convertView.findViewById(R.id.address_item_del);
			this.lyo_app_select_address= (LinearLayout) convertView.findViewById(R.id.lyo_app_select_address);
		}

	}

}
