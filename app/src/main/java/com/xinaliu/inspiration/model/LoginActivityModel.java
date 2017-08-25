package com.xinaliu.inspiration.model;

/**
 * 登录
 * Created by liuwei on 2017/8/25 11:12
 */

public interface LoginActivityModel {

    /**
     *
     * @param phoneNumber 手机号码
     * @param password 密码
     * @param modelHttpCallback 回调给界面
     */
    void login(String  phoneNumber,String password,ModelHttpCallback modelHttpCallback);

}
