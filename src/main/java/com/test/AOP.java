package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.CallDTO;
import com.test.dto.ParameterDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Aspect
public class AOP {

    public AOP() {

        System.out.println("****  AOP ***");
    }

//    @Pointcut("execution(* com.test.BoobleManager.getPage(..))")
    protected void myPointcut() {
    }

    @Around("execution(* com.test.BoobleManager.doit (..)) || " +
            "execution(* com.test.DataDAOImpl.* (..))")
    public void logBefore(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("****" + args + "****");
        int i = 0;
        List<ParameterDTO> parameList = new ArrayList<>();
        try {
            if (args != null && args.length > 0) {
                for (Object obj : args) {
                    Class c = obj.getClass();
                    System.out.println("******" + obj);
                    String json = objectMapper.writeValueAsString(obj);
                    System.out.println("===========" + json);

                    ParameterDTO param = new ParameterDTO(c.getName(), json, i);
                    parameList.add(param);
                    i++;
                }
            }


            System.out.println("logBefore() is running!");
            System.out.println("hijacked : " + joinPoint.getSignature().getName());
            System.out.println("hijacked : " + joinPoint);
            System.out.println("******");
            Object obj = joinPoint.proceed();

            if (obj != null) {
                Class c = obj.getClass();
                String json = objectMapper.writeValueAsString(obj);
                ParameterDTO param = new ParameterDTO(c.getName(), json, i);
                parameList.add(param);
            }
            String className = joinPoint.getSourceLocation().getWithinType().getName();
            String methodName = "";
            CallDTO callData = new CallDTO(className, methodName, parameList);
            callData = null;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("hijacked : " + joinPoint);

    }
}
