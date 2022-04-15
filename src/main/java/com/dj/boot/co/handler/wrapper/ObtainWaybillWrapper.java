package com.dj.boot.co.handler.wrapper;

import com.dj.boot.co.handler.dto.GetWaybillDto;
import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.dto.ObtainWaybillResponse;
import com.dj.boot.co.handler.process.WayBillObtainByShipperPerformService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @描述:单号获取包装类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ObtainWaybillWrapper implements Serializable {

    private WayBillObtainByShipperPerformService waybillObtainService;

    private ObtainWaybillParam waybillParam;


    public ObtainWaybillWrapper(WayBillObtainByShipperPerformService waybillObtainService, ObtainWaybillParam param) {
        this.waybillObtainService = waybillObtainService;
        this.waybillParam = param;
    }
    public GetWaybillDto doObtainWayBillCode(){
        waybillObtainService.processBeforeObtainWaybill(waybillParam);
        ObtainWaybillResponse response = new ObtainWaybillResponse();
        waybillObtainService.processObtainWaybill(waybillParam,response);
        return response.getGetWaybillDto();
    }


}
