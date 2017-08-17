package com.xinaliu.inspiration.util.webview;

import android.content.Context;

/**
 * Created by liuwei on 2017/6/20 10:43
 */

public abstract class BaseWebViewJavaScriptFunction<C extends Context> {

    protected C mActivity;

    public BaseWebViewJavaScriptFunction(C mActivity) {
        this.mActivity = mActivity;
    }
}
