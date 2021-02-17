package org.aop.aspect;

/**
 * File: TraceAspect.java
 * Author: yuzhuzhang
 * Create: 2020/4/4 3:10 PM
 * Description: TODO
 * -----------------------------------------------------------------
 * 2020/4/4 : Create TraceAspect.java (yuzhuzhang);
 * -----------------------------------------------------------------
 */
//@Aspect
public class TraceAspect {

    private static final String METHOD_POINTCUT = "execution(@org.aop.annotation.Trace * *(..))";
    private static final String CONSTRUCTOR_POINTCUT = "execution(@org.aop.annotation.Trace *.new(..))";


    //@Pointcut(METHOD_POINTCUT)
    public void methodTrace() {

    }

    //@Pointcut(CONSTRUCTOR_POINTCUT)
    public void constructorTrace() {

    }

    //@Around("methodTrace()||constructorTrace()")
/*
    public Object traceMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Trace trace = method.getAnnotation(Trace.class);
        if (trace != null && !trace.enable()) {
            return joinPoint.proceed();
        }
        SnapShot shot = new SnapShot();
        shot.start();
        Object result = joinPoint.proceed();
        String clazzName = methodSignature.getDeclaringType().getSimpleName();
        shot.stop();
        */
/*if (StringUtils.isEmpty(clazzName)) {
            clazzName = "traceMethod clazzName blank ";
        }*//*

        Log.e("TAG", "--------->  traceMethod: "+ CommUtils.buildLogMessage(methodSignature.getName(), shot.getElapsedTime()));
       // LogUtils.i(clazzName, CommUtils.buildLogMessage(methodSignature.getName(), shot.getElapsedTime()));
        return result;
    }
*/

}
