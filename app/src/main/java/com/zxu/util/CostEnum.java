package com.zxu.util;

public enum CostEnum {
    INCOME("0"), SPEND("1"), TRANSFER("2");

    private String mCode;

    private CostEnum(String code) {
        this.mCode = code;
    }

    public String code() {
        return mCode;
    }

}
