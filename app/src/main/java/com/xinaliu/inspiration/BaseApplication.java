package com.xinaliu.inspiration;

import android.app.Application;
import android.os.Build;

import com.xinaliu.inspiration.http.OkHttpPresenterImpl;
import com.xinaliu.inspiration.http.util.HttpHelper;
import com.xinaliu.inspiration.util.LogUtils;
import com.xinaliu.inspiration.util.NetWorkUtils;
import com.xinaliu.inspiration.util.image.GildePresenterImpl;
import com.xinaliu.inspiration.util.image.util.ImageHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


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

//        try
//        {
//            setCertificates(getAssets().open("srca.cer"));
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }

    }
    public void setCertificates(InputStream... certificates)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)
                {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
//            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
