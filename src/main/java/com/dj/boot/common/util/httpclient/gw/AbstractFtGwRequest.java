package com.dj.boot.common.util.httpclient.gw;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;


/**
 * 网关调用服务
 *
 * @author wangjia@fescotech.com
 */
@Slf4j
public abstract class AbstractFtGwRequest {

    private static final String URL = "http://127.0.0.1:8082";

    /**
     * 调用网关POST方法
     *
     * @param url    接口地址
     * @param params 接口参数
     * @return 网关响应结果
     */
    public <T> T doPost(String url, Object params, Class<T> classzz) throws Exception {
        String result = this.doExecute(url, params, HttpMethod.POST);
        log.info("AbstractFtGwRequest|result:{}", result);
        return JSONObject.parseObject(result, classzz);
    }

    /**
     * 调用网关GET方法
     *
     * @param url    接口地址
     * @param params 接口参数
     * @return 网关响应结果
     */
    public <T> T doGet(String url, Object params, Class<T> classzz) throws Exception {
        String result = this.doExecute(url, params, HttpMethod.GET);
        return JSONObject.parseObject(result, classzz);
    }

    /**
     * 调用网关
     *
     * @param url        接口地址
     * @param params     接口参数
     * @param httpMethod 接口类型
     * @return 网关响应结果
     */
    private String doExecute(String url, Object params, HttpMethod httpMethod) throws Exception {
        try {
            Method method = httpMethod.toString().toLowerCase().equals("post") ? Method.POST : Method.GET;
            HttpRequest request = HttpUtil.createRequest(method, URL + url);
            HttpResponse post = request.body(JSONObject.toJSONString(params)).execute();
            if (StrUtil.isNotEmpty(post.body())) {
                return post.body();
            }
            throw new Exception();
        } catch (Exception e) {
            log.error("网关调用失败!!", e);
            throw new Exception();
        }
    }


    /**
     * 网关服务地址
     */
    @Getter
    public enum Urls {
        USER_ECHO("/user/echo", "测试查询"),
        ;

        /**
         * 地址
         */
        private String url;

        /**
         * 描述
         */
        private String msg;

        Urls(String url, String msg) {
            this.url = url;
            this.msg = msg;
        }
    }

}
