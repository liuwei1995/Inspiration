package com.xinaliu.inspiration.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuwei on 2017/7/13 10:19
 */

public class LSQLiteOpenHelper extends SQLiteOpenHelper {
    /***
     * 默认的数据库名字
     */
    public static final String DBNAME = "healthy.db";// 数据库名
    public static final int DBVERSION = 1;

    private  Class<?>[] modelClasses;

    public LSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,Class<?> ...modelClasses) {
        super(context, name, factory, version);
        this.modelClasses = modelClasses;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        TableHelper.createTablesByClasses(db,modelClasses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion)
        TableHelper.updateTablesByClasses(db,this.modelClasses);
    }
}
