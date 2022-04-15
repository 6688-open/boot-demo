package com.dj.boot.controller.file.dto;

import org.apache.poi.ss.formula.functions.T;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.file.dto
 * @Author: wangJia
 * @Date: 2021-05-26-14-19
 */
public class JsonWriter {
    private String msg;
    private boolean success = true;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private String errorMsg;
    private int errorCode;
    private String exceptionMsg;

    public JsonWriter() {
    }

    public JsonWriter(T data) {
        this.data = data;
    }

    public JsonWriter(String msg, boolean success, T data) {
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setError(ErrorKeyValueBean error) {
        this.setErrorCode(error.getErrorCode());
        this.setErrorMsg(error.getErrorMsg());
    }

    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
