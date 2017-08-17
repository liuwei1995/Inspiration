package com.xinaliu.inspiration.util.window;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.zhaoyao.zhaoyaohealthy.R;

/**
 * Created by liuwei on 2017/8/1 15:10
 */

public class MenuDialog {

    private EasyDialog mEasyDialog;

    private View mView;

    public MenuDialog(@NonNull Context context,@LayoutRes int layoutResID) {
        this(context, LayoutInflater.from(context).inflate(layoutResID,null));
    }
    private MenuDialog(@NonNull Context context,@NonNull View mView) {
        mEasyDialog = new EasyDialog(context);
        mEasyDialog.setLayout(mView)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimary))
//                .setLocationByAttachedView(v)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 350, 400, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 350, 0, 400)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 48);
        this.mView = mView;
    }
    public void show(View v){
        mEasyDialog.setLocationByAttachedView(v).show();
    }
    public void show(){
        mEasyDialog.show();
    }

    public void setOnClickListener(){

    }

}
