package com.dj.boot.service.so;

import com.dj.boot.common.base.Response;
import com.dj.boot.controller.so.domain.SoExceptionParam;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-15-04
 */
public interface SoBizService {
    /**
     * 尝试恢复订单异常
     * @param soExceptionParam 异常订单信息
     * @param disposeType  尝试恢复的类型
     * @return
     */
    Response tryResumeException(SoExceptionParam soExceptionParam, Integer disposeType);
}
