package com.xinaliu.inspiration.http.util;


import com.xinaliu.inspiration.util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 *  Created by liuwei on 2017/5/25 09:00
 *  <li>这个需要根据自己的业务需求来写{@link HttpCallback#onCallbackResult(Boolean, String)}</li>
 * @param <Result>
 */
public abstract class HttpCallback<Result> implements ICallback {

    private static final String TAG = "HttpCallback";

    @Override
    public void onCallbackResult(Boolean isSuccess, String s) {
        LogUtils.d(TAG,"isSuccess=="+isSuccess+"\n====="+ s);

    }

     public abstract void onCallbackResult(Boolean isSuccess, Result result);

    private static Class<?> analysisClazzInfo(Object object){
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0){
                return (Class<?>) actualTypeArguments[0];
            }
        }
        return null;
    }
}
