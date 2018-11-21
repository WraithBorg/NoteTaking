package com.zxu.util;

import android.database.Cursor;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * sql 生成工具类
 */
public class SqlUtil {
    /**
     * 获取创建表的sql
     *
     * @param clazz
     * @return
     */
    public static String getCreateTableSql(Class clazz) {
        Annotation annotation = clazz.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        Field[] fields = clazz.getDeclaredFields();
        List<String> sqlFragments = new ArrayList<>();
        for (Field field1 : fields) {
            field1.setAccessible(true);
            try {
                Field field = clazz.getDeclaredField(field1.getName());
                DatabaseField dd = field.getAnnotation(DatabaseField.class);
                if (dd == null) {
                    continue;
                }
                String columnName = dd.columnName();
                DataType dataType = dd.dataType();
                String temp = columnName + " " + dataType.type();
                if (dd.id()) {
                    temp = temp + " primary key ";
                } else {
                    temp = temp + " not null";
                }
                temp = temp + ",";
                sqlFragments.add(temp);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        StringBuilder finallySql = new StringBuilder("create table if not exists " + tableName + "(");
        for (String str : sqlFragments) {
            finallySql.append(str);
        }
        finallySql.deleteCharAt(finallySql.length() - 1);
        finallySql.append(");");
        // sql 计入日志
        return finallySql.toString();
    }
    /**
     * 获取insert语句
     */
    public static final <T> String getAddSql(Class<T> clazz, Object obj) {
        Annotation annotation = clazz.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        StringBuilder colName = new StringBuilder();
        StringBuilder colValue = new StringBuilder();
        for (Field field : fields) {
            DatabaseField dd = field.getAnnotation(DatabaseField.class);
            if (dd == null) {
                continue;
            }
            try {
                String name = field.getName();
                Object value = field.get(obj);
                colName.append(name).append(",");
                if (value != null) {
                    colValue.append("'").append(value).append("'").append(",");
                } else {
                    colValue.append("''").append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        colName.deleteCharAt(colName.length() - 1);
        colValue.deleteCharAt(colValue.length() - 1);
        StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
        sql.append(colName).append(") values (").append(colValue).append(");");
        return sql.toString();
    }

    /**
     * 获取Update语句
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     */
    public static final <T> String getEditSql(Class<T> clazz, Object obj) {
        Annotation annotation = clazz.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        StringBuilder colSql = new StringBuilder();
        StringBuilder whrSql = new StringBuilder();
        for (Field field : fields) {
            DatabaseField dd = field.getAnnotation(DatabaseField.class);
            if (dd == null) {
                continue;
            }
            try {
                String name = field.getName();
                Object value = field.get(obj);
                if (value != null) {
                    if (dd.id()) {
                        whrSql.append(" where id = ").append("'").append(value).append("'");
                    } else {
                        colSql.append(name).append("=").append("'").append(value).append("'").append(",");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        colSql.deleteCharAt(colSql.length() - 1);
        StringBuilder sql = new StringBuilder("update " + tableName + " set ");
        sql.append(colSql).append(whrSql).append(";");
        return sql.toString();
    }

    public static final <T>T cursorToEntity(Class<T> clazz,Cursor cursor){
        T bean;
        try {
            bean = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Field.setAccessible(fields, true);
            for (Field field : fields) {
                DatabaseField dd = field.getAnnotation(DatabaseField.class);
                if (dd == null) {
                    continue;
                }
                int columnIndex = cursor.getColumnIndex(field.getName());
                String cursorString = cursor.getString(columnIndex);
                field.set(bean,cursorString);
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
