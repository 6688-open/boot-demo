package com.dj.boot.co.handler.delegate;

import com.dj.boot.co.handler.dto.GetWaybillDto;
import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.process.WayBillObtainByShipperPerformService;
import com.dj.boot.co.handler.selector.WayBillObtainServiceSelector;
import com.dj.boot.co.handler.wrapper.ObtainWaybillWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:获取单号执行器委派器
 */
@Service("wayBillObtainDelegate")
public class WayBillObtainDelegate {

    @Autowired
    private WayBillObtainServiceSelector wayBillObtainServiceSelector;

    public GetWaybillDto obtainOutWayBill(Integer performSystem) {
        WayBillObtainByShipperPerformService wayBillService = wayBillObtainServiceSelector.select(String.valueOf(performSystem));
        ObtainWaybillParam param = new ObtainWaybillParam();
        param.setWbNo("123456");
        ObtainWaybillWrapper wrapper = new ObtainWaybillWrapper(wayBillService,param);
        return wrapper.doObtainWayBillCode();
    }
}
