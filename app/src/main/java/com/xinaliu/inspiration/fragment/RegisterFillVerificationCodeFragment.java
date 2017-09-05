package com.xinaliu.inspiration.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinaliu.inspiration.I.FragmentPopBackStack;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.util.ToastUtil;

/**
 * 注册填写验证码Fragment
 * Created by liuwei on 2017/9/5 09:40
 */

public class RegisterFillVerificationCodeFragment extends BaseNewFragment implements View.OnClickListener {


    public static RegisterFillVerificationCodeFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFillVerificationCodeFragment fragment = new RegisterFillVerificationCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private View view;
    /**
     * 输入密码
     */
    private EditText mEtPassword;
    /**
     * 发送验证码
     */
    private TextView mTvSendCode;
    /**
     * 下一步
     */
    private Button mBtnNextStep;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_register_fill_verification_code, null);
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
            f.changeActivityTitle("请填写验证码");
        }
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        Object o  = mContext;
        if (mContext instanceof FragmentPopBackStack){
            FragmentPopBackStack f = (FragmentPopBackStack) o;
            f.changeActivityTitle("请填写验证码");
        }
    }

    public void initView(View view) {
        mEtPassword = (EditText) view.findViewById(R.id.etPassword);
        mTvSendCode = (TextView) view.findViewById(R.id.tvSendCode);
        mBtnNextStep = (Button) view.findViewById(R.id.btnNextStep);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                ToastUtil.toastSome(mContext,"下一步");
                break;
        }
    }
}
