package com.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AOP {

    public AOP() {

        System.out.println("****  AOP ***");
    }

//    @Pointcut("execution(* com.test.BoobleManager.getPage(..))")
    protected void myPointcut() {
    }

    @Around("execution(* com.test.BoobleManager.getPage(..))")
    public void logBefore(ProceedingJoinPoint joinPoint) {

        System.out.println("****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****");
        System.out.println("****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****");
        System.out.println("****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****");
        System.out.println("****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****");
        System.out.println("****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****");

        System.out.println("logBefore() is running!");
        System.out.println("hijacked : " + joinPoint.getSignature().getName());
        System.out.println("******");
    }
}
