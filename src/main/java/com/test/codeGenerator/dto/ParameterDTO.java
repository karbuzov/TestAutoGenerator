package com.test.codeGenerator.dto;


import java.util.ArrayList;
import java.util.List;

public class ParameterDTO {
    private String className;
    private boolean classPrimitive;
    private String jsonData;
    private String testParameterValue;
    private String testParameterDefinition;
    private List<String> parametrized = new ArrayList<>();
    private int index;

    public ParameterDTO() {
    }

    public ParameterDTO(String className, boolean isClassPrimitive, String jsonData, int index, List<String> parametrized) {
        this.classPrimitive = classPrimitive;
        this.className = className;
        this.jsonData = jsonData;
        this.index = index;
        this.parametrized = parametrized;
    }

    public String getClassName() {
        return className;
    }

    public String getJsonData() {
        return jsonData;
    }

    public int getIndex() {
        return index;
    }

    public String getTestParameterValue() {
        return testParameterValue;
    }

    public ParameterDTO setTestParameterValue(String testParameterValue) {
        this.testParameterValue = testParameterValue;
        return this;
    }

    public String getTestParameterDefinition() {
        return testParameterDefinition;
    }

    public ParameterDTO setTestParameterDefinition(String testParameterDefinition) {
        this.testParameterDefinition = testParameterDefinition;
        return this;
    }

    public boolean isClassPrimitive() {
        return classPrimitive;
    }

    public List<String> getParametrized() {
        return parametrized;
    }

    @Override
    public String toString() {
        return "ParameterDTO{" +
                "className='" + className + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", index=" + index +
                ", classPrimitive=" + classPrimitive +
                ", parametrized=" + parametrized +
                '}';
    }
}
