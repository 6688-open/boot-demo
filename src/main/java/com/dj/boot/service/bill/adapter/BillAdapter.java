package com.dj.boot.service.bill.adapter;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.controller.bill.domain.BillExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class BillAdapter {

    /**
     * 执行创建异常单
     * @param billExceptionDto
     */
    public void handler(BillExceptionDto billExceptionDto) {
        //防并发

        try {
            //新建
            log.info("新建成功:{}", JSONObject.toJSONString(billExceptionDto));
        } catch (Exception e) {
            log.error("billexception处理异常:billNo" + billExceptionDto.getBillNo(), e);
            throw new RuntimeException("billexception处理异常:billNo" + billExceptionDto.getBillNo());
        }
    }










}
