package com.dj.boot.configuration.kernel.execption;

import com.dj.boot.configuration.kernel.execption.enums.InsideEnum;
import com.dj.boot.configuration.kernel.execption.enums.RetryEnum;

public class CResponseException extends RuntimeException {

    /**
     * 0-1  代表异常发生系统
     * 2    代表异常原因是否为c接口或外部异常
     * 3    是否可以重试
     * 4-5  异常分类
     * 6-7  异常细类
     */
    private Integer code; //错误编码
    private String msg;   //错误描述

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return builDetailMessage(this.code, this.msg);
    }


    public String getDesc() {
        return builDetailMessage(this.code, this.msg);
    }

    protected static String builDetailMessage(int code, String msg) {
        final StringBuilder sb = new StringBuilder("[");
        sb.append(code).append("]").append(msg);
        return sb.toString();
    }

    /**
     * 构建错误编码
     *
     * @param insideEnum 是否为c内部接口异常
     * @param retryEnum  是否重试
     * @param bigCode    异常类型
     * @param smallCod   异常明细
     * @return
     */
    protected static int buildCode(InsideEnum insideEnum, RetryEnum retryEnum, int bigCode, int smallCod) {

        if (insideEnum == null) {
            insideEnum = InsideEnum.INNER;
        }

        if (retryEnum == null) {
            retryEnum = RetryEnum.YES;
        }
        //对code值进行移位组装
        Double code = insideEnum.getCode() * Math.pow(10, 5)
                + retryEnum.getCode() * Math.pow(10, 4)
                + bigCode * Math.pow(10, 2)
                + smallCod;

        return code.intValue();
    }

    public CResponseException(String message, Throwable cause) {
        super(message, cause);
    }

}
