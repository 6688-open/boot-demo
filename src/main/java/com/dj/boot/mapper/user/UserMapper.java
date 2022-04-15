package com.dj.boot.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.boot.common.base.page.PageRequestParam;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.dao.DataAccessException;

import java.util.Dictionary;
import java.util.List;

/**
 * @author wangjia
 */
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into  #")
    void addUser();

    @SelectProvider(method = "findUserById",type = UserMapperSql.class)
    User findUserById(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    User selectByPrimaryKey(@Param("fieldName")String fieldName, @Param("id")Integer id);

    /**
     * 新增对象
     * @param user
     * @return
     */
    int insertSelective(User user);
    /**
     * user展示
     */
    Page<User> findUserListPage(Page page, @Param("userDto") UserDto userDto) throws Exception;

    Long getCount();

    /**
     * getList
     * @return
     */
    List<User> getList();

    /**
     * 没有加注解 @Param("useList")     collection 必须list
     * 批量更新
     * @param list
     * @return
     */
    Integer updateBatch( List<User> list);
    void updateEventOrderStatusByEoNos(@Param("eoNos") List<String> eoNos);

    /**
     * 批量新增
     * @param list
     * @return
     */
    Integer insertBatch( List<User> list);

    /**
     * user展示
     */
    List<User> findUserListByCondition( @Param("userDto") UserDto userDto) throws Exception;
    /**
     * 自定义分页插件
     */
    List<User> findUserListForPageByCondition(@Param("page") PageRequestParam page, @Param("userDto") UserDto userDto) throws Exception;

    /**
     * 批量更新库存
     * @param userList
     * @param ids
     * @return
     */
    Integer updateBatchSaleableWarehouseStock(@Param("userList")List<User> userList, @Param("ids")List<Integer> ids);

    /**
     * 行转列  动态列
     * @param userDto
     * @return
     */
    List<User> queryUserTranslateLineToColumn(@Param("userDto") UserDto userDto);




}
