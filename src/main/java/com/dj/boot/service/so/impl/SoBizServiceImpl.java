package com.dj.boot.service.so.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.base.Response;
import com.dj.boot.controller.so.domain.SoExceptionParam;
import com.dj.boot.service.so.SoBizService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-15-04
 */
@Service("soBizService")
public class SoBizServiceImpl implements SoBizService {

    private final Logger log = LogManager.getLogger(SoBizServiceImpl.class);

    @Override
    public Response tryResumeException(SoExceptionParam soExceptionParam, Integer disposeType) {
        Response<Object> response = Response.success();
        log.error("soBizService-tryResumeException:{}", JSONObject.toJSONString(soExceptionParam));
        //redis防重
        switch (disposeType) {
            case 1 :
                //重新下发订单系统
                break;
            case 3 :
                //重新下发其他系统
                break;
        }
        return response;
    }
}
