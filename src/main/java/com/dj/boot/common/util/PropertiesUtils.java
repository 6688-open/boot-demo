package com.dj.boot.common.util;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 非spring容器管理的类可以通过此类获取配置值
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-30-14-27
 */
@Component
public class PropertiesUtils implements EmbeddedValueResolverAware {

    private static StringValueResolver resolver;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        PropertiesUtils.resolver = resolver;
    }

    public static String getPropertiesValue(String key){
        return resolver.resolveStringValue(key);
    }
}
