package com.dj.boot.btp.common.util.sensitive.sensitiveutil;


import com.dj.boot.common.util.RefectUtil;

import java.util.HashMap;
import java.util.Map;

public class SensitiveUtil {

    public static Map<String, Object> putValuesIntoMap(String id, String fieldNames, Object obj){
        String[] fieldNameArr = fieldNames.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        for(String str : fieldNameArr){
            map.put( str, RefectUtil.getFieldValue(obj,str));
        }
        return map;
    }
}
