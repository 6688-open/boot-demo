package com.dj.boot.service.stock.impl;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.service.stock.ShopStockService;
import com.dj.boot.stock.adapter.AbstractAdapter;
import com.dj.boot.stock.dto.StockProcessParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.service.stock.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-04-22-11-03
 */
@Component
public class ShopStockServiceImpl extends AbstractAdapter implements ShopStockService {
    @Override
    public ResultData occupySoStock(List<StockProcessParam> paramList) {


        ResultData resultData = super.startOperationStockSequence(paramList);
        return resultData;
    }
}
