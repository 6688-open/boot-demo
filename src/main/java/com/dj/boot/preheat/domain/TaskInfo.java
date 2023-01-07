package com.dj.boot.preheat.domain;

import lombok.Data;

@Data
public class TaskInfo {
    private String batchNo;
    private String type;
    private String db;
    private String table;
    private Long totalCount;
    private Long successNum;

    @Override
    public String toString() {
        return type + "-" + db + "-" + table+"-"+batchNo;
    }
}
