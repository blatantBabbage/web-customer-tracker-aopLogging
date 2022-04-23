package com.nitesh.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

    // setup pointcut declarations
    // for controller package
    @Pointcut("execution(* com.nitesh.springdemo.controller.*.*(..))")
    public void forController() {}

    // for service package
    @Pointcut("execution(* com.nitesh.springdemo.service.*.*(..))")
    public void forService() {}

    // for dao package
    @Pointcut("execution(* com.nitesh.springdemo.dao.*.*(..))")
    public void forDao() {}

    // combine pointcuts
    @Pointcut("forController() || forService() || forDao()")
    public void appFlowAspect() {}

    // add @Before advice
    @Before("appFlowAspect()")
    public void beforeAdvice(JoinPoint joinPoint) {

        // display method we're calling
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println(System.currentTimeMillis() + ": ======> executing @Before on method : " + methodSignature);

        // display method arguments
        // get args
        Object[] args = joinPoint.getArgs();

        // loop on args to display them
        for (Object eachArgs : args) {
            System.out.println(System.currentTimeMillis() + ": =====> args : " + eachArgs);
        }
    }

    // add @AfterReturning advice
    // in afterReturning method we're using Object param as we aren't sure of what the joinPoints will be returning
    @AfterReturning(pointcut = "appFlowAspect()",
                    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

        // display method we're returning from
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println(System.currentTimeMillis() + ": ======> executing @AfterReturning on method : " + methodSignature);

        // display data returned
        System.out.println(System.currentTimeMillis() + ": ======> @AfterReturning result : " + result);
    }
}
