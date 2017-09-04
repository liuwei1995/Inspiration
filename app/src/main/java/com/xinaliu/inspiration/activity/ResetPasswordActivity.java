package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xinaliu.inspiration.I.ActivityOnBackPressed;
import com.xinaliu.inspiration.I.FragmentPopBackStack;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.fragment.ResetPasswordForgetFragment;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BaseNewActivity implements FragmentPopBackStack {


    public static void startActivity(Context context){
        context.startActivity(new Intent(context, ResetPasswordActivity.class));
    }

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvLeftTitle);
        setSupportActionBar(toolbar);
        toolbar.findViewById(R.id.ivLeft).setOnClickListener(this);

        FragmentManager manager = getSupportFragmentManager();//获取Fragment管理类
        FragmentTransaction transaction = manager.beginTransaction();//事务处理类
        transaction.add(R.id.fragment, ResetPasswordForgetFragment.newInstance());//add 方法事务
        transaction.commit(); //提交事务
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivLeft:
                popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
        if(currentFragment instanceof ActivityOnBackPressed){
            boolean b = ((ActivityOnBackPressed) currentFragment).onBackPressed();
            if (b)return;
        }
        popBackStack();
//        super.onBackPressed();
    }


    @Override
    public void popBackStack() {
        FragmentManager manager = getSupportFragmentManager();//获取Fragment管理类
        Fragment fragment = manager.findFragmentById(R.id.fragment);
        if (fragment != null && fragment instanceof ResetPasswordForgetFragment){
            finish();
        }else {
            manager.popBackStack();
        }
    }

    @Override
    public void changeActivityTitle(String title) {
        tvTitle.setText(title);
    }
}
