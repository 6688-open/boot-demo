package com.dj.boot.service.compensate;


import com.dj.boot.pojo.compensate.CompensateItem;
import com.dj.boot.pojo.compensate.CompensateItemCondition;

import java.util.List;

/**
 * 理赔明细
 * @Author: wangJia
 * @Date: 2021-09-01-10-33
 */
public interface CompensateItemService {
    /**
     * 获取赔付单
     * @param id
     * @return the CompensateOrder
     */
    CompensateItem getCompensateItem(Long id);

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    List<CompensateItem> getCompensateItemList(CompensateItemCondition condition);

    /**
     * 新增
     * @param compensateItem
     */
    void insertCompensateItem(CompensateItem compensateItem);

    /**
     * 新增
     * @param compensateItem
     */
    void insertSelectiveCompensateItem(CompensateItem compensateItem);

    /**
     * 更新
     * @param compensateItem
     */
    void updateCompensateItem(CompensateItem compensateItem);
    /**
     * 删除赔付单
     * @param id
     */
    void deleteCompensateItem(Long id);
}
