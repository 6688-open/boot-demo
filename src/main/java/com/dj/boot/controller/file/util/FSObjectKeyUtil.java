package com.dj.boot.controller.file.util;

import com.dj.boot.common.file.FileUtil;
import com.dj.boot.common.util.date.DateUtil;

import java.util.Date;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.file.util
 * @Author: wangJia
 * @Date: 2021-05-26-14-27
 */
public class FSObjectKeyUtil {

    public static String FS_IMAGE_BUCKET_NAME="FS_IMAGE_BUCKET_NAME";
    public static String FS_FILE_BUCKET_NAME="FS_FILE_BUCKET_NAME";
    public static String FS_END_POINT ="FS_END_POINT";
    public static String FS_DOMAIN = "FS_DOMAIN";
    public static String FS_ACCESS_KEY = "FS_ACCESS_KEY";
    public static String FS_SECRET_KEY = "FS_SECRET_KEY";
    public static int FS_CONNECTION_TIMEOUT ;


    public FSObjectKeyUtil() {
    }

    public static String generateDateObjectKey(String fileName) {
        String strDate = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
        return strDate + "/" + MD5Util.md5Hex(fileName) + "." + FileUtil.getFileType(fileName);
    }

    public static String generateObjectKey(String fileName, String uuid) {
        String strDate = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
        return strDate + "/" + uuid + "." + FileUtil.getFileType(fileName);
    }

    public static String generateDateObjectKeyWithThumbnail(String fileName) {
        String strDate = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
        return strDate + "/thumb/" + MD5Util.md5Hex(fileName) + "." + FileUtil.getFileType(fileName);
    }

    public static String generateObjectKeyWithThumbnail(String fileName, String uuid) {
        String strDate = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
        return strDate + "/thumb/" + uuid + "." + FileUtil.getFileType(fileName);
    }

    public static String parseObjectKey(String url) {
        int index = url.indexOf(FS_IMAGE_BUCKET_NAME) + FS_IMAGE_BUCKET_NAME.length() + 1;
        String objectKey = url.substring(index);
        return objectKey;
    }

    public static String getObjectURL(String objectKey) {
        return "http://" + FS_DOMAIN + "/" + FS_FILE_BUCKET_NAME + "/" + objectKey;
    }

    public static String getObjectLocalURL(String objectKey) {
        return "http://" + FS_END_POINT + "/" + FS_FILE_BUCKET_NAME + "/" + objectKey;
    }
}
