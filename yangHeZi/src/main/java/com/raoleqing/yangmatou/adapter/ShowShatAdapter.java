package com.raoleqing.yangmatou.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.TimeUitls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowShatAdapter  extends BaseAdapter{
	
	private List<ShowShat> sowShatList;
	private LayoutInflater mInflater;
	private Context context;
	
	private int ItemHeight = 0;

	ShowShatAdapter() {

	}

	public ShowShatAdapter(Context context, List<ShowShat> sowShatList ) {
		this.context=context;
		this.sowShatList = sowShatList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sowShatList.size();

		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return sowShatList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.show_shat_adapter, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ShowShat mShowShat = sowShatList.get(position);
		holder.tv_show_username.setText(mShowShat.getMember_name());
		holder.tv_show_time.setText(TimeUitls.getDate(mShowShat.getGeval_addtime()));
		holder.tv_show_comm.setText(mShowShat.getGeval_content());
		holder.tv_show_storename.setText(" "+mShowShat.getGeval_storename());
		holder.tv_show_goodsname.setText(" "+mShowShat.getGeval_goodsname());
		holder.xing_sheng_ratingbar.setRating(mShowShat.getGeval_scores());
		ImageLoader.getInstance().displayImage(mShowShat.getMember_avatar(), holder.user_iocn,
				YangHeZiApplication.imageOption(R.drawable.user_icon));
		holder.tv_show_comment_num.setText("("+mShowShat.getGeval_comment_num()+")");
		holder.tv_show_like_num.setText("("+mShowShat.getGeval_like_num()+")");
//		ItemHeight = convertView.getHeight();
//		System.out.println("拿到的高度: " + ItemHeight);
		return convertView;
	}

	class ViewHolder {

		ImageView cat_goodes_image,user_iocn;
		TextView cat_goodes_text,tv_show_username,tv_show_time,tv_show_comm,tv_show_storename,tv_show_goodsname,tv_show_comment_num,tv_show_like_num;

		RatingBar xing_sheng_ratingbar;
		public ViewHolder(View convertView) {

			this.cat_goodes_image = (ImageView) convertView.findViewById(R.id.cat_goodes_image);
			this.user_iocn = (ImageView) convertView.findViewById(R.id.user_iocn);
			this.cat_goodes_text = (TextView) convertView.findViewById(R.id.cat_goodes_text);
			this.tv_show_username= (TextView) convertView.findViewById(R.id.tv_show_username);
			this.tv_show_time= (TextView) convertView.findViewById(R.id.tv_show_time);
			this.tv_show_comm= (TextView) convertView.findViewById(R.id.tv_show_comm);
			this.tv_show_storename= (TextView) convertView.findViewById(R.id.tv_show_storename);
			this.tv_show_goodsname= (TextView) convertView.findViewById(R.id.tv_show_goodsname);
			this.tv_show_comment_num= (TextView) convertView.findViewById(R.id.tv_show_comment_num);
			this.tv_show_like_num= (TextView) convertView.findViewById(R.id.tv_show_like_num);
			this.xing_sheng_ratingbar= (RatingBar) convertView.findViewById(R.id.xing_sheng_ratingbar);
		}

	}


}
