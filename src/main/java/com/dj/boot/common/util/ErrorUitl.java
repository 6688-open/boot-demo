package com.dj.boot.common.util;

import org.apache.commons.collections4.CollectionUtils;

public class ErrorUitl {
    public static void ifNull(Object target,String message){
        if(target == null) throw new IllegalArgumentException(message+"["+target+"]为null");
    }
    public static void ifBlank(String target,String message){
        if(StringUtils.isBlank(target)) throw new IllegalArgumentException(message+"["+target+"]为空");
    }
    public static void ifSizeEmpty(final Object o, String message){
        if(CollectionUtils.sizeIsEmpty(o)) throw new IllegalArgumentException(message+"["+o+"]为空");
    }
    public static void ifInValid(String regex,String target,String message){
        if(!ValidatorUtils.validateCharSequence(regex,target)) throw new IllegalArgumentException(message+"["+target+"]不合法");
    }

}
