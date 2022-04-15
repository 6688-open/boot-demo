package com.dj.boot.feedback.constant;

/**
 * 回传方式
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
public enum FeedbackModeEnum {
    WG(1,"网关回传"),
    MQ(2,"MQ回传");

    private Integer code;
    private String name;
    FeedbackModeEnum(Integer code, String name) {
        this.code=code;
        this.name=name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
