package com.zxu.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface DatabaseField {
    String columnName() default "";

    DataType dataType() default DataType.UNKNOWN;

    boolean id() default false;

    boolean generatedId() default false;
}
