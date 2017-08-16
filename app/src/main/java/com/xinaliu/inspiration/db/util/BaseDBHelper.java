package com.xinaliu.inspiration.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liuwei on 2017/7/13 13:34
 */

public class BaseDBHelper extends LSQLiteOpenHelper{

    public BaseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Class<?>... modelClasses) {
        super(context, name, factory, version, modelClasses);
    }

    public BaseDBHelper(Context context,Class<?>... modelClasses) {
        super(context, DBNAME, null, DBVERSION, modelClasses);
    }

}
