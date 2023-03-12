package com.dj.boot.common.util.mask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component("maskService")
public class MaskServiceImpl implements MaskService {

    private static final Logger logger = LoggerFactory.getLogger(MaskServiceImpl.class);

    @Resource(name = "maskCfg")
    private Properties maskConfig;

    public Object maskFor(Object maskObj) {
        try {
            return internalMaskFor(maskObj);
        }catch (Exception e){
//            logger.error("mask class error", e.getMessage());
            return null;
        }
    }

    private Object internalMaskFor(Object maskObj) {
        Object obj = null;

        if (null == maskObj) {
            return null;
        }

        if (isBaseDataType(maskObj.getClass())) {
            return maskObj;
        }

        try {
            obj = maskObj.getClass().newInstance();
            BeanUtils.copyProperties(maskObj, obj);
        } catch (Exception e) {
//            logger.error("loan class error", e.getMessage());
            return null;
        }

        if (null == obj) {
            return null;
        }

        Class clazz = obj.getClass();
        if (null == clazz) {
            return null;
        }

        if (isCollection(clazz)) {
            if (clazz == HashMap.class) {
                Map<String, Object> map = (HashMap<String, Object>) obj;
                map.putAll((HashMap) maskObj);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    // Modified by tkz
                    if (value != null && isBaseDataType(value.getClass())) {
                        map.put(key, processMask(key, "" + value));
                    } else {
                        Object resObj = maskFor(value);
                        map.put(key, resObj);
                    }
                }
                return obj;
            } else if (clazz == HashSet.class) {
                Set<Object> set = (HashSet<Object>) obj;
                set.addAll((HashSet<Object>) maskObj);

                Iterator<Object> iter = set.iterator();
                obj = new HashSet();
                while (iter.hasNext()) {
                    Object value = iter.next();
                    Object resObj = maskFor(value);
                    ((HashSet) obj).add(resObj);
                }

                return obj;
            } else if (clazz == ArrayList.class) {
                List<Object> set = (ArrayList<Object>) obj;
                set.addAll((ArrayList<Object>) maskObj);

                Iterator<Object> iter = set.iterator();
                obj = new ArrayList();
                while (iter.hasNext()) {
                    Object value = iter.next();
                    Object resObj = maskFor(value);
                    ((ArrayList) obj).add(resObj);
                }

                return obj;
            } else {
                return maskObj;
            }
        }

        Class superClass = clazz.getSuperclass();

        if (null != superClass && superClass != Object.class && superClass != Serializable.class) {
            try {
                Object superObj = superClass.newInstance();
                BeanUtils.copyProperties(obj, superObj);

                superObj = maskFor(superObj);
                BeanUtils.copyProperties(superObj, obj);
            } catch (Exception e) {
//                logger.error("load super class error", e.getMessage());
            }
        }

        try {

            Field[] fields = clazz.getDeclaredFields();
            if (null == fields) {
                return null;
            }

            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);

                //过滤掉Static类型的字段
                if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
                    continue;
                }

                //过滤掉Final类型的字段
                if ((f.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    continue;
                }

                Object value = f.get(obj);
                if (null != value) {
                    if (!isBaseDataType(value.getClass())) {
                        Object resObj = maskFor(value);
                        f.set(obj, resObj);
                    } else if (f.getType() == String.class) {
                        String maskValue = processMask(f.getName(), (String) value);
                        f.set(obj, maskValue);
                    }
                }

            }

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            if (null != logger) {
//                logger.error("getObjectFields Error:" + e.getMessage(), e);
            }
        }

        return obj;

    }

    @Override
    public String maskName(String name) {
        return processMask("name", name);
    }

    @Override
    public String maskCertNo(String certNo) {
        return processMask("certNo", certNo);
    }

    @Override
    public String maskTelephone(String telephone) {
        return processMask("telephone", telephone);
    }

    private boolean isBaseDataType(Class clazz) {
        return (clazz.equals(String.class) || clazz.equals(Integer.class)
                || clazz.equals(Byte.class) || clazz.equals(Long.class)
                || clazz.equals(Double.class) || clazz.equals(Float.class)
                || clazz.equals(Character.class) || clazz.equals(Short.class)
                || clazz.equals(BigDecimal.class)
                || clazz.equals(BigInteger.class)
                || clazz.equals(Boolean.class) || clazz.equals(Date.class)
                || clazz.isPrimitive());
    }

    private boolean isCollection(Class clazz) {
        return (clazz.equals(HashMap.class) || clazz.equals(ArrayList.class) || clazz.equals(HashSet.class));
    }

    private String processMask(String key, String value) {
        String maskPattern = maskConfig.getProperty(key);

        if (StringUtils.isBlank(maskPattern) || StringUtils.isBlank(value)) {
            return value;
        }

        String[] pattern = maskPattern.split(",");
        int beginIdx = Integer.valueOf(pattern[0]);

        int endIdx = value.length() - Integer.valueOf(pattern[1]);

        if (Integer.valueOf(pattern[0]) + Integer.valueOf(pattern[1]) >= value.length()) {
            beginIdx = value.length() - 1;
            endIdx = value.length() - 1;
        } else {
            if (beginIdx > value.length()) {
                beginIdx = value.length();
            }
            if (endIdx < 0) {
                endIdx = value.length() - 1;
            }
        }

        String maskValue = value.substring(0, beginIdx) + "****" + value.substring(endIdx);

        return maskValue;
    }
}
