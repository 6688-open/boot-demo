package com.dj.boot.stock.adapter;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.sequence.ISequence;
import com.dj.boot.stock.dto.StockProcessParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:Anthony
 * @date:${date}
 **/
public abstract class AbstractAdapter {

    @Resource
    private ISequence operationStockSequence;

    /**
     * 订单预占使用。
     * 1：改造成组件化
     * 2：库存处理异常不在抛给业务层，改为返回值
     * @param paramList
     * @param constraintParam
     * @return
     */
    public ResultData startOperationStockSequence(List<StockProcessParam> paramList/*, ConstraintParam constraintParam*/){
        ComponentContext context = new ComponentContext();
        context.setBusiData(paramList);
        context.setBizNo(paramList.get(0).getBizNo());
        //context.setExtendData(CacheKeyEnum.CONSTRAINT_PARAM.getKey(),constraintParam);
        ResultData resultData = operationStockSequence.start(context);
        return resultData;
    }

}
