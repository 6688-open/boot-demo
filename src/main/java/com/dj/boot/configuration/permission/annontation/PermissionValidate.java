package com.dj.boot.configuration.permission.annontation;



import com.dj.boot.configuration.permission.domain.PermissionType;
import com.dj.boot.configuration.permission.domain.PermissionUri;

import java.lang.annotation.*;


/**
 * 权限认证
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionValidate {

    String[] name() default "authList";

    PermissionType[] type();

    PermissionUri[] uri();



}




