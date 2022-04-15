package com.dj.boot.aspect.privacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.aspect.privacy
 * @Author: wangJia
 * @Date: 2021-11-26-17-39
 */
public class PrivacyBeanUtils {
    private static final Logger log = LoggerFactory.getLogger(PrivacyBeanUtils.class);

    public PrivacyBeanUtils() {
    }

    public static Object getPropertyValue(Object source, String propertyName) {
        if (source == null) {
            log.error("source为null了:{},propertyName:{}", source, propertyName);
            return source;
        } else {
            try {
                if (propertyName.contains(".")) {
                    String currentProperty = propertyName.substring(0, propertyName.indexOf("."));
                    log.debug("currentProperty:{}", currentProperty);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(currentProperty, source.getClass());
                    Object value = propertyDescriptor.getReadMethod().invoke(source);
                    String nextProperty = propertyName.substring(propertyName.indexOf(".") + 1);
                    log.debug("nextProperty:{}", nextProperty);
                    return getPropertyValue(value, nextProperty);
                } else {
                    log.debug("propertyName:{},source:{}", propertyName, source.getClass().getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, source.getClass());
                    Object value = propertyDescriptor.getReadMethod().invoke(source);
                    return value;
                }
            } catch (IntrospectionException var6) {
                var6.printStackTrace();
                throw new RuntimeException("获取对象属性失败" + var6.getMessage(), var6);
            } catch (IllegalAccessException var7) {
                var7.printStackTrace();
                throw new RuntimeException("获取对象属性失败" + var7.getMessage(), var7);
            } catch (InvocationTargetException var8) {
                var8.printStackTrace();
                throw new RuntimeException("获取对象属性失败" + var8.getMessage(), var8);
            }
        }
    }

    public static List<Field> getAllFields(Class cClass) {
        List<Field> fieldList = new ArrayList();

        for(Class tmpClass = cClass; tmpClass != null && !tmpClass.getName().toLowerCase().equals("java.lang.object"); tmpClass = tmpClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
        }

        return fieldList;
    }
}
