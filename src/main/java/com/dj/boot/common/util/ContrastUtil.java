package com.dj.boot.common.util;

import java.math.BigDecimal;

/**
 * 事业部上大部分增值服务的标记为Integer或Baty类型，可能存在null的情况
 * 比较时先要判断为空然后再比较值是否相等，阅读性太差
 * 此工具将小类型转换成大类型，进行equals比较
 * To change this template use File | Settings | File Templates.
 */
public class ContrastUtil {

    /**
     * a值等于b？
     */
    public static boolean intContrast(int a, Integer b) {

        return Integer.valueOf(a).equals(b);
    }

    /**
     * a值小于于b？
     */
    public static boolean intContrastLt(int a, Integer b) {
        if (b == null) return Boolean.TRUE;
        return Integer.valueOf(a).compareTo(b) < 0;
    }

    /**
     * a值大于b？
     */
    public static boolean intContrastGt(int a, Integer b) {
        if (b == null) return Boolean.TRUE;
        return Integer.valueOf(a).compareTo(b) > 0;
    }

    /**
     * a值大于b？
     */
    public static boolean bigDecimalContrastGtOrEn(int a, BigDecimal b) {
        if (b == null) return Boolean.TRUE;
        return BigDecimal.valueOf(a).compareTo(b) >= 0;
    }

    /**
     * a值等于b？
     */
    public static boolean byteContrast(String a, Byte b) {
        if (StringUtils.isBlank(a)) {
            return Boolean.TRUE;
        }
        return Byte.valueOf(a).equals(b);
    }

    /**
     * a值等于b？
     */
    public static boolean byteContrast(int a, Byte b) {

        return byteContrast(String.valueOf(a), b);
    }


    public static void main(String[] args) {
        BigDecimal d1 = new BigDecimal(0.6);
        BigDecimal d2 = new BigDecimal(0.4);
        BigDecimal d3 = d1.divide(d2);
        System.out.println(d3);
        System.out.println(new BigDecimal(1.01).multiply(new BigDecimal(-1)));
        System.out.println(new BigDecimal(1.00));
        System.out.println(bigDecimalContrastGtOrEn(0, new BigDecimal(1.00)));
        System.out.println(byteContrast("1", Byte.valueOf(String.valueOf(1))));
        System.out.println(byteContrast(1, null));

        System.out.println(intContrastLt(1, 0));
        System.out.println(intContrastGt(1, -1));
    }
}
