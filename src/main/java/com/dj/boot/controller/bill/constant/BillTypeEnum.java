package com.dj.boot.controller.bill.constant;

import java.util.*;

public enum BillTypeEnum {
    SO_ORDER_FEEDBACK("SO","销售订单",1,BillExceptionEnum.SO_ORDER_FEEDBACK_EXCEPTION.getExceptionCode()),
    PO_ORDER_FEEDBACK("PO","采购单",3,BillExceptionEnum.PO_ORDER_FEEDBACK_EXCEPTION.getExceptionCode()),
    RTW_ORDER_FEEDBACK("RTW","退货单",4,BillExceptionEnum.RTW_ORDER_FEEDBACK_EXCEPTION.getExceptionCode()),
    RTS_ORDER_FEEDBACK("RTS","退供单",5,BillExceptionEnum.RTS_ORDER_FEEDBACK_EXCEPTION.getExceptionCode()),
    ;

    private final   String  businessType;
    private final   String  billName;
    private final   Integer billCode ;
    private final   Integer exceptionCode ;


    public static  final Map<String,BillTypeEnum> BILL_TYPE_ENUM_DETAIL_MAP = new HashMap<String, BillTypeEnum>();
    public static  final Map<Integer,BillTypeEnum> BILL_TYPE_ENUM_MAP = new HashMap<Integer, BillTypeEnum>();

    static {

        for(BillTypeEnum billTypeEnum : EnumSet.allOf(BillTypeEnum.class)){
            String key =billTypeEnum.getBillCode()+"_"+billTypeEnum.getExceptionCode();
            BILL_TYPE_ENUM_MAP.put(billTypeEnum.getBillCode(),billTypeEnum);
            BILL_TYPE_ENUM_DETAIL_MAP.put(key,billTypeEnum);
        }
    }
    BillTypeEnum(String businessType , String  billName , Integer billCode, Integer exceptionCode) {
        this.billName=billName;
        this.businessType=businessType;
        this.billCode = billCode;
        this.exceptionCode = exceptionCode;
    }


    public Integer getBillCode() {
        return billCode;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }


    public String getBillName() {
        return billName;
    }


    /**
     * 获取列表
     * @return
     */
    public static List<Map<String, String>> getBillTypeMap() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> infoMap = null;
        Set billcodeSet = new HashSet();
        for (BillTypeEnum wh : EnumSet.allOf(BillTypeEnum.class)) {
            if(!billcodeSet.contains(wh.getBillCode())){
                infoMap = new HashMap<String, String>();
                infoMap.put("code", String.valueOf(wh.getBillCode()));
                infoMap.put("name", wh.getBillName());
                result.add(infoMap);
                billcodeSet.add(wh.getBillCode());
            }

        }
        return result;
    }
    /**
     * 获取列表 合作伙伴端
     * @return
     */
    public static List<Map<String, String>> getBillTypeMapForPartner() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> infoMap = null;
        Set billcodeSet = new HashSet();
        for (BillTypeEnum wh : EnumSet.allOf(BillTypeEnum.class)) {
            if (!wh.getBusinessType().equals("AO")) {
                if (!billcodeSet.contains(wh.getBillCode())) {
                    infoMap = new HashMap<String, String>();
                    infoMap.put("code", String.valueOf(wh.getBillCode()));
                    infoMap.put("name", wh.getBillName());
                    result.add(infoMap);
                    billcodeSet.add(wh.getBillCode());
                }
            }
        }
        return result;
    }

    public static String getNameByCode(Integer billCode){
        if(billCode != null && BILL_TYPE_ENUM_MAP.containsKey(billCode)){

            return BILL_TYPE_ENUM_MAP.get(billCode).getBillName();

        }

        return "";
    }

    public static  String  getBusinessTypeByCode(Integer key){
        if(key != null && BILL_TYPE_ENUM_MAP.containsKey(key)){

            return BILL_TYPE_ENUM_MAP.get(key).getBusinessType();

        }
        return "";
    }


    public String getBusinessType() {
        return businessType;
    }
}
