package com.dj.boot.configuration.okhttp3.selector.support;

import java.util.Map;

public abstract class AbstractMethodExecutor implements IMethodExecutor {
    @Override
    public String preRequest(String body, String header) throws Exception {
        return body;
    }

    @Override
    public String processResult(String resultMsg, Map<String, Object> handlerResult) throws Exception {
        // 直接返回
        return resultMsg;
    }
}
