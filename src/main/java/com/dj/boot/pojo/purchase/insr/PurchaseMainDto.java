package com.dj.boot.pojo.purchase.insr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购单主档
 *
 * @author tianka
 * @Date 2019-9-27 11:0:39
 */
@Data
@NoArgsConstructor
public class PurchaseMainDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String purchaseNo;
    private String enterpriseNo;
    private String enterpriseName;
    @NotBlank(message = "货主编号不能为空")
    private String ownerNo;
    private String ownerName;
    @NotBlank(message = "商家采购单号不能为空")
    private String sellerPurchaseNo;
    private String supplyNo;
    private String supplyName;
    private String createUser;
    private String updateUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String tenantId;
    @Positive(message = "商品行数量之和不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "商品行数量之和长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalRowNums;
    @Positive(message = "商品总SKU数量不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "商品总SKU数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalSkuQty;
    @Positive(message = "商品总金额不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "单据总金额长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalMoney;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateTime;
    @NotNull(message = "来源必填")
    private Byte source;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createEndTime;
    private String note;
    private String performNo;
    @Valid
    @NotEmpty(message = "明细不能为空")
    @Size(max = 500,message = "明细数不能超过500")
    private List<PurchaseItemDto> purchaseItemDtoList;
    private Integer cancelStatus;
    private String cancelFailureReason;
    private List<String> ownerNoList;
    @NotBlank(message = "单据业务类型必填")
    private String orderType;
    @NotBlank(message = "操作人必填")
    private String pin;
    private String isvCode;
    private String warehouseNo;
    private String warehouseName;
    @Size(max = 25)
    private String batchNo;
    private Boolean autoClose;
    private Byte receiveType;
    private String orderTypeName;
    private String orderMark;
    private String factoryCode;
    private String factoryName;
    private String stockLocationCode;
    private String stockLocationName;
    private Byte backType;
    @Size(max = 500,message = "附加信息小于500")
    private String extraMsg;
    private String sellerSupplyNo;
    private Integer approveStatus;
    private String sellerReceiptsPerformNo;
    private String upsideDownPurchaseNo;
    private Integer upsideDownStatus;
    private Byte crossFlowFlag;
    private String operator;
    private String originOrderNo;
    private Date postingDate;
    private Date documentDate;
    private String operateType;
    private String supplyEnterpriseNo;
    private String supplyEnterpriseName;
    private String customerNo;
    private List<AcceptanceDto> acceptanceDtos;





}