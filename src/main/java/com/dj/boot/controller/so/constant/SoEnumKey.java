package com.dj.boot.controller.so.constant;

/**
 * 枚举管理关键字，
 * 用于从枚举MAP快速获取枚举的类型
 * 在使用时，需要现在次类中标志出需要使用的枚举类型常量明以及类名
 */
public enum SoEnumKey {
    /**
     * 订单来源
     */
    ISV("SoSourceEnum"),

    ;
    private String className;
    private final String column = "so_mark";

    SoEnumKey(String className) {
        this.className = className;
    }

    /**
     * 获取类变量
     *
     * @return
     */
    public String getClassName() {
        return className;
    }
}
