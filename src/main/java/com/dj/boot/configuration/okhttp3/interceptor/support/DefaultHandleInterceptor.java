package com.dj.boot.configuration.okhttp3.interceptor.support;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionResult;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutorInterceptor;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultHandleInterceptor implements HttpExecutorInterceptor {
    private final static Logger log = LoggerFactory.getLogger(DefaultHandleInterceptor.class);
    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header) throws Exception {
        final Map<String, Object> parsedBody = JsonUtil.fromJson(body, new TypeReference<HashMap<String, Object>>() {
        });
        builder.params(parsedBody);
    }

    @Override
    public void postExecute(HttpExecutionContext context, HttpExecutionResult result) throws Exception {
        final byte[] rawResult = result.getRawResult();
        if (rawResult != null) {
            final String resultStr = new String(rawResult, "utf-8");
            result.setResult(resultStr);
        }
    }
}
