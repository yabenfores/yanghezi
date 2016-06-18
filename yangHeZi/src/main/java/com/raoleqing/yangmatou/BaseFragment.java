package com.raoleqing.yangmatou;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.raoleqing.yangmatou.common.YangHeZiApplication;
import com.raoleqing.yangmatou.uitls.ToastUtil;

import java.util.HashMap;

import entity.NotifyUpdateEntity;

/**
 * Created by ybin on 2016/5/31.
 */
public class BaseFragment extends FragmentActivity {



    public static Context getAppContext(){
        return YangHeZiApplication.getAppContext();
    }

    public void makeLongToast(String msg) {
        ToastUtil.MakeLongToast(getAppContext(), msg);
    }

    public void makeShortToast(String msg) {
        ToastUtil.MakeShortToast(getAppContext(), msg);
    }

    public void makeShortToast(int resId) {
        ToastUtil.MakeShortToast(getAppContext(), resId);
    }

    public void makeLongToast(int resId) {
        ToastUtil.MakeLongToast(getAppContext(), resId);
    }

    public void throwEx(Exception e) {
        e.printStackTrace();
//		switch (BaseApplication.getDebugMode()) {
//			case Debug:
//				makeToast("crash");
//				break;
//			case Test:
//				throw new CException(e.getMessage());
//			case Release:
//				MobclickHelper.reportError(e);
//				break;
//		}
    }

    //---------------------------------------------------------------------------------------
    public final static String NOTIFY_CREATE = "notify_create";
    public final static String NOTIFY_RESUME = "notify_resume";
    public final static String NOTIFY_FINISH = "notify_finish";
    private Handler notifyUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notifyUpdate((NotifyUpdateEntity) msg.obj);
        }
    };

    protected void notifyUpdate(NotifyUpdateEntity notifyUpdateEntity) {
        switch (notifyUpdateEntity.getNotifyTag()) {
            case NOTIFY_FINISH:
                break;
        }
    }

    private static HashMap<String, HashMap<Integer, Handler>> notifyUpdateMap = new HashMap<>(20, 10);

    public static <Aty extends BaseFragment> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, Object entity) {
        sendNotifyUpdate(fgmClass, notifyTag, entity, 0);
    }

    public static <Aty extends BaseFragment> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag) {
        sendNotifyUpdate(fgmClass, notifyTag, null, 0);
    }

    public static <Aty extends BaseFragment> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, long delay) {
        sendNotifyUpdate(fgmClass, notifyTag, null, delay);
    }

    public static <Aty extends BaseFragment> void sendNotifyUpdate(Class<Aty> fgmClass, String notifyTag, Object entity, long delay) {
        String tag = fgmClass.getName();
        if (!notifyUpdateMap.containsKey(tag)) return;
        NotifyUpdateEntity notifyUpdateEntity = new NotifyUpdateEntity(notifyTag, entity);
        HashMap<Integer, Handler> handlerList = notifyUpdateMap.get(tag);
        Handler handler;
        Message message;
        for (int i : handlerList.keySet()) {
            handler = handlerList.get(i);
            message = handler.obtainMessage();
            message.what = handler.hashCode();
            message.obj = notifyUpdateEntity;
            handler.sendMessageDelayed(message, delay);
        }
    }

    private void addNotifyUpdate() {
        String tag = this.getClass().getName();
        HashMap<Integer, Handler> handlerList;
        if (notifyUpdateMap.containsKey(tag)) {
            handlerList = notifyUpdateMap.get(tag);
            if (!handlerList.containsKey(notifyUpdateHandler.hashCode()))
                handlerList.put(notifyUpdateHandler.hashCode(), notifyUpdateHandler);
        } else {
            handlerList = new HashMap<>(2, 2);
            handlerList.put(notifyUpdateHandler.hashCode(), notifyUpdateHandler);
            notifyUpdateMap.put(tag, handlerList);
        }
    }

    private void removeNotifyUpdate() {
        String tag = this.getClass().getName();
        if (!notifyUpdateMap.containsKey(tag)) return;
        HashMap<Integer, Handler> handlerList = notifyUpdateMap.get(tag);
        notifyUpdateHandler.removeMessages(notifyUpdateHandler.hashCode());
        handlerList.remove(notifyUpdateHandler.hashCode());
        if (handlerList.size() == 0)
            notifyUpdateMap.remove(tag);
    }

    @Override
    public void onDestroy() {
        removeNotifyUpdate();
        super.onDestroy();
    }


}
