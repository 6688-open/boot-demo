package com.dj.boot.configuration.okhttp3.sign;


import org.apache.commons.lang3.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * 签名生成工具.
 */
public class KuaiShouSign {

    private static final String CONTENT_ENCODING_GZIP = "gzip";
    private static final String CHARSET_UTF8 = "utf-8";

    public static String signTopRequest(Map<String, Object> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (SignConstants.SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            /*String value = params.get(key);
            if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) ){
                query.append(key).append(value);
            }*/
            query.append(key).append(getIfNotNull(params.get(key)));

        }
        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (SignConstants.SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else if (SignConstants.SIGN_METHOD_HMAC_SHA256.equals(signMethod)){
            bytes = encryptHMACSHA256(query.toString(), secret);
        } else  {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes);
    }

    public static byte[] encryptHMACSHA256(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(SignConstants.CHARSET_UTF8), SignConstants.SIGN_METHOD_HMAC_SHA256);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(SignConstants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(SignConstants.CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(SignConstants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }


    public static byte[] encryptMD5(String data) throws IOException {
        return data.getBytes(SignConstants.CHARSET_UTF8);
    }


    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    private static String getIfNotNull(Object obj) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }


    private static String callApi(URL url, Map<String, String> params) throws IOException {
        String query = buildQuery(params, CHARSET_UTF8);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(CHARSET_UTF8);
        }

        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Host", url.getHost());
            conn.setRequestProperty("Accept", "text/xml,text/javascript");
            conn.setRequestProperty("User-Agent", "top-sdk-java");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET_UTF8);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }
    private static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < 400) {
            String contentEncoding = conn.getContentEncoding();
            if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {// Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }
    private static String getResponseCharset(String ctype) {
        String charset = CHARSET_UTF8;

        if (isNotEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (isNotEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static boolean isNotEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (isNotEmpty(name) && isNotEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }


    public static void main(String[] args) throws Exception {
        String message = "我是HmacSHA256算法";
        byte[] key1 = initHmacSHA256Key();
        String strKey1 = Base64.getEncoder().encodeToString(key1);
        byte[] messageDigest = encodeHmacSHA256(message.getBytes(StandardCharsets.UTF_8), key1);
        String hex = hex(messageDigest);
        System.out.println(hex);

        byte[] key2 = Base64.getDecoder().decode(strKey1.getBytes());
        byte[] messageDigest2 = encodeHmacSHA256(message.getBytes(StandardCharsets.UTF_8), key2);
        String hex2 = hex(messageDigest2);
        System.out.println(hex2);

    }


    public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {
        //初始化KeyGenerator密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(SignConstants.SIGN_METHOD_HMAC_SHA256);
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获取密钥
        byte[] encoded = secretKey.getEncoded();
        return encoded;
    }


    public static byte[] encodeHmacSHA256(byte[] data, byte [] key ) throws Exception {
        //还原密钥
        SecretKey secretKey = new SecretKeySpec(key, SignConstants.SIGN_METHOD_HMAC_SHA256);
        //实例化MAC
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化MAC
        mac.init(secretKey);
        //返回信息摘要
        return mac.doFinal(data);
    }

    public static String hex(byte[] data) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (byte b: data) {
            String str = Integer.toHexString(b & 0xff);
            if (str.length() <2) {
                sb.append("0"+str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }





}
