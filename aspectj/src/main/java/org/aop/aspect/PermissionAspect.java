package org.aop.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * File: PermissionAspect.java
 * Author: yuzhuzhang
 * Create: 2020/4/4 5:03 PM
 * Description: TODO
 * -----------------------------------------------------------------
 * 2020/4/4 : Create PermissionAspect.java (yuzhuzhang);
 * -----------------------------------------------------------------
 */
public class PermissionAspect {


    private static final String METHOD_PERMISSION="execution(@org.aop.annotation.Permission * *(..))";
    @Pointcut(METHOD_PERMISSION)
    public void MethodPermissinRequset() {


    }
}
