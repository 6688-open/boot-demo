package com.dj.boot.service.compensate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.mapper.compensate.CompensateItemMapper;
import com.dj.boot.pojo.compensate.CompensateItem;
import com.dj.boot.pojo.compensate.CompensateItemCondition;
import com.dj.boot.service.compensate.CompensateItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 理赔明细
 * @Author: wangJia
 * @Date: 2021-09-01-10-33
 */
@Component("compensateItemService")
public class CompensateItemServiceImpl extends ServiceImpl<CompensateItemMapper, CompensateItem> implements CompensateItemService {

    private static final Logger log = LogManager.getLogger(CompensateItemServiceImpl.class);

    @Resource
    private CompensateItemMapper compensateItemMapper;

    @Override
    public CompensateItem getCompensateItem(Long id) {
        return compensateItemMapper.getCompensateItem(id);
    }

    @Override
    public List<CompensateItem> getCompensateItemList(CompensateItemCondition condition) {
        return compensateItemMapper.getCompensateItemList(condition);
    }

    @Override
    public void insertCompensateItem(CompensateItem compensateItem) {
        compensateItemMapper.insertCompensateItem(compensateItem);
    }

    @Override
    public void insertSelectiveCompensateItem(CompensateItem compensateItem) {
        compensateItemMapper.insertSelectiveCompensateItem(compensateItem);
    }

    @Override
    public void updateCompensateItem(CompensateItem compensateItem) {
        compensateItemMapper.updateCompensateItem(compensateItem);
    }

    @Override
    public void deleteCompensateItem(Long id) {
        compensateItemMapper.deleteCompensateItem(id);
    }

}
