package com.zxu.util;

import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;
import com.zxu.model.JC_AccountBook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) {
        JC_AccountBook obj = new JC_AccountBook();
        obj.setId("123");
        obj.setName("test 1");
        obj.setUserId("uuid 123");
        getEditSql(JC_AccountBook.class, obj);
    }

    // 输出 属性名+属性值
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

}
