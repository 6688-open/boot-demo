package com.dj.boot.test.domain;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.util.json.JsonUtil;

import java.io.IOException;
import java.io.Serializable;

/**
 * @Author: wangJia
 * @Date: 2021-08-20-10-50
 */
public class CompensateStatusResponse implements Serializable {
    /**
     * 业务类型(400:盘亏单，401：事件单，402：异常单)
     */
    private String businessId;
    /**
     * 唯一请求ID，原始单号_01_业务类型
     */
    private String exptId;
    /**
     * 操作信息(121 :京责，122:非京责，123:未联系上)
     */
    private String resultType;
    /**
     * 描述信息
     */
    private String desc;
    /**
     * MCSS客服系统事件号
     */
    private String eventNo;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getExptId() {
        return exptId;
    }

    public void setExptId(String exptId) {
        this.exptId = exptId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }


}
