package com.dj.boot.controller.so.domain;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.base.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ExceptionCentreWorker implements Callable<List<String>>{

    private static final Logger log = LogManager.getLogger(ExceptionCentreWorker.class);


    private List<SoExceptionParam> works;

    private ExceptionCentreHandler exceptionCentreHandler;

    public ExceptionCentreWorker(){}

    public ExceptionCentreWorker(List<SoExceptionParam> works, ExceptionCentreHandler exceptionCentreHandler){
        this.works = works;
        this.exceptionCentreHandler = exceptionCentreHandler;
    }
    @Override
    public List<String> call() throws Exception {
        List<String> failResult = new ArrayList<String>(50);
        for(SoExceptionParam param:works){
            try {
                log.error("resultData:{}", JSONObject.toJSONString(param));
                Response resultData = exceptionCentreHandler.tryResume(param);
                if (resultData.getCode() != 200) {
                    failResult.add(param.getSoNo());
                }
            } catch (Exception e) {
                log.error("恢复订单失败" + param.getSoNo(), e);
                failResult.add(param.getSoNo());
            }
        }
        return failResult;
    }
}
