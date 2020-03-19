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

//        System.out.println("****" + args + "****");
        int i = 0;
        List<ParameterDTO> parameList = new ArrayList<>();
        try {
            if (args != null && args.length > 0) {
                for (Object obj : args) {
                    ParameterDTO param;
                    if (obj != null) {
                        Class c = obj.getClass();
                        String json = objectMapper.writeValueAsString(obj);

                        param = new ParameterDTO(c.getName(), json, i);
                    } else {
                        param = new ParameterDTO(null, null, i);
                    }
                    parameList.add(param);
                    i++;
                }
            }

            Object obj = joinPoint.proceed();

            ParameterDTO result = null;
            if (obj != null) {
                Class c = obj.getClass();
                String json = objectMapper.writeValueAsString(obj);
                result = new ParameterDTO(c.getName(), json, i);
            }
            String className = joinPoint.getSourceLocation().getWithinType().getName();
            String methodName = "";
            CallDTO callData = new CallDTO(className, methodName, parameList, result);
            generateTest(callData);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("hijacked : " + joinPoint);

    }

    public void generateTest(CallDTO callData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (callData.getParams() != null) {
            String call = "methodname(";
            boolean coma = false;
            for (ParameterDTO param : callData.getParams()) {
                Object val = null;
                if (param.getClassName() != null) {
                    val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));
                }

                call += coma ? ", " + val : val;
                coma = true;
            }
            System.out.println(">>>>>>>>>>>>>>>" + call + ");");

        }

//        System.out.println(">>>>>>>>>>>>>>>" + callData);
    }
}
