package com.train.orm.orm.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import  com.train.orm.orm.helper.BaseOrmColumns;
/**
 * 名称：Id.java
 * 描述：主键
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Id {
    int type() default BaseOrmColumns.TYPE_INCREMENT;
}
