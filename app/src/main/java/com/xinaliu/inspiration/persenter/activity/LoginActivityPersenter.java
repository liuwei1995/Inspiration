package com.xinaliu.inspiration.persenter.activity;

import com.xinaliu.inspiration.entity.UserEntity;

/**
 * Created by liuwei on 2017/8/25 11:08
 */

public interface LoginActivityPersenter {

    void login(String phoneNumber, String password);

    void setUserEntity(UserEntity userEntity);

}
