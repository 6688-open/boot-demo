package com.dj.boot.service.async.impl;

import com.dj.boot.common.util.LogUtils;
import com.dj.boot.service.async.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncServiceImpl
 * @Description TODO
 * @Author wj
 * @Date 2019/12/27 17:46
 * @Version 1.0
 **/
@Service
public class AsyncServiceImpl implements AsyncService {
    /**
     * @Async
     *       @EnableAsync 首先启动类加上 开启异步注解的功能 初始化一些异步的配置信息
     *       @Async注解的方法，只允许返回void或者Future类 否则返回nul
     *       可以作用在方法上 也可以作用在类上 如果是作用在类上  类中的所有的方法都会被代理
     *       注解里面value值  可以指定线程池的名称 可以使用自定义的线程池
     * @EnableAsync
     *         引入了AsyncConfigurationSelector
     *                  继承了AdviceModeImportSelector
     *                              类又实现了ImportSelector
     *                                      selectImports
     *                                             返回了ProxyAsyncConfiguration配置类
     *                                                      初始化了AsyncAnnotationBeanPostProcessor
     *
     * AsyncAnnotationBeanPostProcessor
     *          间接实现了 BeanFactoryAware / BeanPostProcessor
     *          BeanFactoryAware实现是可以拿到beanFactory然后做一些事情，
     *                  setBeanFactroy方法
     *                         AsyncAnnotationAdvisor  构建advisor容器
     *                                  buildAdvice 构建通知
     *                                          AnnotationAsyncExecutionInterceptor对象 实现了MethodInterceptor 核心逻辑invoke方法
     *                                                    获取异步调用所需要的线程池
             *                                                  如果你指定了线程池的名称 (@Async("xxxx"))，他就通过beanFactory去拿对应名称的线程池的bean，
     *                                                          如果你没有指定，他就去拿默认的线程池
     *                                                    执行异步方法
     *                                                    判断返回类型  只有返回是Futrue类型才会返回 只支持Future类型以及void 否则返回null
     *                                          advice方法拦截器
     *                                  buildPointcut 构建切入点
     *                                          Set集合，这个集合放入的是Async注解
     *                                          被Async标记的方法
     *                       advisor给创建好之后就赋值给了当前类的advisor属性
     *          BeanPostProcessor更是被很多源码广泛应用，通过实现它可以在Bean初始化的前后对bean做处理
     *                  postProcessAfterInitialization
     *                          在bean实例化之后，通过ProxyFactory创建出一个代理对象返回
     *                          判断当前bean(AsyncServiceImpl)  是否适用增强器
     *                                  获取增强器的切点方法 匹配当前bean的所有的接口和实现类的所有的方法
     *                                  有一个匹配的就返回适用需要创建代理
     *                                  就是匹配所有的方法是否有@Async注解 且与切点方法.matches()
     *                           创建ProxyFactory
     *                                  复制属性
     *                                  设置目标类
     *                                  ProxyTargetClass 默认false 否的话 就是JDK代理 设置代理接口  true 就是CGLIB  不需要。。
     *                                  设置增强器
     *                                  创建代理 （和AOP类似 判断是JDK代理还是CGLIB）
     *                                  当执行代理类时 自动调用 （和AOP类似） 参考com.dj.boot.test.mybatistest.AopTest
     *
     *
     *
     *
     * spring 在扫描bean的时候会扫描方法上是否包含@Async注解，如果包含，spring会为这个bean动态地生成一个子类（即代理类，proxy），
     * 代理类是继承原来那个bean的。并为有注解的方法加上异步效果
     * 此时，当这个有注解的方法被调用的时候，实际上是由代理类来调用的，代理类在调用时增加异步作用。
     * 然而，如果这个有注解的方法是被同一个类中的其他方法调用的，那么该方法的调用并没有通过代理类，
     * 而是直接通过原来的那个bean，所以就没有增加异步作用，我们看到的现象就是@Async注解无效。
     * @return
     */
    @Async
    @Override
    public String testAsync() {
        try {
            Thread.sleep(10000);
            LogUtils.info("testAsync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Async";
    }
}
