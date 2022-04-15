package com.dj.boot.btp.common.util.sensitive.sensitiveutil;

import com.dj.boot.annotation.sensitive.SensitiveField;
import com.dj.boot.btp.common.util.sensitive.type.DataFieldType;
import com.dj.boot.common.util.json.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 敏感数据处理工具类
 */
@Component
public final class SensitiveMaskViewUtil {

    private static final Logger logger = LogManager.getLogger(SensitiveMaskViewUtil.class);
    public static final String VIEW_NAME_REMAIN = "点击查看";

    /**
     * 根据注解来处理敏感信息
     * @param t
     * @param <T>
     */
    public <T> void maskByAnnotation(T t) {
        if(logger.isDebugEnabled()){
            logger.debug("【详情脱敏前的数据为】"+ JsonUtil.toJson(t));
        }
        if (t == null) {
            return ;
        }
        Field[] allFields = findAllField(t.getClass());
        if (allFields == null || allFields.length == 0) {
            return;
        }
        List<SensitiveInfo> sensitiveInfoList = new ArrayList<SensitiveInfo>();
        for (Field oneField : allFields) {
            SensitiveField annotation = oneField.getAnnotation(SensitiveField.class);
            if (annotation != null) {
                SensitiveMaskViewUtil.SensitiveInfo sensitiveInfo = new SensitiveMaskViewUtil.SensitiveInfo();
                sensitiveInfo.field = oneField;
                sensitiveInfo.dataFieldType = annotation.type();
                sensitiveInfoList.add(sensitiveInfo);
            }
        }
        maskBySensitiveInfoView(t,sensitiveInfoList);
        if(logger.isDebugEnabled()){
            logger.debug("【详情脱敏后的数据为】"+JsonUtil.toJson(t));
        }
    }


    /**
     * 根据注解来处理敏感信息 特殊 处理详情列表
     * @param list
     * @param <T>
     */
    public <T> void maskByAnnotatio4Views(List<T> list) {
        // if config was debug
        if(logger.isDebugEnabled()){
            logger.debug("【脱敏前的数据为】"+JsonUtil.toJson(list));
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Field[] allFields = findAllField(list.get(0).getClass());
        if (allFields == null || allFields.length == 0) {
            return;
        }
        List<SensitiveInfo> sensitiveInfoList = new ArrayList<SensitiveInfo>();
        for (Field oneField : allFields) {
            SensitiveField annotation = oneField.getAnnotation(SensitiveField.class);
            if (annotation != null) {
                SensitiveMaskViewUtil.SensitiveInfo sensitiveInfo = new SensitiveMaskViewUtil.SensitiveInfo();
                sensitiveInfo.field = oneField;
                sensitiveInfo.dataFieldType = annotation.type();
                sensitiveInfoList.add(sensitiveInfo);
            }
        }
        maskBySensitiveInfo4Views(list,sensitiveInfoList);
        if(logger.isDebugEnabled()){
            logger.debug("【脱敏后的数据为】"+JsonUtil.toJson(list));
        }
    }

    private <T> void maskBySensitiveInfo4Views(List<T> list, List<SensitiveInfo> sensitiveInfoList) {
        if (CollectionUtils.isEmpty(sensitiveInfoList)) {
            return;
        }
        for (T t : list) {
            for (SensitiveMaskViewUtil.SensitiveInfo sensitiveInfo : sensitiveInfoList) {
                try {
                    maskBySensitiveInfo(t, sensitiveInfo);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("【敏感数据处理异常】", e);
                }
            }
        }
    }

    private Field getField(Class valueClass, SensitiveFieldEntity sensitiveField){
        try {
            return valueClass.getDeclaredField(sensitiveField.getFieldName());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("【敏感数据处理异常】",e);
        }
    }

    private static class  SensitiveInfo{
        private Field field;
        private DataFieldType dataFieldType;
    }


    /**
     * 获取类的所有字段 暂时不获取父类的字段
     * @param clazz
     * @return
     */
    private static Field[] findAllField(Class<?> clazz) {
        Field[] fileds = clazz.getDeclaredFields();
        return fileds;
    }


    /**
     * 处理敏感信息
     * @param t
     * @param sensitiveInfoList
     * @param <T>
     */
    private <T> void maskBySensitiveInfoView(T t, List<SensitiveInfo> sensitiveInfoList) {
        if (CollectionUtils.isEmpty(sensitiveInfoList)) {
            return;
        }
        if(t != null){
            for (SensitiveMaskViewUtil.SensitiveInfo sensitiveInfo : sensitiveInfoList) {
                try {
                    maskBySensitiveInfo(t, sensitiveInfo);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("【敏感数据处理异常】", e);
                }
            }
        } else {
            throw new NullPointerException("所需脱敏的对象为空！");
        }

    }

    /**
     * 处理一个对象的敏感信息
     * @param object
     * @param sensitiveInfo
     * @param <T>
     * @throws IllegalAccessException
     */
    private <T> void maskBySensitiveInfo(T object, SensitiveMaskViewUtil.SensitiveInfo sensitiveInfo) throws IllegalAccessException {
        Field field = sensitiveInfo.field;
        DataFieldType dataType = sensitiveInfo.dataFieldType;
        field.setAccessible(true);
        Object value = field.get(object);

        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        if (StringUtils.isEmpty(valueStr)) {
            return;
        }
        switch (dataType) {
            case CHINESE_NAME:
            case MOBILE_PHONE:
            case ADDRESS:
            case ID_CARD:
            case FIXED_PHONE:
            case EMAIL:
            case BANK_CARD:
            case CNAPS_CODE:
            case USER_ACCOUNT:
                field.set(object, viewSameDisplay(valueStr));
                break;
        }
    }

    /**
     * 脱敏中文名
     * @param fullName
     * @return
     */
    private String viewSameDisplay(String fullName) {
        return VIEW_NAME_REMAIN;
    }


}
