package com.xinaliu.inspiration;

import android.app.Application;
import android.os.Build;

import com.xinaliu.inspiration.util.LogUtils;
import com.xinaliu.inspiration.util.NetWorkUtils;


/**
 * Created by liuwei on 2017/3/20
 */

public class BaseApplication extends Application{

    public static final int SDK = Build.VERSION.SDK_INT;
    /**
     * 网络是否可用
     */
    public static boolean NETWORK_IS_AVAILABLE = false;

    public static boolean isWiFi = false;

    @Override
    public void onCreate() {
        super.onCreate();

        NETWORK_IS_AVAILABLE = NetWorkUtils.isNetworkConnected(this);
        if(NETWORK_IS_AVAILABLE) {
            isWiFi = NetWorkUtils.isWifiConnected(this);
        }

        CrashUtils.getInstance().init(this);
        new LogUtils.Builder(this).setLogSwitch(true);
    }

}
