package com.dj.boot.common.enums.base;


import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.enums.BusinessSignEnum;
import com.dj.boot.common.enums.Option;
import com.dj.boot.common.enums.OrderTypeEnum;
import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 列表拉下选项
 */
public enum SelectTypeEnum {

    Select_receiptsPerformTypeEnum("ReceiptsPerformTypeEnum","入库单据类型", OrderTypeEnum.class),
    Select_BusinessSignEnum("BusinessSignEnum","业务标识类型", BusinessSignEnum.class),
    ;


    SelectTypeEnum(String code, String name, Class<? extends BaseOptionEnum> optionEnum) {
        this.code = code;
        this.name = name;
        this.optionEnum = optionEnum;
    }

    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String name;
    /**
     * 完全限制类名
     */
    private Class<? extends BaseOptionEnum> optionEnum;

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

    public Class getOptionEnum() {
        return optionEnum;
    }

    public void setOptionEnum(Class optionEnum) {
        this.optionEnum = optionEnum;
    }

    private static final Logger logger = LogManager.getLogger(SelectTypeEnum.class);

    public static final Map<String, SelectTypeEnum> SelectTypeEnum_MAP = new HashMap<>();



    static {
        for (SelectTypeEnum e : EnumSet.allOf(SelectTypeEnum.class)) {
            SelectTypeEnum_MAP.put(e.getCode(), e);
        }
    }


    public static SelectTypeEnum getByCode(String code) {
        return SelectTypeEnum_MAP.get(code);
    }

    /**
     * 获取选择列表  根据code  ReceiptsPerformTypeEnum  获取 枚举类
     *
     * 只查询 实现了BaseOptionEnum这个枚举类的方法
     *
     * @param optionEnum
     * @return
     */
    public List<Option> getOptionList(Class optionEnum) {
        List<Option> optionList = Lists.newArrayList();
        try {
            if (BaseOptionEnum.class.isAssignableFrom(optionEnum)) {
                Method method = optionEnum.getMethod("getOptionList");
                logger.error("当前枚举:{}, 枚举值数组:{} ", optionEnum.getName(), JSONObject.toJSONString(optionEnum.getEnumConstants()));
                optionList = (List<Option>) method.invoke(optionEnum.getEnumConstants()[0], null);
            }
        } catch (Exception e) {
            logger.error(optionEnum.getName() + "获取下发列表异常:", e);
            return optionList;
        }
        return optionList;
    }


}
