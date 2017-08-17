package com.xinaliu.inspiration.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xinaliu.inspiration.BaseApplication;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.entity.ShareDataEntity;
import com.xinaliu.inspiration.http.PersistentCookieStore;
import com.xinaliu.inspiration.util.LogUtils;
import com.xinaliu.inspiration.util.SharedPreferencesUtil;
import com.xinaliu.inspiration.util.ToastUtil;
import com.xinaliu.inspiration.util.webview.BaseDownloadListener;
import com.xinaliu.inspiration.util.webview.BaseWebSettings;
import com.xinaliu.inspiration.util.webview.WebViewUtils;
import com.xinaliu.inspiration.util.window.SharePopupWindow;

import java.util.List;

import okhttp3.Cookie;

/**
 * Created by liuwei on 2017/7/31 13:36
 */

public class WebViewNewFragment extends BaseNewFragment {

    private static final String TAG = "WebViewNewFragment";

    private static final String URL_KEY = "url";

    private ShareDataEntity mShareDataEntity;

    private WebView mWebView;
    private ProgressBar pbProgress;
    private FragmentWebChromeClient mFragmentWebChromeClient;
    public static WebViewNewFragment newInstance(@NonNull String url, boolean isPrepared) {
        Bundle args = new Bundle();
        WebViewNewFragment fragment = new WebViewNewFragment();
        args.putBoolean(IS_PREPARED,isPrepared);
        args.putString(URL_KEY,url);
        fragment.setArguments(args);
        return fragment;
    }

    private  View mView;
    private TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_web_view_new,null);
        mView.findViewById(R.id.ib_left).setOnClickListener(this);
        mView.findViewById(R.id.ib_left_clear).setOnClickListener(this);
//        mView.findViewById(R.id.ib_right).setOnClickListener(this);
        tvTitle =  $(mView,R.id.title);
        tvTitle.setText("");
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
        List<Cookie> cookies = cookieStore.getCookies();
        WebViewUtils.setCookies(mContext,url, cookies);
    }
    private String url;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initData() {
        if (mContext.getApplicationContext() != null)
        {
            mWebView = WebViewUtils.createWebView(mContext.getApplicationContext());
        } else{
            mWebView = WebViewUtils.createWebView(mContext);
        }
        pbProgress = (ProgressBar) mView.findViewById(R.id.pb_progress);
        LinearLayout lView = (LinearLayout) mView.findViewById(R.id.fragment_web_view_new);
        lView.addView(mWebView);
        WebSettings(mContext,mWebView);
        mWebView.setWebViewClient(new FragmentWebViewClient(mContext));
        mWebView.setDownloadListener(new BaseDownloadListener(mContext));

        if (Build.VERSION.SDK_INT >= 17) {
            mWebView.addJavascriptInterface(new WebViewJavaScriptFunction(this,mContext),"Android");
        }

        mFragmentWebChromeClient = new FragmentWebChromeClient(mContext, mWebView,pbProgress);
        mWebView.setWebChromeClient(mFragmentWebChromeClient);
        if(getArguments() != null && (url = getArguments().getString(URL_KEY,null)) != null){
            PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
            List<Cookie> cookies = cookieStore.getCookies();
            WebViewUtils.setCookies(mContext,url, cookies);
            mWebView.loadUrl(url);
        }else {
            ToastUtil.toastSome(mContext,"数据错误");
        }
    }
    public class WebViewJavaScriptFunction{

        private Context mContext;
        private WebViewNewFragment mWebViewNewFragment;

        public WebViewJavaScriptFunction(WebViewNewFragment mWebViewNewFragment,Context mContext) {
            this.mContext = mContext;
            this.mWebViewNewFragment = mWebViewNewFragment;
        }

        @JavascriptInterface
        public void getShareData(String data){
            if (!TextUtils.isEmpty(data)){
                mWebViewNewFragment.mShareDataEntity = JSON.parseObject(data, ShareDataEntity.class);
                LogUtils.d(mShareDataEntity);
            }
        }
    }
    private class FragmentWebViewClient extends WebViewClient {
        private Context mContext;

        public FragmentWebViewClient(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!view.getSettings().getLoadsImagesAutomatically()) {
                view.getSettings().setLoadsImagesAutomatically(true);
            }
            if (!TextUtils.isEmpty(view.getTitle()))
            tvTitle.setText(view.getTitle());
            CookieManager cookieManager = CookieManager.getInstance();
            String CookieStr = cookieManager.getCookie(url);
            SharedPreferencesUtil.saveString(mContext, CookieStr, "cookies", url);
//            view.loadUrl("javascript:window.Android.ret_app_obj(javascript:retappobj());");

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String url = request.getUrl().toString();
                if (!Patterns.WEB_URL.matcher(url).matches()) {
                    showPopupWindow(url);
                    return true;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                commitKey(request.getUrl().toString());
            }
            return true;
//            return super.shouldOverrideUrlLoading(view, request);
        }

        private void showPopupWindow(String url) {
            final Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if(scheme != null && !TextUtils.isEmpty(scheme)) {
//                if (scheme.equals(LoginActivity.LOGIN_SCHEME)) {
//                    if (mContext instanceof Activity) {
//                        ((Activity) mContext).startActivityForResult(new Intent(mContext, LoginActivity.class), LoginActivity.LOGIN_REQUEST_CODE);
////                    ((Activity)mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, uri), LoginActivity.LOGIN_REQUEST_CODE);
//                    } else {
//                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
//                    }
//                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return true;
//            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    private class FragmentWebChromeClient extends WebChromeClient {
        private Context mContext;
        private ProgressBar mProgressBar;
        private WebView mWebView;

        public FragmentWebChromeClient(Context mContext, WebView mWebView, ProgressBar mProgressBar) {
            this.mContext = mContext;
            this.mWebView = mWebView;
            this.mProgressBar = mProgressBar;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(mProgressBar != null)
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if(mProgressBar.getVisibility() != View.VISIBLE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    // 加载中
                    mProgressBar.setProgress(newProgress);
                }
            super.onProgressChanged(view, newProgress);
        }
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin,true,false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        @Override
        public boolean onJsAlert(WebView view, String url, String message,final JsResult result) {
            if (mContext instanceof Activity){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("来自："+url+"的提示")
                        .setMessage(message)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                result.confirm();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                result.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return super.onJsAlert(view, url, message, result);
        }
        public final static int FILECHOOSER_RESULTCODE = 1;
        public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

        //扩展浏览器上传文件
        //3.0++版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooserImpl(uploadMsg);
        }

        //3.0--版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooserImpl(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooserImpl(uploadMsg);
        }

        // For Android > 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
            openFileChooserImplForAndroid5(uploadMsg);
            return true;
        }


        private ValueCallback<Uri> mUploadMessage;
        private void openFileChooserImpl(@NonNull  ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            if (mContext instanceof Activity)
                ((Activity)mContext).startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
        }
        private ValueCallback<Uri[]> mUploadMessageForAndroid5;
        private void openFileChooserImplForAndroid5(@NonNull ValueCallback<Uri[]> uploadMsg) {
            mUploadMessageForAndroid5 = uploadMsg;
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");
            if (mContext instanceof Activity)
                ((Activity)mContext).startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
        }

        /**
         * 这个是Activity回调的示例
         * @param requestCode
         * @param resultCode
         * @param intent
         */
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage)
                    return;
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;

            } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
                if (null == mUploadMessageForAndroid5)
                    return;
                Uri result = (intent == null || resultCode != Activity.RESULT_OK) ? null : intent.getData();
                if (result != null) {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                }
                mUploadMessageForAndroid5 = null;
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void WebSettings(@NonNull final Context context, @NonNull final WebView mWebView){
        synchronized (BaseWebSettings.class){
            mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            // 支持多窗口
            mWebView.getSettings().setSupportMultipleWindows(true);

            //可不要------------------------------------
            mWebView.setInitialScale(25);//为25%，最小缩放等级
            //支持获取手势焦点，输入用户名、密码或其他
            mWebView.requestFocusFromTouch();

            mWebView.setWebChromeClient(new WebChromeClient()); // chrom
            mWebView.setSaveEnabled(false);
            WebSettings settings = mWebView.getSettings();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
//            TODO  图片延迟加载
//            mWebView.getSettings().setBlockNetworkImage(true);

            if (BaseApplication.NETWORK_IS_AVAILABLE) {
                //不使用缓存：
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
            }else {
                //优先使用缓存：
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
            }
            // 开启 DOM storage API 功能
            settings.setDomStorageEnabled(true);

            //开启 database storage API 功能
            settings.setDatabaseEnabled(true);
            final String dbPath = context.getDir("db", Context.MODE_PRIVATE).getPath();
            //设置数据库缓存路径
            settings.setDatabasePath(dbPath);

            // 开启 Application Caches 功能
            settings.setAppCacheEnabled(true);
            final String cachePath = context.getDir("cache",Context.MODE_PRIVATE).getPath();
            settings.setAppCachePath(cachePath);
            settings.setAppCacheMaxSize(5*1024*1024);

            if(Build.VERSION.SDK_INT >= 19){
                settings.setLoadsImagesAutomatically(true);
            }else {
                settings.setLoadsImagesAutomatically(false);
            }
//硬件加速
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//不能播放视频
//                mWebView.setLayerType(View.LAYER_TYPE_NONE,null);
//                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//            }


            // 设置可以支持缩放
            settings.setSupportZoom(true);
            // 设置出现缩放工具
//            TODO
//            settings.setBuiltInZoomControls(true);
            //扩大比例的缩放


            //设置自适应屏幕，两者合用
            settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            // 设置加载进来的页面自适应手机屏幕
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局

            settings.setPluginState(WebSettings.PluginState.ON);

            settings.supportMultipleWindows();//多窗口

            settings.setAllowFileAccess(true);  //设置可以访问文件
            settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
            settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
            settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
            settings.setDefaultTextEncodingName("utf-8");//设置编码格式

            settings.setJavaScriptEnabled(true);  //支持js
//            if (Build.VERSION.SDK_INT > 16)
//            {
//                settings.setJavaScriptEnabled(true);  //支持js
//            }else {
//                mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
//                mWebView.removeJavascriptInterface("accessibility");
//                mWebView.removeJavascriptInterface("accessibilityTraversal");
//            }
            settings.setSavePassword(false);
        }
    }
    private int backStackEntryCount;

    private void commitKey(String url) {
        synchronized (WebViewNewFragment.class){
            final FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out);
            transaction.add(R.id.fragment,WebViewNewFragment.newInstance(url,false));
            transaction.hide(this);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.ib_left:
               FragmentActivity activity = getActivity();
               if (activity != null)
                   activity.onBackPressed();
               break;
           case R.id.ib_left_clear:
               if (getActivity() != null)
                   getActivity().finish();
               break;
//           case R.id.ib_right:
//               show(v);
//               break;
           default:
               break;
       }
    }
    private SharePopupWindow mSharePopupWindow = null;
    public void showSharePopupWindow(){
        if (mShareDataEntity != null && mSharePopupWindow == null){
            synchronized (WebViewNewFragment.class){
                if (mShareDataEntity != null && mSharePopupWindow == null){
                    if (TextUtils.isEmpty(mShareDataEntity.getTitle())
                            || TextUtils.isEmpty(mShareDataEntity.getShareUrl())
                            )return;
                    if (TextUtils.isEmpty(mShareDataEntity.getGeneralContent()))mShareDataEntity.setGeneralContent(mShareDataEntity.getTitle());
                    mSharePopupWindow = new SharePopupWindow(mContext,mShareDataEntity);
                }
            }
        }
        if (mSharePopupWindow != null)
        mSharePopupWindow.showAtLocation(mView);
    }
    public void reloadWebView() {
        synchronized (this){
            if(mWebView != null){
                //不使用缓存：
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
                mWebView.reload();
            }
        }
    }
//    public void show(View v){
//        new EasyDialog(mContext)
//                .setLayoutResourceId(R.layout.layout_show_dialog, new EasyDialog.OnChangeView() {
//                    @Override
//                    public void changeContentView(EasyDialog.ViewHolder holder,final EasyDialog easyDialog) {
//                        holder.setOnClickListener(R.id.share, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                easyDialog.dismiss();
//                                showSharePopupWindow();
//                            }
//                        });
//                        holder.setOnClickListener(R.id.refresh_interface, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                easyDialog.dismiss();
//                                reloadWebView();
//                            }
//                        });
//                    }
//                })
//                .setGravity(EasyDialog.GRAVITY_BOTTOM)
//                .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary))
//                .setLocationByAttachedView(v)
//                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 350, 400, 0)
//                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 350, 0, 400)
//                .setTouchOutsideDismiss(true)
//                .setMatchParent(false)
//                .setMarginLeftAndRight(24, 48)
//                .show();
//    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
