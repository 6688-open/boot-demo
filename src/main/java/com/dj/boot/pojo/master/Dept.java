package com.dj.boot.pojo.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.pojo.master
 * @Author: wangJia
 * @Date: 2021-12-23-11-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String deptNo;
    private String deptName;
    private String bdOwnerNo;
}
