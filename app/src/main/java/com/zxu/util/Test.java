package com.zxu.util;

import com.zxu.annotation.DatabaseTable;
import com.zxu.model.JC_AccountBook;

import java.lang.annotation.Annotation;

public class Test {
    public static void main(String[] args) {
        Annotation annotation = JC_AccountBook.class.getAnnotation(DatabaseTable.class);
        System.out.printf(((DatabaseTable) annotation).tableName());
    }
}
