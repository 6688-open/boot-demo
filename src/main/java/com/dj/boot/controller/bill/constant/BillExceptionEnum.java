package com.dj.boot.controller.bill.constant;

import java.util.*;

public enum BillExceptionEnum {
    /*销售单*/
    SO_ORDER_FEEDBACK_EXCEPTION(1000,"出库回传商家" ),

    /*采购单*/
    PO_ORDER_FEEDBACK_EXCEPTION(4000,"采购单回传" ),

    /*退货单*/
    RTW_ORDER_FEEDBACK_EXCEPTION(5000,"退货单回传" ),

    /*退供单*/
    RTS_ORDER_FEEDBACK_EXCEPTION(6000,"退供单回传" ),
    ;

    private final   Integer exceptionCode ;
    private final   String desc ;


    public static  final Map<Integer, BillExceptionEnum> billExceptionEnumHashMap = new HashMap<Integer, BillExceptionEnum>();

    static {

        for(BillExceptionEnum billTypeEnum : EnumSet.allOf(BillExceptionEnum.class)){
            billExceptionEnumHashMap.put(billTypeEnum.getExceptionCode(),billTypeEnum);
        }
    }
    BillExceptionEnum(Integer exceptionCode, String desc) {
        this.exceptionCode = exceptionCode;
        this.desc=desc;
    }



    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer exceptionCode){
        if(exceptionCode != null && billExceptionEnumHashMap.containsKey(exceptionCode)) {
            return  billExceptionEnumHashMap.get(exceptionCode).desc;
        }

        return null;

    }

    public static BillExceptionEnum getExceptionEnum(Integer exceptionCode){
        if(exceptionCode!= null && billExceptionEnumHashMap.containsKey(exceptionCode)){
            return  billExceptionEnumHashMap.get(exceptionCode);
        }
        return null;
    }


}
