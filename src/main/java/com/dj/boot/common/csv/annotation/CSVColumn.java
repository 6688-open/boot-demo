package com.dj.boot.common.csv.annotation;

import java.lang.annotation.*;

/**
 * CSV导出注解
 * @since 2018.06.21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CSVColumn {
    /**
     * 列的顺序
     * @return 列的顺序
     */
    int order();

    /**
     * 列标题
     * @return 列标题
     */
    String header() default "";

    /**
     * 默认值
     * @return 默认值
     */
    String defVal() default "";
}
