package com.dj.boot.test.mybatistest;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.base.page.PageRequestParam;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.mapper.user.UserMapper;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import net.sf.jsqlparser.statement.execute.Execute;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 *
 * https://www.jianshu.com/p/ada025f97a07 原文地址
 *
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test
 * @Author: wangJia
 * @Date: 2021-10-27-20-29
 */
public class MybatisTest extends BaseController {
    public static void main(String[] args) {
        try {
            // 1. 读取配置
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // 2. 创建SqlSessionFactory工厂
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 3. 获取sqlSession  CachingExecutor 二级缓存包装字类执行器
            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);//创建执行器 创建StatementHandler 创建Statement对象
            //SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.REUSE);//可重用执行器重用的就是Statement对象，以key-value形式来缓存Statement对象
            //SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH); // commit时一次性提交sql
            //直接用sqlSession发送sql语句
            //User user1 = sqlSession.selectOne("com.dj.boot.mapper.user.UserMapper.selectByPrimaryKey", 1);
            // 4. 获取Mapper
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 5. 执行接口方法
            //queryUserForPage(userMapper);//自定义分页插件
            User user = userMapper.selectByPrimaryKey("user_name",1);
            /*sqlSession.commit();
            //开启不同sqlSession 测试二级缓存
            SqlSession sqlSession2 = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
            UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
            User user2 = userMapper2.selectByPrimaryKey("user_name",1);
            sqlSession2.commit();*/
            //System.out.println("userInfo = " + JSONUtil.toJsonStr(user2));
            //SimpleExecutor  从日志打印看到我们执行了2次sql，Statement编译了2次
            //ReuseExecutor   重用的就是Statement对象，以key-value形式来缓存Statement对象，避免了同一个sql编译多次，从而提高性能
//                            从日志打印看到我们执行了2次sql，Statement编译了1次
//            for(Integer i = 1; i < 3; i++){
//                User user2 = userMapper.selectByPrimaryKey(i);
//                System.out.println("userInfo = " + JSONUtil.toJsonStr(user2));
//            }
            //BatchExecutor 日志打印看到BatchExecutor 就是在commit时一次性提交sql，而不是发送一次，执行一次。
            //操作是在最后commit时，才会插入数据到数据库中去，插入时间是一致的
//            for(long i = 1; i < 3; i++){
//                User userInfo = new User();
//                userInfo.setUserName(2000+i+"_nick");
//                userInfo.setPassword(2000+i+"_real");
//                userMapper.insertSelective(userInfo);
//                // 模拟插入间隔
//                Thread.sleep(1000);
//            }
            //CachingExecutor 虽然我们执行了两次sql，但是参数和sql语句都是一样的，所以一次的查询结果会被缓存，第二次查询的时候直接从缓存中去取。
//            CachingExecutor不会执行具体的更新和查询操作，而是在执行更新操作的时候先清除下缓存，在执行查询操作的时候先从缓存中查找，如果命中缓存直接返回
//            for(Integer i = 1; i < 3; i++){
//                User user2 = userMapper.selectByPrimaryKey(1);
//                System.out.println("userInfo = " + JSONUtil.toJsonStr(user2));
//            }

            System.out.println("-------开始提交事务--------- ");
            // 6. 提交事物
            sqlSession.commit();
            System.out.println("-------结束提交事务--------- ");
            // 7. 关闭资源
            sqlSession.close();
            inputStream.close();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    /*
     spring整合mybatis
        SqlSessionFactoryBean
                实现了 FactoryBean  和 InitializingBean
                InitializingBean 实现了此接口的bean会在初始化时调用他的afterPropertiesSet方法的逻辑初始化
                            afterPropertiesSet
                                    校验dateSource
                                    校验SqlSessionFactoryBuilder
                                    构建SqlSessionFactory
                                            XMLConfigBuilder解析配置文件构建 configuration
                                            解析mapperLocations 缓存MapperProxyFactory
                                            构建SqlSessionFactory sqlSessionFactoryBuilder.build(configuration)
                FactoryBean  实现了此接口 当getBean() 时 获取getObject()返回的实例 获取初始化后的SqlSessionFactory


        MapperFactoryBean (一个UserMapper对应一个MapperFactoryBean) spring启动 @Autowired 属性填充时 实例化进而获取MapperFactoryBean.getObject 返回代理实现类
                实现了 FactoryBean  和 InitializingBean
                InitializingBean 实现了此接口的bean会在初始化时调用他的afterPropertiesSet方法的逻辑初始化
                            afterPropertiesSet
                                    checkDaoConfig()
                                            校验sqlSession不为空 sqlSession根据接口创建映射器的代理
                                            校验SqlSessionFactory
                                            校验mapperInterface

                FactoryBean  实现了此接口 当getBean() 时 获取getObject()返回的实例
                             getSqlSession().getMapper(this.mapperInterface);
                             获取sqlSession 根据接口创建映射器的代理 调用方法  返回一个接口的代理实现

       MapperScannerConfigurer
                如果映射器较多   用MapperFactoryBean配置效率低下
                使用MapperScannerConfigurer 扫描特定包 自动成批创建映射器  就不需要为每个接口在spring中配置声明了
                它将会查找类路径下的映射器并自动将它们创建成 MapperFactoryBean。

                实现了 InitializingBean 和 BeanDefinitionRegistryPostProcessor
                InitializingBean 实现了此接口的bean会在初始化时调用他的afterPropertiesSet方法的逻辑初始化
                            afterPropertiesSet
                                    校验basePackage

                BeanDefinitionRegistryPostProcessor spring在初始化时会调用实现了此接口
                              postProcessBeanDefinitionRegistry
                                     processPropertyPlaceHolders
                                            如果value=${}
                                            this.processPropertyPlaceHolders = true
                                            如果value${XXX} PropertyPlaceholderConfigurer实现了BeanFactoryPostProcessors
                                            等其他的配置文件解析完成(bean工厂载入所有的bean配置)之后才会对其解析
                                            (其他的配置文件将属性文件的属性加载到内存中)后续的解析直接从内存获取
                                            就是通过BeanFactoryPostProcessor beanFactory加载Beanfinition之后 在bean初始化(实例化)之前获得配置信息，遍历Beanfinition中的占位符进行替换
                                            但是BeanDefinitionRegistries(注册Beanfinition)先于BeanFactoryPostProcessors 假如basePackage value${XXX} 是在内存中获取不到的
                                            所以processPropertyPlaceHolders意义是手动找出PropertyResourceConfigurers进行调用 获取属性


                                     创建ClassPathMapperScanner
                                            设置属性
                                            registerFilters 设置过滤器 如果用户设置了某些属性 来控制扫描结果
                                                markerInterface：基于接口的过滤器，继承了指定的这个接口dao才会被扫描器扫描，
                                                annotationClass：配置了该注解的dao才会被扫描器扫描
                                                如果两者都被指定了,加入到接口中的映射器会匹配两种标准。默认情况下,这两个 属性都是 null,所以在基包中给定的所有接口可以作为映射器加载

                                            scan
                                                扫描basePackages下的java文件 根据文件路径拼接成绝对路径生成对应的BeanDefinition
                                                processBeanDefinitions
                                                    遍历BeanDefinition设置属性
                                                            setBeanClass   bean的class类型是MapperFactoryBean
                                                            设置mapperInterface

    */



    /*  缓存MappedStatement
            MappedStatement  一个select标签会对应一个MappedStatement对象
            Mybatis会在一开始加载的时候将每个标签中的sql语句包装成MappedStatement对象，
            并以类全路径名+方法名为key，MappedStatement为value缓存在内存中。
            在执行对应的方法时，就会根据这个唯一路径找到TTestUserMapper.xml这条sql语句并且执行返回结果。
    */

    /*缓存MapperProxyFactory
           mybatis 解析配置文件 mappers时 namespace   configuration.addMapper
                                                            configuration.mapperRegistry.addMapper
                                                                    knownMappers.put(type, new MapperProxyFactory(type));

           spring下  初始化SqlSessionFactoryBean的 afterPropertiesSet方法
                   SqlSessionFactoryBean
                        实现了 FactoryBean  和 InitializingBean
                        InitializingBean 实现了此接口的bean会在初始化时调用他的afterPropertiesSet方法的逻辑初始化
                                    afterPropertiesSet
                                            校验dateSource
                                            校验SqlSessionFactoryBuilder
                                            构建SqlSessionFactory
                                                    XMLConfigBuilder解析配置文件构建 configuration
                                                    解析mapperLocations 缓存MapperProxyFactory
                                                    构建SqlSessionFactory sqlSessionFactoryBuilder.build(configuration)
                   解析mapperLocations 缓存MapperProxyFactory
                                xmlMapperBuilder.parse()
                                        configuration.addMapper(boundType)
                                                    configuration.mapperRegistry.addMapper
                                                         knownMappers.put(type, new MapperProxyFactory(type));
    */


    /*获取MapperProxyFactory
        mapper 通过接口名 configuration.getMapper-
                            configuration.mapperRegistry.getMapper
                                knownMappers 的map 获取代理工厂MapperProxyFactory
                         创建代理类MapperProxy
                               执行mapper方法时 直接调用代理类 invoke方法
                                根据mapperInterface + sqlSession.getConfiguration 获取MappedStatement 封装创建MapperMethod
                                执行MapperMethod.execute 方法  command.getType() INSET.. / command.getName() 全路径+key
                                调用 sqlSqlSession 方法 默认 DefaultSqlSession
    */

    //sqlSqlSession 执行通过 全路径+key 获取

    /*获取MappedStatement
            DefaultSqlSession 的方法 全路径+key 获取 从configuration.getMappedStatement获取MappedStatement
                                     当入参 调用 BaseExecutor doUpdate/doQuery
    */

    /*调用指定执行器Executor doUpdate/doQuery
            BaseExecutor 一级缓存
            CachingExecutor 二级缓存开启 包装装饰子类执行器 维护事务缓存管理器TransactionalCacheManager
            SimpleExecutor中根据入参MappedStatement 创建StatementHandler  创建Statement 传递sql
            ReuseExecutor 中根据入参MappedStatement 创建StatementHandler  缓存/创建Statement 传递sql 缓存有的话不需要编译sql
            BatchExecutor
                         doQuery  根据入参MappedStatement 创建StatementHandler  创建Statement 传递sql
                         doUpdate 根据入参MappedStatement 创建StatementHandler  分别创建Statement 批次记录Statement和结果 传递sql  批量执行 handler.batch(stmt)
    */

    /* 读取sql语句流程
        1 根据mapper 创建代理对象 当调用mapper方法时
                        2 自动调用代理类的MapperProxy.invoke()
                            3 MapperMethod.execute()
                                4 调用sqlSession的 query/update/selectOne/selectList/inset 方法
                                       5 获取MappedStatement 调用执行器Executor update/query
                                                update .......
                                                query 只是query方法时默认一级缓存开启会 BoundSql boundSql = ms.getBoundSql(parameter); 解析sql语句
                                            6 SimpleExecutor子类实现的doQuery/doUpdate  StatementHandler Statement 传递sql

        5步时 MappedStatement里面的sqlSource 维护的mapper.xml的原始语句
              创建StatementHandler时
                    boundSql不为空 直接封装到StatementHandler.BoundSql 属性中 (query时已经解析了一遍 参数boundSql不为空)
                    boundSql为空 (update方法时) 调用MappedStatement里面的getBoundSql()方法  返回解析后的语句 封装到StatementHandler.BoundSql 属性中
              StatementHandler 创建Statement 将替换占位符?
    */

    /*解析#{} ${}
        MappedStatement里面的getBoundSql()方法
                 BoundSql boundSql = this.sqlSource.getBoundSql(parameterObject);

        SqlSource作为一个接口，它只有一个作用就是获取BoundSql对象
                如果sql中只包含#{}参数，不包含${}或者其它动态标签，那么创建SqlSource对象时则会创建RawSqlSource，否则创建DynamicSqlSource对象。
                DynamicSqlSource 会优先解析${}标签，然后解析#{}标签。
                            其中${}会被解析为参数内容，不会加上双引号，
                            而#{}会被解析为?，并且参数会加上双引号 -----> "?"

    */

    /*真实数据替换?
        创建Statement
              prepareStatement方法
                  handler.parameterize(stmt) //对创建的Statement对象设置参数，即设置SQL 语句中 ? 设置为指定的参数
                  ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
                  String countSql = "select count(1) count from dj_user where id = ?"
	              PreparedStatement statement = connection.prepareStatement(countSql);
	              parameterHandler.setParameters(ps);
    */



    /*一级缓存
          关闭二级缓存 开启一级缓存
          使用相同SqlSession 查询两次相同的sql  只会打印一次sql日志 第二次是缓存里面获取的
    *     一级缓存是SqlSession的级别的，不同的SqlSession间不能共享缓存
    */
    /*二级缓存
          关闭一级缓存 开启二级缓存
          使用不同SqlSession 查询两次相同的sql  只会打印一次sql日志 第二次是缓存里面获取的
          CachingExecutor的commit方法，在commit的时候才会将缓存放到真正的缓存中，
          这样做的目的就是为了防止不同SqlSession间的脏读，一个SqlSession读取了另一个SqlSession还未提交的数据。
    *     二级缓存是Mapper的级别的，不同的SqlSession间可以共享缓存
    */


    /*
    * MyBatis提供的这两种管理机制来管理事务
    *   Transaction有两个实现JdbcTransaction和ManagedTransaction，提供的这两种管理机制来管理事务
    *         JdbcTransaction：使用Jdbc中的java.sql.Connection来管理事务，包括提交回滚
    *        ManagedTransaction：在这种机制下，MyBatis不会管理事务，而是交由程序的运行容器(weblogic，tomcat)来进行管理。
    *
    *  在mybatis-config.xml中可以配置事务管理的类型，其中transactionManager的类型配置为JDBC，将会以JdbcTransaction管理机制来管理事务。
    *  XMLConfigBuilder 中的environmentsElement会解析transactionManager 并且会创建一个TransactionFactory类型的事务工厂，这个工厂的作用就是来创建Transaction事务对象
    *               TransactionFactory 作为对象工厂，其功能就是创建一个Transaction对象
    *                           从已有的连接中创建Transaction对象
    *                           根据数据源，数据库隔离级别，自动提交创建Transaction对象
    *               TransactionFactory 有两个实现，
    *                           一个是JdbcTransactionFactory，
    *                           另一个是ManagedTransactionFactory，
    *                           他们分别创建出来的就是JdbcTransaction和ManagedTransaction对象
    *  上面创建完TransactionFactory后，就会被Environment引用。
    *  接下来在获取SqlSession的时候，会根据事务工厂创建一个Transaction事务对象，根据这个对象，再创建具体的Executor，最终创建出创建SqlSession对象
    *  对JDBC connection的包装 处理数据库连接的生命周期 包括创建，准备，提交，回滚，关闭
    */

    /**
     * 分页插件测试
     * @param userMapper
     * @throws Exception
     */
    private static void queryUserForPage(UserMapper userMapper) throws Exception {
        PageRequestParam<User> page = new PageRequestParam();
        page.setiDisplayLength(10);
        page.setiDisplayStart(0);
        page.setSortColName("userName");
        page.setSortDir("asc");
        UserDto userDto = new UserDto();
        userDto.setEoType("4");
        List<User> list = userMapper.findUserListForPageByCondition(page, userDto);
        System.out.println(list.size());

        page.setAaData(list);
    }








    /*
     1 解析annotation-driven 解析自定义标签  AnnotationDrivenBeanDefinitionParser.parse()
     2 创建代理
               configureAutoProxyCreator
                          自动代理创建器 注册InfrastructureAdvisorAutoProxyCreator的bean
                          事务属性   创建AnnotationTransactionAttributeSource的bean
                          事务拦截器 创建TransactionInterceptor 的bean
                          事务增强器 创建TransactionAttributeSourceAdvisor的bean

               InfrastructureAdvisorAutoProxyCreator  父类AbstractAutoProxyCreator.postProcessAfterInitialization
                        实现了SmartInstantiationAwareBeanPostProcessor  extends InstantiationAwareBeanPostProcessor
                        其他的bean(userService)实例化时 会调用 postProcessorAfterInitialization方法

               postProcessorAfterInitialization
                    wrapIfNecessary
                        获取指定bean对应增强器
                                从IOC容器获取候选增强器  Advisor.class 包括被创建的TransactionAttributeSourceAdvisor的bean
                                匹配适用的增强器
                                        当前类对应的所有接口及类的方法遍历method
                                        根据方法提取事务标签AnnotationTransactionAttributeSource.annotationParsers (初始化时初始化annotationParsers)
                                        存在事务并解析Transactional并返回TransactionAttribute 是否适用增强器
                        获取的增强器创建代理


     3 代理类调用时会调用事务增强器TransactionAttributeSourceAdvisor 的增强方法 进而调用其中的TransactionInterceptor进行增强 调用invoke方法
       一个Advisor中真正对方法调用起作用的是Advice，
       而BeanFactoryTransactionAttributeSourceAdvisor使用的Advice就是ProxyTransactionManagementConfiguration中创建的最后一个Bean：TransactionInterceptor

     4 事务增强器 TransactionInterceptor.invoke()  声明式/编程式
                获取事务属性  TransactionAttribute
                加载配置的    TransactionManager
                创建事务信息  TransactionInfo
                        根据TransactionAttribute获取事务
                                创建事务实例 doGetTransaction  如果当前线程存在dataSource连接 直接使用
                                当前线程已经存在事务
                                        PROPAGATION_NEVER   抛异常
                                        PROPAGATION_NOT_SUPPORTED 将当前事物挂起  挂起目的记录原事物状态 后续操作对事物的恢复
                                        PROPAGATION_REQUIRES_NEW  新建事物
                                                    doBegin
                                                        设置隔离级别和只读
                                                        更改自动提交
                                                        设置当前线程是否存在事务
                                                    新事物绑定当前线程
                                        PROPAGATION_NESTED 嵌入式事物处理
                                                    spring允许嵌入式事物 设置保存点作为异常处理的回滚
                                                    其他方式 JTA 无法使用保存点(类似REQUIRES_NEW) 出现异常 由spring的异常处理机制处理
                                当前线程不存在事务
                                        PROPAGATION_MANDATORY 抛异常
                                        PROPAGATION_REQUIRED || PROPAGATION_REQUIRES_NEW || PROPAGATION_NESTED) 需要新建事务
                                                    doBegin
                                                        设置隔离级别和只读
                                                        更改自动提交
                                                        设置当前线程是否存在事务
                                                    新事物绑定当前线程

                        构建TransactionInfo 记录事物状态
                执行目标增强方法
                异常回滚 completeTransactionAfterThrowing(txInfo, ex);
                        回滚条件判断当前是否有事物 有事物则回滚
                            抛出的异常是RuntimeException/Error 类型的 回滚 eg:@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
                            否则会提交
                            回滚
                                事物已经完成 再次回滚抛出异常
                                有保存点 也就是当前事物是单独的线程则会退到保存点 常用于嵌套事物 内嵌事物不会引起外部的事物回滚
                                如果当前事物是独立的新事物 则直接回退        常用于单独的事物
                                如果当前事物不是独立的新事物 只能标记状态 等到事物链执行完毕后统一回滚  多数是JTA
                            事物处理首尾
                                设置状态 避免重复调用
                                新事物需要清除资源
                                在事物执行前有事物挂起 那么当前事物执行完需要将挂起的事物恢复

                提交事务 commitTransactionAfterReturning(txInfo);
                        如果事物链中已经标记回滚 不会尝试提交 直接回滚
                        提交
                            如果存在保存点 则清除保存点信息
                            如果是独立的新事物则直接提交
                            提交过程出现异常 直接回滚

                            有保存点或者非新事物不会提交
                                        主要考虑嵌套事物  内嵌事物开始之前会设置保存点
                                        内嵌事物出现异常根据保存点信息回滚
                                        内嵌事物没有异常 也不会单独提交 由外层事物负责提交
                                        如果有保存点信息就不是最外层事物 不做保存

              底层数据库连接进行回滚 提交










    */



    /*
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    1 事务的传播属性（Propagation）
            1) REQUIRED ，这个是默认的属性
                    如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。
                    被设置成这个级别时，会为每一个被调用的方法创建一个逻辑事务域。
                    如果前面的方法已经创建了事务，那么后面的方法支持当前的事务，如果当前没有事务会重新建立事务。
            2) MANDATORY
                    支持当前事务，如果当前没有事务，就抛出异常。
            3) NEVER
                    以非事务方式执行，如果当前存在事务，则抛出异常。
            4) NOT_SUPPORTED
                    以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
            5) REQUIRES_NEW
                    新建事务，如果当前存在事务，把当前事务挂起。
            6) SUPPORTS
                    支持当前事务，如果当前没有事务，就以非事务方式执行。
            7) NESTED
                    支持当前事务，新增Savepoint点，与当前事务同步提交或回滚。
                    嵌套事务一个非常重要的概念就是内层事务依赖于外层事务。
                    外层事务失败时，会回滚内层事务所做的动作。
                    而内层事务操作失败并不会引起外层事务的回滚。
    */

    /*事务隔离级别:@Transactional(isolation = Isolation.READ_UNCOMMITTED)
        READ_UNCOMMITTED
                读取未提交数据(会出现脏读, 不可重复读) 基本不使用
        READ_COMMITTED
                读取已提交数据(会出现不可重复读和幻读)
        REPEATABLE_READ
                可重复读(会出现幻读)
        SERIALIZABLE
                串行化
    MYSQL: 默认为REPEATABLE_READ级别 重复度
    SQLSERVER: 默认为READ_COMMITTED
    */


    /*
    * 创建StatementHandler时会创建paramHandler 和ResultHandler
    * Statement执行完 ResultHandler根据mapper配置的resultMap和ResultType进行结果集处理
    * */
}
