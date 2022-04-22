package com.dj.boot.configuration.kernel.execption.enums;

public enum RetryEnum {


    YES(1, "可直接重试"),
    EDIT(2, "修改后重试"),
    NO(3, "不可重试");

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private RetryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
