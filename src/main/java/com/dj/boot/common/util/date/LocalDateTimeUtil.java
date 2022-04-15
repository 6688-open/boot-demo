package com.dj.boot.common.util.date;

import com.dj.boot.common.util.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util.date
 * @Author: wangJia
 * @Date: 2021-05-28-15-54
 */
public class LocalDateTimeUtil {

    private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";// 默认的年月日
    private static String defaultPattern = "yyyy-MM-dd";// 默认的年月日
    private static String hour24HMPattern = "HH:mm:ss";
    private static String hour12HMPattern = "hh:mm:ss";
    private static String hour24HPattern = "yyyy-MM-dd HH:mm:ss";// 年月日 时分秒 24小时制
    private static String hour12HPattern = "yyyy-MM-dd hh:mm:ss";// 年月日 时分秒 12小时制


    /**
     * 使用指定格式转换Date成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime date, String pattern) {
        String returnValue = "";
        if (date != null && StringUtils.isNotBlank(pattern)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用默认格式转换Date成字符串
     *
     * @param date
     * @return
     */
    public static String format(LocalDateTime date) {
        return format(date, defaultDatePattern);
    }

    /**
     * 使用默认格式将字符串转为Date
     * @param strDate
     * @return
     */
    public static LocalDateTime parse(String strDate){
        return parse(strDate, defaultDatePattern);
    }
    /**
     * 使用指定格式将字符串转为Date
     * @param strDate
     * @param pattern
     * @return
     */
    public static LocalDateTime parse(String strDate, String pattern) {
        return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern));
    }



    @Test
    public void test(){
        LocalDateTime.now();
        String format = LocalDateTimeUtil.format(LocalDateTime.now(), defaultPattern);
        String format1 = LocalDateTimeUtil.format(LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTimeUtil.parse("2021-05-28 12:12:12");
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println(format);

    }

}
