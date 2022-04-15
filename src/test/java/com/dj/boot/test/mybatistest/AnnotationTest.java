package com.dj.boot.test.mybatistest;

import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.mybatistest
 * @Author: wangJia
 * @Date: 2021-11-09-11-38
 */
public class AnnotationTest {

    /*
       一个接口(IUserService)有两个实现类，
       分别是(UserServiceImpl01)和(UserServiceImpl02)，
       在我们给类注入的时候@Autowired private IUserService userService
       因为首先@Autowired是按照类型注入的，也就是.class，但UserServiceImpl01和UserServiceImpl02都是IUserService类型的，
       于是Spring就会按照后面的名字(userService)在容器中查找，但发现根本没有这个名字，
       因为两个实现类在不指定名字情况下，就是首字母小写的类名，然后抛出异常：expected single matching bean but found 2。。。

       如果有两个实现类，还要使用@Autowired注解，可以将userService改成我们指定的实现类名称，
       比如UserServiceImpl01，或者不想改userService，可以加@Qualifier(value = "userServiceImpl01")，指定需要注入的实现类。
       使用@Resource注解，手动指定实现类名称。

       依赖注入：Dependency Injection，简称DI，说白了就是利用反射机制为类的属性赋值的操作

       注入就是为某个对象的外部资源赋值，注入某个对象所需要的外部资源
       IOC容器注入应用程序某个对象，应用程序所依赖的对象。

       AutowiredAnnotationBeanPostProcessor
            它间接实现InstantiationAwareBeanPostProcessor，就具备了实例化前后(而不是初始化前后)管理对象的能力，
            实现了BeanPostProcessor，具有初始化前后管理对象的能力，
            实现BeanFactoryAware，具备随时拿到BeanFactory的能力，
            这个AutowiredAnnotationBeanPostProcessor具备一切后置处理器的能力。

        @Autowired
            Autowired注解是手动注入依赖对象的，并且先通过bean类型去查找然后再根据属性名去查找
            spring容器初始化 将bean实例化 开始实例化bean finishBeanFactoryInitialization(beanFactory);
            实例化所有类非懒加载的单实例
            getBean
                doCreateBean()  创建了实例 还未给属性赋值
                        通过populateBean(beanName, mbd, instanceWrapper);来填充@Autowired和@Resouece注入的对象
            循环后置处理器
               直到AutowireAnnotationBeanPostProcess .postProcessPropertyValues  解析加有@Autowired注解的属性 就是给属性赋值
                   获取当前bean 所有依赖的对象,
                   循环遍历所有需要被依赖注入的对象
                       doResolveDependency()
                            将被依赖的bean注入 先根据type 然后根据name
                            获取注入属性的类型
                            根据注入对象的类型查询匹配的bean  接口的实现类  可能有多个 找不到则报错
                            如果匹配到多个 根据属性名称确定bean 根据beanName从多个bean中匹配 找不到 报错
                            据工厂来创建实例 获取到需要注入的bean 并返回
                      来到AutowiredAnnotationBeanPostProcessor的第611行，利用反射为此对象赋值

        在容器启动，为对象赋值的时候，遇到@Autowired注解，会用后置处理器机制，来创建属性的实例，
        然后再利用反射机制，将实例化好的属性，赋值给对象上，这就是Autowired的原理。


     @Resource
      Spring容器在每个Bean实例化之后，
      @Resource注解，默认基于ByName注入模型实现的注入，来看看具体实现，
      入口在CommonAnnotationBeanPostProcessor的postProcessProperties

        如果@Resource注解中指定了name属性，那么则只会根据name属性的值去找bean，如果找不到则报错
        如果@Resource注解没有指定name属性，那么会先判断当前注入点名字（属性名字或方法参数名字）是不是存在Bean，
        如果存在，则直接根据注入点名字取获取bean，
        如果不存在，则会走@Autowired注解的逻辑，会根据注入点类型去找Bean

    */


    /*
        @Component
        @Component作为元注解标注其它注解 @Service @Controller @Repository

            一·Spring配置文件中配置 Component-scan的方式处理@Component注解
                    当使用Component-scan时，如果指定的包里面包含了被Component注解标识的类，其会被作为Spring bean对象，自动注册到Spring容器中
                    配置一个指定的扫描路径  <context:component-scan base-package="com.xx.xx"/>
                            必须要指定其命名空间，以便Spring自动找到对应的Handler
                            ContextNamespaceHandler extends NamespaceHandlerSupport
                            init()
                                registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
                            ComponentScanBeanDefinitionParser.parse()
                                获取<context:component-scan>节点的base-package属性值 支持多个
                                解析占位符
                                构建和配置ClassPathBeanDefinitionScanner
                                使用scanner在执行的basePackages包中执行扫描，返回已注册的bean定义 scanner.doScan
                                       doScan方法源码如下
                                            遍历basePackages
                                            扫描basePackage,将符合要求的bean定义全部找出来
                                                    被@Component标注的 由于是获取的元注解，所以只要是Component注解的派生注解，
                                                    也能被获取到。（包括Configuration注解  @Service @Controller @Repository
                                            遍历所有候选的bean定义
                                            将当前遍历bean的bean定义和beanName封装成BeanDefinitionHolder
                                            注册beanDefinition
                                组件注册
                                    是通过ClassPathBeanDefinitionScanner的doScan方法返回的BeanDefinitionHolder集合，然后注册到Spring容器中。
                        注：BeanDefinitionHolder是beanName与其对应BeanDefinition的包装类

            二·纯注解配置的情况下
                refresh()方法
                    invokeBeanFactoryPostProcessors() 激活BeanFactory的处理器
                        invokeBeanFactoryPostProcessors()
                            invokeBeanDefinitionRegistryPostProcessors()
                                    遍历BeanDefinitionRegistryPostProcessor处理BeanDefinitionRegistry
                                    postProcessBeanDefinitionRegistry()方法中，实现类是ConfigurationClassPostProcessor 配置类后置处理器 （初始化AnnotatedBeanDefinitionReader时，会向容器中注册一个ConfigurationClassPostProcessor）
                                        取出容器中beanDefinitionsMap中定义的全部bean
                                        判断bean定义对象中是否有@Configuration注解 (Configuration.class)--》配置类 有就加入到集合
                                        实例化ConfigurationClassParser.parse()解析配置类
                                        遍历所有的配置类configCandidates进行解析
                                            如果发现@Import .....
                                            如果发现ComponentScans ComponentScan等注解
                                                调用ComponentScanAnnotationParser进行解析
                                                        构建和配置ClassPathBeanDefinitionScanner
                                                        获取注解上的扫描包路径
                                                        ClassPathBeanDefinitionScanner.doScan()
                                                        doScan方法源码如下
                                                            遍历basePackages
                                                            扫描basePackage,将符合要求的bean定义全部找出来
                                                                    被@Component标注的 由于是获取的元注解，所以只要是Component注解的派生注解，
                                                                    也能被获取到。（包括Configuration注解  @Service @Controller @Repository
                                                            遍历所有候选的bean定义
                                                            将当前遍历bean的bean定义和beanName封装成BeanDefinitionHolder
                                                            注册beanDefinition

     */


    /*
        @Configuration
            refresh()方法
                    invokeBeanFactoryPostProcessors() 激活BeanFactory的处理器
                            invokeBeanDefinitionRegistryPostProcessors() 该方法是将普通注解类转换成beanDefinition对象，并对标有@Configuration注解的类使用full进行标记
                                    遍历BeanDefinitionRegistryPostProcessor处理BeanDefinitionRegistry
                                    直到postProcessBeanDefinitionRegistry()方法中，实现类是ConfigurationClassPostProcessor 配置类后置处理器 （初始化AnnotatedBeanDefinitionReader时，会向容器中注册一个ConfigurationClassPostProcessor）
                                        取出容器中beanDefinitionsMap中定义的全部bean
                                        判断bean定义对象中是否有@Configuration注解 (Configuration.class)--》配置类 有就加入到集合
                                                获取类上面的注解信息，然后判断如果加了@Configuration注解，则给beanDefinition的CONFIGURATION_CLASS_ATTRIBUTE属性设为full，
                                                如果加了@Component、@ComponentScan、@Import、@ImportResource，则给beanDefinition的CONFIGURATION_CLASS_ATTRIBUTE属性设为lite
                                        实例化ConfigurationClassParser.parse()解析配置类
                                        遍历所有的配置类configCandidates进行解析
                           invokeBeanFactoryPostProcessors()
                                    此方法会执行实现了BeanFactoryPostProcessor接口的postProcessBeanFactory方法
                                    由于ConfigurationClassPostProcessor实现BeanDefinitionRegistryPostProcessor接口，
                                    而BeanDefinitionRegistryPostProcessor 又继承了BeanFactoryPostProcessor接口，
                                    所以在这篇文章的重点是讲ConfigurationClassPostProcessor的postProcessBeanFactory方法）。

                                    遍历执行所有BeanFactoryPostProcessor的postProcessBeanFactory方法
                                            直到ConfigurationClassPostProcessor的时候
                                            该方法最终调用ConfigurationClassPostProcessor的postProcessBeanFactory方法，完成会加了@Configuration注解类的增强
                                            postProcessBeanFactory
                                                    给加了@Configuration注解的配置类产生cglib代理 ，并给它设置成EnhancedConfiguration的子类
                                                            判断是否是full类型的 也就是加了@Configuration
                                                            加了@Configuration的类不能是final类型的了（使用cglib代理会继承需要增强的类，而你加了final说明这个类不能有子类了）
                                                            创建增强器 完成对全注解类的cglib代理
                                                            将增强后的cglib代理类设置成BeanDefinition的beanClass，因为在后面Spring进行实例化bean的时候，是根据BeanDefinition的beanClass来进行的，所以在此将beanClass改了之后，就能在下面使用这个代理类来进行实例化

                                                    向容器中注入ImportAwareBeanPostProcessor，它是一个bean的后置处理器，
                                                            其作用就是判断一个类是不是EnhancedConfiguration的子类，
                                                            如果是把bean工厂赋值给他，这样加了@Configuration注解的类，就可以直接去容器中获取bean




     invokeBeanFactoryPostProcessors的代码比较长，
     这个方法会调用所有实现了BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor的方法
     （BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor是继承关系），
     首先他会拿到容器内部所有的BeanFactoryPostProcessor，也就是自定义，判断是否有BeanDefinitionRegistryPostProcessor类型的，
     如果是，执行BeanDefinitionRegistryPostProcessor接口的方法，
     然后根据名称先查找所有的BeanDefinitionRegistryPostProcessor接口，
     根据实现了PriorityOrdered、Ordered和普通的依次调用其BeanDefinitionRegistryPostProcessor接口的方法，
     然后在执行是BeanDefinitionRegistryPostProcessor类型的BeanFactoryPostProcessor接口的postProcessBeanFactory方法，
     因为一个类如果是BeanDefinitionRegistryPostProcessor类型的，那么它必然属于BeanFactoryPostProcessor类型的。


    @Configuration总结：
        在Spring的生命周期中，其中会beanFactory的后置处理器，
        执行时，先执行beanFactory的子接口BeanDefinitionRegistryPostProcessor，在这一步完成会包的扫描和类上面注解信息的解析，
        然后将普通类转换成beanDefinition对象，其中标注了@Configuration注解的类，会在beanDefinition中标记为full。
        然后再执行BeanFactoryPostProcessor的postProcessBeanFactory方法，在这一步会对beanDefinition中标记为full的进行cglib代理增强，
        使用cglib代理增强就意味着需要增强类不能使用final关键字修饰，
        在这个cglib代理增强类中是间接的实现BeanFactoryAware接口，这样就可以拿到BeanFactory，
        并且会创建一个public、BeanFactory类型名称为$$beanFactory进行接收BeanFactory，
        并且cglib代理类会对方法进行拦截，这样就不用每次都去new一个对象，
        所以使用@Configuration可以保证bean的单一性，
        并且，如果标注了@Bean注解的方法返回的对象是FactoryBean类型的，Spring会进行二次拦截判断，确保是获取的bean是单一的


     */


    public static void main(String[] args) {
        //isAssignableFrom  判断父类.class.isAssignableFrom(子类.class)
        //判断继承关系
        if (User.class.isAssignableFrom(UserDto.class)) {
            System.out.println(1111111111);
        }

    }
}
