package com.dj.boot.btp.common.util.sensitive.sensitiveutil;


import com.dj.boot.btp.common.util.sensitive.type.DataFieldType;

/**
 * 敏感字段的配置信息
 */
public class SensitiveFieldEntity {
    /**
     * 数据类型
     */
    private DataFieldType dataFieldType;
    /**
     * 对应字段名
     * 只支持单层结构
     */
    private String fieldName;

    public DataFieldType getDataFieldType() {
        return dataFieldType;
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
