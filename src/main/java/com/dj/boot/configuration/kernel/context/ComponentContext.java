package com.dj.boot.configuration.kernel.context;

import com.dj.boot.configuration.kernel.common.ExceptionData;
import com.dj.boot.configuration.kernel.common.InstructionData;
import com.dj.boot.configuration.kernel.common.ResultData;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-10-50
 */
public class ComponentContext {
    private Object busiData;
    private String bizNo;
    private String currentComponentId;
    private ExceptionData exceptionData;
    private Map<String, Object> extendData = new HashMap();
    private InstructionData instrData;
    private Map<String, ResultData> resultMap = new HashMap();
    private Long session = System.nanoTime();
    private String sequenceId;

    public ComponentContext() {
    }

    public String getCurrentComponentId() {
        return this.currentComponentId;
    }

    public void setCurrentComponentId(String currentComponentId) {
        this.currentComponentId = currentComponentId;
    }

    public Object getBusiData() {
        return this.busiData;
    }

    public void setBusiData(Object busiData) {
        this.busiData = busiData;
    }

    public String getBizNo() {
        return this.bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public ExceptionData getExceptionData() {
        return this.exceptionData;
    }

    public void setExceptionData(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }

    public Map<String, Object> getExtendData() {
        return this.extendData;
    }

    public void setExtendData(Map<String, Object> extendData) {
        this.extendData = extendData;
    }

    public void setExtendData(String key, Object value) {
        if (null != value) {
            if (this.extendData == null) {
                this.extendData = new HashMap();
            }

            this.extendData.put(key, value);
        }
    }

    public InstructionData getInstrData() {
        return this.instrData;
    }

    public void setInstrData(InstructionData instrData) {
        this.instrData = instrData;
    }

    public Map<String, ResultData> getResultMap() {
        return this.resultMap;
    }

    public void setResultMap(Map<String, ResultData> resultMap) {
        this.resultMap = resultMap;
    }

    public void setResultMap(ResultData resultData) {
        if (null != resultData) {
            if (this.resultMap == null) {
                this.resultMap = new HashMap();
            }

            this.resultMap.put(this.getCurrentComponentId(), resultData);
        }
    }

    public Long getSession() {
        return this.session;
    }

    public String getSequenceId() {
        return this.sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }
}
