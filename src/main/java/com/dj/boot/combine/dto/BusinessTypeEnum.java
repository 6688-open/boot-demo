package com.dj.boot.combine.dto;

public enum BusinessTypeEnum {
    PDD_V2_GET_TOKEN("PDDV2", "P0001","获取Token"),
    PDD_V2_REFRESH_TOKEN("PDDV2", "P0002","刷新Token"),
    PDD_V2_GET_CUSTOM("PDDV2", "P0003","获取自定义区模板信息"),

    TIK_TOK_GET_TOKEN("TIKTOK", "C1001","获取token"),


    ;


    private final String businessType;
    private final String operateType;
    private final String desc;

    BusinessTypeEnum(String businessType, String operateType, String desc) {
        this.businessType = businessType;
        this.operateType = operateType;
        this.desc = desc;
    }

    public String businessType() {
        return businessType;
    }


    public String operateType() {
        return operateType;
    }

    public String desc(){
        return desc;
    }
    public static BusinessTypeEnum parseOf(String businessType,String operateType){
        for (BusinessTypeEnum def:BusinessTypeEnum.values()) {
            if(def.businessType.equals(businessType)&&def.operateType.equals(operateType)){
                return def;
            }
        }
        throw new IllegalArgumentException("未知command[businessType="+businessType+",operateType="+operateType+"]");
    }

}


