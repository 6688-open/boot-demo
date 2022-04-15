package com.dj.boot.common.util.es;

import com.dj.boot.common.util.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EsClientWbMain {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(EsClientWbMain.class);

    private static Map<HttpHost, Integer> aliveHosts = new ConcurrentHashMap<HttpHost, Integer>();

    private final static String EXPIRE_KEY;

    private final static String EXPIRE_FIELD = "HOST";

    private final static String TIMESTAMP_FIELD = "time";

    private final static String ES_USERNAME;

    private final static String ES_PASSWORD;

    private static final String SecurityKey = "Authorization";//固定值

    /**
     * 初始化MAP
     * key 为线上节点ip与端口，192为测试环境
     * value  为是否可用 0为不可 1为可用
     */
    static {

        String ips =  PropertiesUtil.getProperty("prop/ips.properties","ipuser");
        ES_USERNAME = PropertiesUtil.getProperty("prop/ips.properties","usernameuser");
        ES_PASSWORD = PropertiesUtil.getProperty("prop/ips.properties","passworduser");
        log.error("WB加载了鉴权信息:"+ES_USERNAME+"_"+ES_PASSWORD+",ips:"+ips);
        String expireKey = "_WB_ES_RE_TEST";
        String hostIp = "";
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
            log.error("EsClientWB获得本机IPv4_IP："+hostIp);
        } catch (UnknownHostException e) {
            log.error("EsClientWB获取本机ip失败！",e);
            e.printStackTrace();
        }
        EXPIRE_KEY = hostIp+expireKey;
        log.error("EsClientWB获得expireKey："+EXPIRE_KEY);
        // String ips = p.getProperty("ip");
        for (String ip : ips.split(",")) {
            String ip_port[] = ip.split(":");
            HttpHost host = new HttpHost(ip_port[0], Integer.valueOf(ip_port[1]), "http");
            boolean alive = false;
            try {
                alive = testHeart(host);
            } catch (MalformedURLException e) {
                log.error("http地址配置有问题，请纠正",e);
            } catch (Exception e){
                log.error("http地址调用出现异常",e);
            }
            aliveHosts.put(host,1);
        }

        log.error("EsClientWB初始化了节点:"+ JsonUtil.toJson(aliveHosts));
    }

    private static String requestHttpHost(HttpHost host, HttpMethod httpMethod, String uri, String params) throws Exception {
        if(host == null){
            throw new RuntimeException("请求服务host不能为空");
        }
        URL url = new URL("http://"+host.getHostName()+":"+host.getPort());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(StringUtils.isNotBlank(ES_USERNAME) && StringUtils.isNotBlank(ES_PASSWORD)) {
            String key =  EsmUtils.basicAuthHeaderValue(ES_USERNAME, ES_PASSWORD);
            log.error("urlWb:"+url.toString()+",设置了SecurityKey:"+key);
            headers.add(SecurityKey, key);
        }
        HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
        try {
            ResponseEntity<String> result = restTemplate.exchange(
                    url.toString()+ (uri == null ? "" : uri),
                    httpMethod,
                    formEntity,
                    String.class);
            log.error("请求es"+url.toString() + "es 返回信息：" + result.getBody());
            return result.getBody();
        } catch (Exception e) {
            log.error("运单es查询发生异常!",e);
            throw e;
        }
    }

    /**
     * 检测心跳,如果可用的返回true
     * */
    private static boolean testHeart(HttpHost key) throws Exception {
        if(StringUtils.isNotBlank(requestHttpHost(key, HttpMethod.GET, null, null))){
            return true;
        }
        return false;
    }

    //暴露的方法
    public static String clientRest(ElasticStrategy strategy, HttpMethod method, String url, String params) throws Exception {
        if (ElasticStrategy.Random == strategy) {
            HttpHost host = luckChoose();
            try {
                return clientRest(host, method, url, params);
            } catch (RestClientException e) {
                log.error(host.toURI()+"调用出现异常",e);
                return clientRest(luckChoose(), method, url, params);
            }
        }else {
            throw new RuntimeException("未知调用策略，目前仅支持Random");
        }
    }

    private static String clientRest(HttpHost hh, HttpMethod method, String url, String params) throws Exception {
        try {
            return requestHttpHost(hh,method,url,params);
        } catch (RestClientException e){
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    //随机策略
    private static HttpHost luckChoose() throws Exception {
        Object[] hosts = aliveHosts.keySet().toArray();
        HttpHost host = (HttpHost) hosts[0];
        log.info("命中的服务器：" + host.getHostName() + ":" + host.getPort());
        return host;
    }
}
