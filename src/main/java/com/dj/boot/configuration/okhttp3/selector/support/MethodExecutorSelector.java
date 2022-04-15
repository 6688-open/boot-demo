package com.dj.boot.configuration.okhttp3.selector.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @描述:MethodExecutor选择器
 * @Author:haosijia
 * @Date:2021/7/4 22:23
 */
@Component("methodExecutorSelector")
public class MethodExecutorSelector implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
       this.beanFactory = beanFactory;
    }
    public IMethodExecutor selector(String method) {
        return beanFactory.getBean(method, IMethodExecutor.class);
    }

}
