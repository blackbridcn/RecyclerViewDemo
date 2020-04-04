package org.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * File: Permisssin.java
 * Author: yuzhuzhang
 * Create: 2020/4/4 5:02 PM
 * Description: TODO
 * -----------------------------------------------------------------
 * 2020/4/4 : Create Permisssin.java (yuzhuzhang);
 * -----------------------------------------------------------------
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String[] value();

    int requestCode() default -1;
}
