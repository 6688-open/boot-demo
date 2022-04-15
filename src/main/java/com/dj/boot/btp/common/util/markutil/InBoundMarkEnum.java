package com.dj.boot.btp.common.util.markutil;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * 标记位
 *
 * @author wj
 * @Date 2020-04-24 10:10
 * Mark的定位：只用作数据存储，应用内部的业务逻辑判断，不作为新建的入参载体
 */
public enum InBoundMarkEnum implements MarkEnum {
    //处理时效、服务态度、服务质量 bit位置  value 1-5分(初始0表示未评论)
    MARK_1_x(1, '0', "处理时效"),
    MARK_2_x(2, '0', "服务态度"),
    MARK_3_x(3, '0', "服务质量"),
    //入库通用
    MARK_4_0(4, '0', "默认"),
    MARK_4_1(4, '1', "虚拟"),
    MARK_5_0(5, '0', "默认"),
    MARK_5_1(5, '1', "指定批次入库"),
    MARK_6_0(6, '0', "默认"),
    MARK_6_1(6, '1', "在途可售"),
    MARK_7_0(7, '0', "未评价"),
    MARK_7_1(7, '1', "已评价"),
    MARK_8_0(8, '0', "未赔付"),
    MARK_8_1(8, '1', "已赔付"),
    MARK_9_x(9, '0', "留言次数");


    public static final String split = "-";

    /**
     * 位置
     */
    private int bit;

    /**
     * 标记位值
     */
    private char value;

    /**
     * 描述
     */
    private String desc;

    InBoundMarkEnum(int bit, char value, String desc) {
        this.bit = bit;
        this.value = value;
        this.desc = desc;
    }


    private static HashMap<String, InBoundMarkEnum> enumHashMap = new HashMap<String, InBoundMarkEnum>();

    static {
        for (InBoundMarkEnum enumItem : EnumSet.allOf(InBoundMarkEnum.class)) {
            enumHashMap.put(enumItem.bit() + split + enumItem.value(), enumItem);
        }
    }

    public int bit() {
        return bit;
    }

    public char value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    public MarkEnum getMarkEnum(int bit, char value) {
        return enumHashMap.get(bit + split + value);
    }
}
