package com.dj.boot.configuration.kernel.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-10-52
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResultData {
    private Object data;
    private String desc;
    private int resultCode;

    public static ResultData successResultData(Object data) {
        ResultData resultData = new ResultData();
        resultData.setData(data);
        resultData.setResultCode(200);
        return resultData;
    }

    public static ResultData failResultData(String failMsg) {
        ResultData resultData = new ResultData();
        resultData.setDesc(failMsg);
        resultData.setResultCode(-1);
        return resultData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"data\":").append(this.data);
        sb.append(",\"desc\":\"").append(this.desc).append('"');
        sb.append(",\"resultCode\":").append(this.resultCode);
        sb.append('}');
        return sb.toString();
    }
}
