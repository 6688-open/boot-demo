package com.dj.boot.service.useritem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.useritem.ExceptionConf;
import com.dj.boot.pojo.useritem.UserItem;

import java.util.List;

/**
 * @author wangjia
 */
public interface UserItemService extends IService<UserItem> {
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
