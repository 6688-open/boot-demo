package com.dj.boot.common.csv.util;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.csv.util
 * @Author: wangJia
 * @Date: 2021-04-15-10-49
 */
public class SoUtil {

    public SoUtil() {
    }

    public static long joint(long x, long y) {
        StringBuilder sb = new StringBuilder();
        sb = sb.append(x);
        sb = sb.append(y);
        long result = -1L;

        try {
            result = Long.valueOf(sb.toString());
        } catch (NumberFormatException var8) {
        }

        return result;
    }

    public static int joint(int x, int y) {
        StringBuilder sb = new StringBuilder();
        sb = sb.append(x);
        sb = sb.append(y);
        int result = -1;

        try {
            result = Integer.valueOf(sb.toString());
        } catch (NumberFormatException var5) {
        }

        return result;
    }
}
