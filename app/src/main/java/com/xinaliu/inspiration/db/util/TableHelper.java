package com.xinaliu.inspiration.db.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.xinaliu.inspiration.db.annotation.Column;
import com.xinaliu.inspiration.db.annotation.Id;
import com.xinaliu.inspiration.db.annotation.Table;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/7/13 10:21
 */

public class TableHelper {

    private static final String TAG = "TableHelper";

    public static void createTablesByClasses(SQLiteDatabase db,Class<?> ...clazzs) {
        if (clazzs != null)
        for (Class<?> clazz : clazzs)
            if (clazz != null)
            createTable(db, clazz);
    }


    public static void dropTablesByClasses(SQLiteDatabase db,Class<?> ...clazzs) {
        if (clazzs != null)
        for (Class<?> clazz : clazzs)
            if (clazz != null)
            dropTable(db, clazz);
    }
    public static void updateTablesByClasses(SQLiteDatabase db,Class<?> ...clazzs) {
        if (clazzs != null)
        for (Class<?> clazz : clazzs)
            if (clazz != null)
            updateTable(db, clazz);
    }

    /**
     * 数据库升级的时候会把以前的表复制到新的版本里面来
     * @param db
     * @param clazz
     */
    private static synchronized void updateTable(SQLiteDatabase db, Class<?> clazz) {
        String tableName = "";

        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }
////		现在
//		Map<String, String> newColumnNames = getNewColumnNames(clazz);
//		旧的
        String oldColumnNames = getOldColumnNames(db,tableName,clazz);
        if (oldColumnNames == null)
        {
            String dropString = "drop table if exists " + tableName;
            db.execSQL(dropString);
            return;
        }
        try
        {
            db.beginTransaction();
            String reOldColumn = oldColumnNames.substring(0, oldColumnNames.length() - 1);
// rename the table
            String tempTable = tableName + "_texp_temptable_"+System.currentTimeMillis();
            String sql = "alter table " + tableName + " rename to " + tempTable;
            db.execSQL( sql );

// drop the oldtable
            String dropString = "drop table if exists " + tableName;
            db.execSQL(dropString);
// create table
            createTable(db,clazz);


// load data
            String ins = "insert into " + tableName + " (" + reOldColumn + ") " + "select " + reOldColumn + "" + " " + " from "+ tempTable;
            db.execSQL(ins);
            // drop the tempTable
            db.execSQL("drop table if exists " + tempTable);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
// TODO: handle exception
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
    }

    /***
     * 获取升级前表中的字段
     * @param db d
     * @param tableName t
     * @return s
     */
    protected static String getOldColumnNames(SQLiteDatabase db, String tableName,Class<?> now) {
        StringBuffer sb = null;
        Cursor c = null;
        try
        {
            Map<String, String> newColumnNames = getNewColumnNames(now);
            if (newColumnNames.isEmpty())return null;
            c = db.rawQuery( "PRAGMA table_info(" + tableName + ")", null );
            if (null != c)
            {
                int columnIndex = c.getColumnIndex( "name" );
                if (-1 == columnIndex)
                {
                    return null;
                }
                int index = 0;
                sb = new StringBuffer(c.getCount());
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                {
                    String columnName = c.getString(columnIndex);
                    if (!newColumnNames.containsKey(columnName)){
                        continue;
                    }
                    sb.append(columnName);
                    sb.append( "," );
                    index++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (c != null)
            {
                c.close();
            }
        }
        return sb.toString();
    }


    public static Map<String,String> getNewColumnNames(Class<?> clazz){
        Map<String,String> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        List<Field> allFields = TableHelper
                .joinFields(clazz.getDeclaredFields(), clazz.getSuperclass()
                        .getDeclaredFields());
        for (Field field : allFields) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = (Column) field.getAnnotation(Column.class);
            if (TextUtils.isEmpty(column.name())){
                sb.append(field.getName());
                map.put(field.getName(),field.getName());
            }else {
                map.put(column.name(),column.name());
                sb.append(column.name());
            }
            sb.append(",");
        }
        return map;
    }

    public static <T> void dropTable(SQLiteDatabase db, Class<T> clazz) {
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }
        String sql = "DROP TABLE IF EXISTS " + tableName;
        Log.d(TAG, "dropTable[" + tableName + "]:" + sql);
        db.execSQL(sql);
    }

    /**
     CREATE TABLE COMPANY(
     ID INT PRIMARY KEY     NOT NULL,
     NAME           TEXT    NOT NULL,
     AGE            INT     NOT NULL,
     ADDRESS        CHAR(50),
     SALARY         REAL
     );
     * @param db
     * @param clazz
     */
    private static <T> void createTable(SQLiteDatabase db, Class<?> clazz) {
        if(clazz == null || db == null)return;
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            tableName = table.name();
        }else {
            tableName = clazz.getSimpleName();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE  IF NOT EXISTS ").append(tableName).append(" (");

        List<Field> allFields = TableHelper
                .joinFields(clazz.getDeclaredFields(), clazz.getSuperclass()
                        .getDeclaredFields());
        for (Field field : allFields) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);

            String columnName = column.name();
            if (TextUtils.isEmpty(columnName)){
                columnName = field.getName();
            }

            if (field.isAnnotationPresent(Id.class)){
                sb.append(" "+columnName+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
            }else {
                String columnType = "";
                if (TextUtils.isEmpty(column.type())){
                    columnType = getColumnType(field.getType());
                }else {
                    columnType = column.type();
                }

                sb.append(""+columnName+" "+columnType+",");
            }
        }
        sb.delete(sb.length() - 1,sb.length()).append(")");


        //1.创建数据库的语句
        //构造建表语句
//        String creaTTable = "create table user (_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,name varchar,age int)";
        db.execSQL(sb.toString());
    }

    public static List<Field> joinFields(Field[] fields1, Field[] fields2) {
        Map<String, Field> map = new LinkedHashMap<String, Field>();
        for (Field field : fields1) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = (Column) field.getAnnotation(Column.class);
            map.put(column.name(), field);
        }
        for (Field field : fields2) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = (Column) field.getAnnotation(Column.class);
            if (!map.containsKey(column.name())) {
                map.put(column.name(), field);
            }
        }
        List<Field> list = new ArrayList<>();
        for (String key : map.keySet()) {
            Field tempField = map.get(key);
            if (tempField.isAnnotationPresent(Id.class)) {
                list.add(0, tempField);
            } else {
                list.add(tempField);
            }
        }
        return list;
    }


    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String BIGINT = "BIGINT";
    public static final String FLOAT = "FLOAT";
    public static final String INT = "INT";
    public static final String DOUBLE = "DOUBLE";
    public static final String BLOB = "BLOB";
    private static String getColumnType(Class<?> fieldType) {
        if (String.class == fieldType) {
            return "TEXT";
        }
        if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return "INTEGER";
        }
        if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return "BIGINT";
        }
        if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return "FLOAT";
        }
        if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            return "INT";
        }
        if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return "DOUBLE";
        }
        if (Blob.class == fieldType) {
            return "BLOB";
        }

        return "TEXT";
    }


}
