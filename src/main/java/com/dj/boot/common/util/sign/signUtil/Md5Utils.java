package com.dj.boot.common.util.sign.signUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密
 */
public class Md5Utils {

    private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * MD5 加密
     *
     * @param value 字符串
     * @return
     */
    public final static String encode(String value) {
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // del ysf 20191120 JCS SCAN start
            //e.printStackTrace();
            logger.error("异常：{}",e);
            // del ysf 20191120 JCS SCAN end
            return null;
        }
        // 使用指定的字节更新摘要
        try {
            mdInst.update(value.getBytes("UTF-8"));
        }catch (Exception e){
            logger.error("转码异常：{}",e);
            throw new RuntimeException("转码异常结束流程"+e.getMessage());
        }
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

}
