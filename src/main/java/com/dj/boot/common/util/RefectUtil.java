package com.dj.boot.common.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 */
public class RefectUtil {

    /**
     * 直接设置对象属性值
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(Object object, String fieldName, Object value){
        Field field = ReflectionUtils.findField(object.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, object, value);
    }

    /**
     * 直接读取对象的属性值
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName){
        Field field = ReflectionUtils.findField(object.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        Object value = ReflectionUtils.getField(field, object);
        return value;
    }
}
