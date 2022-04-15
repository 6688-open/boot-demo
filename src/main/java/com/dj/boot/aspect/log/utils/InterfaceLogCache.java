package com.dj.boot.aspect.log.utils;


import com.dj.boot.aspect.log.output.InterfaceLog;

/**
 * 基于线程
 * Date: 2018-11-15
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceLogCache {

    private static ThreadLocal<InterfaceLog> interfaceLogThreadLocal = new ThreadLocal<InterfaceLog>() {
        @Override
        protected InterfaceLog initialValue() {
            return new InterfaceLog();
        }
    };

    private InterfaceLogCache() {
    }

    public static void print(String bizNo, String interfaceName, Object respInfo, Object... reqInfo) {
        interfaceLogThreadLocal.get().upAttrAndPrint(bizNo, interfaceName, InterfaceLog.DEFAULT, respInfo, reqInfo);
    }

    public static void printLog(String bizNo, String interfaceName, long timeInterval, Object respInfo, Object... reqInfo) {
        interfaceLogThreadLocal.get().upAttrAndPrint(bizNo, interfaceName, String.valueOf(timeInterval), respInfo, reqInfo);
    }

    private static InterfaceLog get() {
        return interfaceLogThreadLocal.get();
    }

}
