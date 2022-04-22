package com.dj.boot.controller.stock;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.base.Response;
import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.service.stock.ShopStockService;
import com.dj.boot.stock.dto.StockProcessParam;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.controller.stock
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-04-22-12-33
 */
@RestController
@RequestMapping("/stock/")
public class StockController extends BaseController {

    @Autowired
    private ShopStockService stockService;

    @PostMapping("echo")
    public Response<ResultData> echo() throws Exception {
        Response<ResultData>  response = Response.success();
        List<StockProcessParam> paramList = Lists.newArrayList();
        StockProcessParam stockProcessParam = new StockProcessParam();
        stockProcessParam.setDeptNo("12345678");
        stockProcessParam.setBizNo("111111");
        paramList.add(stockProcessParam);
        ResultData resultData = stockService.occupySoStock(paramList);
        logger.error("预占库存结果:{}", JSONObject.toJSONString(resultData));
        response.setData(resultData);
        return response;
    }
}
