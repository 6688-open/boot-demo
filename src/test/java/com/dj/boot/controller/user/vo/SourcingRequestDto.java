package com.dj.boot.controller.user.vo;

import java.io.Serializable;

public class SourcingRequestDto implements Serializable {
    /**
     * 商家编码
     */
    private String sellerNo;
    /**
     * 事业部编码
     */
    private String deptNo;
    /**
     * 单据号
     */
    private String orderCode;
    /**
     * 单据类型
     */
    private Integer orderType;

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
