package com.xinaliu.inspiration.util.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xinaliu.inspiration.BaseApplication;
import com.xinaliu.inspiration.util.DeviceUtils;

import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * Created by liuwei on 2017/3/20
 */

public class WebViewUtils {

    public static void setCacheMode(WebView mWebView,boolean userCache){
        if (userCache || !BaseApplication.NETWORK_IS_AVAILABLE) {
            //优先使用缓存：
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
        }else {
            //不使用缓存：
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        }
    }
    public static WebView createWebView(Context mContext){
        WebView mWebView = new WebView(mContext);
//        MyWebView mWebView = new MyWebView(mContext);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return  mWebView;
    }
    public static WebView createWebView(Context mContext,ViewGroup.LayoutParams para){
        WebView mWebView = new WebView(mContext);
        mWebView.setLayoutParams(para);
        return  mWebView;
    }
    public static class WebViewClientCallback extends  WebViewClient{

    }
    public synchronized static void setWebView(@NonNull final Context mContext,final WebView mWebView,final  ProgressBar pbProgress,final Boolean isStartActivity){
        setWebView(mContext,mWebView,pbProgress,isStartActivity,null);
    }
    public synchronized static void setWebView(@NonNull final Context mContext,final WebView mWebView,final  ProgressBar pbProgress,final Boolean isStartActivity,final Boolean isOnKey){
        setWebView(mContext,mWebView,pbProgress,isStartActivity,isOnKey,null);
    }
    public synchronized static void setWebView(@NonNull final Context mContext, final WebView mWebView, final  ProgressBar pbProgress, final Boolean isStartActivity,final Boolean isOnKey,final WebViewClientCallback webViewClientCallback){
        if(mWebView != null){

//            mWebView.setWebViewClient(new WebViewClient(){
//                @Override
//                public void onLoadResource(WebView view, String url) {
//                    super.onLoadResource(view, url);
//                }
//
//                @Override
//                public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                    view.getSettings().setBlockNetworkImage(true);
//                    super.onPageStarted(view, url, favicon);
//                }
//
//                @Override
//                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                    super.onReceivedError(view, request, error);
//                    if(webViewClientCallback != null)
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            webViewClientCallback.onReceivedError(view,request,error);
//                        }
//                }
//
//                @Override
//                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                    super.onReceivedError(view, errorCode, description, failingUrl);
//                    if(webViewClientCallback != null) {
//                        webViewClientCallback.onReceivedError(view,errorCode,description,failingUrl);
//                    }
//                }
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
////                    view.getSettings().setBlockNetworkImage(false);
////                    //判断webview是否加载了，图片资源
////                    if (!view.getSettings().getLoadsImagesAutomatically()) {
////                        //设置wenView加载图片资源
////                        view.getSettings().setLoadsImagesAutomatically(true);
////                    }
//                    if(!view.getSettings().getLoadsImagesAutomatically()) {
//                        view.getSettings().setLoadsImagesAutomatically(true);
//                    }
//                    super.onPageFinished(view, url);
//                    if(webViewClientCallback != null)
//                    webViewClientCallback.onPageFinished(view,url);
//                }
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    String url = request.getUrl().toString();
//                    if(isStartActivity != null && isStartActivity){
//                        if (Patterns.WEB_URL.matcher(url).matches()) {
//                            //符合标准
////                            TestActivity.startActivity(mContext,url);
//                            WebViewActivity.startActivity(mContext,url);
//                        }
//                    }else {
//                        if (Patterns.WEB_URL.matcher(url).matches()) {
//                            //网络请求
//                            if (BaseApplication.NETWORK_IS_AVAILABLE) {
//                                //不使用缓存：
//                                mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//                            }else {
//                                //优先使用缓存：
//                                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
//                            }
//                            mWebView.loadUrl(url);
//                        }
//                    }
//                    return true;
//                }
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView webview, String url) {
//                    if(isStartActivity != null && isStartActivity){
//                        if (Patterns.WEB_URL.matcher(url).matches()) {
//                            //符合标准
////                            TestActivity.startActivity(mContext,url);
//                            WebViewActivity.startActivity(mContext,url);
//                        }
//                    }else {
//                        if (Patterns.WEB_URL.matcher(url).matches()) {
//                            //网络请求
//                                if (BaseApplication.NETWORK_IS_AVAILABLE) {
//                                //不使用缓存：
//                                mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//                            }else {
//                                //优先使用缓存：
//                                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
//                            }
//                            mWebView.loadUrl(url);
//                        }
//                    }
//                    return true;
//                }
//            });
            if(isOnKey != null && isOnKey){
                //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
                mWebView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                                //设置 缓存模式  取本地缓存
                                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                                //表示按返回键  时的操作
                                mWebView.goBack();   //后退
                                //webview.goForward();//前进
                                return true;    //已处理
                            }
                        }
                        return false;
                    }
                });
            }



            if(pbProgress != null){
                mWebView.setWebChromeClient(new WebChromeClient() {//监听网页加载
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                            if (newProgress == 100) {
                                // 网页加载完成
                                pbProgress.setVisibility(View.GONE);
                            } else {
                                // 加载中
                                pbProgress.setProgress(newProgress);
                            }
                        super.onProgressChanged(view, newProgress);
                    }
                });
            }
            WebSettings settings = mWebView.getSettings();

//            TODO  图片延迟加载
//            mWebView.getSettings().setBlockNetworkImage(true);


//            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            if (BaseApplication.NETWORK_IS_AVAILABLE) {
                //不使用缓存：
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
            }else {
                //优先使用缓存：
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
            }

            // webView.getSettings().setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染


            mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            // 支持多窗口
            mWebView.getSettings().setSupportMultipleWindows(true);

             // 开启 DOM storage API 功能
            settings.setDomStorageEnabled(true);

            //开启 database storage API 功能
            mWebView.getSettings().setDatabaseEnabled(true);
            final String dbPath = mContext.getDir("db",Context.MODE_PRIVATE).getPath();
            //设置数据库缓存路径
            mWebView.getSettings().setDatabasePath(dbPath);


            // 开启 Application Caches 功能
            mWebView.getSettings().setAppCacheEnabled(true);
            final String cachePath = mContext.getDir("cache",Context.MODE_PRIVATE).getPath();
            mWebView.getSettings().setAppCachePath(cachePath);
            mWebView.getSettings().setAppCacheMaxSize(5*1024*1024);

//图片加载
            if(Build.VERSION.SDK_INT >= 19){
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }else {
                mWebView.getSettings().setLoadsImagesAutomatically(false);
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                final int layerType = false ? ViewCompat.LAYER_TYPE_HARDWARE : ViewCompat.LAYER_TYPE_NONE;
//                mWebView.setLayerType(layerType, null);
//            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
//            设置WebView是否使用预览模式加载界面。

            settings.setLoadWithOverviewMode(true);


            //可不要------------------------------------
            mWebView.setInitialScale(25);//为25%，最小缩放等级
            // 设置可以支持缩放
            settings.setSupportZoom(true);
            // 设置出现缩放工具
//        settings.setBuiltInZoomControls(true);
            //扩大比例的缩放


            //设置自适应屏幕，两者合用
            settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局

            settings.setAllowFileAccess(true);  //设置可以访问文件
            settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
            settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
            settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
            settings.setDefaultTextEncodingName("utf-8");//设置编码格式
//支持获取手势焦点，输入用户名、密码或其他
            mWebView.requestFocusFromTouch();
            settings.setJavaScriptEnabled(true);  //支持js
        }
    }
    WebViewClient mWebViewClient = new WebViewClient()
    {
        /**
         *

        shouldOverrideUrlLoading(WebView view, String url)  //最常用的，比如上面的。
        //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
        //比如获取url，查看url.contains(“add”)，进行添加操作

        shouldOverrideKeyEvent(WebView view, KeyEvent event)
        //重写此方法才能够处理在浏览器中的按键事件。

        onPageStarted(WebView view, String url, Bitmap favicon)
        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。

        onPageFinished(WebView view, String url)
        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。

        onLoadResource(WebView view, String url)
        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。

        onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        // (报告错误信息)

        doUpdateVisitedHistory(WebView view, String url, boolean isReload)
        //(更新历史记录)

        onFormResubmission(WebView view, Message dontResend, Message resend)
        //(应用程序重新请求网页数据)

        onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
        //（获取返回信息授权请求）

        onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        //重写此方法可以让webview处理https请求。

        onScaleChanged(WebView view, float oldScale, float newScale)
        // (WebView发生改变时调用)

        onUnhandledKeyEvent(WebView view, KeyEvent event)
        //（Key事件未被加载时调用）

         */
    };

    /**
     * 同步一下cookie
     */
    public static void setCookies(Context context,String url, List<Cookie> cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        if (cookies != null){
            for (int i = 0; i < cookies.size(); i++) {
                Cookie cookie = cookies.get(i);
                cookieManager.setCookie(url, cookie.name()+"="+cookie.value());//cookies是在HttpClient中获得的cookie
            }
        }
        cookieManager.setCookie(url, "type="+ DeviceUtils.getUDID());//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
    public static void addCookies(Context context, String url, Map<String,String> map)
    {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (map != null){
            for (String key : map.keySet()){
                cookieManager.setCookie(url, key+"="+map.get(key));//cookies是在HttpClient中获得的cookie
            }
        }
        cookieManager.setCookie(url, "type="+ DeviceUtils.getUDID());//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
    public static void setCookies(Context context, String url, Map<String,String> map) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        if (map != null){
            for (String key : map.keySet()){
                cookieManager.setCookie(url, key+"="+map.get(key));//cookies是在HttpClient中获得的cookie
            }
        }
        cookieManager.setCookie(url, "type="+ DeviceUtils.getUDID());//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

    /**
     *
     * @param context c
     * @param url u
     * @param list_key_value  key+"="+value
     */
    public static void setCookies(Context context, String url, List<String> list_key_value,boolean isRemove) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (isRemove)
        cookieManager.removeSessionCookie();//移除
        if (list_key_value != null){
            for (int i = 0; i < list_key_value.size(); i++) {
                cookieManager.setCookie(url, list_key_value.get(i));//cookies是在HttpClient中获得的cookie
            }
        }
        cookieManager.setCookie(url, "type="+ DeviceUtils.getUDID());//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
}
