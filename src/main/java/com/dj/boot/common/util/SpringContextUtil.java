package com.dj.boot.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContextUtil
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util
 * @Author: wangJia
 * @Date: 2020-07-21-14-08
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     *
     * @param beanId
     * @return  Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static <T> T getBean(String beanId) throws BeansException {
        return (T)applicationContext.getBean(beanId);
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     *
     * @param beanId
     * @return  Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static <T> T getBean(String beanId,Class<T> beanClass) throws BeansException {
        return (T)applicationContext.getBean(beanId,beanClass);
    }
}