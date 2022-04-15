package com.dj.boot.configuration.okhttp3.selector.support;

import java.io.IOException;
import java.util.Map;

/**
* 处理调用Http请求返回的结果
 * @author zhaoxu168
 * @date 2019-03-28
 */
public interface IMethodExecutor {

    /**
     * 接收到Body参数，在HTTP提交之前执行的方法，用于二次处理Body参数
     * @param body
     * @return
     * @throws IOException
     */
    public String preRequest(String body, String header)throws Exception;

    /**
     * 接受http请求返回的字串，并将转为map对象
     * @param resultMsg
     * @return
     */
    public String processResult(String resultMsg, Map<String, Object> handlerResult) throws Exception;
}
