package com.xinaliu.inspiration.model.impl;

import com.xinaliu.inspiration.entity.UserEntity;
import com.xinaliu.inspiration.model.LoginActivityModel;
import com.xinaliu.inspiration.model.ModelHttpCallback;
import com.xinaliu.inspiration.persenter.activity.LoginActivityPersenter;

/**
 * Created by liuwei on 2017/8/25 11:12
 */

public class LoginActivityModelImpl implements LoginActivityModel {

    private LoginActivityPersenter mLoginActivityPersenter;

    public LoginActivityModelImpl(LoginActivityPersenter mLoginActivityPersenter) {
        this.mLoginActivityPersenter = mLoginActivityPersenter;
    }

    @Override
    public void login(String phoneNumber, String password,ModelHttpCallback modelHttpCallback) {
        modelHttpCallback.startRequest();
        modelHttpCallback.endRequest();
        if ("123".equals(phoneNumber) && "123".equals(password)){
            mLoginActivityPersenter.setUserEntity(new UserEntity("123","123"));
        }else {
            modelHttpCallback.resultError(1,"用户名或密码错误");
        }
    }
}
