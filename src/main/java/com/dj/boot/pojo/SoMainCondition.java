package com.dj.boot.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销售订单主档
 */
public class SoMainCondition implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 父单id
     */
    private String parentId;

    /**
     * 订单编号
     */
    private String soNo;

    /**
     * ISV出库单号
     */
    private String isvSoNo;

    /**
     * 销售平台单号
     */
    private String spSoNo;

    /**
     * 订单来源（1:ISV，2:POP）
     */
    private String soSource;

    /**
     * 销售平台ID
     */
    private String spId;

    /**
     * 销售平台编号
     */
    private String spNo;

    /**
     * 销售平台来源（京东/天猫/苏宁/亚马逊/ChinaSkin/其它）
     */
    private String spName;

    /**
     * 青龙销售平台编号
     */
    private String bdSpNo;

    /**
     * 销售平台下单时间
     */
    private String spCreateTime;

    /**
     * 商家Id
     */
    private String sellerId;

    /**
     * 商家编号
     */
    private String sellerNo;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 商家留言（最多支持125个字）
     */
    private String sellerRemark;

    /**
     * 事业部Id
     */
    private String deptId;

    /**
     * 事业部编号
     */
    private String deptNo;

    /**
     * 事业部名称
     */
    private String deptName;

    /**
     * 店铺Id
     */
    private String shopId;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 青龙业主号
     */
    private String bdOwnerNo;

    /**
     * ISV店铺编号
     */
    private String isvShopNo;

    /**
     * 运单
     */
    private String wayBill;

    /**
     * 站点编号
     */
    private String stationNo;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 库房Id
     */
    private String warehouseId;

    /**
     * 库房No
     */
    private String warehouseNo;

    /**
     * 库房名称
     */
    private String warehouseName;

    /**
     * 配送中心编号(外部)
     */
    private String distributionNo;

    /**
     * 机构编号（外部）
     */
    private String orgNo;

    /**
     * 库房编号（外部）
     */
    private String erpWarehouseNo;

    /**
     * 承运商ID(目前默认青龙)
     */
    private String shipperId;

    /**
     * 是否三方配送
     */
    private String thdShipper;



    /**
     * 承运商编号（目前默认青龙）
     */
    private String shipperNo;

    /**
     * 承运商名称（目前默认青龙）
     */
    private String shipperName;

    /**
     * 期望送货时间
     */
    private String expectDate;

    /**
     * 收货人（最大支持25个汉字）
     */
    private String consignee;

    /**
     * 收货人地址（最大支持125个汉字）
     */
    private String consigneeAddr;

    /**
     * 收货地址-省
     */
    private String addrProvince;

    /**
     * 收货地址-市
     */
    private String addrCity;

    /**
     * 收货地址-县
     */
    private String addrCounty;

    /**
     * 收货地址-镇
     */
    private String addrTown;

    /**
     * 收货人邮政编码
     */
    private String consigneePostcode;

    /**
     * 收货人手机
     */
    private String consigneeMobile;

    /**
     * 收货人电话(收件人电话、手机至少有一个不为空)
     */
    private String consigneePhone;

    /**
     * 收货人邮箱
     */
    private String consigneeEmail;

    /**
     * 买家留言
     */
    private String consigneeRemark;

    /**
     * 退货收货人姓名
     */
    private String afterSalesName;

    /**
     * 退货收货联系方式
     */
    private String afterSalesMobile;

    /**
     * 退货收货地址
     */
    private String afterSalesAddr;

    /**
     * 订单生产状态
     */
    private String soStatus;

    /**
     * 运输类型(陆运：1，航空：2。不填或者超出范围时，默认是1)
     */
    private String transType;

    /**
     * 订单异常状态（取消，无法生产，无法分拣，无法发货）
     */
    private String soErrStatus;

    /**
     * 订单标记位:50位的数字串：
     * 第1位：0表示在线支付，1代表货到付款, 不填或者超出范围，默认是0
     * 第2位：0表示青龙配送，1表示需要第三方配送（由青龙转交），2表示商家自主配送（去库房自提）
     * 第3位：是否保价(是：1，否：0，默认是0)
     * 第4位：签单返还(是：1，否：0。不填或者超出范围时，默认是0
     * 第5位：送货时效(普通：1，工作日：2，非工作日：3，晚间：4。不填或者超出范围时，默认是1
     * 第6位：包装要求(不需包装：1，简单包装：2，特殊包装：3。不填或者超出范围，默认是1)
     * 第49位：1代表闪购订单，
     * 第50位：1代表POP下发的仓储服务订单
     */
    private String soMark;

    /**
     * 拆单标志（0:未拆单，1:已拆单）
     */
    private String soSplitFlag;

    /**
     * 应收金额（货到付款必填）
     */
    private String receivable;

    /**
     * 保价金额（需要报价金额必填）
     */
    private String guaranteeValue;

    /**
     * 在销售平台的下单帐号
     */
    private String pinAccount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

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
    private String yn;

    /**
     * 分拣中心编号
     */
    private String sortingCenterNo;

    /**
     * 分拣中心名称
     */
    private String sortingCenterName;

    /**
     * 路区
     */
    private String sortingRoad;


    /** 新增查询条件 */
    /**
     * 订单号集合
     */
    private List<Long> soIdList;
    /**
     * 销售平台订单号集合
     */
    private List<String> spSoNoList;
    /**
     * 事业部商品编号
     */
    private String goodsIdListStr;
    private List<String> goodsIdList;
    /**
     * 销售平台下单时间
     */
    private String spCreateTimeStart;
    private String spCreateTimeEnd;

    /**
     * 接收时间
     */
    private String createTimeStart;
    private String createTimeEnd;
    /**
     * 是否查询暂停订单
     */
    private String queryPauseSo;
    /**
     * 事业部id集合
     */
    private List<String> deptList;

    /**
     * 订单状态集合
     */
    private List<Integer> soStatusList;

    /**
     * 订单类型
     * 与ordermark字段第10位相同，但标记加1存储(B2C:1,B2B:2,B2B2C:3,OTHER:4)//C2B原为3
     */
    private Byte soType;

    /**
     * 订单总重量
     */
    private String soWeight;

    /**
     * 三方站点（大头笔）
     */
    private String soThirdSite;

    /** 李宁B2C添加 */
    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 运单备注
     */
    private String waybillRemark;

    /**
     * hybris单号
     */
    private String hybris;

    /**
     * 发货工厂编号
     */
    private String sellerFactoryNo;

    /**
     * 发货工厂名称
     */
    private String sellerFactoryName;

    /**
     * isv单号集合
     */
    private List<String> isvSoNoList;

    /**
     * 运费（大件运费）
     */
    private String yunFee;

    /**
     * 折扣（大件折扣）
     */
    private String discount;

    /**
     * 异常类型
     */
    private String errType;
    private Integer errTypeNo;


    /**
     * 暂停时间
     */
    private String pauseTime;
    private String pauseTimeStart;
    private String pauseTimeEnd;
    /**
     * 运单号集合
     */
    private List<String> wayBillList;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 件型 1：小件 12：大件
     **/
    private String pieceType;

    /**
     * 是否保价订单
     **/
    private String insuredPriceFlag;

    private String insuredPriceRule;


    /**
     * 耗材相关信息添加
     */
    /**
     * 订单总体积
     **/
    private String soVolume;

    /**
     * CLPS实物库房ID
     */
    private Long targetWarehouseId;

    /**
     * 批量号
     */
    private String batchNo;

    /**
     * 批量订单数
     */
    private Integer batchQty;

    /**
     * 业务来源(腾讯)
     */
    private Byte transactionSource;

    /**
     * ISV来源编号
     */
    private String isvSource;

    private Integer giftType;

    private String giftNo;


    /**
     * 外单合并key
     */
    private String mergeKey;

    /**
     * 外单团单号
     */
    private String groupOrderNumber;

    /**
     * 是否合并过 0 没合并 1 合并过
     */
    private Byte isMerge;

    private String ediRemark;

    /**
     * 三方运单号
     */
    private String thirdWayBill;
    /**
     * 三方运单号集合
     */
    private List<String> thirdWayBillList;
    /**
     * 订单金额
     */
    private String orderAmount;


    /**
     * 拦截状态
     */
    private String interceptStatus;


    /**
     * 时效 航空标添加 来源：OrderMark的第39位
     */
    private Integer chronergy;

    /***
     * 时效展示
     */
    private String chronergyStr;
    /**
     * 预计发货时间
     */
    private String expectDeliveryDate;

    /**
     * 外部订单类型
     */
    private String isvSoType;

    /**
     * 是否支持取消成功后同一单号重新下单(1否2是)
     */
    private Byte canceledRetransport;

    /**
     * 是否加工
     */
    private Byte isProcess;

    /**
     * 是否预约配送
     */
    private Byte isAppointDelivery;

    /**
     * 预约配送日期
     */
    private Date appointDeliveryDate;

    /**
     * 预约配送时间
     */
    private String appointDeliveryDateperiod;

    /**
     * 暂停状态（异常，恢复）
     */
    private String errStatus;
    /**
     * 暂停原因
     */
    private String errReason;

    /**
     * 实际支付金额
     */
    private Double shouldPay;

    /**
     * 库房id集合
     */
    private  List<String>   warehouseIdList ;


    private Integer soMergeMark;

    /**
     * 权限校验使用
     */
    private List<Long> sellerIdList;

    /**
     * es查询加密密文
     * mobile
     */
    private String consigneeMobileEncrypt;

    /**
     * es查询加密索引
     * mobile
     */
    private String consigneeMobileIndex;
    /**
     * es查询加密密文
     * phone
     */
    private String consigneePhoneEncrypt;

    /**
     * es查询加密索引
     * phone
     */
    private String consigneePhoneIndex;

    private String updateTimeStart;

    private String updateTimeEnd;

    private String finishedTime;

    private Integer noStockMark;

    /**
     * 卖家备注
     */
    private String sellerNotes;

    public String getThdShipper() {
        return thdShipper;
    }

    public void setThdShipper(String thdShipper) {
        this.thdShipper = thdShipper;
    }

    public String getSortingRoad() {
        return sortingRoad;
    }

    public void setSortingRoad(String sortingRoad) {
        this.sortingRoad = sortingRoad;
    }


    public String getSoWeight() {
        return soWeight;
    }

    public void setSoWeight(String soWeight) {
        this.soWeight = soWeight;
    }

    public Byte getSoType() {
        return soType;
    }

    public void setSoType(Byte soType) {
        this.soType = soType;
    }

    public List<Integer> getSoStatusList() {
        return soStatusList;
    }

    public void setSoStatusList(List<Integer> soStatusList) {
        this.soStatusList = soStatusList;
    }

    public List<String> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<String> deptList) {
        this.deptList = deptList;
    }

    public String getGoodsIdListStr() {
        return goodsIdListStr;
    }

    public void setGoodsIdListStr(String goodsIdListStr) {
        this.goodsIdListStr = goodsIdListStr;
    }

    public String getSpCreateTimeStart() {
        return spCreateTimeStart;
    }

    public void setSpCreateTimeStart(String spCreateTimeStart) {
        this.spCreateTimeStart = spCreateTimeStart;
    }

    public String getSortingCenterNo() {
        return sortingCenterNo;
    }

    public void setSortingCenterNo(String sortingCenterNo) {
        this.sortingCenterNo = sortingCenterNo;
    }

    public String getSortingCenterName() {
        return sortingCenterName;
    }

    public void setSortingCenterName(String sortingCenterName) {
        this.sortingCenterName = sortingCenterName;
    }

    public String getSpCreateTimeEnd() {
        return spCreateTimeEnd;
    }

    public void setSpCreateTimeEnd(String spCreateTimeEnd) {
        this.spCreateTimeEnd = spCreateTimeEnd;
    }

    public List<Long> getSoIdList() {
        return soIdList;
    }

    public void setSoIdList(List<Long> soIdList) {
        this.soIdList = soIdList;
    }

    public List<String> getSpSoNoList() {
        return spSoNoList;
    }

    public void setSpSoNoList(List<String> spSoNoList) {
        this.spSoNoList = spSoNoList;
    }

    public List<String> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<String> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    /**
     * @return the soNo
     */
    public String getSoNo() {
        return soNo;
    }

    /**
     * @param soNo the soNo to set
     */
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }


    /**
     * @return the isvSoNo
     */
    public String getIsvSoNo() {
        return isvSoNo;
    }

    /**
     * @param isvSoNo the isvSoNo to set
     */
    public void setIsvSoNo(String isvSoNo) {
        this.isvSoNo = isvSoNo;
    }


    /**
     * @return the spSoNo
     */
    public String getSpSoNo() {
        return spSoNo;
    }

    /**
     * @param spSoNo the spSoNo to set
     */
    public void setSpSoNo(String spSoNo) {
        this.spSoNo = spSoNo;
    }


    /**
     * @return the soSource
     */
    public String getSoSource() {
        return soSource;
    }

    /**
     * @param soSource the soSource to set
     */
    public void setSoSource(String soSource) {
        this.soSource = soSource;
    }


    /**
     * @return the spId
     */
    public String getSpId() {
        return spId;
    }

    /**
     * @param spId the spId to set
     */
    public void setSpId(String spId) {
        this.spId = spId;
    }


    /**
     * @return the spNo
     */
    public String getSpNo() {
        return spNo;
    }

    /**
     * @param spNo the spNo to set
     */
    public void setSpNo(String spNo) {
        this.spNo = spNo;
    }


    /**
     * @return the spName
     */
    public String getSpName() {
        return spName;
    }

    /**
     * @param spName the spName to set
     */
    public void setSpName(String spName) {
        this.spName = spName;
    }


    /**
     * @return the bdSpNo
     */
    public String getBdSpNo() {
        return bdSpNo;
    }

    /**
     * @param bdSpNo the bdSpNo to set
     */
    public void setBdSpNo(String bdSpNo) {
        this.bdSpNo = bdSpNo;
    }


    /**
     * @return the spCreateTime
     */
    public String getSpCreateTime() {
        return spCreateTime;
    }

    /**
     * @param spCreateTime the spCreateTime to set
     */
    public void setSpCreateTime(String spCreateTime) {
        this.spCreateTime = spCreateTime;
    }


    /**
     * @return the sellerId
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId the sellerId to set
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }


    /**
     * @return the sellerNo
     */
    public String getSellerNo() {
        return sellerNo;
    }

    /**
     * @param sellerNo the sellerNo to set
     */
    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }


    /**
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName the sellerName to set
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


    /**
     * @return the sellerRemark
     */
    public String getSellerRemark() {
        return sellerRemark;
    }

    /**
     * @param sellerRemark the sellerRemark to set
     */
    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }


    /**
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


    /**
     * @return the deptNo
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * @param deptNo the deptNo to set
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }


    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    /**
     * @return the shopId
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * @param shopId the shopId to set
     */
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    /**
     * @return the shopNo
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * @param shopNo the shopNo to set
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }


    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    /**
     * @return the bdOwnerNo
     */
    public String getBdOwnerNo() {
        return bdOwnerNo;
    }

    /**
     * @param bdOwnerNo the bdOwnerNo to set
     */
    public void setBdOwnerNo(String bdOwnerNo) {
        this.bdOwnerNo = bdOwnerNo;
    }


    /**
     * @return the isvShopNo
     */
    public String getIsvShopNo() {
        return isvShopNo;
    }

    /**
     * @param isvShopNo the isvShopNo to set
     */
    public void setIsvShopNo(String isvShopNo) {
        this.isvShopNo = isvShopNo;
    }


    /**
     * @return the wayBill
     */
    public String getWayBill() {
        return wayBill;
    }

    /**
     * @param wayBill the wayBill to set
     */
    public void setWayBill(String wayBill) {
        this.wayBill = wayBill;
    }


    /**
     * @return the stationNo
     */
    public String getStationNo() {
        return stationNo;
    }

    /**
     * @param stationNo the stationNo to set
     */
    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }


    /**
     * @return the stationName
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param stationName the stationName to set
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    /**
     * @return the warehouseId
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * @param warehouseId the warehouseId to set
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }


    /**
     * @return the warehouseNo
     */
    public String getWarehouseNo() {
        return warehouseNo;
    }

    /**
     * @param warehouseNo the warehouseNo to set
     */
    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }


    /**
     * @return the warehouseName
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * @param warehouseName the warehouseName to set
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }


    /**
     * @return the distributionNo
     */
    public String getDistributionNo() {
        return distributionNo;
    }

    /**
     * @param distributionNo the distributionNo to set
     */
    public void setDistributionNo(String distributionNo) {
        this.distributionNo = distributionNo;
    }


    /**
     * @return the orgNo
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * @param orgNo the orgNo to set
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }


    /**
     * @return the erpWarehouseNo
     */
    public String getErpWarehouseNo() {
        return erpWarehouseNo;
    }

    /**
     * @param erpWarehouseNo the erpWarehouseNo to set
     */
    public void setErpWarehouseNo(String erpWarehouseNo) {
        this.erpWarehouseNo = erpWarehouseNo;
    }


    /**
     * @return the shipperId
     */
    public String getShipperId() {
        return shipperId;
    }

    /**
     * @param shipperId the shipperId to set
     */
    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }


    /**
     * @return the shipperNo
     */
    public String getShipperNo() {
        return shipperNo;
    }

    /**
     * @param shipperNo the shipperNo to set
     */
    public void setShipperNo(String shipperNo) {
        this.shipperNo = shipperNo;
    }


    /**
     * @return the shipperName
     */
    public String getShipperName() {
        return shipperName;
    }

    /**
     * @param shipperName the shipperName to set
     */
    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }


    /**
     * @return the expectDate
     */
    public String getExpectDate() {
        return expectDate;
    }

    /**
     * @param expectDate the expectDate  to set
     */
    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }


    /**
     * @return the consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * @param consignee the consignee to set
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }


    /**
     * @return the consigneeAddr
     */
    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    /**
     * @param consigneeAddr the consigneeAddr to set
     */
    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
    }


    /**
     * @return the addrProvince
     */
    public String getAddrProvince() {
        return addrProvince;
    }

    /**
     * @param addrProvince the addrProvince to set
     */
    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }


    /**
     * @return the addrCity
     */
    public String getAddrCity() {
        return addrCity;
    }

    /**
     * @param addrCity the addrCity to set
     */
    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }


    /**
     * @return the addrCounty
     */
    public String getAddrCounty() {
        return addrCounty;
    }

    /**
     * @param addrCounty the addrCounty to set
     */
    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }


    /**
     * @return the addrTown
     */
    public String getAddrTown() {
        return addrTown;
    }

    /**
     * @param addrTown the addrTown to set
     */
    public void setAddrTown(String addrTown) {
        this.addrTown = addrTown;
    }


    /**
     * @return the consigneePostcode
     */
    public String getConsigneePostcode() {
        return consigneePostcode;
    }

    /**
     * @param consigneePostcode the consigneePostcode to set
     */
    public void setConsigneePostcode(String consigneePostcode) {
        this.consigneePostcode = consigneePostcode;
    }


    /**
     * @return the consigneeMobile
     */
    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    /**
     * @param consigneeMobile the consigneeMobile to set
     */
    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }


    /**
     * @return the consigneePhone
     */
    public String getConsigneePhone() {
        return consigneePhone;
    }

    /**
     * @param consigneePhone the consigneePhone to set
     */
    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }


    /**
     * @return the consigneeEmail
     */
    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    /**
     * @param consigneeEmail the consigneeEmail to set
     */
    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }


    /**
     * @return the consigneeRemark
     */
    public String getConsigneeRemark() {
        return consigneeRemark;
    }

    /**
     * @param consigneeRemark the consigneeRemark to set
     */
    public void setConsigneeRemark(String consigneeRemark) {
        this.consigneeRemark = consigneeRemark;
    }


    /**
     * @return the afterSalesName
     */
    public String getAfterSalesName() {
        return afterSalesName;
    }

    /**
     * @param afterSalesName the afterSalesName to set
     */
    public void setAfterSalesName(String afterSalesName) {
        this.afterSalesName = afterSalesName;
    }


    /**
     * @return the afterSalesMobile
     */
    public String getAfterSalesMobile() {
        return afterSalesMobile;
    }

    /**
     * @param afterSalesMobile the afterSalesMobile to set
     */
    public void setAfterSalesMobile(String afterSalesMobile) {
        this.afterSalesMobile = afterSalesMobile;
    }


    /**
     * @return the afterSalesAddr
     */
    public String getAfterSalesAddr() {
        return afterSalesAddr;
    }

    /**
     * @param afterSalesAddr the afterSalesAddr to set
     */
    public void setAfterSalesAddr(String afterSalesAddr) {
        this.afterSalesAddr = afterSalesAddr;
    }


    /**
     * @return the soStatus
     */
    public String getSoStatus() {
        return soStatus;
    }

    /**
     * @param soStatus the soStatus to set
     */
    public void setSoStatus(String soStatus) {
        this.soStatus = soStatus;
    }


    /**
     * @return the transType
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType the transType  to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }


    /**
     * @return the soErrStatus
     */
    public String getSoErrStatus() {
        return soErrStatus;
    }

    /**
     * @param soErrStatus the soErrStatus to set
     */
    public void setSoErrStatus(String soErrStatus) {
        this.soErrStatus = soErrStatus;
    }


    /**
     * @return the soMark
     */
    public String getSoMark() {
        return soMark;
    }

    /**
     * @param soMark the soMark to set
     */
    public void setSoMark(String soMark) {
        this.soMark = soMark;
    }


    /**
     * @return the soSplitFlag
     */
    public String getSoSplitFlag() {
        return soSplitFlag;
    }

    /**
     * @param soSplitFlag the soSplitFlag to set
     */
    public void setSoSplitFlag(String soSplitFlag) {
        this.soSplitFlag = soSplitFlag;
    }


    /**
     * @return the receivable
     */
    public String getReceivable() {
        return receivable;
    }

    /**
     * @param receivable the receivable to set
     */
    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }


    /**
     * @return the guaranteeValue
     */
    public String getGuaranteeValue() {
        return guaranteeValue;
    }

    /**
     * @param guaranteeValue the guaranteeValue  to set
     */
    public void setGuaranteeValue(String guaranteeValue) {
        this.guaranteeValue = guaranteeValue;
    }


    /**
     * @return the pinAccount
     */
    public String getPinAccount() {
        return pinAccount;
    }

    /**
     * @param pinAccount the pinAccount to set
     */
    public void setPinAccount(String pinAccount) {
        this.pinAccount = pinAccount;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    /**
     * @return the updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    /**
     * @return the yn
     */
    public String getYn() {
        return yn;
    }

    /**
     * @param yn the yn to set
     */
    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getQueryPauseSo() {
        return queryPauseSo;
    }

    public void setQueryPauseSo(String queryPauseSo) {
        this.queryPauseSo = queryPauseSo;
    }

    public String getSoThirdSite() {
        return soThirdSite;
    }

    public void setSoThirdSite(String soThirdSite) {
        this.soThirdSite = soThirdSite;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getWaybillRemark() {
        return waybillRemark;
    }

    public void setWaybillRemark(String waybillRemark) {
        this.waybillRemark = waybillRemark;
    }

    public String getHybris() {
        return hybris;
    }

    public void setHybris(String hybris) {
        this.hybris = hybris;
    }

    public String getSellerFactoryNo() {
        return sellerFactoryNo;
    }

    public void setSellerFactoryNo(String sellerFactoryNo) {
        this.sellerFactoryNo = sellerFactoryNo;
    }

    public String getSellerFactoryName() {
        return sellerFactoryName;
    }

    public void setSellerFactoryName(String sellerFactoryName) {
        this.sellerFactoryName = sellerFactoryName;
    }

    public List<String> getIsvSoNoList() {
        return isvSoNoList;
    }

    public void setIsvSoNoList(List<String> isvSoNoList) {
        this.isvSoNoList = isvSoNoList;
    }

    public String getYunFee() {
        return yunFee;
    }

    public void setYunFee(String yunFee) {
        this.yunFee = yunFee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }



    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType;
    }

    public Integer getErrTypeNo() {
        return errTypeNo;
    }

    public void setErrTypeNo(Integer errTypeNo) {
        this.errTypeNo = errTypeNo;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getPauseTimeStart() {
        return pauseTimeStart;
    }

    public void setPauseTimeStart(String pauseTimeStart) {
        this.pauseTimeStart = pauseTimeStart;
    }

    public String getPauseTimeEnd() {
        return pauseTimeEnd;
    }

    public void setPauseTimeEnd(String pauseTimeEnd) {
        this.pauseTimeEnd = pauseTimeEnd;
    }

    public List<String> getWayBillList() {
        return wayBillList;
    }

    public void setWayBillList(List<String> wayBillList) {
        this.wayBillList = wayBillList;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getInsuredPriceRule() {
        return insuredPriceRule;
    }

    public void setInsuredPriceRule(String insuredPriceRule) {
        this.insuredPriceRule = insuredPriceRule;
    }

    public String getInsuredPriceFlag() {
        return insuredPriceFlag;
    }

    public void setInsuredPriceFlag(String insuredPriceFlag) {
        this.insuredPriceFlag = insuredPriceFlag;
    }

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    public String getSoVolume() {
        return soVolume;
    }

    public void setSoVolume(String soVolume) {
        this.soVolume = soVolume;
    }

    public Long getTargetWarehouseId() {
        return targetWarehouseId;
    }

    public void setTargetWarehouseId(Long targetWarehouseId) {
        this.targetWarehouseId = targetWarehouseId;
    }


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getBatchQty() {
        return batchQty;
    }

    public void setBatchQty(Integer batchQty) {
        this.batchQty = batchQty;
    }

    public Byte getTransactionSource() {
        return transactionSource;
    }

    public void setTransactionSource(Byte transactionSource) {
        this.transactionSource = transactionSource;
    }

    public String getIsvSource() {
        return isvSource;
    }

    public void setIsvSource(String isvSource) {
        this.isvSource = isvSource;
    }


    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public String getGiftNo() {
        return giftNo;
    }

    public void setGiftNo(String giftNo) {
        this.giftNo = giftNo;
    }



    public String getMergeKey() {
        return mergeKey;
    }

    public void setMergeKey(String mergeKey) {
        this.mergeKey = mergeKey;
    }

    public String getGroupOrderNumber() {
        return groupOrderNumber;
    }

    public void setGroupOrderNumber(String groupOrderNumber) {
        this.groupOrderNumber = groupOrderNumber;
    }

    public Byte getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(Byte isMerge) {
        this.isMerge = isMerge;
    }



    public String getEdiRemark() {
        return ediRemark;
    }

    public void setEdiRemark(String ediRemark) {
        this.ediRemark = ediRemark;
    }



    public List<String> getThirdWayBillList() {
        return thirdWayBillList;
    }

    public void setThirdWayBillList(List<String> thirdWayBillList) {
        this.thirdWayBillList = thirdWayBillList;
    }

    public String getThirdWayBill() {
        return thirdWayBill;
    }

    public void setThirdWayBill(String thirdWayBill) {
        this.thirdWayBill = thirdWayBill;
    }



    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }


    public Double getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Double shouldPay) {
        this.shouldPay = shouldPay;
    }


    public String getInterceptStatus() {
        return interceptStatus;
    }

    public void setInterceptStatus(String interceptStatus) {
        this.interceptStatus = interceptStatus;
    }

    public Integer getChronergy() {
        return chronergy;
    }

    public void setChronergy(Integer chronergy) {
        this.chronergy = chronergy;
    }

    public String getChronergyStr() {
        return chronergyStr;
    }

    public void setChronergyStr(String chronergyStr) {
        this.chronergyStr = chronergyStr;
    }

    public String getExpectDeliveryDate() {
        return expectDeliveryDate;
    }

    public void setExpectDeliveryDate(String expectDeliveryDate) {
        this.expectDeliveryDate = expectDeliveryDate;
    }

    public String getIsvSoType() {
        return isvSoType;
    }

    public void setIsvSoType(String isvSoType) {
        this.isvSoType = isvSoType;
    }

    public Byte getCanceledRetransport() {
        return canceledRetransport;
    }

    public void setCanceledRetransport(Byte canceledRetransport) {
        this.canceledRetransport = canceledRetransport;
    }


    public Byte getIsProcess() {
        return isProcess;
    }

    public void setIsProcess(Byte isProcess) {
        this.isProcess = isProcess;
    }

    public Byte getIsAppointDelivery() {
        return isAppointDelivery;
    }

    public void setIsAppointDelivery(Byte isAppointDelivery) {
        this.isAppointDelivery = isAppointDelivery;
    }

    public Date getAppointDeliveryDate() {
        return appointDeliveryDate;
    }

    public void setAppointDeliveryDate(Date appointDeliveryDate) {
        this.appointDeliveryDate = appointDeliveryDate;
    }

    public String getAppointDeliveryDateperiod() {
        return appointDeliveryDateperiod;
    }

    public void setAppointDeliveryDateperiod(String appointDeliveryDateperiod) {
        this.appointDeliveryDateperiod = appointDeliveryDateperiod;
    }

    public String getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(String errStatus) {
        this.errStatus = errStatus;
    }

    public String getErrReason() {
        return errReason;
    }

    public void setErrReason(String errReason) {
        this.errReason = errReason;
    }

    public List<String> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<String> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public Integer getSoMergeMark() {
        return soMergeMark;
    }

    public void setSoMergeMark(Integer soMergeMark) {
        this.soMergeMark = soMergeMark;
    }

    public List<Long> getSellerIdList() {
        return sellerIdList;
    }

    public void setSellerIdList(List<Long> sellerIdList) {
        this.sellerIdList = sellerIdList;
    }

    public String getConsigneeMobileEncrypt() {
        return consigneeMobileEncrypt;
    }

    public void setConsigneeMobileEncrypt(String consigneeMobileEncrypt) {
        this.consigneeMobileEncrypt = consigneeMobileEncrypt;
    }

    public String getConsigneeMobileIndex() {
        return consigneeMobileIndex;
    }

    public void setConsigneeMobileIndex(String consigneeMobileIndex) {
        this.consigneeMobileIndex = consigneeMobileIndex;
    }

    public String getConsigneePhoneEncrypt() {
        return consigneePhoneEncrypt;
    }

    public void setConsigneePhoneEncrypt(String consigneePhoneEncrypt) {
        this.consigneePhoneEncrypt = consigneePhoneEncrypt;
    }

    public String getConsigneePhoneIndex() {
        return consigneePhoneIndex;
    }

    public void setConsigneePhoneIndex(String consigneePhoneIndex) {
        this.consigneePhoneIndex = consigneePhoneIndex;
    }

    public String getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Integer getNoStockMark() {
        return noStockMark;
    }

    public void setNoStockMark(Integer noStockMark) {
        this.noStockMark = noStockMark;
    }

    public String getSellerNotes() {
        return sellerNotes;
    }

    public void setSellerNotes(String sellerNotes) {
        this.sellerNotes = sellerNotes;
    }
}
