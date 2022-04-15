package com.dj.boot.common.util.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MapperKey {
	/**
	 * Properties key name.
	 * @return String
	 */
	String value() default "";
}