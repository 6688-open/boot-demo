package com.dj.boot.common.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util.file
 * @Author: wangJia
 * @Date: 2021-01-22-15-21
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static final  Integer FILE_SIZE = 2;
    public static final  String FILE_UNIT_B = "B";
    public static final  String FILE_UNIT_K = "K";
    public static final  String FILE_UNIT_M = "M";
    public static final  String FILE_UNIT_G = "G";

    // 定义可上传文件扩展名白名单
    final static String[] suffixList = new String[]{"jpg","jpeg","png","doc","docx","xls","xlsx","mp3","mp4","ppt","pptx","pdf","txt"};

    private FileUtil() {
    }

    public static String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    public static boolean verifyFileSuffix(String fileType){
        // 获取上传文件扩展名
        boolean suffixCheck = Arrays.asList(suffixList).contains(fileType);
        if (!suffixCheck){
            logger.error("禁止上传此类型的文件");
            return false;
        }
        return true;
    }

    public static boolean verifyFileSize(Long len, int size, String unit){
        double fileSize = 0;
        if (FILE_UNIT_B.equals(unit)) {
            fileSize = (double)len;
        } else if (FILE_UNIT_K.equals(unit)) {
            fileSize = (double)len/1024;
        } else if (FILE_UNIT_M.equals(unit)) {
            fileSize = (double)len/1048576;
        } else if (FILE_UNIT_G.equals(unit)) {
            fileSize = (double)len/1073741824;
        }
        return !(fileSize>size);
    }


    public static void main(String[] args) {
        String fileName = "20210122.doc";
        String fileType = getFileType(fileName);
        boolean b = verifyFileSuffix(fileType);
        logger.info("文件名称：{},校验结果:{}", fileName,b);
    }
}
