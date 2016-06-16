package com.raoleqing.yangmatou.common;

import java.io.File;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.raoleqing.yangmatou.BaseActivity;
import com.raoleqing.yangmatou.uitls.SharedPreferencesUtil;
import com.raoleqing.yangmatou.uitls.ToastUtil;
import com.raoleqing.yangmatou.webserver.BaseNetConnection;
import com.raoleqing.yangmatou.webserver.Constant;
import com.raoleqing.yangmatou.webserver.NetConnectionInterface;
import com.raoleqing.yangmatou.webserver.NetParams;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class YangHeZiApplication extends Application {
    private static Context appContext;
    public static String TAG = "YangHeZi";
    private static ImageCache mImageCache;
    private static YangHeZiApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        initImageLoader(this);

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 初始化
        EMClient.getInstance().init(this, options);
        // 在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    public static YangHeZiApplication getInstance() {
        if (instance == null)
            instance = new YangHeZiApplication();
        return instance;
    }

    public ImageCache getImageCache() {
        if (mImageCache == null)
            mImageCache = new ImageCache();
        return mImageCache;
    }

    /**
     * 配置ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {

        File cacheDir = ImageUtils.getAlbumDir(getAppContext()); // 缓存文件的存放地址

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height
                .threadPoolSize(3)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // 降低线程的优先级保证主UI线程不受太大影响
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(5 * 1024 * 1024)) // 建议内存设在5-10M,可以有比较好的表现
                .memoryCacheSize(5 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) // 缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
                // (5
                // s),
                // readTimeout
                // (30
                // s)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);

    }

    /**
     * 图片加载可选项
     *
     * @return
     */
    public static DisplayImageOptions imageOption(int res) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(res).showImageForEmptyUri(res)
                .showImageOnFail(res).cacheInMemory(true).cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(500))
                .imageScaleType(ImageScaleType.NONE).bitmapConfig(Bitmap.Config.RGB_565)// 设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)// 载入图片前稍做延时可以提高整体滑动的流畅度
                .build();

        return options;
    }



    public static Context getAppContext() {
        return appContext;
    }
}
