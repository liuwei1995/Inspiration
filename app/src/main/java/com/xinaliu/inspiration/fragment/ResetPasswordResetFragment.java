package com.xinaliu.inspiration.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinaliu.inspiration.I.FragmentPopBackStack;
import com.xinaliu.inspiration.R;

/**
 * 重置密码
 * Created by liuwei on 2017/9/4 13:24
 */

public class ResetPasswordResetFragment extends BaseNewFragment {

    private View view;

    public static ResetPasswordResetFragment newInstance() {

        Bundle args = new Bundle();

        ResetPasswordResetFragment fragment = new ResetPasswordResetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_reset_password_reset, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Object o  = mContext;
        if (mContext instanceof FragmentPopBackStack){
            FragmentPopBackStack f = (FragmentPopBackStack) o;
            f.changeActivityTitle("重置密码");
        }
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        Object o  = mContext;
        if (mContext instanceof FragmentPopBackStack){
            FragmentPopBackStack f = (FragmentPopBackStack) o;
            f.changeActivityTitle("重置密码");
        }
    }

    public void initView(View view) {
    }
}
