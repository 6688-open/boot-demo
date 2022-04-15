package com.dj.boot.controller.bill.domain;


import com.dj.boot.common.base.Response;

public interface BillExceptionHandler {
    public Response tryResume(BillExceptionRequest request);
}
