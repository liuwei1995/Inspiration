package com.xinaliu.inspiration.db.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.xinaliu.inspiration.db.annotation.Column;
import com.xinaliu.inspiration.db.annotation.Id;
import com.xinaliu.inspiration.db.annotation.Table;
import com.xinaliu.inspiration.db.dao.BaseDao;
import com.xinaliu.inspiration.db.util.TableHelper;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/7/13 10:16
 */

public class BaseDaoImpl<T> implements BaseDao<T> {

    private static final String TAG = "BaseDaoImpl";

    private SQLiteOpenHelper dbHelper;
    private String tableName;
    private String idColumn;
    private Class<T> clazz;
    private List<Field> allFields;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl(SQLiteOpenHelper dbHelper, Class<T> clazz) {
        this.dbHelper = dbHelper;
        if (clazz == null) {
            this.clazz = ((Class<T>) ((java.lang.reflect.ParameterizedType) super
                    .getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]);
        } else {
            this.clazz = clazz;
        }

        if (this.clazz.isAnnotationPresent(Table.class)) {
            Table table = this.clazz.getAnnotation(Table.class);
            this.tableName = table.name();
        }

        // 加载所有字段
        this.allFields = TableHelper.joinFields(this.clazz.getDeclaredFields(),
                this.clazz.getSuperclass().getDeclaredFields());

        // 找到主键
        for (Field field : this.allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();
                if (TextUtils.isEmpty(columnName)){
                     this.idColumn = field.getName();
                }else
                     this.idColumn = column.name();
                break;
            }
        }
    }

    public BaseDaoImpl(SQLiteOpenHelper dbHelper) {
        this(dbHelper, null);
    }

    @Override
    public SQLiteOpenHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    public long insert(T entity) {
        return insert(entity, true);
    }

    @Override
    public long insert(T entity, boolean flag) {
        if (dbHelper == null || entity == null) return -1;
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
//            if (flag) {
//                sql = setContentValues(entity, cv, TYPE_INCREMENT,
//                        METHOD_INSERT);//// id自增
//            } else {
//                sql = setContentValues(entity, cv, TYPE_NOT_INCREMENT,
//                        METHOD_INSERT);// id需指定
//            }
            setContentValues(entity, cv);
            boolean b = cv.containsKey(this.idColumn);
            if (b)
                cv.remove(this.idColumn);
//        //1-表名
//        //2-空列的默认值
//        //3-字段和值的key/value集合
//        Long l = db.insert("user", null, cv);
            long row = db.insert(this.tableName, null, cv);
            return row;
        } catch (Exception e) {
            Log.d(this.TAG, "[insert] into DB Exception.");
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return -1;
    }

    private void setContentValues(T entity, ContentValues cv) throws IllegalAccessException {
        for (Field field : this.allFields) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);

            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if (fieldValue == null)
                continue;
//            TODO
//            if ((field.isAnnotationPresent(Id.class))) {
//                continue;
//            }
            String columnName = column.name();
            if (TextUtils.isEmpty(columnName)) {
                columnName = field.getName();
            }
            if (Date.class == field.getType()) {// 处理java.util.Date类型,update
                // 2012-06-10
                cv.put(columnName, ((Date) fieldValue).getTime());
                continue;
            }
            String value = String.valueOf(fieldValue);
            cv.put(columnName, value);
        }
    }


    @Override
    public T findById(Integer id) {
        return getEntity(id);
    }

    private T getEntity(Integer id) {
        String selection = this.idColumn + " = ?";
        List<T> list = find(null, selection, id < 0 ? new String[]{Integer.toString(id)} : null, null, null, null, null);
        if ((list != null) && (list.size() > 0)) {
            return list.get(0);
        }
        return null;
    }

    public T rawQueryT(String sql, String... selectionArgs) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
            return getTFromCursor(cursor);
        } catch (Exception e) {
            Log.e(this.TAG, "[rawQuery] from DB Exception.");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    private T getTFromCursor(Cursor cursor) throws IllegalAccessException, InstantiationException {
        while (cursor.moveToNext()) {
            T entity = this.clazz.newInstance();
            for (Field field : this.allFields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    String columnName = column.name();
                    if (TextUtils.isEmpty(columnName)) columnName = field.getName();
                    int c = cursor.getColumnIndex(columnName);
                    if (c < 0) {
                        continue;  // 如果不存则循环下个属性值ֵ
                    } else if ((Integer.TYPE == fieldType)
                            || (Integer.class == fieldType)) {
                        field.set(entity, cursor.getInt(c));
                    } else if (String.class == fieldType) {
                        field.set(entity, cursor.getString(c));
                    } else if ((Long.TYPE == fieldType)
                            || (Long.class == fieldType)) {
                        field.set(entity, cursor.getLong(c));
                    } else if ((Float.TYPE == fieldType)
                            || (Float.class == fieldType)) {
                        field.set(entity, cursor.getFloat(c));
                    } else if ((Short.TYPE == fieldType)
                            || (Short.class == fieldType)) {
                        field.set(entity, cursor.getShort(c));
                    } else if ((Double.TYPE == fieldType)
                            || (Double.class == fieldType)) {
                        field.set(entity, cursor.getDouble(c));
                    } else if (Date.class == fieldType) {// 处理java.util.Date类型,update2012-06-10
                        Date date = new Date();
                        date.setTime(cursor.getLong(c));
                        field.set(entity, date);
                    } else if (Blob.class == fieldType) {
                        field.set(entity, cursor.getBlob(c));
                    } else if (Character.TYPE == fieldType) {
                        String fieldValue = cursor.getString(c);
                        if ((fieldValue != null) && (fieldValue.length() > 0)) {
                            field.set(entity, fieldValue.charAt(0));
                        }
                    }
                }
            }
            return entity;
        }
        return null;
    }

    @Override
    public List<T> rawQuery(String sql, String... selectionArgs) {
        List<T> list = new ArrayList<T>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);

            getListFromCursor(list, cursor);
        } catch (Exception e) {
            Log.e(this.TAG, "[rawQuery] from DB Exception.");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return list;
    }

    @Override
    public List<T> find() {
        return find(null, null, null, null, null, null, null);
    }

    @Override
    public List<T> find(String orderBy) {
        return find(null, null, null, null, null, orderBy, null);
    }

    @Override
    public List<T> find(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        List<T> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbHelper.getReadableDatabase();
            cursor = db.query(this.tableName, columns, selection,
                    selectionArgs, groupBy, having, orderBy, limit);
            getListFromCursor(list, cursor);
        } catch (Exception e) {
            Log.e(this.TAG, "[find] from DB Exception");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    private void getListFromCursor(List<T> list, Cursor cursor) throws IllegalAccessException, InstantiationException {
        while (cursor.moveToNext()) {
            T entity = this.clazz.newInstance();
            for (Field field : this.allFields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);

                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    String columnName = column.name();
                    if (TextUtils.isEmpty(columnName)) columnName = field.getName();

                    int c = cursor.getColumnIndex(columnName);
                    if (c < 0) {
                        continue;  // 如果不存则循环下个属性值ֵ
                    } else if ((Integer.TYPE == fieldType)
                            || (Integer.class == fieldType)) {
                        field.set(entity, cursor.getInt(c));
                    } else if (String.class == fieldType) {
                        field.set(entity, cursor.getString(c));
                    } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        field.set(entity, cursor.getLong(c));
                    } else if ((Float.TYPE == fieldType)
                            || (Float.class == fieldType)) {
                        field.set(entity, cursor.getFloat(c));
                    } else if ((Short.TYPE == fieldType)
                            || (Short.class == fieldType)) {
                        field.set(entity, cursor.getShort(c));
                    } else if ((Double.TYPE == fieldType)
                            || (Double.class == fieldType)) {
                        field.set(entity, cursor.getDouble(c));
                    } else if (Date.class == fieldType) {// 处理java.util.Date类型,update2012-06-10
                        Date date = new Date();
                        date.setTime(cursor.getLong(c));
                        field.set(entity, date);
                    } else if (Blob.class == fieldType) {
                        field.set(entity, cursor.getBlob(c));
                    } else if (Character.TYPE == fieldType) {
                        String fieldValue = cursor.getString(c);
                        if ((fieldValue != null) && (fieldValue.length() > 0)) {
                            field.set(entity, fieldValue.charAt(0));
                        }
                    }
                }
            }
            list.add(entity);
        }
    }

    @Override
    public boolean isExist(String sql, String... selectionArgs) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
            if (cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "[isExist] from DB Exception.");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 将查询的结果保存为名值对map.
     *
     * @param sql
     *            查询sql
     * @param selectionArgs
     *            参数值
     * @return 返回的Map中的key全部是小写形式.
     */
    @Override
    public List<Map<String, String>> query2MapList(String sql,
                                                   String[] selectionArgs) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        try {
            db = this.dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (String columnName : cursor.getColumnNames()) {
                    int c = cursor.getColumnIndex(columnName);
                    if (c < 0) {
                        continue; // 如果不存在循环下个属性值ֵ
                    } else {
                        map.put(columnName.toLowerCase(), cursor.getString(c));
                    }
                }
                retList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return retList;
    }


    public int delete(int id) {
        SQLiteDatabase db = null;
        int delete = 0;
        try {
            db = this.dbHelper.getWritableDatabase();
            String where = this.idColumn + " = ?";
            String[] whereValue = {Integer.toString(id)};

            delete = db.delete(this.tableName, where, whereValue);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (db != null)
            db.close();
        }
        return delete;
    }

    @Override
    public void delete(Integer... ids) {
        if (ids != null && ids.length > 0) {
            SQLiteDatabase db = null;
            try {
                StringBuffer sb = new StringBuffer();
                for (Integer id : ids) {
                    sb.append('?').append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                db = this.dbHelper.getWritableDatabase();
                String sql = "delete from " + this.tableName + " where "
                        + this.idColumn + " in (" + sb.toString() + ")";

                db.execSQL(sql, ids);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if (db != null)
                db.close();
            }
        }
    }

    @Override
    public boolean deleteAll() {
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            String sql = "delete from " + this.tableName;
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return false;
    }

    @Override
    public boolean deleteTable() {
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            String sql = "drop table if exists " + this.tableName;
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return false;
    }

    @Override
    public void updateExecSQL(String sql) {
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    @Override
    public int update(String[] upColumnName, String[] upValue, String[] columnName, String... whereValue) {
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            String w = "";
            for (int i = 0; i < columnName.length; i++) {
                w += " " + columnName[i] + "= ? AND";
            }
            w = w.substring(0, w.length() - 3);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < upColumnName.length; i++) {
                cv.put(upColumnName[i], upValue[i]);
            }
            return db.update(this.tableName, cv, w, whereValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return -1;
    }

    @Override
    public int update(T entity) {
        if (entity == null)return -1;
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            setContentValues(entity, cv);

            boolean b = cv.containsKey(this.idColumn);
            String where = this.idColumn + " = ?";
            int id = -1;
            if (b){
                id = Integer.parseInt(cv.get(this.idColumn).toString());
                cv.remove(this.idColumn);
            }
            if (id == -1)return -1;
            String[] whereValue = {Integer.toString(id)};
            int update = db.update(this.tableName, cv, where, whereValue);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return -1;
    }

    /**
     * 封装执行sql代码.
     *
     * @param sql
     * @param selectionArgs
     */
    public void execSql(String sql, Object ...selectionArgs) {
        SQLiteDatabase db = null;
        try {
            db = this.dbHelper.getWritableDatabase();
            if (selectionArgs == null) {
                db.execSQL(sql);
            } else {
                db.execSQL(sql, selectionArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}
