package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.utils.ClassUtils;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ParameterDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Aspect
public class AOP {

    ObjectMapper objectMapper = new ObjectMapper();

    public AOP() {
        System.out.println("****  AOP ***");
    }

//    @Pointcut("execution(* com.test.BoobleManager.getPage(..))")
    protected void myPointcut() {
    }

    @Around("execution(* com.test.BoobleManager.getPage (..)) || " +
            "execution(* com.test.DataDAOImpl.* (..))")
    public void logBefore(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

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
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getMethod().getName();
            CallDTO callData = new CallDTO(className, methodName, parameList, result);
            generateTest(callData);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private String formatParameter(ParameterDTO param) throws Exception {
        return param.getTestParameterValue();
    }


    private void prepareParameter(ParameterDTO param) throws Exception {
        Object val = null;
        String result = "";

        if (param.getClassName() != null) {

            if (ClassUtils.isPrimitive(Class.forName(param.getClassName()))) {

                switch (param.getClassName()) {
                    case "java.lang.String": {
                        val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));
                        result = "\"" + val.toString() + "\"";
                        break;
                    }

                    default: {
                        val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));
                    }
                }
            } else {
                result = "var" + (param.getIndex() + 1);
                val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));

                String definition = Class.forName(param.getClassName()).getSimpleName() + " " + result + " = " +
                        "objectMapper.readValue(\"" + val + "\", Class.forName(\"" + param.getClassName() + "\"));";
                param.setTestParameterDefinition(definition);
            }
        } else {
            result = null;
        }

        param.setTestParameterValue(result);
    }

    public void generateTest(CallDTO callData) throws Exception {
        if (callData.getParams() != null) {
            String call = "(";
            boolean coma = false;
            for (ParameterDTO param : callData.getParams()) {
                prepareParameter(param);
            }
            prepareParameter(callData.getResult());
            for (ParameterDTO param : callData.getParams()) {
                if (!StringUtils.isEmpty(param.getTestParameterDefinition())) {
                    System.out.println(param.getTestParameterDefinition());
                }
            }

            for (ParameterDTO param : callData.getParams()) {

                call += coma ? ", " + formatParameter(param) : formatParameter(param);
                coma = true;
            }
            String resultStr = formatParameter(callData.getResult());

            System.out.println("Object actualValue = " + callData.getClassName() + "." + callData.getMethodName() + call + ");");
            System.out.println("assertEquals(" + resultStr + ", actualValue);");
        }
    }
}
