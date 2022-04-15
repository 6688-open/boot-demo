package com.dj.boot.common.util.es;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    // 获取properties文件
    public static Properties loadProperty(String path) {
        Properties prop = new Properties();
        try {
            String class_path = PropertiesUtil.class.getClassLoader().getResource(path).getPath();
            InputStream is = new FileInputStream(class_path);
            prop.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static String getProperty(String propertiesFileName, String key) {
        Properties props = new Properties();
        try {
            props.load(PropertiesUtil.class.getResourceAsStream("/" + propertiesFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) props.get(key);
    }

    /**
     * 更新properties文件属性值
     *
     * @param key
     * @param value
     */
    public static void setProperty(String propertiesFileName, String key, String value) {
        Properties props = new Properties();
        OutputStream os = null;
        try {
            String classRootPath = PropertiesUtil.class.getResource("/").toString();
            if ("Windows".indexOf(System.getProperty("os.name")) != -1)
                classRootPath = classRootPath.replace("file:/", "");
            else
                classRootPath = classRootPath.replace("file:", "");

            props.load(PropertiesUtil.class.getResourceAsStream("/" + propertiesFileName));
            os = new FileOutputStream(classRootPath + propertiesFileName);
            props.put(key, value);
            props.store(os, "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}