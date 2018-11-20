package com.zxu.annotation;

public enum DataType {
    STRING("text"),DATE("text"), UNKNOWN(null);
    private final String type;
    DataType(String type) {
        this.type = type;
    }
    public String type(){
        return type;
    }
}
