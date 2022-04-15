package com.dj.boot.controller.user.vo;

import java.io.Serializable;

public class WaybillSourcingDto implements Serializable {
    /**
     * 事业部编码
     */
    private String deptNo;
    /**
     * 事业部名称
     */
    private String deptName;
    /**
     *承运商编码
     */
    private String shipperNo;
    /**
     * 承运商名称
     */
    private String shipperName;
    /**
     * 承运商合作伙伴编码
     */
    private String partnerNo;
    /**
     * 承运商合作伙伴名称
     */
    private String partnerName;
    /**
     * 配送模式  1 京配
     */
    private Integer deliveryMode;
    /**
     * 承运商所属类型
     */
    private Byte shipperBelongType;

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getShipperNo() {
        return shipperNo;
    }

    public void setShipperNo(String shipperNo) {
        this.shipperNo = shipperNo;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
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

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Byte getShipperBelongType() {
        return shipperBelongType;
    }

    public void setShipperBelongType(Byte shipperBelongType) {
        this.shipperBelongType = shipperBelongType;
    }
}
