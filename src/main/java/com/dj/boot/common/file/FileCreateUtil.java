package com.dj.boot.common.file;

import com.dj.boot.common.excel.exc.ExcelType;
import com.dj.boot.common.util.date.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * 创建临时文件
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-07-22-14-36
 */
@Slf4j
public class FileCreateUtil {

    public static final String TMP_DIR;
    public static final String SUFFIX = "-";

    static {
        TMP_DIR = "/home/download/temp/workbench/";
    }

    /**
     * 创建临时文件
     *
     * @param
     */
    public static File fileExcelCreate(String namePre) {
        String time = String.valueOf(System.currentTimeMillis());
        String time1 = DateUtils.timeNow();
        //根据生成相应的bean对象
        //拼接文件名称
        String fileName = new StringBuilder(namePre).append(SUFFIX).append(time1).append(ExcelType.XLSX.getType()).toString();
        File file = new File(TMP_DIR);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        //根据目录和文件名生成文件
        File fileTemp = new File(file, fileName);
        if (!fileTemp.exists()) {
            try {
                if (fileTemp.createNewFile()) {
                    log.info("【文件工具类】创建文件成功!" + fileName);
                } else {
                    log.info("【文件工具类】创建文件成失败!" + fileName);
                }
            } catch (IOException e) {
                log.error("【文件工具类】创建文件失败：!" + file, e);
                throw new RuntimeException("【文件工具类】创建文件失败!" + fileName, e);
            }
        }
        return fileTemp;
    }


    /**
     * 删除临时文件
     *
     * @param
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            try {
                if (file.delete()) {
                    log.error("【文件工具类】刪除临时文件成功!" + file.toString());
                } else {
                    log.error("【文件工具类】刪除临时文件失败!" + file.toString());
                }
            } catch (Exception e) {
                log.error("【文件工具类】刪除临时文件失败：!" + file.toString(), e);
                throw new RuntimeException("【文件工具类】刪除临时文件失败：!" + file.toString(), e);
            }

        }
    }

}
