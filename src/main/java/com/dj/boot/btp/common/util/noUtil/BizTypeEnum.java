package com.dj.boot.btp.common.util.noUtil;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举常量
 */
public enum BizTypeEnum {
    CWO_SERVICE("CWO_SERVICE", "事件单"),
    CPO_SERVICE("CPO_SERVICE", "赔付单"),
    CBU_SERVICE("CBU_SERVICE", "事业部"),
    CSL_SERVICE("CSL_SERVICE", "订单号"),
    ;
    /**
     * Josl业务类型全局Map
     */
    public static final Map<String, BizTypeEnum> JOSL_BIZ_TYPE_MAP = new HashMap<String, BizTypeEnum>();

    static {

        for (BizTypeEnum e : EnumSet.allOf(BizTypeEnum.class)) {
            JOSL_BIZ_TYPE_MAP.put(e.getCode(), e);
        }
    }

    public static BizTypeEnum getBizType(String code) {
        return JOSL_BIZ_TYPE_MAP.get(code);
    }

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    private BizTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
