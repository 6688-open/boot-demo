package com.dj.boot.handlertest;

import java.lang.annotation.*;

/**
 * HandlerType
 * @author wangjia
 * @ProjectName: boot_demo
 * @Date: 2020-07-15-15-58
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String value();
}
