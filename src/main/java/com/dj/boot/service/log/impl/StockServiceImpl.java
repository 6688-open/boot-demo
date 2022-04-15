package com.dj.boot.service.log.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.annotation.log.InterfaceLog;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.log.SoCreateRequest;
import com.dj.boot.service.log.StockService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-01-24-18-51
 */
@Service
public class StockServiceImpl implements StockService {

    private final static Logger log = LogManager.getLogger(StockServiceImpl.class);

    @Override
    @InterfaceLog(value = "#request.userDeliveryOrder.userName")
    public Response transportSoOrder(String pin, SoCreateRequest request) {
        log.error("transportSoOrder 请求参数:{}, pin{}", JSONObject.toJSONString(request), pin);
        Response<User> response = Response.success();
        response.setData(request.getUserDeliveryOrder());
        return response;
    }
}
