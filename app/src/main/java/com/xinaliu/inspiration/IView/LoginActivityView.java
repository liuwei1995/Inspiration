package com.xinaliu.inspiration.IView;

import com.xinaliu.inspiration.entity.UserEntity;
import com.xinaliu.inspiration.model.ModelHttpCallback;

/**
 * Created by liuwei on 2017/8/25 11:11
 */

public interface LoginActivityView extends ModelHttpCallback {

    void setUserEntity(UserEntity userEntity);

}
