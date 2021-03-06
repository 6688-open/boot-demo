package com.dj.boot.pojo.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.annotation
 * @Author: wangJia
 * @Date: 2020-08-04-11-23
 */
@Constraint(validatedBy = {NotLessThanZeroValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotLessThanZero {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
