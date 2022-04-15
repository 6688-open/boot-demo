package com.dj.boot.controller.bill.domain;

import com.dj.boot.common.base.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BillExceptionWorker implements Callable<List<String>>{

    private static final Logger log = LogManager.getLogger(BillExceptionWorker.class);


    private List<BillExceptionRequest>  works;

    private BillExceptionHandler billExceptionHandler;

    public BillExceptionWorker(){}

    public BillExceptionWorker(List<BillExceptionRequest>  works, BillExceptionHandler billExceptionHandler){
        this.works = works;
        this.billExceptionHandler = billExceptionHandler;
    }
    @Override
    public List<String> call() throws Exception {
        List<String> failResult = new ArrayList<String>(50);
        for(BillExceptionRequest param:works){
            try {
                Response response = billExceptionHandler.tryResume(param);
                if (response.getCode() != 200) {
                    failResult.add(response.getMsg());
                }
            } catch (Exception e) {
                log.error("恢复订单失败 BillExceptionWorker" , e);
                failResult.add("处理异常，稍后处理！");
            }
        }
        return failResult;
    }
}
