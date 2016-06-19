package com.raoleqing.yangmatou.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Circular;
import com.raoleqing.yangmatou.ben.Pavilion;
import com.raoleqing.yangmatou.ben.SendOut;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.showwhat.CountryActivity;
import com.raoleqing.yangmatou.ui.user.CircularActivity;
import com.raoleqing.yangmatou.ui.user.Massage;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.webserver.WebActivity;

import org.json.JSONObject;

import java.util.List;

import entity.NotifyUpdateEntity;

/**
 * Created by ybin on 2016/5/27.
 */
public class CircularAdapter extends BaseAdapter {
    private List<Circular> pavilionList;
    private LayoutInflater mInflater;
    private CircularActivity context;

    CircularAdapter() {

    }

    public CircularAdapter(CircularActivity context, List<Circular> pavilionList) {
        this.context = context;
        this.pavilionList = pavilionList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return pavilionList.size();
    }

    @Override
    public Object getItem(int position) {
        return pavilionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.circular_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            final Circular circular = pavilionList.get(position);

            holder.tv_cir_time.setText(circular.getMsg_createtime());
            holder.tv_cir_title.setText(circular.getMsg_title());
            holder.lyo_cir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, WebActivity.class);
                    intent.putExtra("url",circular.getMsg_url());
                    context.startActivity(intent);
                }
            });

            holder.lyo_cir.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确定删除？").setTitle("提示")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    NetHelper.delMsg(circular.getMsg_id() + "", new NetConnectionInterface.iConnectListener3() {
                                        @Override
                                        public void onStart() {

                                        }

                                        @Override
                                        public void onFinish() {

                                        }

                                        @Override
                                        public void onSuccess(JSONObject result) {
                                            context.sendNotifyUpdate(CircularActivity.class,MSGCHANGE,position,0);
                                        }

                                        @Override
                                        public void onFail(JSONObject result) {

                                            ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));
                                        }
                                    });
                                }
                            }).setNegativeButton("取消", null).show();
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {

        LinearLayout lyo_cir;
        TextView tv_cir_title,tv_cir_time;

        public ViewHolder(View convertView) {
            this.tv_cir_title = (TextView) convertView.findViewById(R.id.tv_cir_title);
            this.tv_cir_time = (TextView) convertView.findViewById(R.id.tv_cir_time);
            this.lyo_cir = (LinearLayout) convertView.findViewById(R.id.lyo_cir);

        }
    }
    //--------------
    private final static String MSGCHANGE="msgchange";


}
