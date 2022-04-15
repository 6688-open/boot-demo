package com.dj.boot.common.csv;


import com.dj.boot.common.csv.enums.StatusEnumInter;
import com.dj.boot.common.csv.enums.StatusEnums;
import com.dj.boot.common.util.date.DateUtil;
import com.dj.boot.controller.base.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unchecked")
public class CsvExportUtil {
    protected static final Logger log = LoggerFactory.getLogger(CsvExportUtil.class);
    public static int MAX_NUM = 10000; //每次读取一万条
    public static int MAX_TOTAL_NUM_LIMIT = 1000000;
    private static final Map errStatusMap = new HashMap();
    static {
        //errStatusMap.put(StatusEnums.SOERR_ONE_0.getKey(), SoCollectEnumUtil.encodeEnumType("StatusEnums",""+StatusEnums.SOERR_ONE_0.getCode()).getDesc());
        errStatusMap.put(StatusEnums.SOERR_ONE_0.getKey(), StatusEnumInter.class);
    }
    
    public static <T> void createCSVFile(File tmpFile, Field[] columnFields, String[] fieldNames, List<T> list) {
        BufferedWriter csvFileOutputStream = null;
        try {
            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "GB2312"), 1024);
            //显示标题
            for (int i = 0; i < fieldNames.length-1; i++) {
                csvFileOutputStream.write("\"" + fieldNames[i] + "\",");
            }
            csvFileOutputStream.write("\"" + fieldNames[fieldNames.length-1] + "\"");
            csvFileOutputStream.newLine();

            // 写入文件内容
            addContenRows(list, csvFileOutputStream, columnFields, fieldNames);
            
            csvFileOutputStream.flush();
        } catch (Exception e) { 
            log.error("订单导出失败", e);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("BufferedWriter 关闭异常", e);
            }
        }
    }

    public static <T> void createCSVFileWithSummary(File tmpFile, Field[] columnFields, String[] fieldNames, List<T> list,T summaryObj) {
        BufferedWriter csvFileOutputStream = null;
        try {
            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "GB2312"), 1024);
            //显示标题
            for (int i = 0; i < fieldNames.length-1; i++) {
                csvFileOutputStream.write("\"" + fieldNames[i] + "\",");
            }
            csvFileOutputStream.write("\"" + fieldNames[fieldNames.length-1] + "\"");
            csvFileOutputStream.newLine();

            // 写入文件内容
            addContenRows(list, csvFileOutputStream, columnFields, fieldNames);
            //添加数据汇总
            addSummaryMsg(csvFileOutputStream,columnFields,fieldNames,summaryObj);
            csvFileOutputStream.flush();
        } catch (Exception e) {
            log.error("导出失败", e);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("BufferedWriter 关闭异常", e);
            }
        }
    }

    /**
     * excel中添加数据汇总行
     * @param csvFileOutputStream
     * @param columnFields
     * @param fieldNames
     * @param summaryObj
     * @param <T>
     */
    private static <T> void addSummaryMsg(BufferedWriter csvFileOutputStream, Field[] columnFields, String[] fieldNames,T summaryObj) {
        if (summaryObj != null) {
            try {
                for (int j = 0; j < fieldNames.length; j++) {
                    Object field = columnFields[j].get(summaryObj);
                    if(field == null) {
                        field = "";
                    }
                    csvFileOutputStream.write("\""+ field + "\",");
                }
                csvFileOutputStream.newLine();

            } catch (Exception e) {
                log.error("订单导出失败", e);
            }
        }
    }
    
    private static <T> void addContenRows(List<T> list, BufferedWriter csvFileOutputStream, Field[] columnFields, String[] fieldNames) {
        if (!CollectionUtils.isEmpty(list)) {
            try {
                //实体类处理
                for (int i = 0; i < list.size(); i++) {
                     T data = list.get(i);
                     for (int j = 0; j < fieldNames.length; j++) {
                         Object field = columnFields[j].get(data);
                         //判断值是否为空
                         if ("soErrStatus".equals(columnFields[j].getName())) {
                             String soErrStatus = String.valueOf(field);
                             field = errStatusMap.get(StatusEnums.SOERR_ONE_0.getKey());
                         } else if(null == field){
                             field = "";
                         } else if (field instanceof Date){
                             field = DateUtil.format((Date) field, "yyyy-MM-dd HH:mm:ss");
                         }

                         //写入，已英文,分割
                         if (j == fieldNames.length - 1) {
                            csvFileOutputStream.write("\""+ field + "\"");
                         } else if ("spSoNo".equals(columnFields[j].getName())) {
                            csvFileOutputStream.write("=\"" + field+ "\",");
                         } else {
                            csvFileOutputStream.write("\"" + field+ "\",");
                         }
                     }
                     csvFileOutputStream.newLine();
                }
            } catch (Exception e) {
                log.error("导出失败", e);
            }
        }
    }
    
    public static <T> void appendCSVFile(File tmpFile, Field[] columnFields, String[] fieldNames, List<T> list) {
        BufferedWriter csvFileOutputStream = null;
        try {
            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile, true), "GB2312"), 1024);
            
            // 写入文件内容
            addContenRows(list, csvFileOutputStream, columnFields, fieldNames);
            
            csvFileOutputStream.flush();
        } catch (Exception e) { 
            log.error("导出失败", e);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("BufferedWriter 关闭异常", e);
            }
        }
    }
    
    /**
     * 实体类处理
     * @param <T>
     * @param fieldNames
     * @param fieldClass
     * @return
     * @throws NoSuchFieldException
     */
    public static <T> Field[] createColumnFileds(String[] fieldNames, Class<T> fieldClass) throws NoSuchFieldException {
        Field[] fields = new Field[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            Field field = fieldClass.getDeclaredField(fieldNames[i]);
            field.setAccessible(true);
            fields[i] = field;
        }
        return fields;
    }

    public static void writeResponse(File file, OutputStream os) {
        try { 
            FileInputStream fis = new FileInputStream(file); 
            byte[] b = new byte[1024];
            int i = 0; 
            while ( (i = fis.read(b)) > 0 ) {  
                os.write(b, 0, i); 
            } 
            fis.close(); 
            os.flush(); 
        }catch ( Exception e ){ 
            log.error("导出失败", e);
        }
    }

    public static void writeResponseStream(InputStream is, OutputStream os) {
        try {
            byte[] b = new byte[1024];
            int i = 0;
            while ( (i = is.read(b)) > 0 ) {
                os.write(b, 0, i);
            }
            os.flush();
        }catch ( Exception e ){
            log.error("导出失败", e);
        }
    }

    public static void writeResponse(InputStream is, OutputStream os) {
        try {
            byte[] b = new byte[1024];
            int i = 0;
            while ( (i = is.read(b)) > 0 ) {
                os.write(b, 0, i);
            }
            os.flush();
        }catch ( Exception e ){
            log.error("导出失败", e);
        }
    }

    /**
     * 导出设置参数
     * @param response
     * @param fileName
     */
    public static void setResponseParam(HttpServletResponse response, String fileName) {
        response.setContentType("application/csv;charset=GBK");
        fileName = fileName + ".csv";
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(new String(fileName.getBytes("GBK"),"iso8859-1")));
        } catch (UnsupportedEncodingException e) {
        }
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/csv");
    }

    /**
     * 通过数据流的方式导出csv文件，本方法只是适用于导出数据条数比较少的情况
     * @param response
     * @param fieldColumnsEN
     * @param fieldColumnsCN
     * @param fileName
     * @param dataList
     * @throws Exception
     */
    public static void exportCsvFileByStream(HttpServletResponse response, String[] fieldColumnsEN, String[] fieldColumnsCN, String fileName, List<Map<String, String>> dataList) throws Exception {
        if (fieldColumnsCN == null || fieldColumnsCN.length == 0) {
            return;
        }
        BufferedWriter csvFileOutputStream = null;
        try {

            setResponseParam(response, fileName);
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "GBK"), 1024);

            //写入文件标题
            int columnLen = fieldColumnsCN.length;
            for (int i = 0; i < columnLen - 1; i++) {
                csvFileOutputStream.write("\"" + fieldColumnsCN[i] + "\",");
            }
            csvFileOutputStream.write("\"" + fieldColumnsCN[columnLen - 1] + "\"");
            csvFileOutputStream.newLine();

            if (CollectionUtils.isEmpty(dataList) || fieldColumnsEN == null || fieldColumnsEN.length == 0) {
                csvFileOutputStream.flush();
                return;
            }
            // 写入文件内容
            try {
                for (Map<String,String> data : dataList) {
                    for (int j = 0; j < columnLen; j++) {
                        String value = data.get(fieldColumnsEN[j]);
                        //写入，已英文,分割
                        if (j == columnLen - 1) {
                            csvFileOutputStream.write("\""+ value + "\"");
                        } else {
                            csvFileOutputStream.write("\"" + value+ "\",");
                        }
                    }
                    csvFileOutputStream.newLine();
                }
            } catch (Exception e) {
                log.error("数据导出异常!", e);
            }

            csvFileOutputStream.flush();
        } catch (Exception e) {
            log.error("数据导出异常!", e);
            throw new RuntimeException("数据导出异常!");
        } finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("BufferedWriter关闭异常", e);
            }
        }

    }
}
