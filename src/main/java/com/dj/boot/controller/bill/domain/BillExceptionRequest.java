package com.dj.boot.controller.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-17-15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BillExceptionRequest<T> {

    private String billType;
    private T data;
}
