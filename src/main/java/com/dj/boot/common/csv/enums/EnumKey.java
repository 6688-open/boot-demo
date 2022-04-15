package com.dj.boot.common.csv.enums;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.csv.enums
 * @Author: wangJia
 * @Date: 2021-04-15-10-50
 */
public enum EnumKey {
    TEST("TestEnum");



    public String className;
    public final String column = "so_mark";

    private EnumKey(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }
}
