package com.dj.boot.co.handler.process.impl;

import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.dto.ObtainWaybillResponse;
import com.dj.boot.co.handler.process.WayBillObtainByShipperPerformService;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-03-25-10-57
 */
@Service("pddObtainWayBillService")
public class PddObtainWayBillServiceImpl implements WayBillObtainByShipperPerformService {
    @Override
    public void processBeforeObtainWaybill(ObtainWaybillParam waybillParam) {

    }

    @Override
    public void processObtainWaybill(ObtainWaybillParam waybillParam, ObtainWaybillResponse response) {

    }
}
