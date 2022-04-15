package com.dj.boot.service.errorreason;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.errorreason.ErrorReason;

import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.test
 * @Author: wangJia
 * @Date: 2020-08-19-15-21
 */

public interface ErrorReasonService extends IService<ErrorReason> {

    /**
     * 获取异常报备实体
     * @param id
     * @return the ErrorDeviceReason
     */
    public ErrorReason getErrorReason(Long id);

    /**
     * @Description: 按条件获取事件单实体列表
     * @param condition
     * @return
     */
    public List<ErrorReason> findErrorReasonListByCondition(ErrorReason condition);

    /**
     * 插入异常报备实体
     * @param errorReason
     */
    public void insertErrorReason(ErrorReason errorReason);

    /**
     * 更新异常报备实体
     * @param errorReason
     */
    public void updateErrorReason(ErrorReason errorReason);

    /**
     * 删除事件单实体
     * @param id
     */
    public void deleteErrorReason(Long id);


}
