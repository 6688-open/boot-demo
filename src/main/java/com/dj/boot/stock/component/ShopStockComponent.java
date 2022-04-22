package com.dj.boot.stock.component;

import com.dj.boot.configuration.kernel.component.SimpleBusiComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.stock.dto.StockProcessParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-12-03
 */
public class ShopStockComponent extends SimpleBusiComponent {

    private static final Logger LOGGER = LogManager.getLogger(ShopStockComponent.class);

    @Override
    public boolean validate(ComponentContext context) {
        return TRUE;
    }

    @Override
    public boolean realWork(ComponentContext context) {
        LOGGER.info("ShopStockComponent req:{}",context.toString());
        List<StockProcessParam> paramList = (List<StockProcessParam>) context.getBusiData();
        StockProcessParam stockProcessParam = paramList.get(0);
        stockProcessParam.setBizNo("ShopStockComponent11111");
        return TRUE;
    }
}
