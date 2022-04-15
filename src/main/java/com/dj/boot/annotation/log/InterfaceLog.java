package com.dj.boot.annotation.log;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InterfaceLog {
    /**
     * The bizNo of request,of which must be specified by the method
     */
    String value();
}
