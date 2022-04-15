package com.dj.boot.configuration.okhttp3.sign;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5SignUtil {
    public static byte[] getMD5Mac(byte[] bySourceByte) {
        byte[] byDisByte;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bySourceByte);
            byDisByte = md.digest();
        } catch (NoSuchAlgorithmException n) {

            return (null);
        }
        return (byDisByte);
    }

    public static String bintoascii(byte[] bySourceByte) {
        int len, i;
        byte tb;
        char high, tmp, low;
        String result = new String();
        len = bySourceByte.length;
        for (i = 0; i < len; i++) {
            tb = bySourceByte[i];
            tmp = (char) ((tb >>> 4) & 0x000f);
            if (tmp >= 10)
                high = (char) ('a' + tmp - 10);
            else
                high = (char) ('0' + tmp);
            result += high;
            tmp = (char) (tb & 0x000f);
            if (tmp >= 10)
                low = (char) ('a' + tmp - 10);
            else
                low = (char) ('0' + tmp);
            result += low;
        }
        return result;
    }

    public static String getMD5ofStr(String inbuf) {
        String bbb = "";
        try {
            byte[] aaaa = inbuf.getBytes("GBK");
            if (inbuf == null || "".equals(inbuf)) return "";
            bbb = bintoascii(getMD5Mac(aaaa));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bbb;
    }

    public static String getMD5ofStrUTF8(String inbuf) {
        String bbb = "";
        try {
            byte[] aaaa = inbuf.getBytes("UTF-8");
            if (inbuf == null || "".equals(inbuf)) return "";
            bbb = bintoascii(getMD5Mac(aaaa));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bbb;
    }

    /**
     * 该方法用于生成签名，参数inbuf=签名种子+xml字符串
     *
     * @param inbuf
     * @return
     */
    public static String getSign(String inbuf) {
        return getMD5ofStr(inbuf).toLowerCase();
    }

    public static String getSignUTF8(String inbuf) {
        return getMD5ofStrUTF8(inbuf).toLowerCase();
    }
    /**
     * md5加密+base64加密
     */
    public static String getTbBillSign(String originStr){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(originStr.getBytes("UTF-8"));
            byte[] digestMD5 = messageDigest.digest();
            return new String(Base64.encodeBase64(digestMD5));
        } catch (Exception e) {

        }
        return null;
    }
    /**
     * md5摘要加密
     */
    public static String getTikTokSign(String plainText){
        byte[] secretBytes = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            secretBytes = messageDigest.digest(plainText.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持md5这个算法！");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持UTF-8编码格式！");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        while (md5code.length() < 32) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}
