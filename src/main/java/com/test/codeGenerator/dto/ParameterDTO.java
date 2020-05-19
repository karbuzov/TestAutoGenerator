package com.test.codeGenerator.dto;

public class ParameterDTO {
    private String className;
    private boolean isClassPrimitive;
    private String jsonData;
    private String testParameterValue;
    private String testParameterDefinition;
    private int index;
    private boolean classPrimitive;


    public ParameterDTO() {
    }
    public ParameterDTO(String className, boolean isClassPrimitive, String jsonData, int index) {
        this.isClassPrimitive = isClassPrimitive;
        this.className = className;
        this.jsonData = jsonData;
        this.index = index;
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
        return isClassPrimitive;
    }

    public boolean getClassPrimitive() {
        return classPrimitive;
    }

    public ParameterDTO setClassPrimitive(boolean classPrimitive) {
        this.classPrimitive = classPrimitive;
        return this;
    }

    @Override
    public String toString() {
        return "ParameterDTO{" +
                "className='" + className + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", index=" + index +
                ", isClassPrimitive=" + isClassPrimitive +
                ", classPrimitive='" + classPrimitive + '\'' +
                '}';
    }
}