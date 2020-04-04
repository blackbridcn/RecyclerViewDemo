package org.aop.utils;

/**
 * File: CommUtils.java
 * Author: yuzhuzhang
 * Create: 2020/4/4 4:40 PM
 * Description: TODO
 * -----------------------------------------------------------------
 * 2020/4/4 : Create CommUtils.java (yuzhuzhang);
 * -----------------------------------------------------------------
 */
public class CommUtils {

    private static final int ns = 1000*1000;

    public static String buildLogMessage(String methodName, long methodDuration) {

        if (methodDuration > 10 * ns) {
            return String.format("%s() take %d ms", methodName, methodDuration / ns);
        } else if (methodDuration > ns) {
            return String.format("%s() take %dms %dns", methodName, methodDuration / ns,
                    methodDuration % ns);
        } else {
            return String.format("%s() take %dns", methodName, methodDuration % ns);
        }
    }
}
