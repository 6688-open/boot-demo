package com.dj.boot.btp.exception.domain;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-01-20-17-52
 */
public class SafJosException extends SafBaseException{
    private static final long serialVersionUID = -2991123028688172002L;
    public String code;
    public String zhMsg;
    public String enMsg;

    public SafJosException(String code, String zhMsg) {
        super(zhMsg);
        this.code = code;
        this.zhMsg = zhMsg;
    }

    public SafJosException(String code, String zhMsg, String enMsg) {
        super(zhMsg);
        this.code = code;
        this.zhMsg = zhMsg;
        this.enMsg = enMsg;
    }

    public SafJosException(String code, String zhMsg, String enMsg, Throwable cause) {
        super(zhMsg, cause);
        this.code = code;
        this.zhMsg = zhMsg;
        this.enMsg = enMsg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZhMsg() {
        return this.zhMsg;
    }

    public void setZhMsg(String zhMsg) {
        this.zhMsg = zhMsg;
    }

    public String getEnMsg() {
        return this.enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }
}
