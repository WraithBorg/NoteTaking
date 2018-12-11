package com.zxu.base.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.application.GaiaApplication;
import com.zxu.helpers.DatabaseHelper;
import com.zxu.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件查询
 *
 * @param <T>
 */
public class ZWhere<T> {
    public Class<T> entityClass;
    StringBuilder sqlBuilser;
    private GaiaApplication context;

    //
    public ZWhere(GaiaApplication context, Class<T> entityClass, String tableName) {
        this.context = context;
        this.entityClass = entityClass;
        sqlBuilser = new StringBuilder(" select * from " + tableName + " where ");
    }

    //
    public List<T> query() {
        return getList(sqlBuilser.toString());
    }

    //
    public ZWhere<T> eq(String columnName, String value) {
        sqlBuilser.append(" " + columnName + " = '" + value + "' ");
        return this;
    }

    //
    public ZWhere<T> between(String columnName, String low, String high) {
        sqlBuilser.append(" " + columnName + " >= '" + low + "' AND " + columnName + " <= '" + high + "' ");
        return this;
    }

    //
    public ZWhere<T> and() {
        sqlBuilser.append(" and ");
        return this;
    }

    //
    public List<T> getList(String sql) {
        try {
            T bean;
            DatabaseHelper helper = context.getDatabaseHelper();
            SQLiteDatabase database = helper.open();
            List<T> list = new ArrayList<>();
            Cursor cursor = database.rawQuery(sql, null);
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
}
