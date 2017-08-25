package com.xinaliu.inspiration.model;

/**
 * Created by liuwei on 2017/8/25 11:18
 */

public interface ModelHttpCallback {

    void startRequest();

    void endRequest();

    void connectionFailed(String failedMessge);

    void resultError(int code,String errorMessge);

}
