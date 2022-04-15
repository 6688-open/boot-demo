package com.dj.boot.controller.so.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SoExceptionResumeParam {
    /**
     * 寄件人地址
     */
    private String senderAddress;
    /**
     * 寄件人电话
     */
    private String senderTel;
    /**
     * 寄件人手机
     */
    private String senderMobile;
    /**
     * 收件人姓名
     */
    private String receiveName;
    /**
     * 收件人地址
     */
    private String receiveAddress;
    /**
     * 重量
     */
    private Double weight;
    /**
     * 代收金额
     */
    private Double collectionMoney;

    /**
     * 审核类型
     */
    private Integer deliverAgainType;

    /**
     * 承运商编码
     */
    private String shipperNo;

    /**
     * 三方运单号
     */
    private String thirdWayBill;


}
