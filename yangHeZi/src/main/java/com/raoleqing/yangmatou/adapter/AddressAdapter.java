package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.BaseFragment;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ui.address.AddressActivity;
import com.raoleqing.yangmatou.ui.goods.GoodsPayActivity;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatFragment;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

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

import org.json.JSONObject;

public class AddressAdapter extends BaseAdapter {

	private List<Address> addressList;
	private LayoutInflater mInflater;
	private AddressActivity context;
	private Handler myHandler;

	AddressAdapter() {

	}

	public AddressAdapter(AddressActivity context, List<Address> addressList, Handler myHandler) {
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

		final Address mAddress = addressList.get(position);
		holder.address_item_name.setText(mAddress.getTrue_name() + "        " + mAddress.getMob_phone());
		holder.address_item_address.setText(mAddress.getAddress() + "    " + mAddress.getArea_info());

		int isdefault = mAddress.getIs_default();
		if (isdefault == 1) {
			holder.address_item_default.setChecked(true);

		}else {
			holder.address_item_default.setChecked(false);
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
		
		
		holder.address_item_default.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAddress.getIs_default()==1){
					NetHelper.edit_Address(mAddress.getAddress_id() + "", mAddress.getTrue_name(), mAddress.getArea_id() + "", mAddress.getCity_id() + "", mAddress.getArea_info(), mAddress.getAddress(), "", "0", mAddress.getMob_phone(), new NetConnectionInterface.iConnectListener3() {
						@Override
						public void onStart() {
							context.setProgressVisibility(View.VISIBLE);

						}

						@Override
						public void onFinish() {
							context.setProgressVisibility(View.GONE);

						}

						@Override
						public void onSuccess(JSONObject result) {
							mAddress.setIs_default(0);
							BaseActivity.sendNotifyUpdate(AddressActivity.class,ADDRESSCHANGE);
						}

						@Override
						public void onFail(JSONObject result) {
							ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));

						}
					});
				}else {
					NetHelper.edit_Address(mAddress.getAddress_id() + "", mAddress.getTrue_name(), mAddress.getArea_id() + "", mAddress.getCity_id() + "", mAddress.getArea_info(), mAddress.getAddress(), "", "1", mAddress.getMob_phone(), new NetConnectionInterface.iConnectListener3() {
						@Override
						public void onStart() {
							context.setProgressVisibility(View.VISIBLE);

						}

						@Override
						public void onFinish() {
							context.setProgressVisibility(View.GONE);

						}

						@Override
						public void onSuccess(JSONObject result) {
							mAddress.setIs_default(1);
							BaseActivity.sendNotifyUpdate(AddressActivity.class,ADDRESSCHANGE);
						}

						@Override
						public void onFail(JSONObject result) {

							ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));
						}
					});
				}
			}
		});

		holder.lyo_app_select_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Address address =addressList.get(position);
				BaseActivity.sendNotifyUpdate(GoodsPayActivity.class,ADDRESSSELECT,address);
				context.onBackPressed();
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
	//----------------
	public final static String ADDRESSSELECT = "addressSelect";
	public final static String ADDRESSCHANGE = "addresschange";

}
