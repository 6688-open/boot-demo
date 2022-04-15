package com.dj.boot.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.btp.exception.BizException;
import com.dj.boot.btp.exception.SomsBizException;
import com.dj.boot.common.threadpoolutil.ThreadUtils2;
import com.dj.boot.common.threadpoolutil.ThreadPoolUtils;
import com.dj.boot.configuration.IdGeneratorSnowFlake;
import com.dj.boot.configuration.transaction.TxConfigBeanName;
import com.dj.boot.helper.UserHelper;
import com.dj.boot.mapper.user.UserMapper;
import com.dj.boot.mapper.useritem.UserItemMapper;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.pojo.useritem.UserItem;
import com.dj.boot.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author wangjia
 */
@Service("userServiceImpl")
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Resource
    private UserItemMapper userItemMapper;
    @Autowired
    private UserHelper userHelper;

    @Override
    public Long getCount() {
        Long count = userMapper.getCount();
        if (count <= 0) {
            throw new SomsBizException("409","查询数量不存在 异常");
        }
        return count;
    }



    /**
     * 用户展示
     * @param pageNo  当前页
     * @param user  传递参数
     * @return 返还数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> findUserAll(Integer pageNo, User user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<User> page = new Page<>(pageNo, 1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if( null != user.getSex()) {
            queryWrapper.eq("sex", user.getSex());
        }
//        if(!StringUtils.isEmpty(user.getUsername())   ) {
//            queryWrapper.and(i -> i.like("username", user.getUsername()).or().like("phone", user.getUsername()).or().like("email",user.getUsername()));
//        }
        queryWrapper.orderByDesc("id");
        IPage<User> iPage = this.page(page, queryWrapper);
        map.put("pageNo", pageNo);
        map.put("list", iPage.getRecords());
        map.put("totalPage", iPage.getPages());
        return map;
    }

    @Override
    public Page<User> findUserListPage(Page<User> page, UserDto userDto) throws Exception {
        return userMapper.findUserListPage(page, userDto);
    }

    @Override
    public User findUserByUsernameAndPassword(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        //queryWrapper.eq("phone", user.getUsername()).or().eq("email", user.getUsername()).or().eq("username", user.getUsername());
        return this.getOne(queryWrapper);
    }


    /**
     * 创建一个线程执行多个任务
     */
    @Override
    public void threadTest() {
        Date date = new Date();
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserName("1111");
        User user1 = new User();
        user1.setUserName("2222");
        User user2 = new User();
        user2.setUserName("3333");
        System.out.println(Thread.currentThread().getName());
        System.out.println("begin");
        User user3 = this.getById(1);
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        saveUser(userList, date);
        System.out.println("over");
    }

    private void saveUser(List<User> userList, Date syncCbiTime) {
        ThreadUtils2.getThreadPoolExecutor().submit(new UserSyncSave(userList, syncCbiTime));
    }



    class UserSyncSave implements Callable<Boolean> {

        private List<User> userList;
        private Date syncCbiTime;

        public UserSyncSave(List<User> userList, Date syncTime) {
            this.userList = userList;
            this.syncCbiTime = syncTime;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName());
                saveUserSync(userList, syncCbiTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }


    private void saveUserSync(List<User> userList, Date syncCbiTime){
        //业务逻辑
        this.saveBatch(userList);
    }

















    /**
     * 创建多个线程执行多个任务
     */
    @Override
    public void threadTest1() {
        System.out.println(Thread.currentThread().getName());
        List<User> list = this.list();
        getNameByList(list);
    }



    private void getNameByList(List<User> userList) {
        System.out.println("启动所有核心线程");
        //启动所有核心线程
        //ThreadPoolUtils.getThreadPoolExecutor().prestartAllCoreThreads();
        //开启多个线程执行多个任务
        for (int i = 1; i <= 10; i++) {
            ThreadPoolUtils.getThreadPoolExecutor().submit(new GetUser(userList));
        }
        try {
            System.in.read(); //阻塞主线程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class GetUser implements Callable<Boolean> {

        private List<User> userList;

        public GetUser(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = f.format(new Date());
                System.out.println("当前线程名称-------"+Thread.currentThread().getName() + "时间 " + format);
                Thread.sleep(3000); //让任务执行慢点
                User user1 = getUserById(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private User getUserById(Integer id){
        return this.getById(id);
    }












    @Autowired
    private IdGeneratorSnowFlake idGeneratorSnowFlake;

    @Override
    public String getIDBySnowFlake() {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 20; i++) {
            threadPool.submit(()->{
                System.out.println(idGeneratorSnowFlake.snowFlakeId());
            });
        }
        threadPool.shutdown();
        return "ok";
    }

    @Override
    public List<User> getList() {
        return userMapper.getList();
    }

    @Override
    public List<User> getList(Page<User> page, UserDto userDto, User user) {
        return userMapper.getList();
    }


    /**
     *  同时提交  同时回滚
     * @Transactional 事务一致性      TxConfigBeanName aop axAdvice 或着   @Transactional
     *   使用 Transactional  注释掉 TxConfigBeanName
     * @see com.dj.boot.configuration.transaction.TxConfigBeanName
     *
     *   1 @Transactional 注解应该只被应用到 public 方法上，这是由 Spring AOP 的本质决定的。
     *          1.1 如果你在 protected、private 或者默认可见性的方法上使用 @Transactional 注解，这将被忽略，也不会抛出任何异常
     *          1.2 这是因为在使用 Spring AOP 代理时，Spring 在调用在图中的 TransactionInterceptor 在目标方法执行前后进行拦截之前，
     *              DynamicAdvisedInterceptor（CglibAopProxy 的内部类）的的 intercept 方法
     *              或 JdkDynamicAopProxy 的 invoke 方法会间接调用 AbstractFallbackTransactionAttributeSource
     *            （Spring 通过这个类获取 @Transactional 注解的事务属性配置属性信息）的 computeTransactionAttribute 方法。
     *
     *              protected TransactionAttribute computeTransactionAttribute(Method method,
     *                  Class<?> targetClass) {
     *                     // Don't allow no-public methods as required.
     *                     if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
     *               return null;}
     *
     *            这个方法会检查目标方法的修饰符是不是 public，若不是 public，就不会获取@Transactional 的属性配置信息，
     *            最终会造成不会用 TransactionInterceptor 来拦截该目标方法进行事务管理。

     *   2 @Transactional(rollbackFor=Exception.class)，如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，数据库里面的数据也会回滚。
     *    在@Transactional注解中如果不配置rollbackFor属性，那么事物只会在遇到RuntimeException的时候才会回滚,
     *    加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚（写代码出现的空指针等异常，会被回滚，文件读写，网络出问题，spring就没法回滚了）
     *  3 没有配置 (rollbackFor=Exception.class)
     *          3.1 service层 @Transactional的方法中使用了try...catch关键字，没有抛出异常/抛出的不是RuntimeException 导致发生异常时没有被事务捕获，造成事务没有回滚
     *          3.2 service层 @Transactional的方法中未使用try...catch关键字，
     *
     * @param user
     * @param userItem
     * @return
     */
    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    //@Transactional(rollbackFor = {Exception.class})
    @Transactional
    public Integer userAndItemAdd(User user, UserItem userItem) throws Exception {
        boolean save = this.save(user);
        System.out.println(user.getId());
        userItem.setUserId(user.getId());
        //int  i = 10/0;
        userItem.setNickName(user.getUserName());
        userItem.setId(1);
        userItemAdd(userItem);
        return 1;
    }

    private void userItemAdd(UserItem userItem) throws Exception {
        try {
            System.out.println("异常了");
            Integer integer = userItemMapper.userItemAdd(userItem);
            //int  s = 10/0;
            System.out.println("userItemMapper.userItemAdd(userItem)");
            if (userItem.getId() == 1) {
                throw new BizException("BizException");
            }
            System.out.println("userItem开始");
            Integer i = userItemMapper.userItemAdd(userItem);
        } catch (BizException e) {
            System.out.println("处理异常"+e.getMessage());
            throw new BizException(e.getMessage());
        } catch (Exception e) {
            System.out.println("处理异常"+e.getMessage());
            throw new RuntimeException("事件单下发留言信息过程中发生异常");
        }
    }

    @Override
    @ConcurrentLock(prefix = "RECEIPTS_PERFORM_lOCK", bizNo = "#user.id", suffix = "LOCK_1")
    public String testConcurrentLock(User user) {
        return " OK ! ";
    }

    @Override
    public Integer updateBatch(List<User> list) {
        return userMapper.updateBatch(list);
    }

    @Override
    public void updateEventOrderStatusByEoNos(List<String> eoNos) {
        userMapper.updateEventOrderStatusByEoNos(eoNos);
    }

    @Override
    public Integer insertBatch(List<User> list) {
        return userMapper.insertBatch(list);
    }

    @Override
    public List<User> findUserListByCondition(UserDto userDto) throws Exception {
        System.out.println(userHelper.getUserInfo());
        return userMapper.findUserListByCondition(userDto);
    }

    @Override
    public Integer updateBatchSaleableWarehouseStock(List<User> userList, List<Integer> ids) {
        return userMapper.updateBatchSaleableWarehouseStock(userList, ids);
    }

    @Override
    public List<User> queryUserTranslateLineToColumn(UserDto userDto) {
        return userMapper.queryUserTranslateLineToColumn(userDto);
    }
}
