package com.dj.boot.test.domain;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * 理赔单信息
 * @Author: wangJia
 * @Date: 2021-08-31-14-02
 */
public class ClaimStatusResponse implements Serializable {
    /**
     * 理赔单号
     */
    private Long claimId;

    /**
     * 线索类型
     *
     */
    private Integer clueType;

    /**
     * 盘亏单
     *         billType =120 的时候，线索值clueValue里面就是盘亏单号
     * 异常单和事件单
     *         billType =120 ，线索值clueValue里面就是运单号
     *         billType =123   线索值是cbu，消息体里的List<ClaimBatchDetailDTO> 里的ClaimBatchDetailDTO 属性clueValue就是运单号
     * 线索值:参考clueType所代表的含义
     */
    private String clueValue;

    /**
     * 线索类型名称：同clueTypeEnum枚举中定义
     */
    private String clueTypeName;

    /**
     * 责任原因一级ID:
     */
    private Long paymentCauseId1;

    /**
     * 责任原因二级ID
     */
    private Long paymentCauseId2;

    /**
     * 责任原因:一级+二级
     */
    private String paymentCause;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 账户类型名称
     */
    private String accountTypeName;

    /**
     * 实际赔付金额:最终赔给客户的数额
     */
    private BigDecimal paymentRealMoney;

    /**
     * 理赔单据状态
     */
    private Integer status;

    /**
     * 理赔状态名称:与status成对出现
     */
    private String statusName;

    /**
     * 理赔单创建时间
     */
    private Date createTime;

    /**
     * 提货方式（退回方式）
     */
    private Integer merchantPickWay;

    /**
     * 商家提货方式名称
     */
    private String merchantPickWayName;

    /**
     * 地址
     */
    private String detailAddress;

    /**
     * 联系人
     */
    private String dockingName;

    /**
     * 联系方式
     */
    private String dockingTel;

    /**
     * 是否整单赔付
     */
    private Integer isWholePay;

    /**
     * 是否整单赔付名称
     */
    private String isWholePayName;

    /**
     * 物权归属
     */
    private Integer goodOwner;

    /**
     * 物权归属名称
     */
    private String goodOwnerName;

    /**
     * 通知时间
     */
    private Date timestamp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 结算主体ID（商家主体编号）
     */
    private String ouid;

    /**
     * 商品明细集合
     */
    private List<GoodsBsMqDTO> goodsBsMqDTOList;

    /**
     * 业务来源类型
     */
    private String bsSourceType;

    /**
     * 业务来源主键
     */
    private String bsSourceValue;


    /**
     * 赔付方式
     */
    private Integer paymentType;

    /**
     * 赔付方式名称
     */
    private String paymentName;

    /**
     * 确认方式
     */
    private Integer confirmWay;

    /**
     * 确认方式
     */
    private String confirmWayName;

    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 理赔单类型
     */
    private Integer billType;

    /**
     * 合同约定理赔金额
     */
    private BigDecimal paymentContractMoney;

    /**
     * 责任最小单位编码
     */
    private String dutyerUnitCode;

    /**
     * 责任最小单位
     */
    private String dutyerUnit;

    /**
     * 责任区域
     */
    private String dutyArea;

    /**
     * 责任区域名称
     */
    private String dutyAreaName;

    /**
     * 责任条线
     */
    private String dutyLines;

    /**
     * 责任条线名称
     */
    private String dutyLinesName;

    /**
     * 批量理赔是否事故
     */
    private Integer accidentFlag;

    /**
     * 批量理赔明细
     */
    private String claimDetail;
    /**
     * 关单原因
     */
    private String closeReason;

    /**
     * 责任人
     */
    private String dutyer;

    /**
     * 工单号列表, 多个用逗号分割
     */
    private String woIds;

    /**
     * 定责单号列表, 多个用逗号分割
     */
    private String jgmntIds;

    /**
     * 异常单号列表, 多个用逗号分割
     */
    private String abnIds;

    /**
     * 金额谈判人
     */
    private String amountNegotiator;
    /**
     * 金额谈判区域
     */
    private String amountNegArea;
    /**
     * 金额谈判区域编码
     */
    private String amountNegAreaCode;


    /**
     * 理赔异常原因一级编码
     */
    private String firstClaimCauseCode;

    /**
     * 理赔异常原因一级名称
     */
    private String firstClaimCause;

    /**
     * 理赔异常原因二级编码
     */
    private String secondClaimCauseCode;

    /**
     * 理赔异常原因二级名称
     */
    private String secondClaimCause;
    /****
     * 批量明细
     */
    List<ClaimBatchDetailDTO> batchDetailDTOList;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Integer getClueType() {
        return clueType;
    }

    public void setClueType(Integer clueType) {
        this.clueType = clueType;
    }

    public String getClueValue() {
        return clueValue;
    }

    public void setClueValue(String clueValue) {
        this.clueValue = clueValue;
    }

    public String getClueTypeName() {
        return clueTypeName;
    }

    public void setClueTypeName(String clueTypeName) {
        this.clueTypeName = clueTypeName;
    }

    public Long getPaymentCauseId1() {
        return paymentCauseId1;
    }

    public void setPaymentCauseId1(Long paymentCauseId1) {
        this.paymentCauseId1 = paymentCauseId1;
    }

    public Long getPaymentCauseId2() {
        return paymentCauseId2;
    }

    public void setPaymentCauseId2(Long paymentCauseId2) {
        this.paymentCauseId2 = paymentCauseId2;
    }

    public String getPaymentCause() {
        return paymentCause;
    }

    public void setPaymentCause(String paymentCause) {
        this.paymentCause = paymentCause;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public BigDecimal getPaymentRealMoney() {
        return paymentRealMoney;
    }

    public void setPaymentRealMoney(BigDecimal paymentRealMoney) {
        this.paymentRealMoney = paymentRealMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMerchantPickWay() {
        return merchantPickWay;
    }

    public void setMerchantPickWay(Integer merchantPickWay) {
        this.merchantPickWay = merchantPickWay;
    }

    public String getMerchantPickWayName() {
        return merchantPickWayName;
    }

    public void setMerchantPickWayName(String merchantPickWayName) {
        this.merchantPickWayName = merchantPickWayName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDockingName() {
        return dockingName;
    }

    public void setDockingName(String dockingName) {
        this.dockingName = dockingName;
    }

    public String getDockingTel() {
        return dockingTel;
    }

    public void setDockingTel(String dockingTel) {
        this.dockingTel = dockingTel;
    }

    public Integer getIsWholePay() {
        return isWholePay;
    }

    public void setIsWholePay(Integer isWholePay) {
        this.isWholePay = isWholePay;
    }

    public String getIsWholePayName() {
        return isWholePayName;
    }

    public void setIsWholePayName(String isWholePayName) {
        this.isWholePayName = isWholePayName;
    }

    public Integer getGoodOwner() {
        return goodOwner;
    }

    public void setGoodOwner(Integer goodOwner) {
        this.goodOwner = goodOwner;
    }

    public String getGoodOwnerName() {
        return goodOwnerName;
    }

    public void setGoodOwnerName(String goodOwnerName) {
        this.goodOwnerName = goodOwnerName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public List<GoodsBsMqDTO> getGoodsBsMqDTOList() {
        return goodsBsMqDTOList;
    }

    public void setGoodsBsMqDTOList(List<GoodsBsMqDTO> goodsBsMqDTOList) {
        this.goodsBsMqDTOList = goodsBsMqDTOList;
    }

    public String getBsSourceType() {
        return bsSourceType;
    }

    public void setBsSourceType(String bsSourceType) {
        this.bsSourceType = bsSourceType;
    }

    public String getBsSourceValue() {
        return bsSourceValue;
    }

    public void setBsSourceValue(String bsSourceValue) {
        this.bsSourceValue = bsSourceValue;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getConfirmWay() {
        return confirmWay;
    }

    public void setConfirmWay(Integer confirmWay) {
        this.confirmWay = confirmWay;
    }

    public String getConfirmWayName() {
        return confirmWayName;
    }

    public void setConfirmWayName(String confirmWayName) {
        this.confirmWayName = confirmWayName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public BigDecimal getPaymentContractMoney() {
        return paymentContractMoney;
    }

    public void setPaymentContractMoney(BigDecimal paymentContractMoney) {
        this.paymentContractMoney = paymentContractMoney;
    }

    public String getDutyerUnitCode() {
        return dutyerUnitCode;
    }

    public void setDutyerUnitCode(String dutyerUnitCode) {
        this.dutyerUnitCode = dutyerUnitCode;
    }

    public String getDutyerUnit() {
        return dutyerUnit;
    }

    public void setDutyerUnit(String dutyerUnit) {
        this.dutyerUnit = dutyerUnit;
    }

    public String getDutyArea() {
        return dutyArea;
    }

    public void setDutyArea(String dutyArea) {
        this.dutyArea = dutyArea;
    }

    public String getDutyAreaName() {
        return dutyAreaName;
    }

    public void setDutyAreaName(String dutyAreaName) {
        this.dutyAreaName = dutyAreaName;
    }

    public String getDutyLines() {
        return dutyLines;
    }

    public void setDutyLines(String dutyLines) {
        this.dutyLines = dutyLines;
    }

    public String getDutyLinesName() {
        return dutyLinesName;
    }

    public void setDutyLinesName(String dutyLinesName) {
        this.dutyLinesName = dutyLinesName;
    }

    public Integer getAccidentFlag() {
        return accidentFlag;
    }

    public void setAccidentFlag(Integer accidentFlag) {
        this.accidentFlag = accidentFlag;
    }

    public String getClaimDetail() {
        return claimDetail;
    }

    public void setClaimDetail(String claimDetail) {
        this.claimDetail = claimDetail;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getDutyer() {
        return dutyer;
    }

    public void setDutyer(String dutyer) {
        this.dutyer = dutyer;
    }

    public String getWoIds() {
        return woIds;
    }

    public void setWoIds(String woIds) {
        this.woIds = woIds;
    }

    public String getJgmntIds() {
        return jgmntIds;
    }

    public void setJgmntIds(String jgmntIds) {
        this.jgmntIds = jgmntIds;
    }

    public String getAbnIds() {
        return abnIds;
    }

    public void setAbnIds(String abnIds) {
        this.abnIds = abnIds;
    }

    public String getAmountNegotiator() {
        return amountNegotiator;
    }

    public void setAmountNegotiator(String amountNegotiator) {
        this.amountNegotiator = amountNegotiator;
    }

    public String getAmountNegArea() {
        return amountNegArea;
    }

    public void setAmountNegArea(String amountNegArea) {
        this.amountNegArea = amountNegArea;
    }

    public String getAmountNegAreaCode() {
        return amountNegAreaCode;
    }

    public void setAmountNegAreaCode(String amountNegAreaCode) {
        this.amountNegAreaCode = amountNegAreaCode;
    }

    public String getFirstClaimCauseCode() {
        return firstClaimCauseCode;
    }

    public void setFirstClaimCauseCode(String firstClaimCauseCode) {
        this.firstClaimCauseCode = firstClaimCauseCode;
    }

    public String getFirstClaimCause() {
        return firstClaimCause;
    }

    public void setFirstClaimCause(String firstClaimCause) {
        this.firstClaimCause = firstClaimCause;
    }

    public String getSecondClaimCauseCode() {
        return secondClaimCauseCode;
    }

    public void setSecondClaimCauseCode(String secondClaimCauseCode) {
        this.secondClaimCauseCode = secondClaimCauseCode;
    }

    public String getSecondClaimCause() {
        return secondClaimCause;
    }

    public void setSecondClaimCause(String secondClaimCause) {
        this.secondClaimCause = secondClaimCause;
    }

    public List<ClaimBatchDetailDTO> getBatchDetailDTOList() {
        return batchDetailDTOList;
    }

    public void setBatchDetailDTOList(List<ClaimBatchDetailDTO> batchDetailDTOList) {
        this.batchDetailDTOList = batchDetailDTOList;
    }


    public static void main(String[] args) throws IOException {
        ClaimStatusResponse claimStatusResponse = new ClaimStatusResponse();
        claimStatusResponse.setClaimId(1000L);
        claimStatusResponse.setCreateTime(new Date());
        claimStatusResponse.setBillType(120);
        claimStatusResponse.setPaymentRealMoney(new BigDecimal(2.22));


        List<ClaimBatchDetailDTO> batchDetailDTOList = new ArrayList<ClaimBatchDetailDTO>();
        ClaimBatchDetailDTO claimBatchDetailDTO = new ClaimBatchDetailDTO();
        claimBatchDetailDTO.setBillId(200L);
        claimBatchDetailDTO.setPaymentRealMoney(new BigDecimal(2.22));
        claimBatchDetailDTO.setClueValue("222");

        ClaimBatchDetailDTO claimBatchDetailDTO2 = new ClaimBatchDetailDTO();
        claimBatchDetailDTO2.setBillId(200L);
        claimBatchDetailDTO2.setPaymentRealMoney(new BigDecimal(2.22));
        claimBatchDetailDTO2.setClueValue("222");

        batchDetailDTOList.add(claimBatchDetailDTO);
        batchDetailDTOList.add(claimBatchDetailDTO2);

        claimStatusResponse.setBatchDetailDTOList(batchDetailDTOList);


        String toJSONString = JSONObject.toJSONString(claimStatusResponse);


        ClaimStatusResponse compensateStatusResponse = JSONObject.parseObject(toJSONString, ClaimStatusResponse.class);

        System.out.println(1);


    }
}
