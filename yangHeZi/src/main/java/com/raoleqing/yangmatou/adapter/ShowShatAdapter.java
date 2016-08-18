package com.raoleqing.yangmatou.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.MainActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.ben.Share;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.showwhat.ShowShatActivity;
import com.raoleqing.yangmatou.uitls.TimeUitls;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowShatAdapter extends BaseAdapter {

    private List<ShowShat> sowShatList;
    private LayoutInflater mInflater;
    private MainActivity context;


    ShowShatAdapter() {

    }

    public ShowShatAdapter(MainActivity context, List<ShowShat> sowShatList) {
        this.context = context;
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
        List<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(holder.iv_show_main);
        imageViewList.add(holder.iv_show1);
        imageViewList.add(holder.iv_show2);
        imageViewList.add(holder.iv_show3);
        imageViewList.add(holder.iv_show4);
        imageViewList.add(holder.iv_show5);
        for (int i = 0; i < imageViewList.size(); i++) {
            imageViewList.get(i).setVisibility(View.GONE);
//            imageViewList.get(i).setImageResource(R.drawable.show_imag01);
            imageViewList.get(i).setImageResource(R.color.transparent);

        }
        final ShowShat mShowShat = sowShatList.get(position);
        if (mShowShat.getGeval_isanonymous()==0){
            holder.tv_show_username.setText(mShowShat.getMember_name());
            ImageLoader.getInstance().displayImage(mShowShat.getMember_avatar(), holder.user_iocn,
                    YangHeZiApplication.imageOption(R.drawable.user_icon));
        }
        final ImageView like=holder.iv_show_like;
        if (mShowShat.getIs_like()==1){
            like.setImageResource(R.drawable.ic_like);
        }else {
            like.setImageResource(R.drawable.like_icon);
        }
        holder.tv_show_time.setText(TimeUitls.getDate(mShowShat.getGeval_addtime()*1000));
        holder.tv_show_comm.setText(mShowShat.getGeval_content());
        holder.tv_show_storename.setText(" " + mShowShat.getGeval_storename());
        holder.tv_show_goodsname.setText(" " + mShowShat.getGeval_goodsname());
        holder.xing_sheng_ratingbar.setRating(mShowShat.getGeval_scores());
        holder.tv_show_comment_num.setText("(" + mShowShat.getGeval_comment_num() + ")");
        holder.tv_show_like_num.setText("(" + mShowShat.getGeval_like_num() + ")");
        final TextView like_num=holder.tv_show_like_num;
        String[] strings = mShowShat.getGeval_image().split(";");
        for (int a = 0; a < strings.length; a++) {
            ImageLoader.getInstance().displayImage(strings[a], imageViewList.get(a));
            imageViewList.get(a).setVisibility(View.VISIBLE);
        }
        holder.lyo_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowShatActivity.class);
                intent.putExtra("showshat", mShowShat);
                context.startActivity(intent);
            }
        });
        holder.lyo_show_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetHelper.Share("2", mShowShat.getGeval_id()+"", new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                        context.setMainProgress(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        context.setMainProgress(View.GONE);
                    }

                    @Override
                    public void onSuccess(JSONObject result) {
                        Share share=new Share(result.optJSONObject(Constant.DATA));
                        context.showShare(share);
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        if (!UserUitls.isLongin(context)) UserUitls.longInDialog(context);
                    }
                });

            }
        });
        holder.lyo_show_eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                inputTitleDialog(mShowShat);
                Intent intent = new Intent(context, ShowShatActivity.class);
                intent.putExtra("showshat", mShowShat);
                context.startActivity(intent);
            }
        });
        holder.lyo_show_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserUitls.isLongin(context)) {
                    UserUitls.longInDialog(context);
                    return;
                }
                if (mShowShat.getIs_like()==1){
                    NetHelper.likedo(mShowShat.getGeval_id() + "", "0", new NetConnectionInterface.iConnectListener3() {
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
                            mShowShat.setIs_like(0);
                            like.setImageResource(R.drawable.like_icon);
                            mShowShat.setGeval_like_num(mShowShat.getGeval_like_num()-1);
                            like_num.setText("(" + mShowShat.getGeval_like_num() + ")");

                        }

                        @Override
                        public void onFail(JSONObject result) {

                            ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));
                        }
                    });
                }else {

                    NetHelper.likedo(mShowShat.getGeval_id() + "", "1", new NetConnectionInterface.iConnectListener3() {
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
                            mShowShat.setIs_like(1);
                            mShowShat.setGeval_like_num(mShowShat.getGeval_like_num()+1);
                            like_num.setText("(" + mShowShat.getGeval_like_num() + ")");
                            like.setImageResource(R.drawable.ic_like);
                        }

                        @Override
                        public void onFail(JSONObject result) {
                            ToastUtil.MakeShortToast(context,result.optString(Constant.INFO));

                        }
                    });
                }

            }
        });


        return convertView;
    }

    class ViewHolder {

        ImageView cat_goodes_image, user_iocn, iv_show_main, iv_show1, iv_show2, iv_show3, iv_show4, iv_show5,iv_show_like;
        TextView cat_goodes_text, tv_show_username, tv_show_time, tv_show_comm, tv_show_storename, tv_show_goodsname, tv_show_comment_num, tv_show_like_num;

        LinearLayout lyo_show, lyo_show_like, lyo_show_eval, lyo_show_share;
        RatingBar xing_sheng_ratingbar;

        public ViewHolder(View convertView) {

            this.iv_show_like= (ImageView) convertView.findViewById(R.id.iv_show_like);
            this.cat_goodes_image = (ImageView) convertView.findViewById(R.id.cat_goodes_image);
            this.user_iocn = (ImageView) convertView.findViewById(R.id.user_iocn);
            this.iv_show_main = (ImageView) convertView.findViewById(R.id.iv_show_main);
            this.iv_show1 = (ImageView) convertView.findViewById(R.id.iv_show1);
            this.iv_show2 = (ImageView) convertView.findViewById(R.id.iv_show2);
            this.iv_show3 = (ImageView) convertView.findViewById(R.id.iv_show3);
            this.iv_show4 = (ImageView) convertView.findViewById(R.id.iv_show4);
            this.iv_show5 = (ImageView) convertView.findViewById(R.id.iv_show5);
            this.cat_goodes_text = (TextView) convertView.findViewById(R.id.cat_goodes_text);
            this.tv_show_username = (TextView) convertView.findViewById(R.id.tv_show_username);
            this.tv_show_time = (TextView) convertView.findViewById(R.id.tv_show_time);
            this.tv_show_comm = (TextView) convertView.findViewById(R.id.tv_show_comm);
            this.tv_show_storename = (TextView) convertView.findViewById(R.id.tv_show_storename);
            this.tv_show_goodsname = (TextView) convertView.findViewById(R.id.tv_show_goodsname);
            this.tv_show_comment_num = (TextView) convertView.findViewById(R.id.tv_show_comment_num);
            this.tv_show_like_num = (TextView) convertView.findViewById(R.id.tv_show_like_num);
            this.xing_sheng_ratingbar = (RatingBar) convertView.findViewById(R.id.xing_sheng_ratingbar);
            this.lyo_show = (LinearLayout) convertView.findViewById(R.id.lyo_show);
            this.lyo_show_like = (LinearLayout) convertView.findViewById(R.id.lyo_show_like);
            this.lyo_show_eval = (LinearLayout) convertView.findViewById(R.id.lyo_show_eval);
            this.lyo_show_share = (LinearLayout) convertView.findViewById(R.id.lyo_show_share);
        }

    }

    private void inputTitleDialog(final ShowShat showShat) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View layout = inflater.inflate(R.layout.input_lyo, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(true);
        final Dialog dialog=builder.show();
        dialog.show();
        TextView tv_commit = (TextView) layout.findViewById(R.id.tv_commit);
        TextView tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
        tv_title.setText("回复");
        final EditText et_con = (EditText) layout.findViewById(R.id.et_con);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (et_con.getText().toString().trim().isEmpty()){
                    context.makeShortToast("请输入回复内容");
                    return;
                }

                NetHelper.Commentdo(showShat.getGeval_id()+"", et_con.getText().toString(), new NetConnectionInterface.iConnectListener3() {
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
                        context.makeShortToast(result.optString(Constant.INFO));
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        context.makeShortToast(result.optString(Constant.INFO));
                    }
                });
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}
