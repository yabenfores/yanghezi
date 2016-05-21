package entity;

import android.content.Context;

import com.raoleqing.yangmatou.BaseActivity;

import java.io.Serializable;

/**
 * Created by ybin on 2016/5/11.
 */
public class CBaseEntity implements Serializable {

    private Context context = BaseActivity.getAppContext();

    public Context getContext() {
        return context;
    }


    public CBaseEntity() {
    }
}
