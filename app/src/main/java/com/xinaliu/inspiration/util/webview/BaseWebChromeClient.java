package com.xinaliu.inspiration.util.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xinaliu.inspiration.util.LogUtils;


/**
 * Created by liuwei on 2017/4/6
 */

public class BaseWebChromeClient<A extends Activity>  extends WebChromeClient {

    protected A mActivity;
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    public BaseWebChromeClient(@NonNull A activity, WebView webView) {
        super();
        mActivity = activity;
        this.webView = webView;
    }
    //配置权限（同样在WebChromeClient中实现）
//    @Override
//    public void onGeolocationPermissionsShowPrompt(String origin,
//                                                   Callback callback) {
//        callback.invoke(origin, true, false);
//        super.onGeolocationPermissionsShowPrompt(origin, callback);
//    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        callback.invoke(origin,true,false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

//    @Override
//    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//        LogUtils.e(TAG,"onJsConfirm");
//        Toast.makeText(mActivity, "onJsConfirm", Toast.LENGTH_LONG).show();
//        return super.onJsConfirm(view, url, message, result);
//    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        LogUtils.e(TAG,"onJsPrompt");
//        Toast.makeText(mActivity, "onJsPrompt", Toast.LENGTH_LONG).show();
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//
//        builder.setTitle("照耀健康").setMessage(message);
//
//        final EditText et = new EditText(view.getContext());
//        et.setSingleLine();
//        et.setText(defaultValue);
//        builder.setView(et)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm(et.getText().toString());
//                    }
//                })
//                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//
//        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
//        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
//                Log.v("onJsPrompt", "keyCode==" + keyCode + "event="+ event);
//                return true;
//            }
//        });
//        // 禁止响应按back键的事件
//        // builder.setCancelable(false);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        return true;
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    private static final String TAG = "BaseWebChromeClient";

//    @Override
//    public boolean onJsTimeout() {
//        LogUtils.e(TAG,"onJsTimeout");
////        Toast.makeText(mActivity, "onJsTimeout", Toast.LENGTH_LONG).show();
//        return super.onJsTimeout();
//    }

//    @Override
//    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
//        LogUtils.e(TAG,"onJsBeforeUnload");
////        Toast.makeText(mActivity, "onJsBeforeUnload", Toast.LENGTH_LONG).show();
//        return super.onJsBeforeUnload(view, url, message, result);
//    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,final JsResult result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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
        return super.onJsAlert(view, url, message, result);
    }

    FrameLayout video_fullView;
    private CustomViewCallback xCustomViewCallback;
    // 播放网络视频时全屏会被调用的方法
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        webView.setVisibility(View.INVISIBLE);
        // 如果一个视图已经存在，那么立刻终止并新建一个
        if (xCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        video_fullView = new FullscreenHolder(mActivity);
        video_fullView.addView(view);
        decor.addView(video_fullView);

        xCustomView = view;
        xCustomViewCallback = callback;
        video_fullView.setVisibility(View.VISIBLE);
    }

    public void onDestroy() {
        if(video_fullView != null)
        video_fullView.removeAllViews();
    }
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return true;
        }

    }
    private View xCustomView;
    // 视频播放退出全屏会被调用的
    @Override
    public void onHideCustomView() {
        if (xCustomView == null)// 不是全屏播放状态
            return;

        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        xCustomView.setVisibility(View.GONE);
        video_fullView.removeView(xCustomView);
        xCustomView = null;
        video_fullView.setVisibility(View.GONE);
        xCustomViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }
    private int video_loading_progress = 0;

    public int getVideo_loading_progress() {
        return video_loading_progress;
    }

    public void setVideo_loading_progress(int video_loading_progress) {
        this.video_loading_progress = video_loading_progress;
    }

    private View xprogressvideo;

    // 视频加载时进程loading
    @Override
    public View getVideoLoadingProgressView() {
        if(video_loading_progress > 0){
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater.from(mActivity);
                xprogressvideo = inflater.inflate(video_loading_progress, null);
            }
        }else {
            if (xprogressvideo == null)
                xprogressvideo = new LinearLayout(mActivity);
        }
        return xprogressvideo;
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
        mActivity.startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
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

        mActivity.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
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

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
        //把事件传递给了ConfirmationDialogFragment
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
    }
}
