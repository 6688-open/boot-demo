package com.dj.boot.feedback.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 回传接口名
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
public enum FeedbackInterfaceNameEnum {

    STOCK_FEEDBACK(1, "库存回传", "stockFeedbackMsg"),
    SO_ORDER_FEEDBACK(2, "SO订单确认回传", "soFeedbackMsg"),
    ;

    public static final Map<Integer, FeedbackInterfaceNameEnum> FEEDBACK_INTERFACE_NAME_MAP = new HashMap<Integer, FeedbackInterfaceNameEnum>();

    static {

        for (FeedbackInterfaceNameEnum e : EnumSet.allOf(FeedbackInterfaceNameEnum.class)) {
            FEEDBACK_INTERFACE_NAME_MAP.put(e.getCode(), e);
        }
    }

    /**
     * 通过业务编码获取枚信息
     *
     * @param code
     * @return 枚举信息
     */
    public static FeedbackInterfaceNameEnum getEnumByCode(Integer code) {
        return FEEDBACK_INTERFACE_NAME_MAP.get(code);
    }

    private final String desc;
    private final String name;
    private final Integer code;

    private FeedbackInterfaceNameEnum(Integer code, String desc, String name) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
