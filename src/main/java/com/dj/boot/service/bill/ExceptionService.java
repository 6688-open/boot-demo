package com.dj.boot.service.bill;

import com.dj.boot.common.base.Response;
import com.dj.boot.controller.bill.domain.BillExceptionRequest;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-17-38
 */
public interface ExceptionService {
    Response execute(BillExceptionRequest var1);

    Response retryException(BillExceptionRequest var1);
}
