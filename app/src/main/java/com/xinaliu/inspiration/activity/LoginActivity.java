package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinaliu.inspiration.IView.LoginActivityView;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.entity.UserEntity;
import com.xinaliu.inspiration.persenter.activity.LoginActivityPersenter;
import com.xinaliu.inspiration.persenter.activity.impl.LoginActivityPersenterImpl;
import com.xinaliu.inspiration.util.ToastUtil;


public class LoginActivity extends BaseNewActivity implements LoginActivityView{

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    /**
     * 手机号或邮箱
     */
    private EditText mEtPhoneNumber;
    /**
     * 输入密码
     */
    private EditText mEtPassword;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 忘记密码
     */
    private TextView mTvForgetPassword;
    /**
     * 手机注册
     */
    private TextView mTvRegister;

    private LoginActivityPersenter mLoginActivityPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mLoginActivityPersenter = new LoginActivityPersenterImpl(this);
    }

    private void initView() {
        mEtPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
        mTvForgetPassword.setOnClickListener(this);
        mTvRegister = (TextView) findViewById(R.id.tvRegister);
        mTvRegister.setOnClickListener(this);
        mEtPhoneNumber.setText("123");
        mEtPassword.setText("123");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                btnLogin();
                break;
            case R.id.tvForgetPassword:
                break;
            case R.id.tvRegister:
                break;
        }
    }

    private void btnLogin() {
        String phoneNumber = mEtPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)){
            ToastUtil.toastSome(this,"用户名不能为空");
            return;
        }
        String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            ToastUtil.toastSome(this,"密码不能为空");
            return;
        }
        mLoginActivityPersenter.login(phoneNumber,password);
    }

    @Override
    public void startRequest() {

    }

    @Override
    public void endRequest() {

    }

    @Override
    public void connectionFailed(String failedMessge) {
        ToastUtil.toastSome(this,failedMessge);
    }


    @Override
    public void resultError(int code, String error) {
        ToastUtil.toastSome(this,error);
    }

    @Override
    public void setUserEntity(UserEntity userEntity) {
        if (userEntity != null && !TextUtils.isEmpty(userEntity.getPhoneNumber())){
            ToastUtil.toastSome(this,"登录成功");
//            WebViewNewActivity.startActivity(this,"http://www.shenmanhua.com/asonline/1ce.html");
            CartoonMainActivity.startActivity(this);
        }else {
            ToastUtil.toastSome(this,"登录失败");
        }
    }
}
