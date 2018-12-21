package com.zxu.util;

public enum CostEnum {
    INCOME("收入"), SPEND("支出"), TRANSFER("转账");

    private String mCode;

    private CostEnum(String code) {
        this.mCode = code;
    }

    public String code() {
        return mCode;
    }

}
