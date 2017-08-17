package com.xinaliu.inspiration.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by liuwei on 2017/8/17 11:19
 */

public class GlideUtils {

    private static Context mContext;

    public void init(Context context) {
        if (context != null){
            if (context.getApplicationContext() != null){
                mContext = context.getApplicationContext();
            }else {
                mContext = context;
            }
        }
    }

    private static GlideUtils mGlideUtils;

    public static GlideUtils newInstance() {
        if (mGlideUtils == null){
            synchronized (GlideUtils.class){
                if (mGlideUtils == null){
                    mGlideUtils = new GlideUtils();
                }
            }
        }
        return mGlideUtils;
    }
    public void load(String url, ImageView imageView){
        if (mContext != null)
        Glide.with(mContext).load(url).into(imageView);
    }

    public void stop() {
        Glide.with(mContext).onStop();
    }

    public void resume() {
        Glide.with(mContext).onStart();
    }

    public void destroy() {
        Glide.with(mContext).onDestroy();
    }
}
