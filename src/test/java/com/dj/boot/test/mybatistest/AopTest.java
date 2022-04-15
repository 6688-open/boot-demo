package com.dj.boot.test.mybatistest;

import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.mybatistest
 * @Author: wangJia
 * @Date: 2021-11-03-19-49
 */
public class AopTest {

    /*
        解析aspectj-autoproxy标签
                AspectjAutoProxyBeanDefinitionParser.parser()
                    注册AnnotationAwareAspectJAutoProxyCreator
                    处理proxy-target-class、expose-proxy属性
        AnnotationAwareAspectJAutoProxyCreator 实现了BeanPostProcessor接口
        当spring加载bean(userService)时会在实例化阶段 初始化前后调用AbstractAutoProxyCreator.postProcessorAfterInitialization()
        如果存在增强方法()匹配通知
            获取所有的增强器
                1 从IOC容器中获取所有的增强 (找出事务相关的advisor)
                2 构建AspectJAdvisors
                        遍历从IOC容器中获取处的所有Bean的名称
                        根据class对象判断是不是切面 @Aspect
                        循环解析所有切面中的所有方法获取Advisor
                        根据方法获取切点 构建切点表达式
                        根据切点构建增强器并实例化切面通知对象 （将切面中的通知构造为advice通知对象）
                        注解不同（AtBefore） 构建的通知对象也不同
            匹配适用当前bean的增强器
                从候选的通知器中找到合适正在创建的实例对象的通知器
                循环所有的候选增强器对象
                获取到targetClass所实现的接口的class对象，然后加入到集合中
                循环所有的class对象的所有的方法
                通过方法匹配器进行匹配  有匹配的就返回true 根据匹配原则判断该方法是否能匹配Pointcut中的规则，如果有一个方法能匹配，则返回true
        增强方法存在创建代理
                createProxy
                创建一个代理对象工厂
                    获取当前类的属性
                    解析ProxyTargetClass和preserveTargetClass  ProxyTargetClass=false时才会进入判断 true的话直接默认是CGLIB代理了
                        设置ProxyTargetClass的值 如果是false 添加代理接口 判断创建jdk还是cglib代理
                    代理工厂加入通知器，
                    设置targetSource目标对象 添加要代理的类
                    真正创建代理对象proxyFactory.getProxy
               创建代理
                    isOptimize 激活适用CGLIB代理
                    ProxyTargetClass 为true 目标类本身被代理而不是目标类的接口   jdk代理为接口创建代理实现类/CGLIB是给目标类生产子类的代理类
                    hasNoUserSuppliedProxyInterfaces 不存在代理接口
                    未激活CGLIB代理&&ProxyTargetClass =false/存在接口 适用JDK代理
                    否则 判断如果代理类是接口 则适用JDK 不是接口则适用CGLIB

        JDK代理
            （1）构造函数，将代理的对象传入
            （2）invoke方法，此方法中实现了AOP增强的所有逻辑
                        equals/hashcode 忽略
                        把aop的advisor转化为拦截器链
                                拦截器链 = null 直接执行切点方法
                                否则封装到ReflectiveMethodInvocation 对拦截器的逐一调用 proceed方法
                                        将此工作委托给了各个增强器，使各个增强器在内部进行逻辑实现。
                                        就是拦截器链逐一调用拦截器的intercept方法
                        返回结果
            （3）getProxy方法，获取代理类
            JDKDynamicAopProxy实现了InvocationHandler  JdkDynamicAopProxy中一定会有个invoke函数，并且JdkDynamicAopProxy会把AOP的核心逻辑写在其中


        CGLIB代理
            CglibAopProxy.getProxy()
                    创建及配置Enhancer getCallbacks设置拦截器
                                将advised增强器 转化为拦截器 封装到DynamicAdvisedInterceptor
                                将拦截器链加入到Callbacks中
                    生成代理类及创建代理

                    CGLIB中对于方法的拦截是通过自定义的拦截器（实现了MethodInterceptor） 加入到Callback中在调用代理时直接激活拦截器中的intercept()方法来实现的
                    DynamicAdvisedInterceptor 继承MethodInterceptor会直接调用 DynamicAdvisedInterceptor.intercept()方法
                               获取拦截器链
                                    拦截器链 = null 直接激活原方法
                                    否则 封装CglibMethodInvocation  对拦截器的逐一调用 proceed方法
                                            将此工作委托给了各个增强器，使各个增强器在内部进行逻辑实现。
                                            就是拦截器链逐一调用拦截器的intercept方法
                               返回结果



  CglibMethodInvocation 继承 ReflectiveMethodInvocation


        AnnotationAwareAspectJAutoProxyCreator
     把aop的advisor转化为拦截器链
    */



    public interface AService{
        public void a();
        public void b();

    }

    public class AServiceImpl implements AService{

        @Override
        @Transactional(propagation = Propagation.REQUIRED)
        public void a() {
            /**
             * 此处的this指向的是目标对象 调用this.b()执行的是目标对象的方法 而不是代理类增强的方法 就不会执行b事物切面，即不会执行事物增强
             * 暴露代理
             *         1 @EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
             *         2 <aop:aspectj-autoproxy expose-proxy = "true">
             */
            AService aService = (AService) AopContext.currentProxy();
            aService.b();
            this.b();
            System.out.println("执行b放法");
        }

        @Override
        @Transactional(propagation = Propagation.REQUIRED)
        public void b() {
            System.out.println("执行b放法");
        }
    }

}
