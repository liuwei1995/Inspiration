package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.TabFragmentAdapter;
import com.xinaliu.inspiration.fragment.CartoonRecommendFragment;

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
        initView();
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
