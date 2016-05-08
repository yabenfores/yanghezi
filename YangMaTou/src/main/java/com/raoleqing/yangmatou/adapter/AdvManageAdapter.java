package com.raoleqing.yangmatou.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.AdvManage;
import com.raoleqing.yangmatou.ben.OneCat;
import com.raoleqing.yangmatou.common.YangMaTouApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 首页广告
 * **/
public class AdvManageAdapter extends BaseAdapter {

	private List<AdvManage> advManageList;
	private LayoutInflater mInflater;

	AdvManageAdapter() {

	}

	public AdvManageAdapter(Context context, List<AdvManage> advManageList) {
		this.advManageList = advManageList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return advManageList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return advManageList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adv_manage_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AdvManage mAdvManage = advManageList.get(position);
		ImageLoader.getInstance().displayImage(mAdvManage.getAdv_image(), 
				holder.adv_manage_iamge, YangMaTouApplication.imageOption(R.drawable.adv_manage_image));

		return convertView;
	}

	class ViewHolder {

		ImageView adv_manage_iamge;

		public ViewHolder(View convertView) {

			this.adv_manage_iamge = (ImageView) convertView.findViewById(R.id.adv_manage_iamge);

		}

	}

}
