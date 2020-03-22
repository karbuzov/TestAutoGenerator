package com.test.codeGenerator.dto;

public class ExampleParameter {

    long number;
    String str;

    public ExampleParameter(String str, long number) {
        this.number = number;
        this.str = str;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
