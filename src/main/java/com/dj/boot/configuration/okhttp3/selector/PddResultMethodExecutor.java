package com.dj.boot.configuration.okhttp3.selector;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.configuration.okhttp3.selector.support.AbstractMethodExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 结果处理器
 */
@Component("p-d-d-test-v1")
public class PddResultMethodExecutor extends AbstractMethodExecutor  {

    private final Logger log = LogManager.getLogger(PddResultMethodExecutor.class);

    @Override
    public String preRequest(String body, String header) throws Exception {
        // DT转换文件不支持非数组数据生成数组模型，需要手工拼接
        StringBuilder sb = new StringBuilder();
        sb.append("{\"orders\":[");
        sb.append(body);
        sb.append("]}");
        log.error("结果前置处理:{}", sb.toString());
        return sb.toString();
    }

    @Override
    public String processResult(String resultMsg,Map<String, Object> handlerResult) throws Exception {
        handlerResult.put("msg","success");
        handlerResult.put("code",100);
        handlerResult.put("data", resultMsg);
        log.error("结果前置处理:{}", JSONObject.toJSONString(handlerResult));
        return JSONObject.toJSONString(handlerResult);
    }

}