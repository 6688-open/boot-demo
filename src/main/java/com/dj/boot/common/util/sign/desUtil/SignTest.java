package com.dj.boot.common.util.sign.desUtil;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignTest {

    private static final Logger logger = LoggerFactory.getLogger(SignTest.class);


    public static void main(String[] args) {
        String returnUrl = "";
        String uiCode = "";
        //des 加密密钥 默认
        String desKey = "f+XXXXXX=";
        String signKey = "BkxI----CxFtNm";
        String jdPin = "";
        Map<String, String> data = createdJdPayUriData(jdPin, returnUrl, uiCode, desKey, signKey);
    }


    public static Map<String, String> createdJdPayUriData(String pin, String returnUrl, String uiCode, String desKey, String signKey) {
        Map<String, String> map = Maps.newHashMap();

        String merchantNo = "00001";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24MMSS");
        String date = sdf.format(new Date(System.currentTimeMillis()));

        // 基础信息
        map.put("customer_no", merchantNo);
        map.put("request_datetime", date);
        // 业务信息
        map.put("ui_code", uiCode);
        map.put("out_customer_code", pin);
        map.put("customer_type", "person");
        if (StringUtils.isNotEmpty(returnUrl)) {
            map.put("return_url", returnUrl);
        }
        // 业务信息-风控信息
        HashMap<String, String> jeepInfo = Maps.newHashMap();
        jeepInfo.put("device_type", "Nexus One");
        jeepInfo.put("ip", "127.0.0.1");
        jeepInfo.put("device_id", UUID.randomUUID().toString());
        jeepInfo.put("imei", UUID.randomUUID().toString());
        map.put("jeep_info", JSON.toJSONString(jeepInfo));
        // 业务信息-扩展
        HashMap<String, String> externalInfo = Maps.newHashMap();
        externalInfo.put("name", "");
        externalInfo.put("certno", "");
        externalInfo.put("phone", "");
        externalInfo.put("flag", "");
        map.put("extend_params", JSON.toJSONString(externalInfo));

        String charset = "utf8";
        //sign加密算法  SHA-256
        String algorithm = "SHA-256";
        String sign = null;
        try {
            sign = SignUtils.sign(map, algorithm, signKey, charset);
        } catch (Exception e) {
            logger.error("" , e);
            e.printStackTrace();
        }
        map.put("un_area", "un_area22222");
        String encryptData = DESCoder.encryption(SignUtils.map2LinkString(map), desKey);

        HashMap<String, String> dataMap = Maps.newHashMap();
        dataMap.put("sign_type", algorithm);
        dataMap.put("sign_data", sign);
        dataMap.put("encrypt_data", encryptData);
        //解密
        String link = DESCoder.decryption(encryptData, desKey);
        Map<String, String> original = SignUtils.linkString2Map(link);
        //FlowParamDTO flowParamDTO = (FlowParamDTO) BeanConverter.convertMap2Bean(FlowParamDTO.class, original);
        //logger.info("FlowController>begin>flowParamDTO:" + JSON.toJSONString(flowParamDTO));
        logger.info("FlowController>begin>flowParamDTO:" + JSON.toJSONString(dataMap));

        try {
            if (!SignUtils.verify(sign, original, algorithm, signKey, charset)) {
                System.out.println(1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
