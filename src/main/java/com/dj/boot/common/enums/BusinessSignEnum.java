package com.dj.boot.common.enums;


import com.dj.boot.common.enums.base.BaseOptionEnum;
import com.dj.boot.common.enums.base.ValidEnum;

import java.util.*;

/**
 * @描述:业务标识枚举值
 * @Date:2020/10/20 22:22
 */
public enum BusinessSignEnum implements BaseOptionEnum, ValidEnum {
    SIGN_CLOUD_WAREHOUSE(1, "云仓"),
    SIGN_ECONOMY_WAREHOUSE(2, "小件经济仓"),
    SIGN_COLD_CHAIN(3, "冷链"),
    SIGN_LARGE_ECONOMY_WAREHOUSE(4, "大件经济仓"),
    ;

    private static Map<Integer, BusinessSignEnum> SIGN_MAP = new HashMap<Integer, BusinessSignEnum>();

    public static final List<Option> OPTIONS_LIST = new ArrayList<Option>();

    static {
        for (BusinessSignEnum em : EnumSet.allOf(BusinessSignEnum.class)) {
            SIGN_MAP.put(em.code, em);
        }
    }

    static {
        for (BusinessSignEnum e : EnumSet.allOf(BusinessSignEnum.class)) {
            Option op = new Option();
            op.setCode(String.valueOf(e.code));
            op.setName(e.getName());
            OPTIONS_LIST.add(op);
        }
    }

    /**
     * 业务标识编码
     */
    private Integer code;
    /**
     * 业务标识名称
     */
    private String name;

    private BusinessSignEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通过编码获取业务标识名称
     */
    public static String getBusinessSignNameByCode(Integer code) {
        if (code == null) {
            return null;
        }
        if (SIGN_MAP.get(code) == null) {
            return null;
        }
        return SIGN_MAP.get(code).getName();

    }

    /**
     * 获取业务标识列表
     */
    public static List<Map<String, String>> getBusinessSignList() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for (Map.Entry<Integer, BusinessSignEnum> entry : SIGN_MAP.entrySet()) {
            map = new HashMap<String, String>();
            map.put("code", String.valueOf(entry.getKey()));
            map.put("name", entry.getValue().getName());
            result.add(map);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Map<String, String>> businessSignList = BusinessSignEnum.getBusinessSignList();
        for (Map<String, String> stringStringMap : businessSignList) {
            stringStringMap.get("code");
            stringStringMap.get("name");
        }
        String name  = BusinessSignEnum.getBusinessSignNameByCode(2);
    }

    /**
     * 用于统一枚举 维护 下拉框
     * @return
     */
    @Override
    public List<Option> getOptionList() {
        return OPTIONS_LIST;
    }

    /**
     * 校验参数   比如传来的参数类型是否可以找到
     * @param code
     * @return
     */
    @Override
    public boolean isValidCode(Object code) {
        if (code == null) {
            return Boolean.FALSE;
        }
        return SIGN_MAP.containsKey(code);
    }
}
