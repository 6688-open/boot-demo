package com.dj.boot.co.handler.process;

import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.dto.ObtainWaybillResponse;

/**
 * @描述:根据承运商类别获取单号服务
 */
public interface WayBillObtainByShipperPerformService {
    /**
     * 处理前置处理
     * @param waybillParam
     */
    void processBeforeObtainWaybill(ObtainWaybillParam waybillParam);

    /**
     * 处理方法
     * @param waybillParam
     * @param response
     */
    void processObtainWaybill(ObtainWaybillParam waybillParam, ObtainWaybillResponse response);
}
