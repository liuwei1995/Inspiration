package com.xinaliu.inspiration;

import android.app.Application;
import android.os.Build;

import com.xinaliu.inspiration.http.OkHttpPresenterImpl;
import com.xinaliu.inspiration.http.util.HttpHelper;
import com.xinaliu.inspiration.util.LogUtils;
import com.xinaliu.inspiration.util.NetWorkUtils;
import com.xinaliu.inspiration.util.image.GildePresenterImpl;
import com.xinaliu.inspiration.util.image.util.ImageHelper;


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
        CrashUtils.getInstance().init(this);
        NETWORK_IS_AVAILABLE = NetWorkUtils.isNetworkConnected(this);
        if(NETWORK_IS_AVAILABLE) {
            isWiFi = NetWorkUtils.isWifiConnected(this);
        }
        HttpHelper.newInstance().init(OkHttpPresenterImpl.newInstance().init(this));

        new LogUtils.Builder(this).setLogSwitch(true);
//        ImageHelper.init(ImageLoaderPresenterImpl.newInstance(this));
        ImageHelper.init(GildePresenterImpl.newInstance(this));
    }

}
