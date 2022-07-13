package com.dj.boot.common.util.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * http调用工具
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-07-13-16-57
 */
public class HttpRequestUtil {
    private static CloseableHttpClient httpClient;
    private static final Map<String, String> HRADERS = new HashMap<>();

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // headres所有接口都需要携带headers才能使用,如果不需要可以不写
        HRADERS.put("abc", "abc");
    }

    public static String get(String url) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            // 调用得接口都需要使用这些headers
            for (String key : HRADERS.keySet()) {
                System.out.println("key= "+ key + " and value= " + HRADERS.get(key));
                httpGet.addHeader(key, HRADERS.get(key));
            }

            response = httpClient.execute(httpGet);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            //httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            // 调用得接口都需要使用这些headers
            for (String key : HRADERS.keySet()) {
                System.out.println("key= "+ key + " and value= " + HRADERS.get(key));
                httpPost.addHeader(key, HRADERS.get(key));
            }

            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 接口返回得String类型得json用来解析返回Map
    public Map jsonUtil(String json) {
        JSONObject obj = JSONObject.parseObject(json);
        Map mapjson = new HashMap<>();

        for (String str : obj.keySet()) {
            mapjson.put(str, obj.get(str));
        }
        return mapjson;
    }

    public static void main(String[] args) {
        Map headers = new HashMap<>();
        String str = get("接口的url");
        HttpRequestUtil hru = new HttpRequestUtil();

        for(Object key :(hru.jsonUtil(str).keySet())) {
            if("code".equals(key)) {
                System.out.println("value:"+hru.jsonUtil(str).get(key));
            }
        }

    }

}
