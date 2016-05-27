package com.raoleqing.yangmatou.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Evaluation;
import com.raoleqing.yangmatou.common.CircleImageView;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.uitls.TimeUitls;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EvaluationAdapter extends BaseAdapter {

	List<Evaluation> goodsList;
	private LayoutInflater mInflater;

	EvaluationAdapter() {

	}

	public EvaluationAdapter(Context context, List<Evaluation> goodsList) {
		this.goodsList = goodsList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goodsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return goodsList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.evaluation_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		try {
			
		
		Evaluation mEvaluation = goodsList.get(position);

		ImageLoader.getInstance().displayImage(mEvaluation.getMember_img(), holder.evaluation_user_icon,
				YangHeZiApplication.imageOption(R.drawable.user_icon));
		holder.evaluation_user_name.setText(mEvaluation.getGeval_storename());
		holder.evaluation_content.setText(mEvaluation.getGeval_content());
		
		long time = mEvaluation.getGeval_addtime();
		String date = TimeUitls.getDate(time);
		holder.evaluation_time.setText(date);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		
		return convertView;
	}

	class ViewHolder {

		CircleImageView evaluation_user_icon;
		TextView evaluation_user_name;
		TextView evaluation_content;
		TextView evaluation_time;

		public ViewHolder(View convertView) {

			this.evaluation_user_icon = (CircleImageView) convertView.findViewById(R.id.evaluation_user_icon);
			this.evaluation_user_name = (TextView) convertView.findViewById(R.id.evaluation_user_name);
			this.evaluation_content = (TextView) convertView.findViewById(R.id.evaluation_content);
			this.evaluation_time = (TextView) convertView.findViewById(R.id.evaluation_time);

		}

	}

}
