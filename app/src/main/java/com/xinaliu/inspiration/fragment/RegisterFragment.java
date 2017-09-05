package com.xinaliu.inspiration.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xinaliu.inspiration.I.FragmentPopBackStack;
import com.xinaliu.inspiration.R;

/**
 * 注册Fragment
 * Created by liuwei on 2017/9/5 09:36
 */

public class RegisterFragment extends BaseNewFragment implements View.OnClickListener {

    private View view;
    /**
     * 手机号或邮箱
     */
    private EditText mEtPhoneNumber;
    /**
     * 输入密码
     */
    private EditText mEtPassword;
    /**
     * 注册
     */
    private Button mBtnRegister;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_register, null);
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
            f.changeActivityTitle("注册");
        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Object o  = mContext;
        if (mContext instanceof FragmentPopBackStack){
            FragmentPopBackStack f = (FragmentPopBackStack) o;
            f.changeActivityTitle("注册");
        }
    }


    public void initView(View view) {
        mEtPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        mEtPassword = (EditText) view.findViewById(R.id.etPassword);
        mBtnRegister = (Button) view.findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                final FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out);
                transaction.add(R.id.fragment,RegisterFillVerificationCodeFragment.newInstance());
                transaction.hide(this);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
