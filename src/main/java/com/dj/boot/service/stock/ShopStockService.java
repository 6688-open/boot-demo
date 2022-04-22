package com.dj.boot.service.stock;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.stock.dto.StockProcessParam;

import java.util.List;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.service.stock
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-04-22-11-03
 */
public interface ShopStockService {
    ResultData occupySoStock(List<StockProcessParam> paramList);
}
