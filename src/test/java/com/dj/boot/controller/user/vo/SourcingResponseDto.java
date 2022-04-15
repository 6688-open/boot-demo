package com.dj.boot.controller.user.vo;

import java.io.Serializable;
import java.util.List;

public class SourcingResponseDto implements Serializable {

    private SalesOrderSourcingDto orderSourcing;
    private List<WaybillSourcingDto> waybillSourcingList;

    public SalesOrderSourcingDto getOrderSourcing() {
        return orderSourcing;
    }

    public void setOrderSourcing(SalesOrderSourcingDto orderSourcing) {
        this.orderSourcing = orderSourcing;
    }

    public List<WaybillSourcingDto> getWaybillSourcingList() {
        return waybillSourcingList;
    }

    public void setWaybillSourcingList(List<WaybillSourcingDto> waybillSourcingList) {
        this.waybillSourcingList = waybillSourcingList;
    }
}
