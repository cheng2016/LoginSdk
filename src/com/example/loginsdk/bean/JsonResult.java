package com.example.loginsdk.bean;

/**
 * @author zhenbiao.cai
 * @date 2016/6/15.
 */
public class JsonResult<T> {
    private int code;
    private String message;
    private T data;

    public JsonResult() {}

    public JsonResult(int code) {
        this.code = code;
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == ErrorCode.Success.SUCCESS.getCode();
    }
}
