package com.dj.boot.controller.so.constant;


import com.dj.boot.common.csv.util.SoUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单异常枚举
 */
public enum SoIssuedErrorEnum implements SoEnumInter<Enum, Integer> {


    /**
     * 主业务异常级别
     */
    R_20(0, 20, "订单业务异常"),
    R_30(0, 30, "订单业务异常"),
    R_40(0, 40, "订单业务异常"),

    R_20_2(R_20.key, 2, "订单下发其他库存不足"),  //该异常为业务异常
    R_30_2(R_30.key, 2, "订单下发失败"),  //该异常为业务异常
    ;

    /**
     * 异常枚举代码
     */
    private final int key;
    /**
     * 异常级别 各个业务需要根据异常级别做出不同的处理
     */

    private final int level;
    /**
     * 异常描述
     */
    private final String value;

    private SoIssuedErrorEnum(int level, int key, String value) {
        this.level = level;
        this.key = key;
        this.value = value;
    }


    public static final Map<Integer, SoIssuedErrorEnum> SoIssuedErrorEnum_MAP = new HashMap<Integer, SoIssuedErrorEnum>();
    public static final Map<Integer, SoIssuedErrorEnum> SoIssuedErrorEnumBeforeOccupiedStock_MAP = new HashMap<Integer, SoIssuedErrorEnum>();

    static {
        for (SoIssuedErrorEnum e : SoIssuedErrorEnum.values()) {
            SoIssuedErrorEnum_MAP.put(e.getCode(), e);
        }
        SoIssuedErrorEnumBeforeOccupiedStock_MAP.put(R_20.getCode(), R_20);
    }

    public Enum getSelf() {
        return this;
    }

    public Integer getCode() {
        return SoUtil.joint(level, key);
    }


    public int getLevel() {
        return level;
    }

    public SoEnumKey getKey(Enum anEnum) {
        for (SoEnumKey e : SoEnumKey.values()) {
            if (e.name().equals(anEnum.name())) {
                return e;
            }
        }
        return null;
    }

    public String getDesc() {
        return value;
    }

    /**
     * 根据code获取描述
     * 防止出现空指针异常，对于不存在的直接返回编码
     *
     * @param code
     * @return
     */
    public static String getDescByCode(int code) {
        SoIssuedErrorEnum soIssuedErrorEnum = SoIssuedErrorEnum_MAP.get(code);
        return soIssuedErrorEnum == null ? String.valueOf(code) : soIssuedErrorEnum.getDesc();
    }


    public static void main(String[] args) {
        String processBtn = SoErrResume.getProcessBtn(Integer.valueOf("302"));
        System.out.println(processBtn);
    }

}
