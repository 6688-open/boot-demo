package com.dj.boot.controller.file.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.file.util
 * @Author: wangJia
 * @Date: 2021-05-26-14-29
 */
public class MD5Util {
    private MD5Util() {
    }

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException(var1);
        }
    }

    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    //@SuppressFBWarnings({"DM_DEFAULT_ENCODING"})
    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
}
