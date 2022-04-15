package com.dj.boot.test.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量明细对象
 * 只有事件单/报备单批量时才有值
 * @Author: wangJia
 * @Date: 2021-08-31-14-09
 */
public class ClaimBatchDetailDTO implements Serializable {
    /**
     * 理赔单id
     */
    private Long billId;
    /**
     * 线索值
     */
    private String clueValue;
    /**
     * 一级责任原因
     */
    private String paymentCauseId1Desc;
    /**
     * 二级责任原因
     */
    private String paymentCauseId2Desc;
    /**
     * 客户索赔金额
     */
    private BigDecimal merchantClaimMoney;
    /**
     * 实际赔付金额
     */
    private BigDecimal paymentRealMoney;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getClueValue() {
        return clueValue;
    }

    public void setClueValue(String clueValue) {
        this.clueValue = clueValue;
    }

    public String getPaymentCauseId1Desc() {
        return paymentCauseId1Desc;
    }

    public void setPaymentCauseId1Desc(String paymentCauseId1Desc) {
        this.paymentCauseId1Desc = paymentCauseId1Desc;
    }

    public String getPaymentCauseId2Desc() {
        return paymentCauseId2Desc;
    }

    public void setPaymentCauseId2Desc(String paymentCauseId2Desc) {
        this.paymentCauseId2Desc = paymentCauseId2Desc;
    }

    public BigDecimal getMerchantClaimMoney() {
        return merchantClaimMoney;
    }

    public void setMerchantClaimMoney(BigDecimal merchantClaimMoney) {
        this.merchantClaimMoney = merchantClaimMoney;
    }

    public BigDecimal getPaymentRealMoney() {
        return paymentRealMoney;
    }

    public void setPaymentRealMoney(BigDecimal paymentRealMoney) {
        this.paymentRealMoney = paymentRealMoney;
    }
}
