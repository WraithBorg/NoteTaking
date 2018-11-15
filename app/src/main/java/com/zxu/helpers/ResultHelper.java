package com.zxu.helpers;

/**
 * 返回结果helper
 */
public class ResultHelper {
    private boolean result;
    private String message;

    public ResultHelper() {
    }

    public ResultHelper(boolean result) {
        this.result = result;
        this.message = "";
    }

    public ResultHelper(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
