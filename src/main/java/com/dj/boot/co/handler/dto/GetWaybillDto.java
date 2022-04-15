package com.dj.boot.co.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-03-25-10-45
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetWaybillDto {
    private String outWbNo;
}
