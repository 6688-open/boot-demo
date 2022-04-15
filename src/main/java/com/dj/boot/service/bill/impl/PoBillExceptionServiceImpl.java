package com.dj.boot.service.bill.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.common.base.Response;
import com.dj.boot.controller.bill.constant.Module;
import com.dj.boot.controller.bill.domain.BillExceptionDto;
import com.dj.boot.controller.bill.domain.BillExceptionRequest;
import com.dj.boot.service.bill.ExceptionService;
import com.dj.boot.service.bill.adapter.BillAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 采购订单异常处理
 */
@Component
@Module(businessType = "PO")
public class PoBillExceptionServiceImpl implements ExceptionService {

    private static final Logger logger = LoggerFactory.getLogger(PoBillExceptionServiceImpl.class);

    @Resource
    private BillAdapter billAdapter;

    @Override
    public Response execute(BillExceptionRequest request) {

        BillExceptionDto billExceptionDto = (BillExceptionDto) request.getData();
        billAdapter.handler(billExceptionDto);
        return Response.success(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE);
    }


    /**
     * 采购单回传处理 只处理  回传qi men
     * @param request
     * @return
     */
    @Override
    public Response retryException(BillExceptionRequest request) {
        logger.error("采购单回传处理入参:{}", JSONObject.toJSONString(request));
        try {
            BillExceptionDto billExceptionDto = (BillExceptionDto) request.getData();

            //根据billExceptionDto.getId() 查询数据库源数据

            //校验数据是否已处理

            //异步处理回传操作

            //更新异常单据信息

        } catch (Exception e) {
            logger.error("PO retryException  error", e);
            Response.error(Constant.ERROR_CODE, "重试提交中请等待5分钟！");
        }
        return Response.success("重试提交中请等待5分钟！", Constant.SUCCESS_CODE);
    }
}
