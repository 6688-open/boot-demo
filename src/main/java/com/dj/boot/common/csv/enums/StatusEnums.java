package com.dj.boot.common.csv.enums;

import com.dj.boot.common.csv.util.SoUtil;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.csv.enums
 * @Author: wangJia
 * @Date: 2021-04-15-10-47
 */
public enum StatusEnums implements StatusEnumInter<Enum, Integer> {
    SOERR_ONE_0(1, '0', "未取消"),
    SOERR_ONE_1(1, '1', "取消成功");



    private  String name;
    private  char key;
    private  int b;

    private StatusEnums(int b, char key, String name) {
        this.b = b;
        this.name = name;
        this.key = key;
    }

    public int getB() {
        return this.b;
    }

    public char getKey() {
        return this.key;
    }

    public Enum getSelf() {
        return this;
    }

    public Integer getCode() {
        return SoUtil.joint(this.b, this.key);
    }

    public EnumKey getKey(Enum anEnum) {
        EnumKey[] arr$ = EnumKey.values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            EnumKey e = arr$[i$];
            if (e.name().equals(anEnum.name())) {
                return e;
            }
        }

        return null;
    }

    public String getDesc() {
        return this.name;
    }
}
