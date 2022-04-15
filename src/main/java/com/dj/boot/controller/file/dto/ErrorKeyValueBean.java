package com.dj.boot.controller.file.dto;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.file.dto
 * @Author: wangJia
 * @Date: 2021-05-26-14-20
 */
public class ErrorKeyValueBean {
    private int errorCode;
    private String errorMsg;

    public ErrorKeyValueBean(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
