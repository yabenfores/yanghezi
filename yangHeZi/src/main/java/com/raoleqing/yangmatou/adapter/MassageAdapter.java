package com.raoleqing.yangmatou.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.user.CircularActivity;
import com.raoleqing.yangmatou.ui.user.Massage;
import com.raoleqing.yangmatou.ui.user.SendOutActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by ybin on 2016/6/8.
 */
public class MassageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Massage> massageList;
    private Context context;

    MassageAdapter() {

    }

    public MassageAdapter(Context context, List<Massage> massageList) {
        this.context = context;
        this.massageList = massageList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return massageList.size();
    }

    @Override
    public Object getItem(int position) {
        return massageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.massage_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            final Massage massage = massageList.get(position);

            holder.tv_massage_title.setText(massage.getMsg_grouptitle());
            ImageLoader.getInstance().displayImage(massage.getMsg_groupimg(), holder.iv_massage);

            String message = massage.getMessage_array();
            if (!TextUtils.isEmpty(message)) {
                JSONObject object = new JSONObject(message);
                holder.tv_massage_time.setText(object.optString("msg_createtime"));
                holder.tv_massage_com.setText(object.optString("msg_title"));
                if (object.optInt("msg_is_read") != 0) {
                    holder.newmsg.setVisibility(View.GONE);
                } else {
                    holder.newmsg.setVisibility(View.VISIBLE);
                }
            }
            //1-洋盒子通告，2-发货提醒，3-客服信息
            switch (massage.getMsg_grouptype()) {
                case 1:
                    holder.lyo_massage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, CircularActivity.class);
                            intent.putExtra("msg_groupid", massage.getMsg_groupid());
                            intent.putExtra("msg_grouptype", massage.getMsg_grouptype());
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    holder.lyo_massage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SendOutActivity.class);
                            intent.putExtra("msg_groupid", massage.getMsg_groupid());
                            intent.putExtra("msg_grouptype", massage.getMsg_grouptype());
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    holder.lyo_massage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String user_msg_helper = SharedPreferencesUtil.getString(context, "user_msg_helper");
                            Intent i = new Intent(context, ChatActivity.class);
                            i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
                            context.startActivity(i);
                        }
                    });
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout lyo_massage;
        ImageView iv_massage, newmsg;
        TextView tv_massage_com, tv_massage_time, tv_massage_title;

        public ViewHolder(View convertView) {
            this.iv_massage = (ImageView) convertView.findViewById(R.id.iv_massage);
            this.newmsg = (ImageView) convertView.findViewById(R.id.iv_new_massage);
            this.tv_massage_com = (TextView) convertView.findViewById(R.id.tv_massage_com);
            this.tv_massage_time = (TextView) convertView.findViewById(R.id.tv_massage_time);
            this.tv_massage_title = (TextView) convertView.findViewById(R.id.tv_massage_title);
            this.lyo_massage = (LinearLayout) convertView.findViewById(R.id.lyo_massage);

        }
    }
}
