package com.dj.boot.pojo.master;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class DeptEntity {
    private Long id;
    private String deptNo;
    private String deptName;
    private String bdOwnerNo;
    /**
     * 是否包有业主号
     * @return
     */
    public boolean hasBdOwnerNo(){
        return StringUtils.isNotBlank(bdOwnerNo);
    }
}
