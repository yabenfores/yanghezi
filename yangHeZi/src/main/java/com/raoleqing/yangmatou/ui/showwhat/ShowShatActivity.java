package com.raoleqing.yangmatou.ui.showwhat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.adapter.EvaluationAdapter;
import com.raoleqing.yangmatou.ben.Evaluation;
import com.raoleqing.yangmatou.ben.ShowShat;
import com.raoleqing.yangmatou.common.ChildViewPager;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.ui.ImageBrowseActivity;
import com.raoleqing.yangmatou.uitls.TimeUitls;
import com.raoleqing.yangmatou.uitls.UserUitls;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;
import com.raoleqing.yangmatou.xlist.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowShatActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {


    private ImageView user_iocn, iv_show_main, iv_show1, iv_show2, iv_show3, iv_show4, iv_show5, iv_return, iv_like;
    private TextView tv_show_username, tv_show_time, tv_show_comm, tv_show_storename, tv_show_goodsname, tv_show_comment_num, tv_show_like_num, tv_eval_con;
    private RatingBar xing_sheng_ratingbar;
    private LinearLayout lyo_show_like, lyo_show_eval, lyo_show_share, lyo_show_re, lyo_show_image;

    private ArrayList<String> goodsImageList=new ArrayList<>();
    private Button btn_show_re;
    private View showHrad;
    private EvaluationAdapter adapter;

    private EditText et_show_re;
    private XListView xListView;
    private List<Evaluation> evaluationList = new ArrayList<>();
    private ShowShat showShat;
    private int page = 1, maxPage = 1, evalNum = 0, likeNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shat);
        showShat = (ShowShat) getIntent().getSerializableExtra("showshat");
        setTitleText("晒单详情");
        viewInfo();
    }
    protected void viewInfo() {
        et_show_re = (EditText) findViewById(R.id.et_show_re);
        btn_show_re = (Button) findViewById(R.id.btn_show_re);
        btn_show_re.setOnClickListener(this);
        lyo_show_re = (LinearLayout) findViewById(R.id.lyo_show_re);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        lyo_show_like = (LinearLayout) findViewById(R.id.lyo_show_like);
        lyo_show_eval = (LinearLayout) findViewById(R.id.lyo_show_eval);
        lyo_show_share = (LinearLayout) findViewById(R.id.lyo_show_share);
        lyo_show_share.setOnClickListener(this);
        lyo_show_eval.setOnClickListener(this);
        lyo_show_like.setOnClickListener(this);
        iv_return = (ImageView) findViewById(R.id.activity_return);
        xListView = (XListView) findViewById(R.id.lv_show);
        adapter = new EvaluationAdapter(this, evaluationList);
        xListView.setAdapter(adapter);
        if (showShat.getIs_like() == 1) {
            iv_like.setImageResource(R.drawable.ic_like);
        }
        try {
            showHrad = this.getLayoutInflater().from(this)
                    .inflate(R.layout.show_aty, null);
            lyo_show_image = (LinearLayout) showHrad.findViewById(R.id.lyo_show_image);
            lyo_show_image.setOnClickListener(this);
            user_iocn = (ImageView) showHrad.findViewById(R.id.user_iocn);
            iv_show_main = (ImageView) showHrad.findViewById(R.id.iv_show_main);
            iv_show1 = (ImageView) showHrad.findViewById(R.id.iv_show1);
            iv_show2 = (ImageView) showHrad.findViewById(R.id.iv_show2);
            iv_show3 = (ImageView) showHrad.findViewById(R.id.iv_show3);
            iv_show4 = (ImageView) showHrad.findViewById(R.id.iv_show4);
            iv_show5 = (ImageView) showHrad.findViewById(R.id.iv_show5);
            tv_show_username = (TextView) showHrad.findViewById(R.id.tv_show_username);
            tv_show_time = (TextView) showHrad.findViewById(R.id.tv_show_time);
            tv_show_comm = (TextView) showHrad.findViewById(R.id.tv_show_comm);
            tv_show_storename = (TextView) showHrad.findViewById(R.id.tv_show_storename);
            tv_show_goodsname = (TextView) showHrad.findViewById(R.id.tv_show_goodsname);
            tv_show_comment_num = (TextView) findViewById(R.id.tv_show_comment_num);
            tv_show_like_num = (TextView) findViewById(R.id.tv_show_like_num);
            tv_eval_con = (TextView) showHrad.findViewById(R.id.tv_eval_con);
            xing_sheng_ratingbar = (RatingBar) showHrad.findViewById(R.id.xing_sheng_ratingbar);
            List<ImageView> imageViewList = new ArrayList<>();
            imageViewList.add(iv_show_main);
            imageViewList.add(iv_show1);
            imageViewList.add(iv_show2);
            imageViewList.add(iv_show3);
            imageViewList.add(iv_show4);
            imageViewList.add(iv_show5);
            for (int i = 0; i < imageViewList.size(); i++) {
                imageViewList.get(i).setVisibility(View.GONE);
            }
            if (showShat.getGeval_isanonymous() == 0) {
                tv_show_username.setText(showShat.getMember_name());
                ImageLoader.getInstance().displayImage(showShat.getMember_avatar(), user_iocn,
                        YangHeZiApplication.imageOption(R.drawable.user_icon));
            }

            tv_show_time.setText(TimeUitls.getDate(showShat.getGeval_addtime() * 1000));
            tv_show_comm.setText(showShat.getGeval_content());
            tv_show_storename.setText(" " + showShat.getGeval_storename());
            tv_show_goodsname.setText(" " + showShat.getGeval_goodsname());
            xing_sheng_ratingbar.setRating(showShat.getGeval_scores());
            tv_eval_con.setText("共" + showShat.getGeval_comment_num() + "条评论");
            tv_show_comment_num.setText("(" + showShat.getGeval_comment_num() + ")");
            likeNum = showShat.getGeval_like_num();
            tv_show_like_num.setText("(" + likeNum + ")");
            String[] strings = showShat.getGeval_image().split(";");
            for (int a = 0; a < strings.length; a++) {
                imageViewList.get(a).setVisibility(View.VISIBLE);
                goodsImageList.add(strings[a]);
                ImageLoader.getInstance().displayImage(strings[a], imageViewList.get(a));
            }
        } catch (Exception e) {
            throwEx(e);
        }
        xListView.addHeaderView(showHrad);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        iv_return.setOnClickListener(this);
        et_show_re.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    lyo_show_re.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        getInfo();
    }

    private void getInfo() {

        NetHelper.Comments(showShat.getGeval_id() + "", page + "", new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {
                setProgressVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                setProgressVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject result) {
                xListView.stopRefresh();
                xListView.stopLoadMore();
                ResolveJson(result);
            }

            @Override
            public void onFail(JSONObject result) {

                makeShortToast(result.optString(Constant.INFO));
            }
        });
    }

    private void ResolveJson(JSONObject result) {
        JSONObject json = result.optJSONObject(Constant.DATA);
        if (json == null) {
            return;
        }
        try {
            maxPage = json.optInt("pagetotal");
            evalNum = json.optInt("rowcount");
            tv_eval_con.setText("共" + evalNum + "条评论");
            tv_show_comment_num.setText("(" + evalNum + ")");
            JSONArray jsonArray = json.optJSONArray("comments");
            if (jsonArray == null) return;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Evaluation mEval = new Evaluation();
                mEval.setGeval_content(object.optString("comment_context"));
                mEval.setGeval_addtime(object.optString("comment_addtime"));
                mEval.setGeval_frommembername(object.optString("member_name"));
                mEval.setMember_img(object.optString("member_avatar"));
                mEval.setComment_id(object.optInt("comment_id"));
                evaluationList.add(mEval);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            throwEx(e);
        }
    }


    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.activity_return:
                    this.onBackPressed();
                    break;

                case R.id.lyo_show_eval:
                    if (!UserUitls.isLongin(ShowShatActivity.this)) {
                        UserUitls.longInDialog(ShowShatActivity.this);
                        return;
                    }
                    lyo_show_re.setVisibility(View.VISIBLE);
//                    inputTitleDialog();
                    break;
                case R.id.lyo_show_like:
                    if (!UserUitls.isLongin(ShowShatActivity.this)) {
                        UserUitls.longInDialog(ShowShatActivity.this);
                        return;
                    }
                    if (showShat.getIs_like() == 1) {
                        NetHelper.likedo(showShat.getGeval_id() + "", "0", new NetConnectionInterface.iConnectListener3() {
                            @Override
                            public void onStart() {
                                setProgressVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {

                                setProgressVisibility(View.GONE);
                            }

                            @Override
                            public void onSuccess(JSONObject result) {
                                showShat.setIs_like(0);
                                likeNum--;
                                tv_show_like_num.setText("(" + likeNum + ")");
                                iv_like.setImageResource(R.drawable.like_icon);
                            }

                            @Override
                            public void onFail(JSONObject result) {

                                makeShortToast(result.optString(Constant.INFO));
                            }
                        });
                    } else {
                        NetHelper.likedo(showShat.getGeval_id() + "", "1", new NetConnectionInterface.iConnectListener3() {
                            @Override
                            public void onStart() {
                                setProgressVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                setProgressVisibility(View.GONE);
                            }

                            @Override
                            public void onSuccess(JSONObject result) {
                                showShat.setIs_like(1);
                                likeNum++;
                                tv_show_like_num.setText("(" + likeNum + ")");
                                iv_like.setImageResource(R.drawable.ic_like);
                            }

                            @Override
                            public void onFail(JSONObject result) {

                                makeShortToast(result.optString(Constant.INFO));
                            }
                        });
                    }
                    break;

                case R.id.btn_show_re:
                    if (et_show_re.getText().toString().trim().isEmpty()) {
                        makeShortToast("请输入回复内容");
                        return;
                    }
                    NetHelper.Commentdo(showShat.getGeval_id() + "", et_show_re.getText().toString(), new NetConnectionInterface.iConnectListener3() {
                        @Override
                        public void onStart() {
                            setProgressVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            setProgressVisibility(View.GONE);
                        }

                        @Override
                        public void onSuccess(JSONObject result) {
                            makeShortToast(result.optString(Constant.INFO));
                            evaluationList.removeAll(evaluationList);
                            lyo_show_re.setVisibility(View.GONE);
                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(et_show_re.getWindowToken(), 0);
                            getInfo();
                        }

                        @Override
                        public void onFail(JSONObject result) {
                            makeShortToast(result.optString(Constant.INFO));
                        }
                    });
                    break;
                case R.id.lyo_show_image:
                    Intent intent = new Intent(getAppContext(), ImageBrowseActivity.class);
                    intent.putStringArrayListExtra("imageList", goodsImageList);
                    startActivity(intent);

                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throwEx(e);
        }

    }

    private void inputTitleDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.input_lyo, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(true);
        final Dialog dialog = builder.show();
        dialog.show();
        TextView tv_commit = (TextView) layout.findViewById(R.id.tv_commit);
        TextView tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
        tv_title.setText("回复");
        final EditText et_con = (EditText) layout.findViewById(R.id.et_con);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (et_con.getText().toString().trim().isEmpty()) {
                    makeShortToast("请输入回复内容");
                    return;
                }

                NetHelper.Commentdo(showShat.getGeval_id() + "", et_con.getText().toString(), new NetConnectionInterface.iConnectListener3() {
                    @Override
                    public void onStart() {
                        setProgressVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {

                        setProgressVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
                        evaluationList.removeAll(evaluationList);
                        getInfo();
                    }

                    @Override
                    public void onFail(JSONObject result) {
                        makeShortToast(result.optString(Constant.INFO));
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


    @Override
    public void onRefresh() {
        page = 1;
        evaluationList.removeAll(evaluationList);
        getInfo();
    }

    @Override
    public void onLoadMore() {
        if (maxPage > page) {
            page++;
            getInfo();
        } else {
            makeShortToast("没有更多了");
            xListView.stopLoadMore();
            return;
        }
    }
}
