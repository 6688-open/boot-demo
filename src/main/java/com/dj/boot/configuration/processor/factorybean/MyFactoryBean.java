package com.dj.boot.configuration.processor.factorybean;

import com.dj.boot.service.user.UserService;
import com.dj.boot.service.user.impl.UserServiceArchiveImpl;
import com.dj.boot.service.user.impl.UserServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.configuration.processor
 * @Author: wangJia
 * @Date: 2021-11-18-13-53
 */
@Component
public class MyFactoryBean<T> implements FactoryBean<T> {
    /**
     *   于是可以自己创建一个 MyFactoryBean 并添加到IOC容器中，
     *   这样就可以将它生成的 MyFactoryBeanService 也一并交由IOC容器管理了。
     *   当我们需要的是 MyFactoryBeanService，现在可以使用 @Autowired 等标签从容器中获取了。
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        MyFactoryBeanServiceImpl service = new MyFactoryBeanServiceImpl();
        return (T)service;
    }

    @Override
    public Class<?> getObjectType() {
        return MyFactoryBeanService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
