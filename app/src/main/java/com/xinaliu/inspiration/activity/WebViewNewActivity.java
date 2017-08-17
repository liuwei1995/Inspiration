package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.widget.Toast;

import com.xinaliu.inspiration.I.ActivityOnBackPressed;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.entity.ShareDataEntity;
import com.xinaliu.inspiration.fragment.WebViewNewFragment;


/**
 * Created by liuwei on 2017/7/31 13:24
 */

public class WebViewNewActivity extends BaseNewActivity {

    public static final String PARCELABLE_KEY = "PARCELABLE_KEY";
    private String url;

    public static void startActivity(@NonNull Context context, @NonNull String url) {
        startActivity(context, url, null);
    }

    public static void startActivity(@NonNull Context context, @NonNull String url,ShareDataEntity baseEntity) {
        Intent intent = new Intent(context, WebViewNewActivity.class);
        Bundle bundle = new Bundle();
        if (baseEntity != null)
            bundle.putParcelable(PARCELABLE_KEY, baseEntity);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_new);

        if (savedInstanceState == null && (savedInstanceState = getIntent().getExtras()) == null) {
            Toast.makeText(this, "数据错误稍后重试", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        url = savedInstanceState.getString("url");
        if (url == null || url.isEmpty() || ! Patterns.WEB_URL.matcher(url).matches()) {
            //不符合标准
            Toast.makeText(this, "数据错误稍后重试", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        FragmentManager manager = getSupportFragmentManager();//获取Fragment管理类
        FragmentTransaction transaction = manager.beginTransaction();//事务处理类

        transaction.add(R.id.fragment, WebViewNewFragment.newInstance(url,false), WebViewNewFragment.class.getSimpleName());//add 方法事务
        transaction.commit(); //提交事务
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
        if(currentFragment instanceof ActivityOnBackPressed){
            boolean b = ((ActivityOnBackPressed) currentFragment).onBackPressed();
            if (b)return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
