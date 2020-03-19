package com.test.dto;

public class ParameterDTO {
    private String className;
    private String jsonData;
    private int index;

    public ParameterDTO(String className, String jsonData, int index) {
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

    @Override
    public String toString() {
        return "ParameterDTO{" +
                "className='" + className + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", index=" + index +
                '}';
    }
}
