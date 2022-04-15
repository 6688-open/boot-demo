package com.dj.boot.combine.dto;

import java.io.Serializable;

public class Result<T extends Serializable> implements Serializable {

    public static final int SUCCESS = 200;
    public static final int WARN = 300;
    public static final int FAIL = 400;
    public static final int ERROR = 500;
    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){
        return code == SUCCESS || code == WARN;
    }

    public static <T extends Serializable> Result<T> success(T data, String message) {
        return new Result<>(SUCCESS, message, data);
    }

    public static <T extends Serializable> Result<T> fail(String message) {
        return new Result<>(FAIL, message, null);
    }

    public static <T extends Serializable> Result<T> error(String message) {
        return new Result<>(ERROR, message, null);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
