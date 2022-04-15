package com.dj.boot.common.constant.privacy;

import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.common.constant
 * @Author: wangJia
 * @Date: 2021-11-26-17-23
 */
public class SingleClassConstants {

    public static final Set<Class<?>> SINGLE_PARAM_CLASSES = new HashSet();

    public SingleClassConstants() {
    }

    public static boolean isSingleClass(Object o) {
        return SINGLE_PARAM_CLASSES.contains(o.getClass());
    }

    static {
        SINGLE_PARAM_CLASSES.add(Integer.TYPE);
        SINGLE_PARAM_CLASSES.add(Integer.class);
        SINGLE_PARAM_CLASSES.add(Long.TYPE);
        SINGLE_PARAM_CLASSES.add(Long.class);
        SINGLE_PARAM_CLASSES.add(Short.TYPE);
        SINGLE_PARAM_CLASSES.add(Short.class);
        SINGLE_PARAM_CLASSES.add(Byte.TYPE);
        SINGLE_PARAM_CLASSES.add(Byte.class);
        SINGLE_PARAM_CLASSES.add(Float.TYPE);
        SINGLE_PARAM_CLASSES.add(Float.class);
        SINGLE_PARAM_CLASSES.add(Double.TYPE);
        SINGLE_PARAM_CLASSES.add(Double.class);
        SINGLE_PARAM_CLASSES.add(Boolean.TYPE);
        SINGLE_PARAM_CLASSES.add(Boolean.class);
        SINGLE_PARAM_CLASSES.add(Character.TYPE);
        SINGLE_PARAM_CLASSES.add(Character.class);
        SINGLE_PARAM_CLASSES.add(String.class);
    }
}
