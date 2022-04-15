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
import java.util.Map;

/**
 * 敏感数据处理工具类
 */
@Component
public final class SensitiveMaskUtil {

    private static final Logger logger = LogManager.getLogger(SensitiveMaskUtil.class);

    private static final Integer ADDRESS_REMAIN = 6;
    private static final Integer CHINESE_NAME_REMAIN = 1;
    private static final Integer MOBILE_PHONE_LEFT_REMAIN = 3;
    private static final Integer MOBILE_PHONE_RIGHT_REMAIN = 4;

    private Map<String, List<SensitiveFieldEntity>> sensitiveConfigMap;

    public Map<String, List<SensitiveFieldEntity>> getSensitiveConfigMap() {
        return sensitiveConfigMap;
    }

    public void setSensitiveConfigMap(Map<String, List<SensitiveFieldEntity>> sensitiveConfigMap) {
        this.sensitiveConfigMap = sensitiveConfigMap;
    }

    /**
     * 根据spring配置来处理敏感信息
     * @param configCode
     * @param list
     * @param <T>
     */
    public <T> void markByConfig(String configCode, List<T> list)  {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<SensitiveFieldEntity> sensitiveConfig =  sensitiveConfigMap.get(configCode);
        if (CollectionUtils.isEmpty(sensitiveConfig)) {
            return;
        }
        Class valueClass = list.get(0).getClass();
        List<SensitiveInfo> sensitiveInfoList = new ArrayList<SensitiveInfo>();
        for (SensitiveFieldEntity sensitiveField : sensitiveConfig) {
            Field field =  getField(valueClass, sensitiveField);
            SensitiveInfo sensitiveInfo = new SensitiveInfo();
            sensitiveInfo.field = field;
            sensitiveInfo.dataFieldType = sensitiveField.getDataFieldType();
            sensitiveInfoList.add(sensitiveInfo);
        }
        maskBySensitiveInfo(list,sensitiveInfoList);
        // if config was debug
        // if(logger.isDebugEnabled()){
            logger.debug("【脱敏后的数据为】"+ JsonUtil.toJson(list));
        //}
    }



    /**
     * 根据注解来处理敏感信息
     * @param <T>
     * @param list
     */
    public <T> void maskByAnnotation(List<T> list) {
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
                SensitiveInfo sensitiveInfo = new SensitiveInfo();
                sensitiveInfo.field = oneField;
                sensitiveInfo.dataFieldType = annotation.type();
                sensitiveInfoList.add(sensitiveInfo);
            }
        }
        maskBySensitiveInfo(list,sensitiveInfoList);
        if(logger.isDebugEnabled()){
            logger.debug("【脱敏后的数据为】"+JsonUtil.toJson(list));
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
        Field[] fields = clazz.getDeclaredFields();
        return fields;
    }


    /**
     * 处理敏感信息
     * @param list
     * @param sensitiveInfoList
     * @param <T>
     */
    private <T> void maskBySensitiveInfo(List<T> list, List<SensitiveInfo> sensitiveInfoList) {
        if (CollectionUtils.isEmpty(sensitiveInfoList)) {
            return;
        }
        for (T t : list) {
            for (SensitiveInfo sensitiveInfo : sensitiveInfoList) {
                try {
                    maskBySensitiveInfo(t, sensitiveInfo);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("【敏感数据处理异常】", e);
                }
            }
        }
    }

    /**
     * 处理一个对象的敏感信息
     * @param object
     * @param sensitiveInfo
     * @param <T>
     * @throws IllegalAccessException
     */
    private <T> void maskBySensitiveInfo(T object, SensitiveInfo sensitiveInfo) throws IllegalAccessException {
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
        String maskedValue = maskBySensitiveInfo(valueStr,dataType);
        field.set(object, maskedValue);
    }


    /**
     *
     * @param obj
     * @param dataType
     * @return
     * @throws IllegalAccessException
     */
    public String maskBySensitiveInfo(Object obj, DataFieldType dataType){
        if (obj == null) {
            return null;
        }
        String valueStr = obj.toString();
        if (StringUtils.isEmpty(valueStr)) {
            return "";
        }
        String maskedValue = "";
        switch (dataType) {
            case CHINESE_NAME: {
                maskedValue =  chineseName(valueStr);
                break;
            }
            case MOBILE_PHONE: {
                maskedValue =  mobilePhone(valueStr);
                break;
            }
            case ADDRESS: {
                maskedValue = address(valueStr);
                break;
            }
            case ID_CARD: {
                maskedValue = IDCardNum(valueStr);
                break;
            }
            case FIXED_PHONE: {
                maskedValue =  fixedPhone(valueStr);
                break;
            }
            case EMAIL: {
                maskedValue =  email(valueStr);
                break;
            }
            case BANK_CARD: {
                maskedValue =  bankCard(valueStr);
                break;
            }
            case CNAPS_CODE: {
                maskedValue =  cnapsCode(valueStr);
                break;
            }
            case USER_ACCOUNT:{
                maskedValue =  cnapsCode(valueStr);
                break;
            }
        }
        return maskedValue;
    }


    /**
     * 脱敏中文名
     * @param fullName
     * @return
     */
    private String chineseName(String fullName) {
        int length = fullName.length();
        if (length <= CHINESE_NAME_REMAIN) {
            return fullName;
        }
        String name = StringUtils.left(fullName, CHINESE_NAME_REMAIN);
        return StringUtils.rightPad(name, length, "*");

    }


    /**
     * [中文姓名] 只隐藏第一个汉字，其他展示<例子：*杏花>
     */
    public static String chineseNameRight(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = null;
        if(fullName.length() > CHINESE_NAME_REMAIN )
            name = StringUtils.right(fullName, fullName.length() - CHINESE_NAME_REMAIN);
        return StringUtils.leftPad(name, StringUtils.length(fullName), "*");
    }


    /**
     * 脱敏手机号
     * @param mobilePhone
     * @return
     */
    private String mobilePhone(String mobilePhone) {
        int length = mobilePhone.length();
        if (length <= MOBILE_PHONE_LEFT_REMAIN + MOBILE_PHONE_RIGHT_REMAIN) {
            return mobilePhone;
        }
        return StringUtils.left(mobilePhone, MOBILE_PHONE_LEFT_REMAIN).concat(
                StringUtils.leftPad(StringUtils.right(mobilePhone, MOBILE_PHONE_RIGHT_REMAIN),
                        length-MOBILE_PHONE_LEFT_REMAIN, "*"));

    }

    /**
     * 脱敏地址
     * @param address
     * @return
     */
    private String address(String address) {
        int length = StringUtils.length(address);
        if(length <= ADDRESS_REMAIN){
            return address;
        }
        return StringUtils.rightPad(StringUtils.left(address, ADDRESS_REMAIN), length, "*");
    }








    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param id
     * @return
     */
    public static String IDCardNum(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        String num = StringUtils.right(id, 4);
        return StringUtils.leftPad(num, StringUtils.length(id), "*");
    }


    /**
     * 【固定电话】 后四位，其他隐藏<例子：****1234>
     *
     * @param num
     * @return
     */
    public static String fixedPhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email
     * @return
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1)
            return email;
        else
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
    }


    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     *
     * @param cardNum
     * @return
     */
    public static String bankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }


    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     *
     * @param code
     * @return
     */
    public static String cnapsCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
    }


    /**
     * [用户账号]显示首/尾各1位，中间加** <例子:风**扬，m**d>
     *
     * @param userAccount
     * @return
     */
    public static String userAccount(String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            return "";
        }
        String leftWord = StringUtils.rightPad(StringUtils.left(userAccount, 1), StringUtils.length(userAccount)-1, "*");
        StringBuilder bu = new StringBuilder(leftWord);
        bu.append(StringUtils.right(userAccount,1));
        return bu.toString();
    }

}
