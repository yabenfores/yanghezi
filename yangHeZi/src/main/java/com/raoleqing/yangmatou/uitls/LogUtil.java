package com.raoleqing.yangmatou.uitls;

import android.app.Application;
import android.util.Log;

import com.handmark.pulltorefresh.library.CFile;
import com.raoleqing.yangmatou.BaseActivity;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by ybin on 2016/5/22.
 */
public class LogUtil {
    private LogUtil() {
    }

    public static void write(Class<?> clazz, String msg) {
        System.out.println("----------------------------");
        System.out.println(clazz.getName() + ":");
        System.out.println(msg);
    }

    public static void write(String exInfo, String strFilePath) {
        //每次写入时，都换行写
        String strContent = exInfo + "\n";
        try {
            CFile file = new CFile(strFilePath);
            if (!file.exists()) {
                file.createNewFileAndDirectory();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {

        }
    }

    public static void loge(Class c, String... msg) {
            for (String s : msg) {
                Log.e(c.getClass().getName(), s);
        }
    }

    public static void printStackTrace(Class c, Exception ex) {
            ToastUtil.MakeShortToast(BaseActivity.getAppContext(),"crash");
            System.out.println(c.getClass().getName());
            ex.printStackTrace();
    }
}
