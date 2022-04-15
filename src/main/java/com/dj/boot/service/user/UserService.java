package com.dj.boot.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.pojo.useritem.UserItem;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wangjia
 */
public interface UserService extends IService<User> {
    /**
     * getCount
     * @return
     */
    Long getCount();


    /**
     * 创建一个线程执行任务
     */
    void threadTest();

    /**
     * 创建多个线程执行多个任务
     *
     */
    void threadTest1();

    /**
     *  分页查询
     * @param pageNo  当前页
     * @param user  传递参数
     * @return 返还数据
     * @throws Exception
     */
    Map<String, Object> findUserAll(Integer pageNo, User user)throws Exception;

    /**
     * 根据条件查询
     * @param userDto
     * @return
     * @throws Exception
     */
    List<User> findUserListByCondition(UserDto userDto) throws Exception;

    /**
     * findUserList  分页查询
     * @param page
     * @param userDto
     * @return
     * @throws Exception
     */
    Page<User> findUserListPage(Page<User> page, UserDto userDto) throws Exception;

    /**
     * findUserByUsernameAndPassword
     * @param userName
     * @return
     */
    User findUserByUsernameAndPassword(String userName);


    /**
     * 获取全局Id  雪花算法
     * @return
     */
    String getIDBySnowFlake();

    /**
     * getList
     * @return
     */
    List<User> getList();

    /**
     * findUserList
     * @param page
     * @param userDto
     * @param user
     * @return
     */
    List<User> getList(Page<User> page, UserDto userDto, User user);


    /**
     * UserAndItemAdd 添加UserAndItem
     * @param user
     * @param userItem
     * @return
     */
    Integer userAndItemAdd(User user, UserItem userItem) throws Exception;

    /**
     * 接口测试ConcurrentLock
     * @param user
     * @return
     */
    String testConcurrentLock(User user);



    /**
     * 批量更新
     * @param list
     * @return
     */
    Integer updateBatch( List<User> list);
    void updateEventOrderStatusByEoNos(List<String> eoNos);

    /**
     * 批量新增
     * @param list
     * @return
     */
    Integer insertBatch( List<User> list);



    /**
     * 批量更新库存
     * @param userList
     * @param ids
     * @return
     */
    Integer updateBatchSaleableWarehouseStock(List<User> userList, List<Integer> ids);


    List<User> queryUserTranslateLineToColumn(@Param("userDto") UserDto userDto);



}
