package com.xinaliu.inspiration.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.TabFragmentAdapter;
import com.xinaliu.inspiration.fragment.CartoonRecommendFragment;
import com.xinaliu.inspiration.util.permission.AndPermission;
import com.xinaliu.inspiration.util.permission.PermissionListener;
import com.xinaliu.inspiration.util.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/8/28 10:19
 */

public class CartoonMainActivity extends BaseNewActivity {


    public static void startActivity(Context context){
        context.startActivity(new Intent(context, CartoonMainActivity.class));
    }


    private FrameLayout mFlCartoonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_main);
        getPermission();
    }

    private void getPermission() {
        AndPermission.with(this)
                .setPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setCallback(new PermissionListener() {
                    @Override
                    protected void onSucceed(Context context, int requestCode, @NonNull List<String> grantPermissions) {
                        initView();
                    }
                    @Override
                    protected void onFailed(@NonNull Context context, int requestCode, List<String> deniedPermissionsList, List<String> deniedDontRemindList, RationaleListener rationale) {
                        rationale.showSettingDialog(context,rationale,deniedDontRemindList != null && deniedDontRemindList.size() != 0 ? deniedDontRemindList : deniedPermissionsList);
                    }
                    @Override
                    protected void onCancel(Context context, int requestCode, List<String> deniedPermissionsList, List<String> deniedDontRemindList) {
                       finish();
                    }
                    @Override
                    protected void settingDialogCallBack(Context context, boolean isAgreement, String[] deniedDontRemindList) {
                        if (!isAgreement)finish();
                        else   initView();
                    }
                })
                .start();
    }

    private List<Fragment> fragments;
    private List<String> lists = null;
    private TabFragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
//    private TabLayout tablayout;

    public void initView() {
        fragments = new ArrayList<>();
        lists = new ArrayList<>();
        mFlCartoonMain = (FrameLayout) findViewById(R.id.fl_Cartoon_Main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        lists.add("推荐");
        fragments.add(CartoonRecommendFragment.newInstance());

        if (fragments.size() != lists.size() || lists.size() == 0){
            return;
        }

        fragmentAdapter = new TabFragmentAdapter(fragments, lists, getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setOffscreenPageLimit(fragments.size());
    }
}
