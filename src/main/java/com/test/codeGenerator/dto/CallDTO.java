package com.test.codeGenerator.dto;

import java.util.ArrayList;
import java.util.List;

public class CallDTO {
    private List<ParameterDTO> params = new ArrayList<>();
    private ParameterDTO result = null;
    private String className;
    private String methodName;
    private String uid;

    public CallDTO(String className, String methodName, List<ParameterDTO> params, ParameterDTO result) {
        this.params = params;
        this.className = className;
        this.methodName = methodName;
        this.result = result;
    }

    public CallDTO() {
    }

    public List<ParameterDTO> getParams() {
        return params;
    }

    public void setParams(List<ParameterDTO> params) {
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ParameterDTO getResult() {
        return result;
    }

    public void setResult(ParameterDTO result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public CallDTO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return "CallDTO{" +
                "params=" + params +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", result='" + result + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
