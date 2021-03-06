package com.dj.boot.pojo;

import com.dj.boot.annotation.privacy.PrivacyField;
import com.dj.boot.annotation.privacy.PrivacyFieldType;

import java.io.Serializable;

public class KdnTrackReqDto implements Serializable {

    private String orderCode;
    private String shipperCode;
    private String logisticCode;
    @PrivacyField(type = PrivacyFieldType.MOBILE_PHONE,group = "info")
    private String customerName;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
