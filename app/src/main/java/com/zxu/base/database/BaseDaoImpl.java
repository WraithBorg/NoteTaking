package com.zxu.base.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.annotation.DatabaseTable;
import com.zxu.helpers.DatabaseHelper;
import com.zxu.util.SqlUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoImpl<T> extends AbstractService {
    public Class<T> entityClass;

    public BaseDaoImpl() {
        Class c = this.getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class) parameterizedType[0];
        }
    }

    /**
     * get All
     *
     * @return
     */
    public List<T> getList() {
        try {
            T bean;
            Annotation annotation = entityClass.getAnnotation(DatabaseTable.class);
            String tableName = ((DatabaseTable) annotation).tableName();
            DatabaseHelper helper = getContext().getDatabaseHelper();
            SQLiteDatabase database = helper.open();
            List<T> list = new ArrayList<>();
            Cursor cursor = database.rawQuery("select * from " + tableName + ";", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bean = SqlUtil.cursorToEntity(entityClass, cursor);
                list.add(bean);
                cursor.moveToNext();
            }
            cursor.close();
            helper.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增
     *
     * @param t
     */
    public void add(T t) {
        DatabaseHelper helper = getContext().getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            String sql = SqlUtil.getAddSql(entityClass, t);
            database.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改
     *
     * @param t
     */
    public void modify(T t) {
        DatabaseHelper helper = getContext().getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            String sql = SqlUtil.getEditSql(entityClass, t);
            database.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void delete(String id) {
        Annotation annotation = entityClass.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        DatabaseHelper helper = getContext().getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(tableName, "id=?", new String[]{String.valueOf(id)});

    }

    //
    public ZWhere<T> where() {
        Annotation annotation = entityClass.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        return new ZWhere<T>(getContext(), entityClass,tableName);
    }
}
