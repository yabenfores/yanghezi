package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.AddressAdapter.ViewHolder;
import com.raoleqing.yangmatou.ben.Address;
import com.raoleqing.yangmatou.ben.PoiInfo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class LocNearAddressAdapter extends BaseAdapter{
	
	private List<PoiInfo> nearList;
	private LayoutInflater mInflater;
	private Context context;

	LocNearAddressAdapter() {

	}

	public LocNearAddressAdapter(Context context, List<PoiInfo> nearList) {
		this.nearList = nearList;
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nearList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return nearList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.loc_near_address, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PoiInfo mPoiInfo = nearList.get(position);
//		holder.loc_near_name.setText("    " + mPoiInfo.name);
//		holder.loc_near_address.setText(mPoiInfo.address);
	
		return convertView;
	}

	class ViewHolder {

		TextView loc_near_name;
		TextView loc_near_address;
		

		public ViewHolder(View convertView) {

			this.loc_near_name = (TextView) convertView.findViewById(R.id.loc_near_name);
			this.loc_near_address = (TextView) convertView.findViewById(R.id.loc_near_address);
		
		}

	}

}
