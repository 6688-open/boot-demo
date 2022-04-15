package com.dj.boot.configuration.okhttp3.executor;


import com.dj.boot.configuration.filter.service.impl.PermissionServiceImpl;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class PostFormBodyExecutor implements HttpExecutor {

    private final static Logger log = LoggerFactory.getLogger(PostFormBodyExecutor.class);

    private OkHttpClient okHttpClient;
    @Override
    public byte[] execute(HttpExecutionContext context)throws Exception{
        log.info("填充请求参数:["+context.toString()+"]");
        final Request.Builder builder = new Request.Builder();
        builder.url(context.url());
        builder.headers(Headers.of(context.headers()));
        final FormBody.Builder bodyBuilder=new FormBody.Builder();
        if(context.params()!=null&&!context.params().isEmpty()){
            final Set<Map.Entry<String, Object>> params = context.params().entrySet();
            for (Map.Entry<String, Object> param : params) {
                bodyBuilder.addEncoded(param.getKey(), param.getValue().toString());
            }
        }
        final Request request = builder.post(bodyBuilder.build()).build();
        log.info("发起请求:["+context.toString()+"]");
        final Response response = okHttpClient.newCall(request).execute();
        byte[] returnByte = null;
        if (response.body()!=null){
            returnByte = response.body().bytes();
        }
        final StringBuilder message = new StringBuilder();
        message.append(context.toString());
        message.append(",");
        message.append(response.toString());
        if (response.isSuccessful()) {
            log.info("请求调用成功:"+message.toString());
             return returnByte;
        }else {
            message.append(",Body[");
            message.append(returnByte!=null?new String(returnByte,"utf-8"):"null");
            message.append("]");
        }
        log.error("请求调用失败:"+message.toString());
        throw new RuntimeException("请求调用失败:"+message.toString());
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}
