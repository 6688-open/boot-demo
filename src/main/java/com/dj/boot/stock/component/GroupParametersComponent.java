package com.dj.boot.stock.component;

import com.dj.boot.common.util.StringUtils;
import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.SimpleBusiComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.execption.ParamResponseException;
import com.dj.boot.stock.dto.StockProcessParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-12-03
 */
public class GroupParametersComponent extends SimpleBusiComponent {

    private static final Logger LOGGER = LogManager.getLogger(GroupParametersComponent.class);


    @Override
    public boolean isInInstruction(ComponentContext context) {
        /**
         * isInInstruction 返回TRUE  执行本类的realWork方法
         *                 返回false 不在执行本类的后续方法 直接执行下一个Component
         */
        return TRUE;
    }

    @Override
    public boolean isMyWork(ComponentContext context) {
        /**
         * isMyWork 返回TRUE  执行本类的realWork方法
         *                 返回false 不在执行本类的后续方法 直接执行下一个Component
         */
        return Boolean.TRUE;
    }

    @Override
    public boolean validate(ComponentContext context) {
        /**
         * validate 返回false  本类后续方法不在执行 后面的Component将不在处理 直接直接最后的Component
         */
        List<StockProcessParam> paramList = (List<StockProcessParam>) context.getBusiData();
        StockProcessParam stockProcessParam = paramList.get(0);
        if (StringUtils.isBlank(stockProcessParam.getBizNo())) {
            ResultData resultData = ResultData.failResultData(ParamResponseException.notNullException("单号不能为空").getDesc());
            context.setResultMap(resultData);
            return FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * realWork 返回false  本类后续方法不在执行 后面的Component将不在处理 直接直接最后的Component
     * context上下文改变将传递给下个Component
     */
    @Override
    public boolean realWork(ComponentContext context) {
        LOGGER.info("GroupParametersComponent req:{}",context.toString());
        List<StockProcessParam> paramList = (List<StockProcessParam>) context.getBusiData();
        StockProcessParam stockProcessParam = paramList.get(0);
        stockProcessParam.setBizNo("GroupParametersComponent111111");

        //抛出异常 将由异常节点Component捕捉 由EndComponent返回
//        if (stockProcessParam.getBizNo().equals("GroupParametersComponent111111")) {
//            throw ParamResponseException.notNullException("单号不能为空");
//        }

        return TRUE;
    }
}
