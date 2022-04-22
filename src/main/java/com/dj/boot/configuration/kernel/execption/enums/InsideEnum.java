package com.dj.boot.configuration.kernel.execption.enums;

public enum InsideEnum {

    INNER(1, "c内部接口"),
    OUTER(2, "外部系统");

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

    private InsideEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
