package com.dj.boot.mapper.compensate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.compensate.CompensateItem;
import com.dj.boot.pojo.compensate.CompensateItemCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 理赔明细
 * @Author: wangJia
 * @Date: 2021-08-31-18-33
 */
public interface CompensateItemMapper extends BaseMapper<CompensateItem> {
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
    List<CompensateItem> getCompensateItemList(@Param("condition") CompensateItemCondition condition);

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
