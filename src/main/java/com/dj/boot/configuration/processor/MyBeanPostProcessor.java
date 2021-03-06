package com.dj.boot.configuration.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2020-08-17-17-43
 *
 * 实例化之后 初始化之前 的初始化前置方法 后置方法
 * 后置处理器：初始化前后进行处理工作
 *  将后置处理器加入到容器中
 */
@Component
public class MyBeanPostProcessor  implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        //System.out.println("postProcessBeforeInitialization..."+beanName+"=>"+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        //System.out.println("postProcessAfterInitialization..."+beanName+"=>"+bean);
        return bean;
    }
}
