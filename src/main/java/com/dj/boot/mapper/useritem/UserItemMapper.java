package com.dj.boot.mapper.useritem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.useritem.ExceptionConf;
import com.dj.boot.pojo.useritem.UserItem;

import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.mapper.useritem
 * @Author: wangJia
 * @Date: 2020-08-18-11-10
 */
public interface UserItemMapper extends BaseMapper<UserItem> {
    /**
     * 添加
     * @param userItem
     * @return
     */
    Integer userItemAdd(UserItem userItem);

    /**
     * 异常映射配置
     * @return
     */
    List<ExceptionConf> queryExceptionConf();
}
