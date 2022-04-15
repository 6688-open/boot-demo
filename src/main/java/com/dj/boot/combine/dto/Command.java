package com.dj.boot.combine.dto;

import java.io.Serializable;
import java.util.Map;

public class Command<T extends Serializable> {

    private String businessType;
    private String operateType;
    private String vendorCode;
    private String vendorName;
    private T data;

    private Map<String,Object> businessMap;


    public Command() {
    }

    public Command(String businessType, String operateType) {
        this.businessType = businessType;
        this.operateType = operateType;
    }

    public Command(String businessType, String operateType, T data) {
        this.businessType = businessType;
        this.operateType = operateType;
        this.data = data;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Map<String,Object> getBusinessMap() {
        return businessMap;
    }

    public void setBusinessMap(Map<String,Object> businessMap) {
        this.businessMap = businessMap;
    }
}
