package com.dj.boot.common.csv.enums;


/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.csv.enums
 * @Author: wangJia
 * @Date: 2021-04-15-10-55
 */
public interface StatusEnumInter<T, V> {
    T getSelf();

    V getCode();

    EnumKey getKey(T var1);

    String getDesc();
}
