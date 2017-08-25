package com.xinaliu.inspiration.persenter.activity.impl;

import com.xinaliu.inspiration.IView.LoginActivityView;
import com.xinaliu.inspiration.entity.UserEntity;
import com.xinaliu.inspiration.model.LoginActivityModel;
import com.xinaliu.inspiration.model.impl.LoginActivityModelImpl;
import com.xinaliu.inspiration.persenter.activity.LoginActivityPersenter;

/**
 * Created by liuwei on 2017/8/25 11:10
 */

public class LoginActivityPersenterImpl implements LoginActivityPersenter {


    private LoginActivityView mLoginActivityView;
    private LoginActivityModel mLoginActivityModel;

    public LoginActivityPersenterImpl(LoginActivityView mLoginActivityView) {
        this.mLoginActivityView = mLoginActivityView;
        mLoginActivityModel = new LoginActivityModelImpl(this);
    }

    @Override
    public void login(String phoneNumber, String password) {
        mLoginActivityModel.login(phoneNumber,password,mLoginActivityView);
    }

    @Override
    public void setUserEntity(UserEntity userEntity) {
        mLoginActivityView.setUserEntity(userEntity);
    }
}
