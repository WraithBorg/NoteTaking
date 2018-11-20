package com.zxu.util;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;
import com.zxu.model.JC_AccountBook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        System.out.println(SqlUtil.getCreateTableSql(JC_AccountBook.class));
//        System.out.println(JC_AccountBook.class.getDeclaredFields().length);
    }

    static String getCreateTableSql(Class clazz) {

        Annotation annotation = clazz.getAnnotation(DatabaseTable.class);
        String tableName = ((DatabaseTable) annotation).tableName();
        Field[] fields = clazz.getDeclaredFields();
        Set<String> sqlFragments = new HashSet<>();
        System.out.println("fields == " + fields.length);
//        for (int i = 0; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            try {
//                Field field = clazz.getDeclaredField(fields[i].getName());
//                DatabaseField dd = field.getAnnotation(DatabaseField.class);
//                String columnName = dd.columnName();
//                DataType dataType = dd.dataType();
//                String temp = columnName + " " + dataType.type();
//                if (columnName.equals("id")){
//                    temp = temp + " primary key ";
//                }else {
//                    temp = temp + " not null";
//                }
//                if (i < fields.length-1){
//                    temp = temp + ",";
//                }
//                sqlFragments.add(temp);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }

        StringBuilder finallySql = new StringBuilder("create table if not exists " + tableName + "(");
        for (String str : sqlFragments){
            finallySql.append(str);
        }
        finallySql.append(");");

        return finallySql.toString();
    }
}
