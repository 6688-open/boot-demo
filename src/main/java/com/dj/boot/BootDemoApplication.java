package com.dj.boot;

import com.dj.boot.annotation.Login;
import com.dj.boot.common.util.mapper.MapperKey;
import com.dj.boot.common.util.mapper.MapperSign;
import com.dj.boot.configuration.XmlConfiguration;
import com.dj.boot.configuration.applicationcontext.CustomerApplicationContext;
import com.dj.boot.configuration.applicationcontext.MyClassPathXmlApplicationContext;
import com.dj.boot.service.user.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.locks.LockSupport;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.BootDemoApplication
 * @Author: wangJia
 * @Date: 2020-07-03-10-30
 */
//@EnableTransactionManagement 开启事务管理   SpringBootApplication里面导入 META-INF-spring.factories 118行 导入了此组件 不需要再开启
//@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = false) //开启AOP的功能  SpringBootApplication里面导入 META-INF-spring.factories  23行 导入了此组件 不需要再开启
@SpringBootApplication
//markerInterface：基于接口的过滤器，继承了指定的这个接口dao才会被扫描器扫描，
//annotationClass：配置了该注解的dao才会被扫描器扫描，
//如果两者都被指定了,加入到接口中的映射器会匹配两种标准。默认情况下,这两个 属性都是 null,所以在基包中给定的所有接口可以作为映射器加载
//@MapperScan(value = "com.dj.boot.mapper", annotationClass = MapperKey.class, markerInterface = MapperSign.class)  // 两个保留一个就可以 com.dj.boot.configuration.mybatisplus.MybatisPlusConfig
//开启定时
@EnableScheduling
@EnableAsync
/**
 * 导入xml文件 也可以配合@Configuration 新增配置类  eg: XmlConfiguration
 *  1 @ImportResource 直接导入xml文件
 *  2 可以配合@Configuration 新增配置类 XmlConfiguration 需要加 @Configuration注解 无需导入配置类
 *  3 @Import 导入配置类  可以不加@Configuration注解
 */
//@ImportResource(locations = {"classpath:/spring/spring-main.xml"})
@Import(XmlConfiguration.class)
@PropertySources(value = {@PropertySource(value = {"classpath:prop/important.properties"}, encoding = "utf-8", ignoreResourceNotFound = true)})
public class BootDemoApplication {

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(BootDemoApplication.class);
//        springApplication.setApplicationContextClass(CustomerApplicationContext.class);
//        springApplication.run();
        SpringApplication.run(BootDemoApplication.class, args);
        new Thread(()-> LockSupport.park(BootDemoApplication.class)).start();
        System.out.println("BootDemoApplication start successfully......");
    }


    /**
     * bean 注入
     * @return
     */
    @Bean
    public Redisson redisson () {
        //单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        return (Redisson)Redisson.create(config);
    }


    /*ApplicationContext 初始化

            初始化SpringApplication 做一些必要的属性初始化和赋值
                配置primarySources
                配置环境是否为web环境
                创建初始化构造器 setInitializers
                创建应用监听器
                配置应用主方法所在类（就是main方法所在类）

            run
                第一步：获取并启动监听器
                第二步：准备环境
                    prepareEnvironment
                        spring.profiles.active属性配置Environment对象中的activeProfile（比如dev、prod、test）
                第三步：打印banner，就是启动的时候在console的spring图案
                第四步：创建spring容器
                第五步：spring容器前置处理
                    prepareContext
                        设置上下文环境
                        执行所有ApplicationContextInitializer对象的initialize方法（这些对象是通过读取spring.factories加载）
                        发布上下文准备完成事件到所有监听器
                第六步：刷新容器 刷新应用程序上下文
                    refreshContext
                            refresh()
                                prepareRefresh()
                                    初始化准备工作 对系统属性或者环境变量进行准备或者验证
                                obtainFreshBeanFactory()
                                    初始化BeanFactory 并进行XML文件解析 提取注册bean
                                prepareBeanFactory(beanFactory);
                                    对BeanFactory进行各种功能填充
                                postProcessBeanFactory(beanFactory);
                                    子类覆盖扩展方法
                                invokeBeanFactoryPostProcessors(beanFactory);
                                    激活各种BeanFactory的处理器
                                registerBeanPostProcessors(beanFactory);
                                    注册拦截bean创建的bean处理器 这里只是注册 真正的调用在getBean时
                                initMessageSource();
                                    为上下文初始化Message源 即对不同语言的消息体进行国际化处理
                                initApplicationEventMulticaster();
                                    初始化应用消息广播器 并放入到 ApplicationEventMulticaster bean中
                                onRefresh();
                                    留给子类扩展实现
                                registerListeners();
                                    在所有注册的bean中查找 listener bean 注册到消息广播器中
                                finishBeanFactoryInitialization(beanFactory);
                                    初始化剩下非懒加载的单实例的bean
                                finishRefresh();
                                    完成刷新过程 通知生命周期处理器lifecycleProcessor 刷新过程 同时发出ContextRefreshEvent通知别人

                第七步：spring容器后置处理
                第八步：发出启动结束事件
                第九步：执行runner的run方法
                第十步：异常处理，
     */


    /*简化了配置，内置了tomcat
      自动配置实现
            @SpringBootApplication
                @SpringBootConfiguration
                        继承了@Configuration 表明当前是注解配置类
                @ComponentScan
                        扫描包路径
                @EnableAutoConfiguration
                        @AutoConfigurationPackage 自动配置包
                                @Import(AutoConfigurationPackages.Registrar.class) 导入组件AutoConfigurationPackages.Registrar
                                    AutoConfigurationPackages.Registrar
                                            registerBeanDefinitions
                                                   注册当前主程序同级以及子集的包组件 其实就是注册了一个Bean的定义
                                                   获取注解的类，这里是SpringDemoApplication
                                                   获取同级的package以及子package
                                                   扫描这些package，并将组件导入到spring管理的容器中，之后可以被用作spring.factories配置文件的key

                        @Import(AutoConfigurationImportSelector.class) 导入自动配置组件
                                通过Spring底层注解@Import，给容器导入一个组件 AutoConfigurationImportSelector.class 实现了DeferredImportSelector
                                实现了 ImportSelector接口 ImportSelector接口中的selectImports方法所返回的类将被Spring容器管理起来。
                                将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器中
                                selectImports() 获取需要导入的全限定类名列表
                                    配置参数spring.boot.enableautoconfiguration 是否打开 默认人打开
                                    第一步：加载配置文件META-INF/spring-autoconfigure-metadata.properties，从中获取所有支持自动配置的信息
                                            spring-autoconfigure-metadata.properties文件格式如下：
                                            自动配置的类全名.条件=值
                                            这个值就是key中全类名所依赖的条件，如果类路径下不存在这个Java类，那么这个自动配置的全类名对应的bean将会被过滤掉。
                                    第二步：读取META-INF/spring.factories下的EnableAutoConfiguration的配置 获取自动配置对象 对象包含需要配置的全限定类名列表和需要排除的列表
                                            getCandidateConfigurations
                                                getCandidateConfigurations()用来获取默认支持的自动配置类名列表，
                                                Spring Boot在启动的时候，使用内部工具类SpringFactoriesLoader，
                                                查找classpath上所有jar包中的META-INFspring.factories，
                                                找出其中key为org.springframework.boot.autoconfigure.EnableAutoConfiguration的属性定义的工厂类名称，
                                                将这些值作为自动配置类导入到容器中，自动配置类就生效了
                                                筛选
                                                    从spring.factories获取到的Auto Configure信息都是支持自动配置的类，
                                                    这些类的使用都是有依赖条件的，如果某个类的这些依赖条件不存在，那这个类也就没必要加载了，应当排除。
                                                    而这些依赖条件是在第一步的spring-autoconfigure-metadata.properties文件中配置好的。

                                                    根据spring-autoconfigure-metadata.properties中的依赖配置。
                                                    要加载的类上的condition注解。
                                                    如@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
                                                    表示需要在类路径中存在SqlSessionFactory.class、SqlSessionFactoryBean.class这两个类才能完成自动注册。

              总结  springboot 内部对大量的第三方库 或Spring内部库进行了默认配置 配置是否生效
                    取决于项目中是否引入了对应库所需要的依赖 如果有就默认生效

              容器启动 run 初始化getSpringFactoriesInstances
                            获取所依赖的pom里面的所有的jar下的META-INFspring.factories 然后缓存下来

              这就是Spi的加载机制，可以通过配置的方式实现和业务代码的解耦，需要增加时直接配置到文件内。

              这一步是在容器启动的时候，加载所有的factoryname的值到缓存，所有jar下的 包括自动配置的。
              总结：
              自动配置的实现就是spring启动的时候，加载spring.factories的配置，
              解析每一个Configuration类，根据condition加载自动配置类，
              根据condition条件实例化bean对象，然后维护到spring容器中


              https://www.cnblogs.com/warrior4236/p/13281331.html 参考文档


     */

}
