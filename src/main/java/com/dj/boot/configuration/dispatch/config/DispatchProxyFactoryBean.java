package com.dj.boot.configuration.dispatch.config;

import com.dj.boot.configuration.dispatch.handler.DispatchInvocationHandler;
import com.dj.boot.configuration.dispatch.proxy.DispatchProxyHandler;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，
 *  用户可以通过实现该接口定制实例化Bean的逻辑
 *
 * 归档代理FactoryBean
 * @param <T>
 * @Author: wangJia
 */
public class DispatchProxyFactoryBean<T> implements FactoryBean<T> {
    /**
     * 被代理类接口
     */
    private Class<T> targetInterface;
    /**
     * 被代理类实例
     */
    private Map<String,T> targetInstances;
    /**
     * 默认Target
     */
    private String defaultTarget;
    /**
     * 调用处理
     */
    private DispatchInvocationHandler handler;

    /**
     * 实现了FactoryBean接口的bean是一类叫做factory的bean。
     * 其特点是，spring会在使用 getBean()调用获得该bean时，
     * 会自动调用该bean的getObject()方法，所以返回的不是factory这个bean，
     * 而是这个bean.getOjbect()方法的返回值：
     *
     * 参数在自定义解析xml时 已经将DispatchProxyFactoryBean注入IOC容器中
     * 详解 在初始化容器 refresh方法中 实例化单例非懒加载的bean时 调用getBean进行实例化
     *      在实例化过程中遇到userService 或者别的bean解析属性是userService 则会调用getBean方法实例化useService
     *      此时是由工厂bean 《DispatchProxyFactoryBean》的getObject方法返回的实例
     *      从而返回的实例则是创建的代理 当调用目标接口的方法时 则会直接调用invoke方法
     *
     *  于是可以自己创建一个 DispatchProxyFactoryBean 并添加到IOC容器中，
     *  这样就可以将它生成的 UserService的代理类也一并交由IOC容器管理了。
     *  当我们需要的是 UserService，现在可以使用 @Autowired 等标签从容器中获取返回的对象。
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        //制实例化Bean的逻辑    这里返回的就是 创建一个代理对象的类
        return DispatchProxyHandler.newDispatchProxy(targetInterface, targetInstances,defaultTarget,handler);
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setTargetInterface(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public void setTargetInstances(Map<String, T> targetInstances) {
        this.targetInstances = targetInstances;
    }

    public void setDefaultTarget(String defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public void setDispatchInvocationHandler(DispatchInvocationHandler handler) {
        this.handler = handler;
    }


    public Class<T> getTargetInterface() {
        return targetInterface;
    }

    public Map<String, T> getTargetInstances() {
        return targetInstances;
    }

    public String getDefaultTarget() {
        return defaultTarget;
    }

    public DispatchInvocationHandler getHandler() {
        return handler;
    }
}
