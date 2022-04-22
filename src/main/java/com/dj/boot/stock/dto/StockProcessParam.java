package com.dj.boot.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.stock.dto
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-04-22-10-58
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StockProcessParam {

    private String deptNo;
    private String bizNo;
}
