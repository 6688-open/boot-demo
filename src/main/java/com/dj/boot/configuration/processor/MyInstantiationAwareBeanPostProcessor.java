package com.dj.boot.configuration.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2021-11-16-10-05
 */
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {


    /**
     * 创建了代理或者重写了InstantiationAwareBeanPostProcessor的postProcessBeforeInstantiation方法 实例化之前的前置处理器方法
     * 返回的bean不为空 则直接返回此实例  否则走实例化的流程
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    @Nullable
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }
}
