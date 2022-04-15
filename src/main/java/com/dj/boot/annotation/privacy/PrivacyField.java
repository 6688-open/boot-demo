package com.dj.boot.annotation.privacy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.annotation.privacy
 * @Author: wangJia
 * @Date: 2021-11-26-17-41
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivacyField {
    PrivacyFieldType type() default PrivacyFieldType.ALL;

    String group() default "none";

    String handerClass() default "";

    int[] remainIndexs() default {};
}
