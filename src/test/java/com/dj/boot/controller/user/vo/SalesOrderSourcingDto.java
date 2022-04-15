package com.dj.boot.controller.user.vo;

import java.io.Serializable;

public class SalesOrderSourcingDto implements Serializable {
    /**
     * 仓库编码
     */
    private String warehouseNo;
    /**
     * 仓配名称
     */
    private String warehouseName;
    /**
     * 仓库合作伙伴编码
     */
    private String partnerNo;
    /**
     * 仓库合作伙伴名称
     */
    private String partnerName;

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
}
