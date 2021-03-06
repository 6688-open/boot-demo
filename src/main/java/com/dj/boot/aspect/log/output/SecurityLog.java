/*
package com.dj.boot.aspect.log.output;

import com.alibaba.fastjson.JSON;
import com.dj.boot.aspect.log.log4j.SecurityLoggerFactory;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

*/
/**
 * 安全部规定日志
 * 包括 head(头部)，reqInfo(请求部分)，respInfo(响应部分)
 *//*

public class SecurityLog {
    //安全日志Logger
    private static final Logger LOGGER = SecurityLoggerFactory.getLogger();
    private HeadLogSecurityInfo head;
    private ReqLogSecurityInfo reqInfo;
    private RespLogSecurityInfo respInfo;

    public SecurityLog(HeadLogSecurityInfo head, ReqLogSecurityInfo reqInfo, RespLogSecurityInfo respInfo) {
        this.head = head;
        this.reqInfo = reqInfo;
        this.respInfo = respInfo;
    }

    public HeadLogSecurityInfo getHead() {
        return head;
    }

    public ReqLogSecurityInfo getReqInfo() {
        return reqInfo;
    }

    public RespLogSecurityInfo getRespInfo() {
        return respInfo;
    }

    public void setHead(HeadLogSecurityInfo head) {
        this.head = head;
    }

    public void setReqInfo(ReqLogSecurityInfo reqInfo) {
        this.reqInfo = reqInfo;
    }

    public void setRespInfo(RespLogSecurityInfo respInfo) {
        this.respInfo = respInfo;
    }

    @Override
    public String toString() {
        return "SecurityLog{" +
                "head=" + head +
                ", reqInfo=" + reqInfo +
                ", respInfo=" + respInfo +
                '}';
    }

    public static void main(String[] args) throws Exception {
        //创造查询日志
        //head(头域)
        HeadLogSecurityInfo headInfo = new HeadLogSecurityInfo();
        headInfo.setVersion("V1.0");
        headInfo.setSystemName("polaris");
        headInfo.setUuid(UUID.randomUUID().toString());
        headInfo.setAppName("plaris-config");
        headInfo.setInterfaceName("sourceTableController");
        headInfo.setTime(new Date().getTime() / 1000);
        headInfo.setServerIp(InetAddress.getLocalHost().getHostAddress());
        headInfo.setClientIp(InetAddress.getLocalHost().getHostAddress());
        headInfo.setAccountType(1);
        headInfo.setAccountName("张三");
        headInfo.setOp(3); //查询

        // reqInfo(请求部分)
        ReqLogSecurityInfo reqInfo = new ReqLogSecurityInfo();
        reqInfo.setErpId("dddd");
        reqInfo.setTimeFrom(0L);
        reqInfo.setTimeTo(System.currentTimeMillis());

        //respInfo(应答部分)
        RespLogSecurityInfo respInfo = new RespLogSecurityInfo();
        respInfo.setStatus(0); //success
        respInfo.setRecordCnt(100L); //记录总数


        //总日志创建
        SecurityLog info = new SecurityLog(headInfo, reqInfo, respInfo);
        info.print();
       // LOGGER.info("SecurityLog:{}", JSON.toJSON(info));
    }

    public void print(){
        LOGGER.info(JSON.toJSONString(this));
    }
    */
/**
     * 头域：包含系统名称、应用名称、接口名称、接口名称、时间、用户标识、操作行为等通用信息
     *//*

    public static class HeadLogSecurityInfo {

        //本日志格式的版本号,必填 例如 V1.0；；
        private String version;
        // 系统英文名称；必填；
        private String systemName;
        // 应用英文名称； 必填；
        private String appName;
        // 接口名称； 必填；
        private String interfaceName;
        //唯一ID； 保证同一个应用是唯一的。当接口分为多个记录返回同一个查询信息时，可通过唯一ID的方式做关联
        private String uuid;
        //操作时间（北京时间），数值型.；必填；单位：秒；
        private Long time;
        //部署机器ip，点分制；必填；
        private String serverIp;
        //客户端ip,点分制；非必填;
        private String clientIp;
        //账号类型；1：erp; 2：passport; 3：selfCreated（自建）必填；
        private Integer accountType;
        //账号名称；必填；
        private String accountName;
        //具体操作行为：0：添加; 1：删除; 2：更新; 3：查询; 4：导出 必填；
        private Integer op;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getInterfaceName() {
            return interfaceName;
        }

        public void setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getServerIp() {
            return serverIp;
        }

        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public Integer getAccountType() {
            return accountType;
        }

        public void setAccountType(Integer accountType) {
            this.accountType = accountType;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Integer getOp() {
            return op;
        }

        public void setOp(Integer op) {
            this.op = op;
        }

        public HeadLogSecurityInfo() {  //默认服务器ip
            try {
                InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
                this.serverIp = address.getHostAddress();//192.168.0.121
            } catch (Exception e) {

            }
            this.version = "V1.0";
            this.systemName = "USER";
            this.uuid = String.valueOf(System.nanoTime());
            this.time = new Date().getTime() / 1000;
        }
    }


    */
/**
     * 请求日志类
     * 添加、删除、更新、查询、导出的应用场景不同，请求部分内容亦不相同，现对请求部分做约定如下：
     * 1) 查询、导出、删除、更新的请求部分填写搜索条件，如订单系统，请求部分可能包含手机号、搜索时间范围、订单类型等
     * 例如："reqInfo":{"telephone":"13301288089",‘“timeFrom”:15678778872, ‘“timeTO”:15678778872};
     * 2) 添加的请求部分填写为空，即不用填写任何信息。
     *//*

    public static class ReqLogSecurityInfo {
        //员工账号
        private String erpId;
        //开始时间（北京时间），数值型.；单位：秒；NO MD5
        private Long timeFrom;
        //终止 时间（北京时间），数值型.；单位：秒；NO MD5
        private Long timeTo;
        //京东用户账号 NO MD5
        private String pin;
        //订单号 NO MD5
        private String orderId;
        //运单号 NO MD5
        private String carryBillId;
        //商家ID NO MD5
        private Integer venderId;
        //收货地址省份编号 NO MD5
        private Integer addrProvinceId;
        //收货地址城市编号 NO MD5
        private Integer addrCityId;
        //收货地址区县编号 NO MD5
        private Integer addrCountyId;
        //仓库编号 NO MD5
        private Integer storeId;
        //商品编号 NO MD5
        private String skuId;
        //订单类型 NO MD5
        private String orderCd;
        //配送方式 NO MD5
        private String carryBillCd;
        //售后单号 NO MD5
        private String afsOrdId;
        //商品类型 NO MD5
        private String itemCate;
        //手机号  MD5
        private String telephone;
        //邮箱  MD5
        private String email;
        //收件人
        private String receiveName;
        //收件人地址
        private String receiveAddress;
        //通用扩展key1
        private String extKey1;
        //通用扩展值1
        private String extValue1;
        //供应商名称
        private String vendorName;

        private String inputParam;

        public String getErpId() {
            return erpId;
        }

        public void setErpId(String erpId) {
            this.erpId = erpId;
        }

        public Long getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(Long timeFrom) {
            this.timeFrom = timeFrom;
        }

        public Long getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(Long timeTo) {
            this.timeTo = timeTo;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCarryBillId() {
            return carryBillId;
        }

        public void setCarryBillId(String carryBillId) {
            this.carryBillId = carryBillId;
        }

        public Integer getVenderId() {
            return venderId;
        }

        public void setVenderId(Integer venderId) {
            this.venderId = venderId;
        }

        public Integer getAddrProvinceId() {
            return addrProvinceId;
        }

        public void setAddrProvinceId(Integer addrProvinceId) {
            this.addrProvinceId = addrProvinceId;
        }

        public Integer getAddrCityId() {
            return addrCityId;
        }

        public void setAddrCityId(Integer addrCityId) {
            this.addrCityId = addrCityId;
        }

        public Integer getAddrCountyId() {
            return addrCountyId;
        }

        public void setAddrCountyId(Integer addrCountyId) {
            this.addrCountyId = addrCountyId;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getOrderCd() {
            return orderCd;
        }

        public void setOrderCd(String orderCd) {
            this.orderCd = orderCd;
        }

        public String getCarryBillCd() {
            return carryBillCd;
        }

        public void setCarryBillCd(String carryBillCd) {
            this.carryBillCd = carryBillCd;
        }

        public String getAfsOrdId() {
            return afsOrdId;
        }

        public void setAfsOrdId(String afsOrdId) {
            this.afsOrdId = afsOrdId;
        }

        public String getItemCate() {
            return itemCate;
        }

        public void setItemCate(String itemCate) {
            this.itemCate = itemCate;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = md5(telephone);
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = md5(email);
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceiveAddress() {
            return receiveAddress;
        }

        public void setReceiveAddress(String receiveAddress) {
            this.receiveAddress = receiveAddress;
        }

        public String getExtKey1() {
            return extKey1;
        }

        public void setExtKey1(String extKey1) {
            this.extKey1 = extKey1;
        }

        public String getExtValue1() {
            return extValue1;
        }

        public void setExtValue1(String extValue1) {
            this.extValue1 = extValue1;
        }

        public String getVendorName() {
            return vendorName;
        }

        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

        public String getInputParam() {
            return inputParam;
        }

        public void setInputParam(String inputParam) {
            this.inputParam = inputParam;
        }
    }

    */
/**
     * 应答部分是查询、导出、更新、删除、添加等操作返回的内容或状态。为了数据安全，不可记录数据的详细内容
     * （必须严格遵守），只记录本次操作的唯一标识，
     *//*

    public static class RespLogSecurityInfo {

        //记录操作是否成功：0 ：成功 ;-1：失败
        private Integer status;

        //若返回成功，记录条数 如返回失败，填为0
        private Long recordCnt = 0L;

        // 记录的唯一标识
        //uniqueIdentifier唯一标识
        //字段约定如下：
        // 1）通过唯一标识必须能够反查出本记录的详细信息，如通过订单号可以获取到商品名称、订购人、订购时间等信息。
        // 2）如唯一标识不在uniqueIdentifier 列表中，业务方应联系安全接口人，由安全部统一分配相关字段。
        // 3）查询、导出、删除、更新的响应部分填写唯一标识。
        // 4）添加的响应部分亦填写唯一标识。
        // 5）批量查询应返回全部记录，每条记录可以包含多个唯一标识，
        // 例如示例填写了accountName和orderID字段，共2条记录："uniqueIdentifier": [{"accountName": "张三"," orderID ": "1238765876"},{"accountName": "李四","orderNumber": "1238765876"}]
        private List<UniqueIdentifier> uniqueIdentifier = new ArrayList<UniqueIdentifier>();

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Long getRecordCnt() {
            return recordCnt;
        }

        public void setRecordCnt(Long recordCnt) {
            this.recordCnt = recordCnt;
        }

        public List<UniqueIdentifier> getUniqueIdentifier() {
            return uniqueIdentifier;
        }

        public void setUniqueIdentifier(List<UniqueIdentifier> uniqueIdentifier) {
            this.uniqueIdentifier = uniqueIdentifier;
        }

        public class UniqueIdentifier {

            //不是安全规范中的字段，测试阶段使用
            private Integer id;

            //用户标识
            private String accountName;

            //订单号
            private String orderId;

            //运单号
            private String carryBillId;

            //商品编号
            private String skuId;

            //售后单号
            private String afsOrdId;

            //导出文件的名称
            private String fileName;

            //商家相关：商家id
            private String merchantId;

            //收件人
            private String receiveName;
            //收货电话
            private String extKey1;
            //收货电话
            private String extValue1;
            //寄件人电话
            private String extKey2;
            private String extValue2;
            //寄件人地址
            private String extKey3;
            private String extValue3;
            //收件人地址
            private String receiveAddress;
            //文件链接URL
            private String fileUrl;
            //文件MD5值
            private String fileMd5;
            //采购单号
            private String purchaseId;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getCarryBillId() {
                return carryBillId;
            }

            public void setCarryBillId(String carryBillId) {
                this.carryBillId = carryBillId;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public String getAfsOrdId() {
                return afsOrdId;
            }

            public void setAfsOrdId(String afsOrdId) {
                this.afsOrdId = afsOrdId;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getReceiveName() {
                return receiveName;
            }

            public void setReceiveName(String receiveName) {
                this.receiveName = receiveName;
            }

            public String getExtKey1() {
                return extKey1;
            }

            public void setExtKey1(String extKey1) {
                this.extKey1 = extKey1;
            }

            public String getExtValue1() {
                return extValue1;
            }

            public void setExtValue1(String extValue1) {
                this.extValue1 = extValue1;
            }

            public String getExtKey2() {
                return extKey2;
            }

            public void setExtKey2(String extKey2) {
                this.extKey2 = extKey2;
            }

            public String getExtValue2() {
                return extValue2;
            }

            public void setExtValue2(String extValue2) {
                this.extValue2 = extValue2;
            }

            public String getExtKey3() {
                return extKey3;
            }

            public void setExtKey3(String extKey3) {
                this.extKey3 = extKey3;
            }

            public String getExtValue3() {
                return extValue3;
            }

            public void setExtValue3(String extValue3) {
                this.extValue3 = extValue3;
            }

            public String getReceiveAddress() {
                return receiveAddress;
            }

            public void setReceiveAddress(String receiveAddress) {
                this.receiveAddress = receiveAddress;
            }

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            public String getFileMd5() {
                return fileMd5;
            }

            public void setFileMd5(String fileMd5) {
                this.fileMd5 = fileMd5;
            }

            public String getPurchaseId() {
                return purchaseId;
            }

            public void setPurchaseId(String purchaseId) {
                this.purchaseId = purchaseId;
            }
        }
    }

    public static String md5(String sourceStr) {
        if(sourceStr ==null || sourceStr.trim() == ""){
            return null;
        }
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((sourceStr + "seclog1359").getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer();

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public enum AccountTypeEnum {
        ERP(1),
        PASSPORT(2),
        SELFCREATED(3);
        private Integer desc;

        AccountTypeEnum(Integer desc) {
            this.desc = desc;
        }

        public Integer getDesc() {
            return desc;
        }
    }

    public enum OpEnum {
        ADD(0),
        DEL(1),
        UPDATE(2),
        SELECT(3),
        EXPORT(4);
        private Integer desc;

        OpEnum(Integer desc) {
            this.desc = desc;
        }

        public Integer getDesc() {
            return desc;
        }
    }

    public enum StatusEnum {
        SUCCEED(0),
        Err(-1),;
        private Integer desc;

        StatusEnum(Integer desc) {
            this.desc = desc;
        }

        public Integer getDesc() {
            return desc;
        }
    }


}

*/
