package com.dj.boot.handler;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;

/**
 * 模板方法模式
 *抽象处理器
 * @param <T>
 * @param <V>
 */
public abstract class AbstractHandler <T,V> {

    abstract public BaseResponse<V> handle(Request<T> request);
}
