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
 * 忘记密码
 * Created by liuwei on 2017/9/4 13:25
 */

public class ResetPasswordForgetFragment extends BaseNewFragment implements View.OnClickListener {

    private View view;
    /**
     * 手机号或邮箱
     */
    private EditText mEtAccount;
    /**
     * 下一步
     */
    private Button mBtnNextStep;

    public static ResetPasswordForgetFragment newInstance() {

        Bundle args = new Bundle();

        ResetPasswordForgetFragment fragment = new ResetPasswordForgetFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_reset_password_forget, null);
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
            f.changeActivityTitle("忘记密码");
        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Object o  = mContext;
        if (mContext instanceof FragmentPopBackStack){
            FragmentPopBackStack f = (FragmentPopBackStack) o;
            f.changeActivityTitle("忘记密码");
        }
    }

    public void initView(View view) {

        mEtAccount = (EditText) view.findViewById(R.id.etNewPassword);
        mBtnNextStep = (Button) view.findViewById(R.id.btnSubmit);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                final FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out);
                transaction.add(R.id.fragment,ResetPasswordResetFragment.newInstance());
                transaction.hide(this);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
