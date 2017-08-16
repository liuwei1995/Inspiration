package com.xinaliu.inspiration.db.dao;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    SQLiteOpenHelper getDbHelper();

    long insert(T entity);

    long insert(T entity, boolean flag);


    void delete(Integer... ids);

    boolean deleteAll();

    boolean deleteTable();

    int update(T entity);

    void updateExecSQL(String sql);

    int update(String[] upColumnName, String[] upValue, String[] columnName, String... whereValue);

    T findById(Integer id);

    List<T> rawQuery(String sql, String... selectionArgs);

    List<T> find();

    List<T> find(String orderBy);


    List<T> find(String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy, String limit);

    boolean isExist(String sql, String... selectionArgs);

    List<Map<String, String>> query2MapList(String sql, String[] selectionArgs);

    void execSql(String sql, Object... selectionArgs);

}