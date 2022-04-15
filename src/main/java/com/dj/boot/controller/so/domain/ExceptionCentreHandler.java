package com.dj.boot.controller.so.domain;


import com.dj.boot.common.base.Response;

public interface ExceptionCentreHandler {
    public Response tryResume(SoExceptionParam soExceptionParam);
}
