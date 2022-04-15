package com.dj.boot.btp.common.util.error;


import com.dj.boot.common.csv.util.SoUtil;
import com.dj.boot.controller.so.constant.SoEnumInter;
import com.dj.boot.controller.so.constant.SoEnumKey;

/**
 * 订单异常描述枚举
 */
public enum ErrStatusEnum implements SoEnumInter<Enum, Integer> {
    ERR_1_0(1, '0', "未取消"),
    ERR_1_1(1, '1', "取消成功"),
    ERR_1_2(1, '2', "取消失败"),
    ERR_1_3(1, '3', "订单取消中"),

    ERR_2_0(2, '0', "无异常"),
    ERR_2_1(2, '1', "异常暂停"),
    ERR_2_2(2, '2', "异常恢复"),

    ERR_3_0(3, '0', "未审核"),
    ERR_3_1(3, '1', "审核通过"),
    ERR_3_2(3, '2', "审核驳回"),

    ERR_4_0(4, '0', "非病单"),
    ERR_4_1(4, '1', "病单"),

    ERR_5_0(5, '0', "正常"),
    ERR_5_1(5, '1', "拦截中"),
    ERR_5_2(5, '2', "拦截失败"),
    ERR_5_3(5, '3', "拦截成功"),
    ERR_5_4(5, '4', "客服放行"),

    //    * 异常状态7：订单在发货后取消
    ERR_7_0(7, '0', "未取消"),
    ERR_7_1(7, '1', "已取消"),

    ERR_8_0(8, '0', "未删除"),
    ERR_8_1(8, '1', "已删除"),
    /**
     * 区别于第5位的恶意订单拦截,此为病单拦截
     */
    ERR_9_0(9, '0', "正常"),
    ERR_9_1(9, '1', "拦截中"),
    ERR_9_2(9, '2', "拦截失败"),
    ERR_9_3(9, '3', "拦截成功"),
    ;
    /** 描述 */
    private final String name;
    /** key值 */
    private final char key;
    /** 位数 */
    private final int b;


    private ErrStatusEnum(int b, char key, String name) {
        this.b = b;
        this.name = name;
        this.key = key;
    }

    public int getB() {
        return b;
    }

    public char getKey() {
        return key;
    }


    @Override
    public Enum getSelf() {
        return this;
    }

    @Override
    public Integer getCode() {
        return SoUtil.joint(b, key);
    }

    @Override
    public SoEnumKey getKey(Enum anEnum) {
        for (SoEnumKey e : SoEnumKey.values()) {
            if (e.name().equals(anEnum.name())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String getDesc() {
        return name;
    }
}
