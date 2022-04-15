package com.dj.boot.common.util;

import org.junit.Test;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util
 * @Author: wangJia
 * @Date: 2021-04-28-11-07
 */
public class SignUtils {


    @Test
    public void test () throws Exception {
        String appSecret = "11111111111111xxxxxxxxxxxxxx22222222222";
        TreeMap<String, String> map = new TreeMap<>();
        /*method=task.list（系统参数）
        access_token=TESTACCESSTOKEN（系统参数）
        timestamp=1367819523（系统参数）
        app_key=10011（系统参数）*/
        map.put("method", "list系统参数");
        map.put("abstract_param", "abstract_param");
        map.put("access_token", "ACCESSTOKEN系统参数");
        map.put("timestamp", "1367819523");
        map.put("app_key", "1001001001");
        String sign = sign(map, appSecret);
        System.out.println(sign);
    }

    // 代码一, 签名排序代码.
    // pmap 为所有参数, TreeMap 表示为树形结构的哈希容器
   // appSecret 班牛分配给您的密钥
    private String sign(TreeMap<String, String> pmap, String appSecret) throws Exception {
        StringBuilder sb = new StringBuilder(appSecret);
        Iterator i$ = pmap.entrySet().iterator();
        while (i$.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) i$.next();
            String name = entry.getKey();
            String value = entry.getValue();
            /*if (StringUtils.isNoneEmpty(new String[]{name, value})) {
                sb.append(name).append(value);
            }*/
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                sb.append(name).append(value);
            }
        }
        sb.append(appSecret);
        String result = SignUtils.md5(sb.toString());
        return result;
    }

    // 代码二 md5 加密代码
    public static String md5(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(source.getBytes("utf-8"));
        return byte2hex(bytes);
    }


    //代码三  字节处理
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}
