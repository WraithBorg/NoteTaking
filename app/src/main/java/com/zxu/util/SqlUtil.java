package com.zxu.util;

import android.util.Log;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * sql 生成工具类
 */
public class SqlUtil {
    /**
     * 获取创建表的sql
     * @param clazz
     * @return
     */
    public static String getCreateTableSql(Class clazz) {

        Annotation annotation = clazz.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        Field[] fields = clazz.getDeclaredFields();
        List<String> sqlFragments = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Field field = clazz.getDeclaredField(fields[i].getName());
                DatabaseField dd = field.getAnnotation(DatabaseField.class);
                if (dd == null){
                    continue;
                }
                String columnName = dd.columnName();
                DataType dataType = dd.dataType();
                String temp = columnName + " " + dataType.type();
                if (columnName.equals("id")){
                    temp = temp + " primary key ";
                }else {
                    temp = temp + " not null";
                }
                temp = temp + ",";
                sqlFragments.add(temp);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        StringBuilder finallySql = new StringBuilder("create table if not exists " + tableName + "(");
        for (String str : sqlFragments){
            finallySql.append(str);
        }
        finallySql.deleteCharAt(finallySql.length() - 1);
        finallySql.append(");");
        // sql 计入日志
        return finallySql.toString();
    }
}
