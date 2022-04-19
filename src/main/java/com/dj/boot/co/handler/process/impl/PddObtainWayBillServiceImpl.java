package com.dj.boot.co.handler.process.impl;

import com.dj.boot.co.handler.dto.GetWaybillDto;
import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.dto.ObtainWaybillResponse;
import com.dj.boot.co.handler.helper.WbBillObtainHelper;
import com.dj.boot.co.handler.process.WayBillObtainByShipperPerformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-03-25-10-57
 */
@Service("pddObtainWayBillService")
public class PddObtainWayBillServiceImpl implements WayBillObtainByShipperPerformService {

    @Resource
    WbBillObtainHelper WbBillObtainHelper;

    @Override
    public void processBeforeObtainWaybill(ObtainWaybillParam waybillParam) {
        String wbNo = waybillParam.getWbNo();
    }

    @Override
    public void processObtainWaybill(ObtainWaybillParam waybillParam, ObtainWaybillResponse response) {
        GetWaybillDto outWayBill = WbBillObtainHelper.getWaybill(waybillParam, response);
        response.setGetWaybillDto(outWayBill);
    }
}
