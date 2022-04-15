package com.dj.boot.controller.so.constant;

public interface SoEnumInter<T, V> {

    /**
     * 获取枚举本身
     *
     * @return
     */
    T getSelf();

    /**
     * 获取编码
     *
     * @return
     */
    V getCode();


    /**
     * 获取枚举的Key
     *
     * @param t
     * @return
     */
    SoEnumKey getKey(T t);

    /**
     * 获取中文描述
     *
     * @return
     */
    String getDesc();


}
