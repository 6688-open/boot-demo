package com.dj.boot.configuration.okhttp3.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.dj.boot.combine.handler.enums.PddTemplateConstants;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.selector.support.IMethodExecutor;
import com.dj.boot.configuration.okhttp3.selector.support.MethodExecutorSelector;
import com.dj.boot.configuration.okhttp3.sign.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * pdd v2拦截器
 */
public class PddInterceptor implements HttpExecutorInterceptor {

    private final Logger LOGGER = LogManager.getLogger(PddInterceptor.class);

    /**
     * 方法执行器选择器
     */
    @Resource
    private MethodExecutorSelector methodExecutorSelector;

    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header) throws Exception {
        LOGGER.error("PddV2Interceptor body:{},header:{}", body, header);
        Map<String, Object> parsedHeader = JsonUtil.fromJson(header, new TypeReference<HashMap<String, Object>>() {
        });
        Map<String, Object> parsedBody = JsonUtil.fromJson(body, new TypeReference<TreeMap<String, Object>>() {
        });
        if(parsedHeader.containsKey(PddTemplateConstants.ORIGIN_REQUEST_KEY)&&parsedHeader.get(PddTemplateConstants.ORIGIN_REQUEST_KEY).equals(PddTemplateConstants.ORIGIN_REQUEST_KEY_1)){
            String originBody = (String) parsedHeader.get(PddTemplateConstants.ORIGIN_REQUEST_BODY);
            parsedBody = JsonUtil.fromJson(originBody, new TypeReference<TreeMap<String, Object>>() {
            });
            String targetBody = JSONObject.toJSONString(parsedBody.get(PddTemplateConstants.WAY_BILL_PARAM));
            parsedBody = JsonUtil.fromJson(targetBody, new TypeReference<TreeMap<String, Object>>() {
            });
            //parsedBody = (Map<String, Object>) parsedBody.get(PddTemplateConstants.WAY_BILL_PARAM);
        }
        String appSecret = (String) parsedHeader.get(PddTemplateConstants.CLIENT_SECRET);
        // parsedBody排序，嵌套排序
        handleOrderMap(parsedBody);
        Map<String, Object> requestParam = buildRequestParams(parsedBody, parsedHeader);
        buildRequestSign(requestParam, appSecret);
        //String sign =  KuaiShouSign.signTopRequest(requestParam,appSecret, SignConstants.SIGN_METHOD_HMAC_SHA256);
        builder.params(requestParam);
        // 回调时获取执行方法名称
        builder.addExtra(PddTemplateConstants.TYPE, parsedHeader.get(PddTemplateConstants.TYPE));
        if(parsedHeader.containsKey(PddTemplateConstants.ORIGIN_RESP)&&parsedHeader.get(PddTemplateConstants.ORIGIN_RESP).equals(PddTemplateConstants.ORIGIN_RESP_1)){
            builder.addExtra(PddTemplateConstants.ORIGIN_RESP,true);
        } else {
            builder.addExtra(PddTemplateConstants.ORIGIN_RESP,false);
        }
        // 构建URL
        String requestUrl = String.valueOf(parsedHeader.get(PddTemplateConstants.CLIENT_URL));
        builder.url(requestUrl);
        Map<String, String> headers = JsonUtil.fromJson(header, new TypeReference<HashMap<String, Object>>() {
        });
        builder.headers(headers);
        LOGGER.error("PddV2Interceptor url:{},params:{}", requestUrl, JsonUtil.toJson(parsedBody));
    }


    /**
     * 处理顺序 MAP
     *
     * @param parsedBody 解析 body
     */
    private void handleOrderMap(Map<String, Object> parsedBody) {
        for (Map.Entry<String, Object> entry : parsedBody.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Map curMap = (Map) entry.getValue();
                TreeMap treeMap = new TreeMap(curMap);
                parsedBody.put(entry.getKey(), treeMap);
                handleOrderMap(treeMap);
            } else if (entry.getValue() instanceof List) {
                handleOrderList((List) entry.getValue());
            } else {
                parsedBody.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 处理排序列表
     *
     * @param list 列表
     */
    private void handleOrderList(List list) {
        int index = 0;
        for (Object obj : list) {
            if (obj instanceof Map) {
                TreeMap treeMap = new TreeMap((Map) obj);
                handleOrderMap((Map) treeMap);
                list.set(index++, treeMap);
            }
        }
    }


    /**
     * 签名算法:
     *   目前支持的签名算法为：MD5(sign_method=md5)，签名过程如下：
     *      本次请求中所有请求参数（包含公共参数与业务参数）进行首字母以ASCII方式升序排列（ASCII ASC），对于相同字母则使用下个字母做二次排序，字母序为从左到右，以此类推
     *      排序后的结果按照参数名（key）参数值（value）的次序进行字符串拼接，拼接处不包含任何字符
     *      拼接完成的字符串做进一步拼接成1个字符串（包含所有kv字符串的长串），并在该长串的头部及尾部分别拼接client_secret，完成签名字符串的组装
     *      最后对签名字符串，使用MD5算法加密后，得到的MD5加密密文后转为大写，即为sign值
     *
     * @param requestParam 请求参数
     * @param appSecret    应用程序的秘密
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    private void buildRequestSign(Map<String, Object> requestParam, String appSecret) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(appSecret);
        Set<String> keySet = requestParam.keySet();
        for (String key : keySet) {
            sb.append(key).append(getIfNotNull(requestParam.get(key)));
        }
        sb.append(appSecret);
        String signParam = Md5SignUtil.getTikTokSign(sb.toString()).toUpperCase();
        requestParam.put(PddTemplateConstants.SIGN, signParam);
        // 特殊字符转义
        String bodyParam = (String)requestParam.get(PddTemplateConstants.WAY_BILL_PARAM);
        if (StringUtils.isNotBlank(bodyParam)) {
            requestParam.put(PddTemplateConstants.WAY_BILL_PARAM, URLEncoder.encode(bodyParam, "UTF-8"));
        }
    }

    private String getIfNotNull(Object obj) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    /**
     * 构建请求参数
     *
     * @param parsedBody   解析 body
     * @param parsedHeader 解析头
     * @return {@link Map<String, Object>}
     */
    private Map<String, Object> buildRequestParams(Map<String, Object> parsedBody, Map<String, Object> parsedHeader) {
        Map<String, Object> params = new TreeMap<String, Object>();

        // app_key
        if (parsedHeader.get(PddTemplateConstants.CLIENT_ID) == null) {
            throw new RuntimeException("参数中缺失：" + PddTemplateConstants.CLIENT_ID);
        }
        params.put(PddTemplateConstants.CLIENT_ID, parsedHeader.get(PddTemplateConstants.CLIENT_ID));
        // type
        if (parsedHeader.get(PddTemplateConstants.TYPE) == null) {
            throw new RuntimeException("参数中缺失：" + PddTemplateConstants.TYPE);
        }
        params.put(PddTemplateConstants.TYPE, parsedHeader.get(PddTemplateConstants.TYPE));
        //data_type
        if (parsedHeader.get(PddTemplateConstants.DATA_TYPE) != null) {
            params.put(PddTemplateConstants.DATA_TYPE, parsedHeader.get(PddTemplateConstants.DATA_TYPE));
        }
        // token
        if (parsedHeader.get(PddTemplateConstants.ACCESS_TOKEN) != null) {
            params.put(PddTemplateConstants.ACCESS_TOKEN, parsedHeader.get(PddTemplateConstants.ACCESS_TOKEN));
        }
        if(!params.get(PddTemplateConstants.TYPE).toString().equals(PddTemplateConstants.TYPE_WAY_BILL)){
            params.putAll(parsedBody);
        }
        if(params.get(PddTemplateConstants.TYPE).toString().equals(PddTemplateConstants.TYPE_WAY_BILL)){
            params.put(PddTemplateConstants.WAY_BILL_PARAM,JsonUtil.toJson(parsedBody));
        }

        // timestamp
        params.put(PddTemplateConstants.TIMESTAMP,System.currentTimeMillis()/1000);
        //VERSION
        params.put(PddTemplateConstants.VERSION, parsedHeader.get(PddTemplateConstants.VERSION));
        return params;
    }

    @Override
    public void postExecute(HttpExecutionContext context, HttpExecutionResult result) throws Exception {
        Map<String, Object> handlerResult = new HashMap<String, Object>(5, 1);
        handlerResult.put("code", 100);
        String postMsg = new String(result.getRawResult(), "utf-8");
        LOGGER.error("call PDD2 get response - {}" + postMsg);
        // 对接外部支持
        if (context.extras().containsKey(PddTemplateConstants.ORIGIN_RESP) && (Boolean) context.extras().get(PddTemplateConstants.ORIGIN_RESP)) {
            result.setResult(postMsg);
            return;
        }
        // 获取api执行的方法，解析具体的api返回参数
        String method = (String) context.extras().get(PddTemplateConstants.TYPE);
        IMethodExecutor methodExecutor = methodExecutorSelector.selector(method);
        if (methodExecutor != null) {
            result.setResult(methodExecutor.processResult(postMsg, handlerResult));
        }
    }
}
