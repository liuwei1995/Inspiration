package com.xinaliu.inspiration.db.util;

import android.content.Context;


/**
 * Created by liuwei on 2017/7/13 13:34
 */

public class BaseChildDBHelper<T> extends BaseDBHelper {

    /**
     *
     * @param context
     * @param modelClass 表对应的类  添加注解{@link Table#name()}
     */
    public BaseChildDBHelper(Context context, Class<T> modelClass) {
        super(context, DBNAME, null, DBVERSION, modelClass);
    }

    public BaseChildDBHelper(Context context, String name, Class<T> modelClass) {
        super(context, name, null, DBVERSION, modelClass);
    }

    public BaseChildDBHelper(Context context, int version, Class<T> modelClass) {
        super(context, DBNAME, null, version, modelClass);
    }

    public BaseChildDBHelper(Context context, String name, int version, Class<T> modelClass) {
        super(context, name, null, version, modelClass);
    }

}
