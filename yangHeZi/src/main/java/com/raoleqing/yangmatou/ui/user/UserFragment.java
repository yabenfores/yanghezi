package com.raoleqing.yangmatou.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raoleqing.yangmatou.R;
import com.raoleqing.yangmatou.common.CircleImageView;
import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.mi.ChatActivity;
import com.raoleqing.yangmatou.ui.order.OrderActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetHelper;

import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.raoleqing.yangmatou.BaseActivity.getAppContext;


/**
 * 个人中心
 **/
public class UserFragment extends Fragment implements OnClickListener {

    private CircleImageView main_user_icon;

    private ImageView iv_user_massage;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView user_order;
    private Button navBtn01;// 待支付
    private Button navBtn02;// 待发货
    private Button navBtn03;// 待收货
    private Button navBtn04;// 待评介
    private Button navBtn05;// 退换货
    private View user_fragment_view01;
    private View user_fragment_view02;
    private View user_fragment_view03;
    private View user_fragment_view04;
    private View user_fragment_view05;

    private TextView user_fragemnt_tiem01;
    private TextView user_fragemnt_tiem02;
    private TextView user_fragemnt_tiem04;
    private TextView user_fragemnt_tiem05;
    private TextView user_fragemnt_tiem06;
    private TextView user_name;

//	UserFragment() {
//	}

    public static Fragment newInstance() {
        Fragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.user_fragment, null);
        manager = getFragmentManager();

        viewInfo(view);
        return view;
    }

    private void viewInfo(View view) {
        // TODO Auto-generated method stub
        iv_user_massage = (ImageView) view.findViewById(R.id.iv_user_massage);
        iv_user_massage.setOnClickListener(this);
        user_name = (TextView) view.findViewById(R.id.main_user_name);
        main_user_icon = (CircleImageView) view.findViewById(R.id.main_user_icon);
        user_order = (TextView) view.findViewById(R.id.user_order);
//        navBtn01 = (Button) view.findViewById(R.id.navBtn01);
//        navBtn02 = (Button) view.findViewById(R.id.navBtn02);
//        navBtn03 = (Button) view.findViewById(R.id.navBtn03);
//        navBtn04 = (Button) view.findViewById(R.id.navBtn04);
//        navBtn05 = (Button) view.findViewById(R.id.navBtn05);
        user_fragment_view01 = (View) view.findViewById(R.id.user_fragment_view01);
        user_fragment_view02 = (View) view.findViewById(R.id.user_fragment_view02);
        user_fragment_view03 = (View) view.findViewById(R.id.user_fragment_view03);
        user_fragment_view04 = (View) view.findViewById(R.id.user_fragment_view04);
        user_fragment_view05 = (View) view.findViewById(R.id.user_fragment_view05);

        user_fragemnt_tiem01 = (TextView) view.findViewById(R.id.user_fragemnt_tiem01);
        user_fragemnt_tiem02 = (TextView) view.findViewById(R.id.user_fragemnt_tiem02);
        user_fragemnt_tiem04 = (TextView) view.findViewById(R.id.user_fragemnt_tiem04);
        user_fragemnt_tiem05 = (TextView) view.findViewById(R.id.user_fragemnt_tiem05);
        user_fragemnt_tiem06 = (TextView) view.findViewById(R.id.user_fragemnt_tiem06);

        user_fragemnt_tiem01.setOnClickListener(this);
        user_fragemnt_tiem02.setOnClickListener(this);
        user_fragemnt_tiem04.setOnClickListener(this);
        user_fragemnt_tiem05.setOnClickListener(this);
        user_fragemnt_tiem06.setOnClickListener(this);

        user_order.setOnClickListener(this);
        view.findViewById(R.id.navBtn01).setOnClickListener(this);
        view.findViewById(R.id.navBtn02).setOnClickListener(this);
        view.findViewById(R.id.navBtn03).setOnClickListener(this);
        view.findViewById(R.id.navBtn04).setOnClickListener(this);
        view.findViewById(R.id.navBtn05).setOnClickListener(this);


        getUsers();
    }

    private void getUsers() {

        NetHelper.Users(new NetConnectionInterface.iConnectListener3() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject result) {

                ResolveJson(result);
            }

            @Override
            public void onFail(JSONObject result) {

                ToastUtil.MakeShortToast(getAppContext(), result.optString(Constant.INFO));

            }
        });
    }
    protected void ResolveJson(JSONObject response) {
        // TODO Auto-generated method stub

        System.out.println("登录： " + response);

        try {
            String message = response.optString("message");

            if (response == null) {

                return;
            }
            JSONObject json = response.optJSONObject(Constant.DATA);
            String member_name = json.optString("member_name");//用户名
            String member_truename = json.optString("member_truename");//真实信名
            String member_card = json.optString("member_card");//身份证
            String member_mobile = json.optString("member_mobile");//手机号码
            String member_email = json.optString("member_email");//邮箱
            String member_avatar = json.optString("member_avatar");//图象地址
            String member_id = json.optString("member_id");//用户id
            String member_mobile_bind = json.optString("member_mobile_bind");//是否绑定
            String wh_id = json.optString("wh_id");//区域id
            SharedPreferencesUtil.putString(getActivity(), "member_name", member_name);
            SharedPreferencesUtil.putString(getActivity(), "member_truename", member_truename);
            SharedPreferencesUtil.putString(getActivity(), "member_card", member_card);
            SharedPreferencesUtil.putString(getActivity(), "member_mobile", member_mobile);
            SharedPreferencesUtil.putString(getActivity(), "member_email", member_email);
            SharedPreferencesUtil.putString(getActivity(), "member_avatar", member_avatar);
            SharedPreferencesUtil.putString(getActivity(), "member_id", member_id);
            SharedPreferencesUtil.putString(getActivity(), "member_mobile_bind", member_mobile_bind);
            SharedPreferencesUtil.putString(getActivity(), "wh_id", wh_id);
            SharedPreferencesUtil.putBoolean(getActivity(), "isLongin", true);

            reloadUser();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void reloadUser() {
        String member_avatar = SharedPreferencesUtil.getString(getActivity().getBaseContext(), "member_avatar");
//		String member_avatar="http://rescdn.qqmail.com/dyimg/20140409/72B8663B7F23.jpg";
        ImageLoader.getInstance().displayImage(member_avatar, main_user_icon,
                YangHeZiApplication.imageOption(R.drawable.user_icon));
        String name = SharedPreferencesUtil.getString(getAppContext(), "member_name");
        user_name.setText(name);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {

            case R.id.user_fragemnt_tiem01:
                // 安全

                Intent intent01 = new Intent(getActivity(), SafetyActivity.class);
                startActivity(intent01);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.user_fragemnt_tiem02:
                // 我的收藏

                Intent intent02 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent02);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                break;
            case R.id.user_fragemnt_tiem03:
                // 等级说明


                break;
            case R.id.user_fragemnt_tiem04:
                // 在线客服
                String user_msg_helper = SharedPreferencesUtil.getString(getActivity().getBaseContext(), "user_msg_helper");
                Intent i = new Intent(getActivity(), ChatActivity.class);
                i.putExtra(EaseConstant.EXTRA_USER_ID, user_msg_helper);
                startActivity(i);
                break;
            case R.id.user_fragemnt_tiem05:
                // 关于我们

                Intent intent5 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent5);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.user_fragemnt_tiem06:
                // 设置

                Intent intent6 = new Intent(getActivity(), SetActivity.class);
                startActivity(intent6);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.user_order:
                setView(0);
                break;

            case R.id.navBtn01:
                setView(1);

                break;
            case R.id.navBtn02:
                setView(2);

                break;
            case R.id.navBtn03:

                setView(3);

                break;
            case R.id.navBtn04:

                setView(4);

                break;
            case R.id.navBtn05:

                setView(5);

                break;

            case R.id.iv_user_massage:
                Intent intent = new Intent(getContext(), MassageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private void setView(int i) {
        // TODO Auto-generated method stub

        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    @Override
    public void onResume() {
        reloadUser();
        super.onResume();

    }

    private void showShare() {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("yaben");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://baidu.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(getContext());
    }
}
