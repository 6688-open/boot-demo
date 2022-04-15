package com.dj.boot.pojo.compensate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 理赔单明细
 * @Author: wangJia
 * @Date: 2021-08-31-16-41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompensateItemCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * CLPS赔付单主键
     */
    private Long compensateId;
    /**
     * 理赔系统 理赔单ID
     */
    private String claimId;
    /**
     * 状态
     */
    private Integer itemStatus;
    /**
     * 状态名称
     */
    private String itemStatusName;
    /**
     * 实际赔付金额:最终赔给客户的数额
     */
    private BigDecimal paymentRealMoney;
    /**
     * 物权归属
     */
    private Integer goodOwner;

    /**
     * 物权归属名称
     */
    private String goodOwnerName;
    /**
     * 一级责任原因
     */
    private String firstClaimCause;
    /**
     * 二级责任原因
     */
    private String secondClaimCause;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;
    private String createUserStr;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 是否删除 0：是 1：否
     */
    private Byte yn;
    /**
     * 时间戳
     */
    private Timestamp ts;

}
