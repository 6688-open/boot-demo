package com.dj.boot.co.handler;

import com.dj.boot.co.handler.common.AbstractHandler;
import com.dj.boot.co.handler.delegate.WayBillObtainDelegate;
import com.dj.boot.co.handler.dto.GetWaybillDto;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-03-25-10-37
 */
@Component("getWaybillHandler")
public class GetWaybillHandler extends AbstractHandler {

    @Autowired
    private WayBillObtainDelegate wayBillObtainDelegate;

    @Override
    public void handle(Request request, Response response) throws Exception {
        //并发校验
        //去重
        //获取运单
        //获取ship 的 performSystem
        Integer performSystem = 3;
        GetWaybillDto getWaybillDto = wayBillObtainDelegate.obtainOutWayBill(performSystem);

        //修改运单
    }
}
