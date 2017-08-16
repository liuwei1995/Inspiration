package com.xinaliu.inspiration.http;


import com.xinaliu.inspiration.http.util.HttpHelper;

/**
 * Created by liuwei on 2017/3/22
 */

public final class HttpUtils {

    public static final String TOKEN_NAME = "token";

    public static HttpHelper getPresenter(){
        return HttpHelper.newInstance();
    }

    private HttpUtils() {
        throw new NullPointerException("private  final class");
    }


    public static void cancel(Object tag){
        getPresenter().cancel(tag);
    }

}
