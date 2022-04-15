package com.dj.boot.controller.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.controller.bill.domain
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-02-15-17-18
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BillExceptionDto {
    private Long id;
    private String billNo;
    private String sellerBillNo;
    private Integer billType;
    private String billTypeStr;
    private Integer billSource;
    private String billSourceStr;
    private Integer exceptionCode;
    private String exceptionCodeStr;
    private String exceptionDesc;
    private Integer exceptionStatus;
    private String exceptionStatusStr;
    private Long exceptionDuration;
    private String renewNo;
    private String renewReason;
    private String updateUser;
    private List<Long> deptList;
    private String handlerAction;
    private Integer handlerGroup;
}
