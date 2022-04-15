package com.dj.boot.controller.so.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsServiceShipper  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 物流主表ID
     */
    private Long logisticsServiceId;

    /**
     * 物流服务编码
     */
    private Long serviceId;

    /**
     * 物流服务名称
     */
    private String serviceName;


    /**
     * 承运商编码
     */
    private String shipperNo;

    /**
     * 承运商名称
     */
    private String shipperName;
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

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 删除标志
     */
    private byte yn;

    /**
     * 商家承运商编码
     */
    private String sellerShipperNo;

    /**
     * 承运商类型
     */
    private String type;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 执行类型
     */
    private String performSystem;

    /**
     * 是否保价
     */
    private Byte isGuarateeValue;
    /**
     * 承运商是否支持保价-页面用
     */
    private Byte shipIsGuarateeValue;
    /**
     * 众包承运商时效产品
     */
    private String agingType;

    private List<String> logisticsServiceIdList;

    /**
     * 物流服务承运商是否支持贷后货款 0否 1是
     */
    private Byte isCashOnDelivery;
    /**
     * 承运商主数据是否支持贷后货款
     */
    private Byte isShipCashOnDelivery;
    /**
     * 是否允许修改运单
     */
    private Byte isAllowModifyWb;
    /**
     * 承运商是否支持允许修改运单
     */
    private Byte shipIsAllowModifyWb;
    /**
     * 开通承运产品类型 多个值 逗号分隔
     * 配合枚举对应枚举值处理
     */
    private String expProductType;

    /**
     * 代收货款支持扫码支付
     */
    @Deprecated
    private Byte scanpayType;
    /**
     * 协商再投信息：1,7,1 解释：支持协商再投,天数自动到期,支持拒收作废
     *
     */
    private String deliverAgainInfo;
    /**
     * 是否协商再投
     */
    private Byte isDeliverAgain;

    /**
     * 扫码支付标识，字符串
     * 支持多选
     */
    private String bizScanPay;
    /**
     * 是否送装一体
     *
     */
    private Byte isSendAndInstall;

    /**
     * 是否重货上楼（0否，1是）
     */
    private Byte heavyStuffUpstairs;

    /**
     * 执行系统编码（仅页面使用）
     */
    private Byte performSystemNo;

    /**
     * 保价金额
     */
    private String guarateeValue;

    /**
     *承运商渠道渠道（页面用）
     */
    private Byte shipChannel;

}
